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
import com.qrqy.mysql.entity.FormRecordEntity;
import com.qrqy.mysql.manager.FormRecordManager;
import com.qrqy.dah.qo.FormRecordEditQO;

import java.math.BigDecimal;

/**
 * route : admin-form-record-save
 *
 * @author : QRQY
 * @date : 2021-06-22 15:36
 */
@Service
@Slf4j
@Validated
@Api(value = "管理后台问卷保存答题记录", tags = {"管理端", "问卷", "这里放筛选标签"})
public class AdminFormRecordSaveService implements IBaseService<FormRecordEditQO> {

    @Autowired
    private FormRecordManager manager;

    @Autowired
    private FormAnswerRecordImplSaveService formAnswerRecordImplSaveService;






    @Override
    public ICommonResult execute(@Valid FormRecordEditQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        FormRecordEntity entity;
        entity = new FormRecordEntity();
        BeanUtils.copyProperties(qo, entity, "status");
        entity.setStatus("0");
        entity.setAccountType("ADMIN");
        entity.setTotalScore(new BigDecimal(0));
        manager.save(entity, curUser);
        if(qo.getFormAnswerRecordList().size() >0){
            formAnswerRecordImplSaveService.saveFormAnswerRecord(qo.getFormAnswerRecordList(),entity,curUser);
        }


        return new CommonObjectResult<>("Success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormRecordEditQO qo, IBaseUser curUser) {
//        if (!qo.getPassword().equals(qo.getConfirmPassword())) {
//            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
//        }
    }

}
