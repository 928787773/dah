package com.qrqy.dah.service;

import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.CountryEntity;
import com.qrqy.mysql.entity.SystemModularEntity;
import com.qrqy.mysql.manager.SystemModularManager;
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
import com.qrqy.mysql.entity.AdminSystemModularEntity;
import com.qrqy.mysql.manager.AdminSystemModularManager;
import com.qrqy.dah.qo.AdminSystemModularIdQO;
import com.qrqy.dah.dto.AdminSystemModularDetailDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * route : admin-admin-system-modular-get
 *
 * @author : QRQY
 * @date : 2021-06-25 11:43
 */

@Service
@Slf4j
@Validated
@Api(value = "获取管理员权限模块详情", tags = {"管理端", "权限", "这里放筛选标签"})
public class AdminAdminSystemModularGetService implements IBaseService<AdminSystemModularIdQO> {

    @Autowired
    private AdminSystemModularRepository repository;


    @Autowired
    private SystemModularManager systemModularManager;



    @Override
    public ICommonResult execute(@Valid AdminSystemModularIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        Specification query = new BaseMysqlQuery()
                .append("status", "1").append("adminId", curUser.getUserId());

        List<AdminSystemModularEntity> entity = repository.findAll(query);
        AdminSystemModularDetailDTO dto = new AdminSystemModularDetailDTO();
        BeanUtils.copyProperties(entity.get(0), dto);

        String[] systemModularIdsArray = dto.getSystemModularIds().split(",");
        List<SystemModularEntity> systemModularList = new ArrayList<>();
        for (String item:systemModularIdsArray) {
            int id = Integer.parseInt(item);
            SystemModularEntity systemModular = systemModularManager.get(id);
            if(systemModular != null){
                systemModularList.add(systemModular);
            }
        }
        dto.setSystemModularList(systemModularList);
        return new CommonObjectResult<>(dto);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminSystemModularIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
