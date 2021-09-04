package com.qrqy.dah.service;

import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.DrugEntity;
import com.qrqy.mysql.repository.DeviceRepository;
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
import com.qrqy.mysql.entity.DeviceEntity;
import com.qrqy.mysql.manager.DeviceManager;
import com.qrqy.dah.qo.DeviceEditQO;

import java.util.Optional;

/**
 * route : admin-device-save
 *
 * @author : QRQY
 * @date : 2021-06-29 15:23
 */
@Service
@Slf4j
@Validated
@Api(value = "设备添加或编辑", tags = {"管理端", "设备", "这里放筛选标签"})
public class AdminDeviceSaveService implements IBaseService<DeviceEditQO> {
    @Autowired
    private DeviceManager manager;

    @Autowired
    private DeviceRepository repository;



    @Override
    public ICommonResult execute(@Valid DeviceEditQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        DeviceEntity entity;

        Specification query = new BaseMysqlQuery().append("name", qo.getName());

        Optional<DeviceEntity> beanOptional = repository.findOne(query);
        if (beanOptional.isPresent()) {
            DeviceEntity bean = beanOptional.get();
            if (null == qo.getId()) {
                throw new BizException(6002, "设备名称已存在");
            }
            if (null != qo.getId() && bean.getId().intValue() != qo.getId()) {
                throw new BizException(6002, "设备名称已存在");
            }
        }

        if (null == qo.getId()) {
            entity = new DeviceEntity();
        } else {
            entity = manager.get(qo.getId());
        }
        BeanUtils.copyProperties(qo, entity, "status");
        entity.setStatus("1");
        manager.save(entity, curUser);

        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(DeviceEditQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
