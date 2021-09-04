package com.qrqy.dah.service;

import com.qrqy.dah.dto.AdminCaseInfoUserTypeStatisticDTO;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.mysql.repository.CaseInfoRepository;
import com.qrqy.mysql.repository.UserTypeRepository;
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

import com.qrqy.mysql.entity.UserTypeEntity;
import com.qrqy.dah.qo.AdminCaseInfoUserTypeStatisticQO;

/**
 * route : admin-case-info-user-type-statistic
 *
 * @author : QRQY
 * @date : 2021-07-01 14:26
 */
@Service
@Slf4j
@Validated
@Api(value = "人员性质统计", tags = {"管理端", "统计", "这里放筛选标签"})
public class AdminCaseInfoUserTypeStatisticService implements IBaseService<AdminCaseInfoUserTypeStatisticQO> {
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private CaseInfoRepository caseInfoRepository;


    @Override
    public ICommonResult execute(@Valid AdminCaseInfoUserTypeStatisticQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
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
                "\tCOUNT( user_type_id ) count,\n" +
                "\tuser_type_id \n" +
                "FROM\n" +
                "\tcase_info \n" +
                "WHERE\n" +
                "\t`status` = \"1\" \n" +
                "\tAND deleted_flag = 0 \n" +countrySql + createdAtGteSql + createdAtLteSql + genderSql + birthdayGteSql + birthdayLteSql+
                "\tGROUP BY\n" +
                "\tuser_type_id\n";
        List<Map> list = caseInfoRepository.querySqlToMap(sql);

        //获取所有用户分类
        Specification<UserTypeEntity> query = new BaseMysqlQuery<UserTypeEntity>()
                .append("status", "1").orderBy("sort", Sort.Direction.ASC);;
        List<UserTypeEntity> userTypeList = userTypeRepository.findAll(query);

        List<String> seriesDataArray= new ArrayList<>();
        List<String> yAxisDataArray= new ArrayList<>();
        userTypeList.forEach(t -> {
            yAxisDataArray.add(t.getName());
            Optional<Map> seriesDataMap =list.stream().filter(it->it.get("user_type_id").equals(t.getId())).findFirst();
            String count = "0";
            if(seriesDataMap.isPresent()){
                count = seriesDataMap.get().get("count").toString();
            }
            seriesDataArray.add(count);

        });
        AdminCaseInfoUserTypeStatisticDTO dto = new AdminCaseInfoUserTypeStatisticDTO();
        dto.setYAxisData(yAxisDataArray);
        dto.setSeriesData(seriesDataArray);

        return new CommonObjectResult<>(dto);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminCaseInfoUserTypeStatisticQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
