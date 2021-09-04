package com.qrqy.dah.service;

import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.*;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import com.qrqy.mysql.repository.FormLevelRuleRepository;
import com.qrqy.mysql.repository.FormQuestionOptionRepository;
import com.qrqy.mysql.repository.FormQuestionRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.qrqy.mysql.manager.FormInfoManager;
import com.qrqy.dah.qo.FormInfoIdQO;
import com.qrqy.dah.dto.FormInfoDetailDTO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * route : admin-form-info-get
 *
 * @author : QRQY
 * @date : 2021-06-24 09:37
 */

@Service
@Slf4j
@Validated
@Api(value = "获取问卷详情", tags = {"管理端", "问卷", "这里放筛选标签"})
public class AdminFormInfoGetService implements IBaseService<FormInfoIdQO> {
    @Autowired
    private FormInfoManager manager;

    @Autowired
    private FormLevelRuleRepository formLevelRuleRepository;

    @Autowired
    private FormQuestionOptionRepository formQuestionOptionRepository;

    @Autowired
    private FormQuestionRepository formQuestionRepository;






    @Override
    public ICommonResult execute(@Valid FormInfoIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        FormInfoEntity entity = manager.get(qo.getId());
        FormInfoDetailDTO dto = new FormInfoDetailDTO();
        BeanUtils.copyProperties(entity, dto);

        //1查询满意分数
        BigDecimal formLevelRuleScore = this.getFormLevelRuleScore(qo.getId());
        dto.setFormLevelRuleScore(formLevelRuleScore);


        //2查询最高得分
        BigDecimal maxTotalSore = new BigDecimal(0);
        Specification query = new BaseMysqlQuery()
                .append("typeNeq", FormQuestionTypeEnum.FILLIN)
                .append("status", "1")
                .append("formInfoId", entity.getId());

        List<FormQuestionEntity> formQuestionEntityList = formQuestionRepository.findAll(query);
        if(formQuestionEntityList.size() >0){
            //2.1多选
            List<Integer> formQuestionIdList= formQuestionEntityList.stream().filter(formQuestion->formQuestion.getType().equals(FormQuestionTypeEnum.MULTIPLECHOICE)).map(FormQuestionEntity::getId).collect(Collectors.toList());

            if(formQuestionIdList.size()>0){
                Specification query1 = new BaseMysqlQuery()
                        .append("status", "1")
                        .append("formQuestionIdIn", formQuestionIdList);

                List<FormQuestionOptionEntity> formQuestionOptionEntities = formQuestionOptionRepository.findAll(query1);
                BigDecimal sore = new BigDecimal(formQuestionOptionEntities.stream().mapToInt(t->t.getScore().intValue()).sum());
                maxTotalSore = maxTotalSore.add(sore);
            }


            //2.2单选和评分
            List<Integer> formQuestionIdList1= formQuestionEntityList.stream().filter(formQuestion->!formQuestion.getType().equals(FormQuestionTypeEnum.MULTIPLECHOICE)).map(FormQuestionEntity::getId).collect(Collectors.toList());

            if(formQuestionIdList1.size() > 0){
                Specification query2 = new BaseMysqlQuery()
                        .append("status", "1")
                        .append("formQuestionIdIn", formQuestionIdList1);

                List<FormQuestionOptionEntity> formQuestionOptionEntities2 = formQuestionOptionRepository.findAll(query2);
                Map<Integer, List<FormQuestionOptionEntity>> questionIdMap = formQuestionOptionEntities2.stream().collect(Collectors.groupingBy(FormQuestionOptionEntity::getFormQuestionId));

                List<FormQuestionOptionEntity> seriesDataArray= new ArrayList<>();
                formQuestionIdList1.forEach(t->{
                    if (questionIdMap.containsKey(t)) {
                        FormQuestionOptionEntity sore1 = questionIdMap.get(t).stream().max(Comparator.comparing(FormQuestionOptionEntity::getScore)).get();
                        seriesDataArray.add(sore1);
                    }

                });
                maxTotalSore = maxTotalSore.add(new BigDecimal(seriesDataArray.stream().mapToInt(t->t.getScore().intValue()).sum()));
            }



        }
        dto.setMaxTotalSore(maxTotalSore);

        return new CommonObjectResult<>(dto);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormInfoIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }

    public BigDecimal getFormLevelRuleScore(Integer formInfoId){
        BigDecimal formLevelRuleScore = null;
        Specification query = new BaseMysqlQuery().append("status", "1")
                .append("formInfoId", formInfoId);

        Optional<FormLevelRuleEntity> beanOptional = formLevelRuleRepository.findOne(query);
        if (beanOptional.isPresent()) {
            FormLevelRuleEntity bean = beanOptional.get();
            formLevelRuleScore = bean.getStartScore();
        }
        return formLevelRuleScore;
    }


}
