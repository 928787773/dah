package com.qrqy.dah.service;

import com.qrqy.mysql.entity.FormQuestionEntity;
import io.swagger.annotations.Api;
import com.qrqy.dp.mysql.BaseMysqlSetSortQO;
import com.qrqy.dp.result.CommonObjectResult;
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

import java.math.BigDecimal;


/**
 * route : admin-form-question-set-sort
 *
 * @author : QRQY
 * @date : 2021-08-17 12:34
 */

@Service
@Slf4j
@Validated
@Api(value = "改变问题排序", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminFormQuestionSetSortService implements IBaseService<BaseMysqlSetSortQO> {
    @Autowired
    private FormQuestionManager manager;

    @Override
    public ICommonResult execute(@Valid BaseMysqlSetSortQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        FormQuestionEntity formQuestionEntity = manager.get(qo.getId());
        //这里处理问题排序的上移下移,正序排列,上移是传负整数,下移传正整数
        BigDecimal sort = new BigDecimal(formQuestionEntity.getSort()).add(new BigDecimal(qo.getSort()));
        manager.setSort(qo.getId(), Integer.parseInt(sort.toString()));
        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(BaseMysqlSetSortQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
