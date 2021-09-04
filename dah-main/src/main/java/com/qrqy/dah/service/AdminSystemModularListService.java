package com.qrqy.dah.service;

import com.qrqy.dp.result.CommonListResult;
import com.qrqy.mysql.repository.SystemModularRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.SystemModularEntity;
import com.qrqy.dah.qo.AdminSystemModularListQO;
import com.qrqy.dah.dto.AdminSystemModularListDTO;

/**
 * route : admin-system-modular-list
 *
 * @author : QRQY
 * @date : 2021-06-16 16:41
 */
@Service
@Slf4j
@Validated
@Api(value = "模块列表", tags = {"管理端", "管理员", "这里放筛选标签"})
public class AdminSystemModularListService implements IBaseService<AdminSystemModularListQO> {

    @Autowired
    private SystemModularRepository systemModularRepository;


    @Override
    public ICommonResult execute(@Valid AdminSystemModularListQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        Specification query = new BaseMysqlQuery(qo)
                .append("status", "1").append("level", 0).orderBy("sort", Sort.Direction.ASC);

        List<SystemModularEntity> systemModularList = systemModularRepository.findAll(query);

        List<AdminSystemModularListDTO> content = systemModularList.stream().map(t -> {
            AdminSystemModularListDTO dto = new AdminSystemModularListDTO();
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
    public void validate(AdminSystemModularListQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
