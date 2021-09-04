package com.qrqy.dah.service;

import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.AdminSystemModularEntity;
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
import com.qrqy.dah.qo.AdminIdQO;
import com.qrqy.dah.dto.AdminDetailDTO;

import java.util.Optional;

/**
 * route : admin-get
 *
 * @author : QRQY
 * @date : 2021-06-11 09:50
 */
@Service
@Slf4j
@Validated
@Api(value = "管理员详情", tags = {"管理端", "管理员", "这里放筛选标签"})
public class AdminGetService implements IBaseService<AdminIdQO> {
    @Autowired
    private AdminManager manager;

    @Autowired
    private AdminSystemModularRepository adminSystemModularRepository;



    @Override
    public ICommonResult execute(@Valid AdminIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        AdminEntity entity = manager.get(qo.getId());
        AdminDetailDTO dto = new AdminDetailDTO();
        BeanUtils.copyProperties(entity, dto);

        //管理员权限表
        Specification query2 = new BaseMysqlQuery().append("adminId", entity.getId());
        Optional<AdminSystemModularEntity> beanOptional2 = adminSystemModularRepository.findOne(query2);
        if (beanOptional2.isPresent()) {
            AdminSystemModularEntity adminSystemModular = beanOptional2.get();
            String[] systemModularIdArray =adminSystemModular.getSystemModularIds().split(",");
            dto.setSystemModularIdArray(systemModularIdArray);
        }
        return new CommonObjectResult<>(dto);

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
