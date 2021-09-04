package com.qrqy.dah.service;

import com.qrqy.dah.dto.FormQuestionOptionListDTO;
import com.qrqy.mysql.entity.*;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import com.qrqy.mysql.repository.FormQuestionOptionRelyRepository;
import com.qrqy.mysql.repository.FormQuestionOptionRepository;
import com.qrqy.mysql.repository.FormQuestionRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.qrqy.mysql.manager.FormQuestionManager;
import com.qrqy.dah.qo.FormQuestionQueryQO;
import com.qrqy.dah.dto.FormQuestionListDTO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : admin-form-question-query
 *
 * @author : QRQY
 * @date : 2021-06-21 11:07
 */
@Service
@Slf4j
@Validated
@Api(value = "问卷问题", tags = {"管理端", "问卷", "这里放筛选标签"})
public class AdminFormQuestionQueryService implements IBaseService<FormQuestionQueryQO> {
    @Autowired
    private FormQuestionManager manager;

    @Autowired
    private FormQuestionOptionRepository formQuestionOptionRepository;

    @Autowired
    private FormQuestionRepository formQuestionRepository;



    @Autowired
    private FormQuestionOptionRelyRepository formQuestionOptionRelyRepository;




    @Override
    public ICommonResult execute(@Valid FormQuestionQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        Specification query = new BaseMysqlQuery(qo).append("level", 0)
                .orderBy("sort", Sort.Direction.ASC).orderBy("id", Sort.Direction.DESC);

        Page<FormQuestionEntity> page = manager.query(query, qo.getPageable(), curUser);

        List<FormQuestionListDTO> content = page.getContent().stream().map(t -> {

            FormQuestionListDTO dto = new FormQuestionListDTO();
            BeanUtils.copyProperties(t, dto);

            //查看选项
            if(!dto.getType().equals(FormQuestionTypeEnum.FILLIN)){
                List<FormQuestionOptionListDTO> formQuestionOptionList = this.getFormQuestionOptionList(dto.getId());
                dto.setFormQuestionOptionList(formQuestionOptionList);
            }

            return dto;
        }).collect(Collectors.toList());

        return new CommonPagingResult<>(content, page);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormQuestionQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }

    /**
     *
     * @param formQuestionId
     * @return
     */
    public List<FormQuestionOptionListDTO> getFormQuestionOptionList(Integer formQuestionId){
        //1 该问题的选项
        Specification queryOption = new BaseMysqlQuery().append("status", "1").append("formQuestionId", formQuestionId)
                .orderBy("sort", Sort.Direction.ASC);

        List<FormQuestionOptionEntity> formQuestionOptionList = formQuestionOptionRepository.findAll(queryOption);

        //2 该问题所有选项的补充子集问题
        Specification query = new BaseMysqlQuery().append("parentFormQuestionId", formQuestionId)
                .append("status", "1");
        List<FormQuestionOptionRelyEntity> formQuestionOptionRelyList = formQuestionOptionRelyRepository.findAll(query);

        //2.1 根据父级选项id分组
        Map<Integer, List<FormQuestionOptionRelyEntity>> formQuestionOptionMap = formQuestionOptionRelyList.stream().collect(Collectors.groupingBy(FormQuestionOptionRelyEntity::getParentFormQuestionOptionId));

        //1.1 处理选项格式
        List<FormQuestionOptionListDTO> content = formQuestionOptionList.stream().map(t -> {

            FormQuestionOptionListDTO dto = new FormQuestionOptionListDTO();
            BeanUtils.copyProperties(t, dto);

            //2.2 处理自己问题格式
            if(formQuestionOptionMap.containsKey(dto.getId())){
                List<FormQuestionOptionRelyEntity> formQuestionOptionRelyMapList = formQuestionOptionMap.get(dto.getId());
                //2.2.1 获取所有子级问题id
                List<Integer> childFormQuestionIdList = formQuestionOptionRelyMapList.stream().map(FormQuestionOptionRelyEntity::getChildFormQuestionId).collect(Collectors.toList());

                //2.2.2 获取子级问题
                Specification queryChildFormQuestionList = new BaseMysqlQuery()
                        .append("status", "1")
                        .append("idIn", childFormQuestionIdList)
                        .orderBy("sort", Sort.Direction.ASC);

                List<FormQuestionEntity> childQuestionList = formQuestionRepository.findAll(queryChildFormQuestionList);

                //2.2.3 获取子级问题的选项
                Specification childQuestionOptionGuery = new BaseMysqlQuery()
                        .append("formQuestionIdIn", childFormQuestionIdList)
                        .append("status", "1");
                List<FormQuestionOptionEntity> childQuestionOptionList = formQuestionOptionRepository.findAll(childQuestionOptionGuery);

                //2.2.4 子级问题选项按问题id分组
                Map<Integer, List<FormQuestionOptionEntity>> childFormQuestionOptionMap = childQuestionOptionList.stream().collect(Collectors.groupingBy(FormQuestionOptionEntity::getFormQuestionId));


                //2.2.5 处理子级问题格式
                List<FormQuestionListDTO> itemFormQuestionList = new ArrayList<>();
                childQuestionList.forEach(it->{
                    FormQuestionListDTO childQuestionDto = new FormQuestionListDTO();
                    BeanUtils.copyProperties(it, childQuestionDto);

                    //2.2.5.1 处理子级问题选项的格式
                    if (childFormQuestionOptionMap.containsKey(it.getId())) {
                        List<FormQuestionOptionEntity> childFormQuestionOptionList = childFormQuestionOptionMap.get(it.getId());
                        List<FormQuestionOptionListDTO> result = childFormQuestionOptionList.stream().map(child -> {
                            FormQuestionOptionListDTO questionOptionListDto = new FormQuestionOptionListDTO();
                            BeanUtils.copyProperties(child, questionOptionListDto);
                            return questionOptionListDto;
                        }).collect(Collectors.toList());
                        childQuestionDto.setFormQuestionOptionList(result);
                        itemFormQuestionList.add(childQuestionDto);
                    }

                });


                dto.setItemFormQuestionList(itemFormQuestionList);
            }

            return dto;
        }).collect(Collectors.toList());
        return content;
    }
}
