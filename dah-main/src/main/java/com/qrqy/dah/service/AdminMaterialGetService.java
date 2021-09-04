package com.qrqy.dah.service;

import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.qrqy.mysql.entity.MaterialEntity;
import com.qrqy.mysql.manager.MaterialManager;
import com.qrqy.dah.qo.MaterialIdQO;
import com.qrqy.dah.dto.MaterialDetailDTO;

import java.math.BigDecimal;


/**
 * route : admin-material-get
 *
 * @author : QRQY
 * @date : 2021-06-29 14:42
 */
@Service
@Slf4j
@Validated
@Api(value = "物资详情", tags = {"管理端", "物资", "这里放筛选标签"})
public class AdminMaterialGetService implements IBaseService<MaterialIdQO> {
    @Autowired
    private MaterialManager manager;

    @Override
    public ICommonResult execute(@Valid MaterialIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        MaterialEntity entity = manager.get(qo.getId());
        MaterialDetailDTO dto = new MaterialDetailDTO();
        BeanUtils.copyProperties(entity, dto);
        BigDecimal totalPrice = dto.getUnitPrice().multiply(new BigDecimal(dto.getAmount()));
        dto.setTotalPrice(totalPrice);
        return new CommonObjectResult<>(dto);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(MaterialIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
