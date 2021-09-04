package com.qrqy.dah.service;

import com.alibaba.fastjson.JSONObject;
import com.qrqy.dah.dto.AdminCaseInfoGenderStatisticDTO;
import com.qrqy.dah.utils.CommonUtils;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.CommonListResult;
import com.qrqy.mysql.entity.CaseInfoEntity;
import com.qrqy.mysql.repository.CaseInfoRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qrqy.dah.qo.AdminCaseInfoGenderStatisticQO;

/**
 * route : admin-case-info-gender-statistic
 *
 * @author : QRQY
 * @date : 2021-06-29 17:34
 */
@Service
@Slf4j
@Validated
@Api(value = "性别统计图", tags = {"管理端", "统计", "性别"})
public class AdminCaseInfoGenderStatisticService implements IBaseService<AdminCaseInfoGenderStatisticQO> {
    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Override
    public ICommonResult execute(@Valid AdminCaseInfoGenderStatisticQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        String countrySql = "";
        String createdAtGteSql = "";
        String createdAtLteSql = "";
        String birthdayGteSql = "";
        String birthdayLteSql = "";
        LocalDate now =LocalDate.now();
        if(qo.getAgeGte() != null){
            LocalDate birthday= now.minusYears(qo.getAgeGte());
            birthdayGteSql = " AND birthday <= '"+birthday+"'";
        }
        if(qo.getAgeLte() != null){
            LocalDate birthday= now.minusYears(qo.getAgeLte());
            birthdayLteSql = " AND birthday >= '"+birthday+"'";
        }
        if(qo.getCountryId() != null){
            countrySql = " AND country_id="+qo.getCountryId();
        }
        if(qo.getCreatedAtGte() != null){
            createdAtGteSql = " AND created_at >= '"+qo.getCreatedAtGte() + "'";
        }
        if(qo.getCreatedAtLte() != null){
            createdAtLteSql = " AND created_at <= '"+qo.getCreatedAtLte() + "'";
        }

        List<AdminCaseInfoGenderStatisticDTO> content= new ArrayList<>();
        String sql = "SELECT\n" +
                "\tcount( CASE gender WHEN '1' THEN id END ) AS count1,\n" +
                "\tcount( CASE gender WHEN '2' THEN id END ) AS count2 \n" +
                "FROM\n" +
                "\tcase_info \n" +
                "WHERE\n" +
                "\t`status` = \"1\" \n" +countrySql + createdAtGteSql + createdAtLteSql + birthdayGteSql + birthdayLteSql +
                "\tAND deleted_flag =0";
        List<Map> list = caseInfoRepository.querySqlToMap(sql);
        AdminCaseInfoGenderStatisticDTO dto1= new AdminCaseInfoGenderStatisticDTO();
        dto1.setName("男");
        dto1.setValue(list.get(0).get("count1").toString());
        content.add(dto1);
        AdminCaseInfoGenderStatisticDTO dto2= new AdminCaseInfoGenderStatisticDTO();
        dto2.setName("女");
        dto2.setValue(list.get(0).get("count2").toString());
        content.add(dto2);

        return new CommonListResult<>(content);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminCaseInfoGenderStatisticQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
