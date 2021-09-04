package com.qrqy.dah.service;

import com.qrqy.dp.result.CommonObjectResult;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.qrqy.mysql.manager.MedicalStaffManager;
import com.qrqy.dah.qo.MedicalStaffIdQO;

/**
 * route : admin-medical-staff-delete
 *
 * @author : QRQY
 * @date : 2021-06-29 13:48
 */

@Service
@Slf4j
@Validated
@Api(value = "删除医务人员", tags = {"管理端", "医务人员", "这里放筛选标签"})
public class AdminMedicalStaffDeleteService implements IBaseService<MedicalStaffIdQO> {
    @Autowired
    private MedicalStaffManager manager;

    @Override
    public ICommonResult execute(@Valid MedicalStaffIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        manager.delete(qo.getId());
        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(MedicalStaffIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
