package com.qrqy.dah.service;

import com.qrqy.dp.result.CommonObjectResult;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import com.qrqy.mysql.entity.AdminEntity;
import com.qrqy.mysql.manager.AdminManager;
import com.qrqy.dah.qo.CommonLoginQO;
import com.qrqy.dah.dto.CommonLoginDTO;

/**
 * route : common-login
 *
 * @author : QRQY
 * @date : 2021-06-10 13:59
 */

@Service
@Slf4j
@Validated
@Api(value = "管理后台登录", tags = {"管理后台登录相关", "登录", "这里放筛选标签"})
public class CommonLoginService implements IBaseService<CommonLoginQO> {
    @Autowired
    private AdminManager manager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ICommonResult execute(@Valid CommonLoginQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        AdminEntity adminTab = manager.passwordLogin(qo.getPhonenum(), qo.getPassword());

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String bearer = "Bearer " + uuid;

        CommonLoginDTO dto = new CommonLoginDTO();
        BeanUtils.copyProperties(adminTab, dto);
        dto.setToken(bearer);

        redisTemplate.opsForValue().setIfAbsent(bearer, dto, 2, TimeUnit.DAYS);

        return new CommonObjectResult<>(dto);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(CommonLoginQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
