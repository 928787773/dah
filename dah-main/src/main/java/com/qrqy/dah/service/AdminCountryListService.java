package com.qrqy.dah.service;

import com.qrqy.dp.result.CommonListResult;
import com.qrqy.mysql.repository.CountryRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.CountryEntity;
import com.qrqy.dah.qo.AdminCountryListQO;
import com.qrqy.dah.dto.AdminCountryListDTO;

/**
 * route : admin-country-list
 *
 * @author : QRQY
 * @date : 2021-06-25 14:20
 */

@Service
@Slf4j
@Validated
@Api(value = "国家列表", tags = {"管理端", "国家", "这里放筛选标签"})
public class AdminCountryListService implements IBaseService<AdminCountryListQO> {
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public ICommonResult execute(@Valid AdminCountryListQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        Specification query = new BaseMysqlQuery()
                .append("status", "1").orderBy("sort", Sort.Direction.ASC);

        List<CountryEntity> countryList = countryRepository.findAll(query);

        List<AdminCountryListDTO> content = countryList.stream().map(t -> {
            AdminCountryListDTO dto = new AdminCountryListDTO();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());

        return new CommonListResult<>(content);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminCountryListQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
