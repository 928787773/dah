package com.qrqy.dah.service;

import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.qrqy.mysql.entity.CaseInfoEntity;
import com.qrqy.mysql.manager.CaseInfoManager;
import com.qrqy.dah.qo.CaseInfoEditQO;

/**
 * route : admin-case-info-save
 *
 * @author : QRQY
 * @date : 2021-06-25 14:10
 */
@Service
@Slf4j
@Validated
@Api(value = "添加或编辑病例", tags = {"管理端", "病例", "这里放筛选标签"})
public class AdminCaseInfoSaveService implements IBaseService<CaseInfoEditQO> {
    @Autowired
    private CaseInfoManager manager;

    @Override
    public ICommonResult execute(@Valid CaseInfoEditQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        CaseInfoEntity entity;
        if (null == qo.getId()) {
            entity = new CaseInfoEntity();
        } else {
            entity = manager.get(qo.getId());
        }
        BeanUtils.copyProperties(qo, entity, "status");
        entity.setStatus("1");
        manager.save(entity, curUser);

        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(CaseInfoEditQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
