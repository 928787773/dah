package com.qrqy.dah.service;

import com.qrqy.dp.result.CommonObjectResult;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.qrqy.mysql.manager.FormQuestionManager;
import com.qrqy.dah.qo.FormQuestionIdQO;

/**
 * route : admin-form-question-delete
 *
 * @author : QRQY
 * @date : 2021-06-28 17:40
 */

@Service
@Slf4j
@Validated
@Api(value = "删除问题", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminFormQuestionDeleteService implements IBaseService<FormQuestionIdQO> {
    @Autowired
    private FormQuestionManager manager;

    @Override
    public ICommonResult execute(@Valid FormQuestionIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        manager.delete(qo.getId());
        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormQuestionIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
