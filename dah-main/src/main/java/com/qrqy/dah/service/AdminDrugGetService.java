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
import com.qrqy.mysql.entity.DrugEntity;
import com.qrqy.mysql.manager.DrugManager;
import com.qrqy.dah.qo.DrugIdQO;
import com.qrqy.dah.dto.DrugDetailDTO;

/**
 * route : admin-drug-get
 *
 * @author : QRQY
 * @date : 2021-06-29 15:17
 */
@Service
@Slf4j
@Validated
@Api(value = "药品详情", tags = {"管理端", "药品", "这里放筛选标签"})
public class AdminDrugGetService implements IBaseService<DrugIdQO> {
    @Autowired
    private DrugManager manager;

    @Override
    public ICommonResult execute(@Valid DrugIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        DrugEntity entity = manager.get(qo.getId());
        DrugDetailDTO dto = new DrugDetailDTO();
        BeanUtils.copyProperties(entity, dto);
        return new CommonObjectResult<>(dto);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(DrugIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
