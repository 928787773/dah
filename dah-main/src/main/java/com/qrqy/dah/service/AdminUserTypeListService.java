package com.qrqy.dah.service;

import com.qrqy.dp.result.CommonListResult;
import com.qrqy.mysql.repository.UserTypeRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.mysql.BaseMysqlQuery;
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
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.UserTypeEntity;
import com.qrqy.dah.qo.AdminUserTypeListQO;
import com.qrqy.dah.dto.AdminUserTypeListDTO;

/**
 * route : admin-user-type-list
 *
 * @author : QRQY
 * @date : 2021-06-25 14:28
 */

@Service
@Slf4j
@Validated
@Api(value = "人员性质", tags = {"管理端", "人员性质", "这里放筛选标签"})
public class AdminUserTypeListService implements IBaseService<AdminUserTypeListQO> {
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public ICommonResult execute(@Valid AdminUserTypeListQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        Specification query = new BaseMysqlQuery()
                .append("status", "1");

        List<UserTypeEntity> userTypeList = userTypeRepository.findAll(query);

        List<AdminUserTypeListDTO> content = userTypeList.stream().map(t -> {
            AdminUserTypeListDTO dto = new AdminUserTypeListDTO();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());

        return new CommonListResult<>(content);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminUserTypeListQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
