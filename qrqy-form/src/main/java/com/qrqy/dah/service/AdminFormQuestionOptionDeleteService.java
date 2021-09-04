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
import com.qrqy.mysql.manager.FormQuestionOptionManager;
import com.qrqy.dah.qo.FormQuestionOptionIdQO;

/**
 * route : admin-form-question-option-delete
 *
 * @author : QRQY
 * @date : 2021-06-28 17:42
 */

@Service
@Slf4j
@Validated
@Api(value = "这里是功能说明", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminFormQuestionOptionDeleteService implements IBaseService<FormQuestionOptionIdQO> {
    @Autowired
    private FormQuestionOptionManager manager;

    @Override
    public ICommonResult execute(@Valid FormQuestionOptionIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        throw new UnsupportedOperationException("删除方法尚未实现");
        //manager.delete(qo.getId());
        //return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormQuestionOptionIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
