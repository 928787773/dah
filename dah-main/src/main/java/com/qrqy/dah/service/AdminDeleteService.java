package com.qrqy.dah.service;

import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.mysql.entity.AdminEntity;
import com.qrqy.mysql.repository.AdminRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.qrqy.mysql.manager.AdminManager;
import com.qrqy.dah.qo.AdminIdQO;

import java.util.Optional;

/**
 * route : admin-delete
 *
 * @author : QRQY
 * @date : 2021-06-10 17:36
 */
@Service
@Slf4j
@Validated
@Api(value = "管理员删除", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminDeleteService implements IBaseService<AdminIdQO> {
    @Autowired
    private AdminManager manager;



    @Override
    public ICommonResult execute(@Valid AdminIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        if(qo.getId().equals(curUser.getUserId())){
//            不可删除自己
            throw new BizException(5002,"不可以删除自己");
        }
        manager.delete(qo.getId());
        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
