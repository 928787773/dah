package com.qrqy.dah.service;

import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.repository.FormCategoryRepository;
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
import com.qrqy.mysql.entity.FormCategoryEntity;
import com.qrqy.mysql.manager.FormCategoryManager;
import com.qrqy.dah.qo.FormCategoryEditQO;

import java.util.Optional;

/**
 * route : admin-form-category-save
 *
 * @author : QRQY
 * @date : 2021-06-17 15:33
 */
@Service
@Slf4j
@Validated
@Api(value = "问卷方向添加或编辑", tags = {"管理端", "问卷方向", "这里放筛选标签"})
public class AdminFormCategorySaveService implements IBaseService<FormCategoryEditQO> {
    @Autowired
    private FormCategoryManager manager;
    @Autowired
    private FormCategoryRepository repository;



    @Override
    public ICommonResult execute(@Valid FormCategoryEditQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        FormCategoryEntity entity;
        Specification query = new BaseMysqlQuery().append("name", qo.getName()).append("type", qo.getType());

        Optional<FormCategoryEntity> beanOptional = repository.findOne(query);
        if (beanOptional.isPresent()) {
            FormCategoryEntity bean = beanOptional.get();
            if (null == qo.getId()) {
                throw new BizException(6002, "方向已存在");
            }
            if (null != qo.getId() && bean.getId().intValue() != qo.getId()) {
                throw new BizException(6002, "方向已存在");
            }
        }

        if(qo.getType().equals(FormCategoryTypeEnum.EXTERNAL)){
            Specification query2 = new BaseMysqlQuery().append("type", qo.getType());

            Optional<FormCategoryEntity> beanOptional2 = repository.findOne(query2);
            if (null == qo.getId()) {
                if (beanOptional2.isPresent()) {
                    throw new BizException(6002, "外部只能存在一个方向");
                }
            } else {
                FormCategoryEntity bean = beanOptional2.get();
                if (beanOptional2.isPresent() && bean.getId().intValue() != qo.getId()) {
                    throw new BizException(6002, "外部只能存在一个方向");
                }
            }
        }

        if (null == qo.getId()) {
            entity = new FormCategoryEntity();
            entity.setStatus("1");
        } else {
            entity = manager.get(qo.getId());
        }

        BeanUtils.copyProperties(qo, entity, "status");
        manager.save(entity, curUser);

        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormCategoryEditQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
