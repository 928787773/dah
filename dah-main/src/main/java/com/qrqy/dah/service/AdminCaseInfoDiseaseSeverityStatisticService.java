package com.qrqy.dah.service;

import com.qrqy.dah.dto.AdminCaseInfoDiseaseSeverityStatisticDTO;
import com.qrqy.dp.result.CommonObjectResult;
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
import java.util.Optional;

import com.qrqy.dah.qo.AdminCaseInfoDiseaseSeverityStatisticQO;

/**
 * route : admin-case-info-disease-severity-statistic
 *
 * @author : QRQY
 * @date : 2021-07-01 14:09
 */

@Service
@Slf4j
@Validated
@Api(value = "疾病严重程度统计", tags = {"管理端", "统计", "这里放筛选标签"})
public class AdminCaseInfoDiseaseSeverityStatisticService implements IBaseService<AdminCaseInfoDiseaseSeverityStatisticQO> {
    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Override
    public ICommonResult execute(@Valid AdminCaseInfoDiseaseSeverityStatisticQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
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
                "\tCOUNT( disease_severity ) count,\n" +
                "\tdisease_severity \n" +
                "\tFROM\n" +
                "\tcase_info \n" +
                "\tWHERE\n" +
                "\t`status` = \"1\" \n" +
                "\tAND deleted_flag = 0 \n" +countrySql + createdAtGteSql + createdAtLteSql + genderSql + birthdayGteSql + birthdayLteSql+
                "\tGROUP BY\n" +
                "\tdisease_severity ORDER BY disease_severity ASC\n";
        List<Map> list = caseInfoRepository.querySqlToMap(sql);
        List<String> seriesDataArray= new ArrayList<>();
        String[] xAxisDataArray = new String[]{
                "1", "2", "3", "4", "5", "其他"
        };
        for (String xAxisData:xAxisDataArray) {
            Optional<Map> seriesDataMap =list.stream().filter(it->it.get("disease_severity").equals(xAxisData)).findFirst();
            String count = "0";
            if(seriesDataMap.isPresent()){
                count = seriesDataMap.get().get("count").toString();
            }
            seriesDataArray.add(count);
        }
        AdminCaseInfoDiseaseSeverityStatisticDTO dto = new AdminCaseInfoDiseaseSeverityStatisticDTO();
        dto.setXAxisData(xAxisDataArray);
        dto.setSeriesData(seriesDataArray);

        return new CommonObjectResult<>(dto);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminCaseInfoDiseaseSeverityStatisticQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
