package com.qrqy.dah.service;

import com.qrqy.dah.dto.AdminCaseInfoDiseaseOutcomeStatisticDTO;
import com.qrqy.dah.dto.AdminCaseInfoGenderWorldMapStatisticDTO;
import com.qrqy.dah.dto.CaseListDTO;
import com.qrqy.dah.qo.AdminCaseInfoGenderWorldMapStatisticQO;
import com.qrqy.dah.utils.CommonUtils;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.CommonListResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import com.qrqy.mysql.entity.CaseInfoEntity;
import com.qrqy.mysql.entity.CountryEntity;
import com.qrqy.mysql.repository.CaseInfoRepository;
import com.qrqy.mysql.repository.CountryRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * route : admin-case-info-gender-world-map-statistic
 *
 * @author : QRQY
 * @date : 2021-06-29 17:34
 */
@Service
@Slf4j
@Validated
@Api(value = "世界地图性别统计图", tags = {"管理端", "统计", "性别"})
public class AdminCaseInfoGenderWorldMapStatisticService implements IBaseService<AdminCaseInfoGenderWorldMapStatisticQO> {
    @Autowired
    private CaseInfoRepository caseInfoRepository;



    @Override
    public ICommonResult execute(@Valid AdminCaseInfoGenderWorldMapStatisticQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        List<AdminCaseInfoGenderWorldMapStatisticDTO> result = new ArrayList<>();
        String sql = "SELECT\n" +
                "\tcount( CASE gender WHEN '1' THEN case_info.country_id END ) AS count1,\n" +
                "\tcount( CASE gender WHEN '2' THEN case_info.country_id END ) AS count2,\n" +
                "\tcountry.short_name,\n" +
                "\tcountry.lat,\n" +
                "\tcountry.lon \n" +
                "FROM\n" +
                "\tcase_info,\n" +
                "\tcountry \n" +
                "WHERE\n" +
                "\tcase_info.`status` = \"1\" \n" +
                "\tAND case_info.deleted_flag = 0 \n" +
                "\tAND case_info.country_id = country.id \n" +
                "GROUP BY\n" +
                "\tcase_info.country_id";
        List<Map> list = caseInfoRepository.querySqlToMap(sql);

        // 组装数据
        list.forEach(t->{
            AdminCaseInfoGenderWorldMapStatisticDTO response = new AdminCaseInfoGenderWorldMapStatisticDTO();
            response.setName(t.get("short_name").toString());
            response.setLat(t.get("lat").toString());
            response.setLon(t.get("lon").toString());
            response.setCountGender1(t.get("count1").toString());
            response.setCountGender2(t.get("count2").toString());
            result.add(response);
        });
        return new CommonListResult<>(result);

    }

}
