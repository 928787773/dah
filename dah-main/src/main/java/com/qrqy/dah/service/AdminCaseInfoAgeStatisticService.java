package com.qrqy.dah.service;

import com.qrqy.dah.dto.AdminCaseInfoAgeStatisticDTO;
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

import com.qrqy.dah.qo.AdminCaseInfoAgeStatisticQO;

/**
 * route : admin-case-info-age-statistic
 *
 * @author : QRQY
 * @date : 2021-06-30 16:17
 */
@Service
@Slf4j
@Validated
@Api(value = "年龄统计", tags = {"首页", "统计", "这里放筛选标签"})
public class AdminCaseInfoAgeStatisticService implements IBaseService<AdminCaseInfoAgeStatisticQO> {
    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Override
    public ICommonResult execute(@Valid AdminCaseInfoAgeStatisticQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        String[] xAxisDataArray = new String[]{
                "0-19", "20-25", "26-30", "31-35", "36-40", "41-45", "46+"
        };

        String countrySql = "";
        String genderSql = "";
        String createdAtGteSql = "";
        String createdAtLteSql = "";
        if(qo.getCountryId() != null){
            countrySql = " AND country_id="+qo.getCountryId();
        }
        if(qo.getGender() != null && !"".equals(qo.getGender())){
            genderSql = " AND gender="+qo.getGender();
        }
        if(qo.getCreatedAtGte() != null){
            createdAtGteSql = " AND created_at >= '"+qo.getCreatedAtGte() + "'";
        }
        if(qo.getCreatedAtLte() != null){
            createdAtLteSql = " AND created_at <= '"+qo.getCreatedAtLte() + "'";
        }
        String sql = "SELECT\n" +
                "\tcount( CASE WHEN ca.age BETWEEN 0 AND 19 THEN age END ) AS \"0-19\" ,\n" +
                "\tcount( CASE WHEN ca.age BETWEEN 20 AND 25 THEN age END ) AS \"20-25\" ,\n" +
                "\tcount( CASE WHEN ca.age BETWEEN 26 AND 30 THEN age END ) AS \"26-30\" ,\n" +
                "\tcount( CASE WHEN ca.age BETWEEN 31 AND 35 THEN age END ) AS \"31-35\" ,\n" +
                "\tcount( CASE WHEN ca.age BETWEEN 36 AND 40 THEN age END ) AS \"36-40\" ,\n" +
                "\tcount( CASE WHEN ca.age BETWEEN 41 AND 45 THEN age END ) AS \"41-45\" ,\n" +
                "\tcount( CASE WHEN ca.age >=46 THEN age END ) AS \"46+\"\n" +
                "\tFROM\n" +
                "\t( SELECT TIMESTAMPDIFF( YEAR, birthday, NOW( ) ) age FROM case_info WHERE `status` = \"1\" AND deleted_flag = 0 " +
                countrySql + createdAtGteSql + createdAtLteSql + genderSql +
                ") ca \n";
        List<Map> list = caseInfoRepository.querySqlToMap(sql);
        List<String> seriesDataArray= new ArrayList<>();
        for (String data:xAxisDataArray) {
            seriesDataArray.add(list.get(0).get(data).toString());
        }

        AdminCaseInfoAgeStatisticDTO dto = new AdminCaseInfoAgeStatisticDTO();
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
    public void validate(AdminCaseInfoAgeStatisticQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
