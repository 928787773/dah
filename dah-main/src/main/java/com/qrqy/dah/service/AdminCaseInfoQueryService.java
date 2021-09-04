package com.qrqy.dah.service;


import com.qrqy.dah.dto.CaseListDTO;
import com.qrqy.dah.qo.CaseInfoQueryQO;
import com.qrqy.dah.utils.CommonUtils;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import com.qrqy.mysql.entity.CaseInfoEntity;
import com.qrqy.mysql.manager.CaseInfoManager;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.ParseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * route : admin-case-info-query
 *
 * @author : QRQY
 * @date : 2021-06-25 14:09
 */
@Service
@Slf4j
@Validated
@Api(value = "病例列表", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminCaseInfoQueryService implements IBaseService<CaseInfoQueryQO> {
    @Autowired
    private CaseInfoManager manager;

    @Override
    public ICommonResult execute(@Valid CaseInfoQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        LocalDate birthdayGte = null;
        LocalDate birthdayLte = null;
        if (qo.getAgeGte() != null) {
            int birthdayYear = LocalDate.now().minusYears(qo.getAgeGte()).getYear();
            birthdayLte = LocalDate.of(birthdayYear, 12, 31);
            qo.setAgeGte(null);
        }
        if (qo.getAgeLte() != null) {
            birthdayGte = CommonUtils.getBirthday(qo.getAgeLte());
            qo.setAgeLte(null);
        }
        if (qo.getCreatedAtLte() != null) {
            LocalDateTime newCreatedAtLte = qo.getCreatedAtLte().plusHours(23).plusMinutes(59).plusSeconds(59);
            qo.setCreatedAtLte(newCreatedAtLte);
        }


        Specification query = new BaseMysqlQuery(qo)
                .append("birthdayGte",birthdayGte)
                .append("birthdayLte",birthdayLte)
                .orderBy("id", Sort.Direction.DESC);

        Page<CaseInfoEntity> page = manager.query(query, qo.getPageable(), curUser);

        List<CaseListDTO> content = page.getContent().stream().map(t -> {
            CaseListDTO dto = new CaseListDTO();
            BeanUtils.copyProperties(t, dto);
            dto.setGenderStr(CommonUtils.getGenderStr(dto.getGender()));
            dto.setAge(CommonUtils.getAge(dto.getBirthday()));
            return dto;
        }).collect(Collectors.toList());

        return new CommonPagingResult<>(content, page);

    }

    /**
     * 业务上的校验可以放在这里
     *
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(CaseInfoQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
