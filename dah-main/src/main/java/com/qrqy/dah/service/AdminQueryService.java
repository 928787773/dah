package com.qrqy.dah.service;

import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.AdminEntity;
import com.qrqy.mysql.manager.AdminManager;
import com.qrqy.dah.qo.AdminQueryQO;
import com.qrqy.dah.dto.AdminListDTO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : admin-query
 *
 * @author : QRQY
 * @date : 2021-06-11 09:33
 */

@Service
@Slf4j
@Validated
@Api(value = "管理员列表", tags = {"管理后台", "管理员", "这里放筛选标签"})
public class AdminQueryService implements IBaseService<AdminQueryQO> {
    @Autowired
    private AdminManager manager;

    @Override
    public ICommonResult execute(@Valid AdminQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        Specification query = new BaseMysqlQuery(qo).orderBy("createdAt", Sort.Direction.DESC);

        Page<AdminEntity> page = manager.query(query, qo.getPageable(), curUser);

        List<AdminListDTO> content = page.getContent().stream().map(t -> {
            AdminListDTO dto = new AdminListDTO();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());

        return new CommonPagingResult<>(content, page);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
