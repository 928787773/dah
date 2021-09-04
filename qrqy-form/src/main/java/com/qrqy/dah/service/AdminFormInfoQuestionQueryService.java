package com.qrqy.dah.service;

import com.qrqy.dah.dto.*;
import com.qrqy.mysql.entity.FormQuestionOptionEntity;
import com.qrqy.mysql.entity.FormQuestionOptionRelyEntity;
import com.qrqy.mysql.manager.FormQuestionOptionManager;
import com.qrqy.mysql.repository.FormQuestionOptionRelyRepository;
import com.qrqy.mysql.repository.FormQuestionOptionRepository;
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
import com.qrqy.mysql.entity.FormQuestionEntity;
import com.qrqy.mysql.manager.FormQuestionManager;
import com.qrqy.dah.qo.FormQuestionQueryQO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : admin-form-info-question-query
 *
 * @author : QRQY
 * @date : 2021-06-28 14:04
 */
@Service
@Slf4j
@Validated
@Api(value = "题目详情", tags = {"管理端", "问卷", "这里放筛选标签"})
public class AdminFormInfoQuestionQueryService implements IBaseService<FormQuestionQueryQO> {
    @Autowired
    private FormQuestionOptionManager formQuestionOptionManager;

    @Autowired
    private FormQuestionOptionRelyRepository formQuestionOptionRelyRepository;


    @Autowired
    private FormQuestionManager formQuestionManager;

    @Autowired
    private FormQuestionOptionRepository formQuestionOptionRepository;



    @Override
    public ICommonResult execute(@Valid FormQuestionQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);


        Specification query = new BaseMysqlQuery(qo).append("status", "1")
                .orderBy("sort", Sort.Direction.ASC).orderBy("id", Sort.Direction.DESC);

        Page<FormQuestionEntity> page = formQuestionManager.query(query, qo.getPageable(), curUser);

        List<FormQuestionListDetailDTO> content = page.getContent().stream().map(t -> {
            FormQuestionListDetailDTO dto = new FormQuestionListDetailDTO();
            BeanUtils.copyProperties(t, dto);

            //查看选项
            Specification queryOption = new BaseMysqlQuery().append("status", "1").append("formQuestionId", dto.getId())
                    .orderBy("sort", Sort.Direction.ASC);

            List<FormInfoQuestionQueryOptionListDTO> formQuestionOptionList = formQuestionOptionRepository.findAll(queryOption);
            dto.setFormQuestionOptionList(formQuestionOptionList);

            //查询父级问题
            FormQuestionOptionRelyDetailDTO formQuestionOptionRelyDetail= this.getFormQuestionOptionRelyDetail(dto.getId());
            dto.setFormQuestionOptionRelyDetail(formQuestionOptionRelyDetail);

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
     * 获取关联问题
     * @param formQuestionId
     * @return
     */
    public FormQuestionOptionRelyDetailDTO getFormQuestionOptionRelyDetail(Integer formQuestionId){
        FormQuestionOptionRelyDetailDTO dto = new FormQuestionOptionRelyDetailDTO();

        Specification query = new BaseMysqlQuery().append("status", "1")
                .append("childFormQuestionId", formQuestionId);

        List<FormQuestionOptionRelyEntity> beanOptionalList = formQuestionOptionRelyRepository.findAll(query);

        List<Integer> parentFormQuestionOptionIdArray = new ArrayList<>();
        List<String> parentFormQuestionOptionCodeArray = new ArrayList<>();
        if(beanOptionalList.size()>0){
            //父级问题id只有一个
            Integer parentFormQuestionId = beanOptionalList.get(0).getParentFormQuestionId();

            //取出所有父级选项id
            List<Integer> parentFormQuestionOptionIdList = beanOptionalList.stream().map(FormQuestionOptionRelyEntity::getParentFormQuestionOptionId).collect(Collectors.toList());

            FormQuestionEntity parentFormQuestion = formQuestionManager.get(parentFormQuestionId);
            if(parentFormQuestion != null && parentFormQuestion.getStatus().equals("1")){
                FormQuestionListDTO parentDto = new FormQuestionListDTO();
                BeanUtils.copyProperties(parentFormQuestion, parentDto);
                //查看选项
                Specification queryOption = new BaseMysqlQuery()
                        .append("status", "1").append("formQuestionId", parentFormQuestion.getId())
                        .orderBy("sort", Sort.Direction.ASC);

                List<FormQuestionOptionEntity> parentFormQuestionOptionList = formQuestionOptionRepository.findAll(queryOption);

                List<FormQuestionOptionListDTO> resultParentFormQuestionOptionList = parentFormQuestionOptionList.stream().map(parentFormQuestionOption -> {
                    FormQuestionOptionListDTO parentFormQuestionOptionListDTO = new FormQuestionOptionListDTO();
                    BeanUtils.copyProperties(parentFormQuestionOption, parentFormQuestionOptionListDTO);

                    if(parentFormQuestionOptionIdList.contains(parentFormQuestionOption.getId())){
                        parentFormQuestionOptionCodeArray.add(parentFormQuestionOption.getCode());
                        parentFormQuestionOptionIdArray.add(parentFormQuestionOption.getId());
                    }

                    return parentFormQuestionOptionListDTO;
                }).collect(Collectors.toList());

                parentDto.setFormQuestionOptionList(resultParentFormQuestionOptionList);

                dto.setParentFormQuestion(parentDto);
                dto.setParentFormQuestionOptionIdArray(parentFormQuestionOptionIdArray);
                dto.setParentFormQuestionOptionCodeArray(parentFormQuestionOptionCodeArray);
            }


        }
        return dto;
    }


}
