package com.qrqy.dah.service;

import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.mysql.entity.FormCategoryEntity;
import com.qrqy.mysql.entity.FormInfoEntity;
import com.qrqy.mysql.manager.FormInfoManager;
import com.qrqy.mysql.repository.FormCategoryRepository;
import com.qrqy.mysql.repository.FormInfoRepository;
import io.swagger.annotations.Api;
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
import com.qrqy.dah.qo.FormCategoryIdQO;

import java.util.List;
import java.util.Optional;

/**
 * route : admin-form-category-delete
 *
 * @author : QRQY
 * @date : 2021-06-17 15:29
 */
@Service
@Slf4j
@Validated
@Api(value = "删除问卷方向", tags = {"管理端", "直播", "这里放筛选标签"})
public class AdminFormCategoryDeleteService implements IBaseService<FormCategoryIdQO> {
    @Autowired
    private FormCategoryManager manager;

    @Autowired
    private FormInfoRepository formInfoRepository;



    @Override
    public ICommonResult execute(@Valid FormCategoryIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        Specification query = new BaseMysqlQuery().append("formCategoryId", qo.getId());

        List<FormInfoEntity> beanOptional = formInfoRepository.findAll(query);
        if (!beanOptional.isEmpty()) {
            throw new BizException(5001, "请先清空该方向下的问卷");
        }
        manager.delete(qo.getId());

        return new CommonObjectResult<>("success");

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormCategoryIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
