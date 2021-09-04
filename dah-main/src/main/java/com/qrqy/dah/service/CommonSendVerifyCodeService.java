package com.qrqy.dah.service;

import com.qrqy.dah.utils.CommonUtils;
import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.mysql.entity.AdminEntity;
import com.qrqy.mysql.repository.AdminRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;
import com.qrqy.mysql.entity.VerifyCodeEntity;
import com.qrqy.mysql.manager.VerifyCodeManager;
import com.qrqy.dah.qo.CommonSendVerifyCodeQO;

/**
 * route : common-send-verify-code
 *
 * @author : QRQY
 * @date : 2021-06-15 09:55
 */

@Service
@Slf4j
@Validated
@Api(value = "发送验证码", tags = {"管理端", "通用", "这里放筛选标签"})
public class CommonSendVerifyCodeService implements IBaseService<CommonSendVerifyCodeQO> {
    @Autowired
    private VerifyCodeManager manager;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public ICommonResult execute(@Valid CommonSendVerifyCodeQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        VerifyCodeEntity verifyCodeEntity;

        Specification query = new BaseMysqlQuery().append("phonenum", qo.getPhonenum());

        Object beanOptional1 = adminRepository.findOne(query);
        Optional<AdminEntity> beanOptional = adminRepository.findOne(query);
        if (!beanOptional.isPresent()) {
            throw new BizException(6002, "手机号不存在");
        }
        verifyCodeEntity = new VerifyCodeEntity();
        verifyCodeEntity.setPhonenum(qo.getPhonenum());

//        String code = CommonUtils.vcode();
        String code = "888888";
        verifyCodeEntity.setCode(code);
        verifyCodeEntity.setStatus("1");
        manager.save(verifyCodeEntity, curUser);

//        manager.sendMessage(qo.getPhonenum(), code);

        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(CommonSendVerifyCodeQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
