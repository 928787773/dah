package com.qrqy.dah.service;

import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.FormCategoryEntity;
import com.qrqy.mysql.entity.FormInfoEntity;
import com.qrqy.mysql.repository.FormInfoRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.mysql.BaseMysqlSetStatusQO;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.qrqy.mysql.manager.FormCategoryManager;

import java.util.Optional;

/**
 * route : admin-form-category-set-status
 *
 * @author : QRQY
 * @date : 2021-06-17 15:42
 */
@Service
@Slf4j
@Validated
@Api(value = "改变量表方向状态", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminFormCategorySetStatusService implements IBaseService<BaseMysqlSetStatusQO> {
    @Autowired
    private FormCategoryManager manager;

    @Autowired
    private FormInfoRepository formInfoRepository;



    @Override
    public ICommonResult execute(@Valid BaseMysqlSetStatusQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        manager.setStatus(qo.getId(), qo.getStatus());
        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(BaseMysqlSetStatusQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
