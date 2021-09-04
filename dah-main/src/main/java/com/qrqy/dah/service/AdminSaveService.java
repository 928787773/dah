package com.qrqy.dah.service;

import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.AdminSystemModularEntity;
import com.qrqy.mysql.manager.AdminSystemModularManager;
import com.qrqy.mysql.repository.AdminRepository;
import com.qrqy.mysql.repository.AdminSystemModularRepository;
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
import com.qrqy.mysql.entity.AdminEntity;
import com.qrqy.mysql.manager.AdminManager;
import com.qrqy.dah.qo.AdminEditQO;

import java.util.Optional;

/**
 * route : admin-save
 *
 * @author : QRQY
 * @date : 2021-06-10 15:46
 */

@Service
@Slf4j
@Validated
@Api(value = "管理员添加和修改", tags = {"管理端", "人员管理", "这里放筛选标签"})
public class AdminSaveService implements IBaseService<AdminEditQO> {

    @Autowired
    private AdminManager manager;

    @Autowired
    private AdminSystemModularManager adminSystemModularManager;

    @Autowired
    private AdminRepository repository;

    @Autowired
    private AdminSystemModularRepository adminSystemModularRepository;



    @Override
    public ICommonResult execute(@Valid AdminEditQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);


        AdminEntity entity;

        Specification query = new BaseMysqlQuery().append("phonenum", qo.getPhonenum());

        Optional<AdminEntity> beanOptional = repository.findOne(query);
        if (beanOptional.isPresent()) {
            AdminEntity bean = beanOptional.get();
            if (null == qo.getId()) {
                throw new BizException(6002, "手机号已存在");
            }
            if (null != qo.getId() && bean.getId().intValue() != qo.getId()) {
                throw new BizException(6002, "手机号已存在");
            }
        }

        if (null == qo.getId()) {
            entity = new AdminEntity();
            entity.setStatus("1");
        } else {
            entity = manager.get(qo.getId());
        }
        BeanUtils.copyProperties(qo, entity, "status");
        manager.save(entity, curUser);

        //管理员权限表
        AdminSystemModularEntity adminSystemModularEntity;

        Specification query2 = new BaseMysqlQuery().append("adminId", entity.getId());
        Optional<AdminSystemModularEntity> beanOptional2 = adminSystemModularRepository.findOne(query2);
        if (!beanOptional2.isPresent()) {
            adminSystemModularEntity = new AdminSystemModularEntity();
        }else{
            adminSystemModularEntity = beanOptional2.get();
        }

        adminSystemModularEntity.setSystemModularIds(qo.getSystemModularIds());
        adminSystemModularEntity.setAdminId(entity.getId());
        adminSystemModularEntity.setStatus("1");
        adminSystemModularManager.save(adminSystemModularEntity, curUser);

        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminEditQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
