package com.qrqy.dah.service;

import com.qrqy.dp.result.CommonListResult;
import com.qrqy.mysql.repository.DiseaseTypeRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.DiseaseTypeEntity;
import com.qrqy.mysql.manager.DiseaseTypeManager;
import com.qrqy.dah.qo.AdminDiseaseTypeListQO;
import com.qrqy.dah.dto.AdminDiseaseTypeListDTO;

/**
 * route : admin-disease-type-list
 *
 * @author : QRQY
 * @date : 2021-06-25 14:47
 */

@Service
@Slf4j
@Validated
@Api(value = "疾病范畴", tags = {"管理端", "疾病范畴", "这里放筛选标签"})
public class AdminDiseaseTypeListService implements IBaseService<AdminDiseaseTypeListQO> {
    @Autowired
    private DiseaseTypeRepository repository;

    @Override
    public ICommonResult execute(@Valid AdminDiseaseTypeListQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        Specification query = new BaseMysqlQuery()
                .append("status", "1").orderBy("sort", Sort.Direction.ASC);;

        List<DiseaseTypeEntity> diseaseTypeList = repository.findAll(query);

        List<AdminDiseaseTypeListDTO> content = diseaseTypeList.stream().map(t -> {
            AdminDiseaseTypeListDTO dto = new AdminDiseaseTypeListDTO();
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
    public void validate(AdminDiseaseTypeListQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
