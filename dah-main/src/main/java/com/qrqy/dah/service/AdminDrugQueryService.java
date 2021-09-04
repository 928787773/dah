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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.DrugEntity;
import com.qrqy.mysql.manager.DrugManager;
import com.qrqy.dah.qo.DrugQueryQO;
import com.qrqy.dah.dto.DrugListDTO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : admin-drug-query
 *
 * @author : QRQY
 * @date : 2021-06-29 15:18
 */
@Service
@Slf4j
@Validated
@Api(value = "药品列表", tags = {"管理端", "药品", "这里放筛选标签"})
public class AdminDrugQueryService implements IBaseService<DrugQueryQO> {
    @Autowired
    private DrugManager manager;

    @Override
    public ICommonResult execute(@Valid DrugQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        if (qo.getCreatedAtLte() != null) {
            LocalDateTime newCreatedAtLte = qo.getCreatedAtLte().plusHours(23).plusMinutes(59).plusSeconds(59);
            qo.setCreatedAtLte(newCreatedAtLte);
        }
        Specification query = new BaseMysqlQuery(qo)
                .orderBy("createdAt", Sort.Direction.DESC);

        Page<DrugEntity> page = manager.query(query, qo.getPageable(), curUser);

        List<DrugListDTO> content = page.getContent().stream().map(t -> {
            DrugListDTO dto = new DrugListDTO();
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
    public void validate(DrugQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
