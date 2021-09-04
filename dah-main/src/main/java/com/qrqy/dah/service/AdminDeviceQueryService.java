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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.DeviceEntity;
import com.qrqy.mysql.manager.DeviceManager;
import com.qrqy.dah.qo.DeviceQueryQO;
import com.qrqy.dah.dto.DeviceListDTO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : admin-device-query
 *
 * @author : QRQY
 * @date : 2021-06-29 15:24
 */
@Service
@Slf4j
@Validated
@Api(value = "设备列表", tags = {"管理端", "设备", "这里放筛选标签"})
public class AdminDeviceQueryService implements IBaseService<DeviceQueryQO> {
    @Autowired
    private DeviceManager manager;

    @Override
    public ICommonResult execute(@Valid DeviceQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        if (qo.getCreatedAtLte() != null) {
            LocalDateTime newCreatedAtLte = qo.getCreatedAtLte().plusHours(23).plusMinutes(59).plusSeconds(59);
            qo.setCreatedAtLte(newCreatedAtLte);
        }
        Specification query = new BaseMysqlQuery(qo)
                .orderBy("createdAt", Sort.Direction.DESC);

        Page<DeviceEntity> page = manager.query(query, qo.getPageable(), curUser);

        List<DeviceListDTO> content = page.getContent().stream().map(t -> {
            DeviceListDTO dto = new DeviceListDTO();
            BeanUtils.copyProperties(t, dto);
            BigDecimal totalPrice = dto.getUnitPrice().multiply(new BigDecimal(dto.getAmount()));
            dto.setTotalPrice(totalPrice);
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
    public void validate(DeviceQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
