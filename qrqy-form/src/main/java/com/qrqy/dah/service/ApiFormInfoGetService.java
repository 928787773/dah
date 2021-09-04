package com.qrqy.dah.service;

import com.qrqy.dah.dto.ApiFormInfoDetailDTO;
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
import com.qrqy.mysql.entity.FormInfoEntity;
import com.qrqy.mysql.manager.FormInfoManager;
import com.qrqy.dah.qo.FormInfoIdQO;

/**
 * route : api-form-info-get
 *
 * @author : QRQY
 * @date : 2021-06-24 16:17
 */
@Service
@Slf4j
@Validated
@Api(value = "问卷详情", tags = {"数据大屏", "问卷", "这里放筛选标签"})
public class ApiFormInfoGetService implements IBaseService<FormInfoIdQO> {
    @Autowired
    private FormInfoManager manager;

    @Override
    public ICommonResult execute(@Valid FormInfoIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        FormInfoEntity entity = manager.get(qo.getId());
        ApiFormInfoDetailDTO dto = new ApiFormInfoDetailDTO();
        BeanUtils.copyProperties(entity, dto);
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
}
