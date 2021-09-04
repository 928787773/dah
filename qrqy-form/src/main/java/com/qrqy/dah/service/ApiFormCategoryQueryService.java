package com.qrqy.dah.service;

import com.qrqy.dah.dto.ApiFormCategoryListDTO;
import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.exception.BizValidationException;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.mysql.repository.FormCategoryRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.FormCategoryEntity;
import com.qrqy.mysql.manager.FormCategoryManager;
import com.qrqy.dah.qo.FormCategoryQueryQO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : api-form-category-query
 *
 * @author : QRQY
 * @date : 2021-06-24 16:09
 */

@Service
@Slf4j
@Validated
@Api(value = "数据大屏问卷方向列表", tags = {"数据大屏", "问卷方向", "这里放筛选标签"})
public class ApiFormCategoryQueryService implements IBaseService<FormCategoryQueryQO> {
    @Autowired
    private FormCategoryRepository repository;

    @Override
    public ICommonResult execute(@Valid FormCategoryQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        //只有外部展示问卷方向列表
        Specification query = new BaseMysqlQuery(qo)
                .append("status", "1")
                .orderBy("createdAt", Sort.Direction.DESC);

        List<FormCategoryEntity> list = repository.findAll(query);

        List<ApiFormCategoryListDTO> content = list.stream().map(t -> {
            ApiFormCategoryListDTO dto = new ApiFormCategoryListDTO();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());

        return new CommonObjectResult<>(content);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormCategoryQueryQO qo, IBaseUser curUser) {
        if (qo.getType() == null) {
            throw new BizException(5801, "缺少领域");
        }
    }
}
