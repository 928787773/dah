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
import com.qrqy.mysql.entity.FormCategoryEntity;
import com.qrqy.mysql.manager.FormCategoryManager;
import com.qrqy.dah.qo.FormCategoryIdQO;
import com.qrqy.dah.dto.FormCategoryDetailDTO;

/**
 * route : admin-form-category-get
 *
 * @author : QRQY
 * @date : 2021-06-17 15:52
 */
@Service
@Slf4j
@Validated
@Api(value = "根据id获取问卷方向详情", tags = {"管理端", "问卷方向", "这里放筛选标签"})
public class AdminFormCategoryGetService implements IBaseService<FormCategoryIdQO> {
    @Autowired
    private FormCategoryManager manager;

    @Override
    public ICommonResult execute(@Valid FormCategoryIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        FormCategoryEntity entity = manager.get(qo.getId());
        FormCategoryDetailDTO dto = new FormCategoryDetailDTO();
        BeanUtils.copyProperties(entity, dto);
        return new CommonObjectResult<>(dto);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormCategoryIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
