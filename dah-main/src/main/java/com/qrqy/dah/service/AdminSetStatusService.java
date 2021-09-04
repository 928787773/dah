package com.qrqy.dah.service;

import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.mysql.BaseMysqlSetStatusQO;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.qrqy.mysql.manager.AdminManager;

/**
 * route : admin-set-status
 *
 * @author : QRQY
 * @date : 2021-06-11 09:48
 */

@Service
@Slf4j
@Validated
@Api(value = "管理员改变状态", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminSetStatusService implements IBaseService<BaseMysqlSetStatusQO> {
    @Autowired
    private AdminManager manager;

    @Override
    public ICommonResult execute(@Valid BaseMysqlSetStatusQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        if(qo.getId().equals(curUser.getUserId())){
//            不可改变自己的状态
            throw new BizException(5002,"不可改变自己的状态");
        }

        manager.setStatus(qo.getId(), qo.getStatus());
        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(BaseMysqlSetStatusQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
