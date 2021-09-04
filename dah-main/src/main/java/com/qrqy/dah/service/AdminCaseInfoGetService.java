package com.qrqy.dah.service;

import com.qrqy.dah.utils.CommonUtils;
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
import com.qrqy.mysql.entity.CaseInfoEntity;
import com.qrqy.mysql.manager.CaseInfoManager;
import com.qrqy.dah.qo.CaseInfoIdQO;
import com.qrqy.dah.dto.CaseDetailDTO;

/**
 * route : admin-case-info-get
 *
 * @author : QRQY
 * @date : 2021-06-25 14:11
 */
@Service
@Slf4j
@Validated
@Api(value = "病例详情", tags = {"管理端", "病例", "这里放筛选标签"})
public class AdminCaseInfoGetService implements IBaseService<CaseInfoIdQO> {
    @Autowired
    private CaseInfoManager manager;

    @Override
    public ICommonResult execute(@Valid CaseInfoIdQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        CaseInfoEntity entity = manager.get(qo.getId());
        CaseDetailDTO dto = new CaseDetailDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setGenderStr(CommonUtils.getGenderStr(dto.getGender()));
        dto.setAge(CommonUtils.getAge(dto.getBirthday()));
        return new CommonObjectResult<>(dto);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(CaseInfoIdQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
