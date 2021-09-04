package com.qrqy.dah.service;

import com.qrqy.dah.utils.CommonUtils;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.MedicalStaffEntity;
import com.qrqy.mysql.manager.MedicalStaffManager;
import com.qrqy.dah.qo.MedicalStaffQueryQO;
import com.qrqy.dah.dto.MedicalStaffListDTO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : admin-medical-staff-query
 *
 * @author : QRQY
 * @date : 2021-07-14 13:56
 */
@Service
@Slf4j
@Validated
@Api(value = "医务人员列表", tags = {"管理端", "医务", "这里放筛选标签"})
public class AdminMedicalStaffQueryService implements IBaseService<MedicalStaffQueryQO> {
    @Autowired
    private MedicalStaffManager manager;

    @Override
    public ICommonResult execute(@Valid MedicalStaffQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        if (qo.getCreatedAtLte() != null) {
            LocalDateTime newCreatedAtLte = qo.getCreatedAtLte().plusHours(23).plusMinutes(59).plusSeconds(59);
            qo.setCreatedAtLte(newCreatedAtLte);
        }
        Specification query = new BaseMysqlQuery(qo)
                .orderBy("createdAt", Sort.Direction.DESC);

        Page<MedicalStaffEntity> page = manager.query(query, qo.getPageable(), curUser);

        List<MedicalStaffListDTO> content = page.getContent().stream().map(t -> {
            MedicalStaffListDTO dto = new MedicalStaffListDTO();
            BeanUtils.copyProperties(t, dto);
            dto.setGenderStr(CommonUtils.getGenderStr(dto.getGender().toString()));
            return dto;
        }).collect(Collectors.toList());

        return new CommonPagingResult<>(content, page);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(MedicalStaffQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
