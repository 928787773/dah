package com.qrqy.dah.service;

import com.qrqy.dah.dto.AdminCaseInfoDiseaseOutcomeStatisticDTO;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.mysql.entity.DiseaseOutcomeEntity;
import com.qrqy.mysql.repository.CaseInfoRepository;
import com.qrqy.mysql.repository.DiseaseOutcomeRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.qrqy.dah.qo.AdminCaseInfoDiseaseOutcomeStatisticQO;

/**
 * route : admin-case-info-disease-outcome-statistic
 *
 * @author : QRQY
 * @date : 2021-06-30 17:41
 */

@Service
@Slf4j
@Validated
@Api(value = "疾病转归统计", tags = {"运营端", "直播", "这里放筛选标签"})
public class AdminCaseInfoDiseaseOutcomeStatisticService implements IBaseService<AdminCaseInfoDiseaseOutcomeStatisticQO> {
    @Autowired
    private CaseInfoRepository caseInfoRepository;
    @Autowired
    private DiseaseOutcomeRepository diseaseOutcomeRepository;



    @Override
    public ICommonResult execute(@Valid AdminCaseInfoDiseaseOutcomeStatisticQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
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
                "\tCOUNT( disease_outcome_id ) count,\n" +
                "\tdisease_outcome_id \n" +
                "FROM\n" +
                "\tcase_info \n" +
                "WHERE\n" +
                "\t`status` = \"1\" \n" +
                "\tAND deleted_flag = 0 \n" +countrySql + createdAtGteSql + createdAtLteSql + genderSql + birthdayGteSql + birthdayLteSql+
                "\tGROUP BY\n" +
                "\tdisease_outcome_id\n";
        List<Map> list = caseInfoRepository.querySqlToMap(sql);

        //获取所有疾病转归
        Specification<DiseaseOutcomeEntity> query = new BaseMysqlQuery<DiseaseOutcomeEntity>()
                .append("status", "1").orderBy("sort", Sort.Direction.ASC);;

        List<DiseaseOutcomeEntity> diseaseOutcomeList = diseaseOutcomeRepository.findAll(query);

        //组装数据
        List<String> seriesDataArray= new ArrayList<>();
        List<String> yAxisDataArray= new ArrayList<>();
        diseaseOutcomeList.forEach(t->{
            yAxisDataArray.add(t.getName());
            Optional<Map> seriesDataMap = list.stream().filter(it -> it.get("disease_outcome_id").equals(t.getId())).findFirst();
            String count = "0";
            if(seriesDataMap.isPresent()){
                count = seriesDataMap.get().get("count").toString();
            }
            seriesDataArray.add(count);

        });
        AdminCaseInfoDiseaseOutcomeStatisticDTO dto = new AdminCaseInfoDiseaseOutcomeStatisticDTO();
        dto.setYAxisData(yAxisDataArray);
        dto.setSeriesData(seriesDataArray);

        return new CommonObjectResult<>(dto);

    }

}
