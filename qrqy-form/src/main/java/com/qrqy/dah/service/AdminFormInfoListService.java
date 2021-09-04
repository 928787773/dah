package com.qrqy.dah.service;

import com.qrqy.dp.result.CommonListResult;
import com.qrqy.mysql.repository.FormInfoRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.mysql.BaseMysqlQuery;
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
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.FormInfoEntity;
import com.qrqy.dah.qo.AdminFormInfoListQO;
import com.qrqy.dah.dto.AdminFormInfoListDTO;

/**
 * route : admin-form-info-list
 *
 * @author : QRQY
 * @date : 2021-06-29 14:22
 */
@Service
@Slf4j
@Validated
@Api(value = "问卷", tags = {"管理端", "问卷", "这里放筛选标签"})
public class AdminFormInfoListService implements IBaseService<AdminFormInfoListQO> {
    @Autowired
    private FormInfoRepository formInfoRepository;

    @Override
    public ICommonResult execute(@Valid AdminFormInfoListQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        Specification query = new BaseMysqlQuery(qo)
                .append("status", "1");

        List<FormInfoEntity> beanOptional = formInfoRepository.findAll(query);

        List<AdminFormInfoListDTO> content = beanOptional.stream().map(t -> {
            AdminFormInfoListDTO dto = new AdminFormInfoListDTO();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());

        return new CommonListResult<>(content);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminFormInfoListQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
