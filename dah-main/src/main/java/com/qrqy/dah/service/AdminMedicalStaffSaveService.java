package com.qrqy.dah.service;

import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.AdminEntity;
import com.qrqy.mysql.repository.MedicalStaffRepository;
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
import com.qrqy.mysql.entity.MedicalStaffEntity;
import com.qrqy.mysql.manager.MedicalStaffManager;
import com.qrqy.dah.qo.MedicalStaffEditQO;

import java.util.Optional;

/**
 * route : admin-medical-staff-save
 *
 * @author : QRQY
 * @date : 2021-06-29 11:33
 */
@Service
@Slf4j
@Validated
@Api(value = "添加或编辑医务人员", tags = {"管理端", "医务人员", "这里放筛选标签"})
public class AdminMedicalStaffSaveService implements IBaseService<MedicalStaffEditQO> {
    @Autowired
    private MedicalStaffManager manager;

    @Autowired
    private MedicalStaffRepository medicalStaffRepository;



    @Override
    public ICommonResult execute(@Valid MedicalStaffEditQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        MedicalStaffEntity entity;

        Specification query = new BaseMysqlQuery().append("phonenum", qo.getPhonenum());

        Optional<MedicalStaffEntity> beanOptional = medicalStaffRepository.findOne(query);
        if (beanOptional.isPresent()) {
            MedicalStaffEntity bean = beanOptional.get();
            if (null == qo.getId()) {
                throw new BizException(6002, "手机号已存在");
            }
            if (null != qo.getId() && bean.getId().intValue() != qo.getId()) {
                throw new BizException(6002, "手机号已存在");
            }
        }

        if (null == qo.getId()) {
            entity = new MedicalStaffEntity();
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
    public void validate(MedicalStaffEditQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
