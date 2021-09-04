package com.qrqy.dah.service;

import com.qrqy.dah.dto.AdminCaseInfoTcmInterventionStatisticDTO;
import com.qrqy.dp.result.CommonListResult;
import com.qrqy.mysql.repository.CaseInfoRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qrqy.dah.qo.AdminCaseInfoTcmInterventionStatisticQO;

/**
 * route : admin-case-info-tcm-intervention-statistic
 *
 * @author : QRQY
 * @date : 2021-07-01 14:04
 */

@Service
@Slf4j
@Validated
@Api(value = "中医干预统计", tags = {"管理端", "统计", "这里放筛选标签"})
public class AdminCaseInfoTcmInterventionStatisticService implements IBaseService<AdminCaseInfoTcmInterventionStatisticQO> {
    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Override
    public ICommonResult execute(@Valid AdminCaseInfoTcmInterventionStatisticQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        String countrySql = "";
        String genderSql = "";
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
        if(qo.getGender() != null && !qo.getGender().equals("")){
            genderSql = " AND gender="+qo.getGender();
        }
        if(qo.getCreatedAtGte() != null){
            createdAtGteSql = " AND created_at >= '"+qo.getCreatedAtGte() + "'";
        }
        if(qo.getCreatedAtLte() != null){
            createdAtLteSql = " AND created_at <= '"+qo.getCreatedAtLte() + "'";
        }
        String sql = "SELECT\n" +
                "\tCOUNT( is_tcm_intervention ) count,\n" +
                "\tis_tcm_intervention \n" +
                "FROM\n" +
                "\tcase_info \n" +
                "WHERE\n" +
                "\t`status` = \"1\" \n" +
                "\tAND deleted_flag = 0 \n" +countrySql + createdAtGteSql + createdAtLteSql + genderSql + birthdayGteSql + birthdayLteSql+
                "\tGROUP BY\n"+
                "\tis_tcm_intervention \n";
        List<Map> list = caseInfoRepository.querySqlToMap(sql);
        List<AdminCaseInfoTcmInterventionStatisticDTO> content = new ArrayList();
        if(list.isEmpty()){
            AdminCaseInfoTcmInterventionStatisticDTO dto= new AdminCaseInfoTcmInterventionStatisticDTO();
            dto.setName("是");
            dto.setValue("0");
            content.add(dto);
            dto= new AdminCaseInfoTcmInterventionStatisticDTO();
            dto.setName("否");
            dto.setValue("0");
            content.add(dto);

        }else{
            list.forEach(t->{
                AdminCaseInfoTcmInterventionStatisticDTO dto= new AdminCaseInfoTcmInterventionStatisticDTO();
                if("1".equals(t.get("is_tcm_intervention").toString())){
                    dto.setName("是");
                }
                if("0".equals(t.get("is_tcm_intervention").toString())){
                    dto.setName("否");
                }
                dto.setValue(t.get("count").toString());
                content.add(dto);
            });
        }



        return new CommonListResult<>(content);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminCaseInfoTcmInterventionStatisticQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
