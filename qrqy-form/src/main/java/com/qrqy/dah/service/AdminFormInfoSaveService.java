package com.qrqy.dah.service;

import com.alibaba.fastjson.JSONObject;
import com.qrqy.dah.qo.FormQuestionEditQO;
import com.qrqy.dah.qo.FormQuestionOptionEditQO;
import com.qrqy.dah.qo.FormQuestionOptionRelyEditQO;
import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.*;
import com.qrqy.mysql.enumeration.FormQuestionAttributeEnum;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import com.qrqy.mysql.manager.*;
import com.qrqy.mysql.repository.*;
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
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.qrqy.dah.qo.FormInfoEditQO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * route : admin-form-info-save
 *
 * @author : QRQY
 * @date : 2021-06-17 16:34
 */
@Service
@Slf4j
@Validated
@Transactional
@Api(value = "添加或编辑问卷信息", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminFormInfoSaveService implements IBaseService<FormInfoEditQO> {
    @Autowired
    private FormInfoManager manager;

    @Autowired
    private FormLevelRuleManager formLevelRuleManager;



    @Autowired
    private FormLevelRuleRepository formLevelRuleRepository;


    @Autowired
    private FormInfoRepository formInfoRepository;



    @Override
    public ICommonResult execute(@Valid FormInfoEditQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        FormInfoEntity entity;
        FormLevelRuleEntity formLevelRuleEntity;

        Specification queryForm = new BaseMysqlQuery().append("name", qo.getName())
                .append("formCategoryId", qo.getFormCategoryId());

        Optional<FormInfoEntity> beanOptionalForm = formInfoRepository.findOne(queryForm);
        if (beanOptionalForm.isPresent()) {
            FormInfoEntity beanForm = beanOptionalForm.get();
            if (null == qo.getId()) {
                throw new BizException(6002, "问卷名称重复");
            }
            if (null != qo.getId() && beanForm.getId().intValue() != qo.getId()) {
                throw new BizException(6002, "问卷名称重复");
            }
        }

        if (null == qo.getId()) {
            entity = new FormInfoEntity();
            formLevelRuleEntity = new FormLevelRuleEntity();
            entity.setStatus("0");
        } else {
            entity = manager.get(qo.getId());
            Specification query = new BaseMysqlQuery().append("formInfoId", qo.getId());

            Optional<FormLevelRuleEntity> beanOptional = formLevelRuleRepository.findOne(query);
            formLevelRuleEntity = beanOptional.get();
        }
        BeanUtils.copyProperties(qo, entity, "status");
        manager.save(entity, curUser);

        //保存满意等级
        formLevelRuleEntity.setStatus("1");
        formLevelRuleEntity.setFormInfoId(entity.getId());
        formLevelRuleEntity.setLevel(qo.getFormLevelRule().getLevel());
        formLevelRuleEntity.setStartScore(qo.getFormLevelRule().getStartScore());
        formLevelRuleManager.save(formLevelRuleEntity, curUser);


        return new CommonObjectResult<>(entity);

    }


}
