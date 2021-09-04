package com.qrqy.dah.service;

import com.qrqy.dah.dto.AdminCaseInfoTreatmentInterventStatisticDTO;
import com.qrqy.dp.result.CommonListResult;
import com.qrqy.mysql.repository.CaseInfoRepository;
import com.qrqy.mysql.repository.TreatmentInterventRepository;
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

import com.qrqy.mysql.entity.TreatmentInterventEntity;
import com.qrqy.dah.qo.AdminCaseInfoTreatmentInterventStatisticQO;

/**
 * route : admin-case-info-treatment-intervent-statistic
 *
 * @author : QRQY
 * @date : 2021-07-01 13:51
 */
@Service
@Slf4j
@Validated
@Api(value = "治疗干预方式统计", tags = {"管理端", "统计", "这里放筛选标签"})
public class AdminCaseInfoTreatmentInterventStatisticService implements IBaseService<AdminCaseInfoTreatmentInterventStatisticQO> {
    @Autowired
    private TreatmentInterventRepository treatmentInterventRepository;
    @Autowired
    private CaseInfoRepository caseInfoRepository;



    @Override
    public ICommonResult execute(@Valid AdminCaseInfoTreatmentInterventStatisticQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
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
                "\tCOUNT( treatment_intervent_id ) count,\n" +
                "\ttreatment_intervent_id \n" +
                "FROM\n" +
                "\tcase_info \n" +
                "WHERE\n" +
                "\t`status` = \"1\" \n" +
                "\tAND deleted_flag = 0 \n" +countrySql + createdAtGteSql + createdAtLteSql + genderSql + birthdayGteSql + birthdayLteSql+
                "\tGROUP BY\n" +
                "\ttreatment_intervent_id \n";
        List<Map> list = caseInfoRepository.querySqlToMap(sql);

        //1 获取所有疾病转归
        Specification<TreatmentInterventEntity> query = new BaseMysqlQuery<TreatmentInterventEntity>()
                .append("status", "1").orderBy("sort", Sort.Direction.ASC);;

        List<TreatmentInterventEntity> treatmentInterventList = treatmentInterventRepository.findAll(query);

        List<AdminCaseInfoTreatmentInterventStatisticDTO> result = new ArrayList<>();
        treatmentInterventList.forEach(t->{
            AdminCaseInfoTreatmentInterventStatisticDTO dto = new AdminCaseInfoTreatmentInterventStatisticDTO();
            dto.setName(t.getName());
            Optional<Map> valueMap = list.stream().filter(it->it.get("treatment_intervent_id").equals(t.getId())).findFirst();
            String value = "0";
            if(valueMap.isPresent()){
                value = valueMap.get().get("count").toString();
            }
            dto.setValue(value);
            result.add(dto);
        });
        return new CommonListResult<>(result);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminCaseInfoTreatmentInterventStatisticQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
