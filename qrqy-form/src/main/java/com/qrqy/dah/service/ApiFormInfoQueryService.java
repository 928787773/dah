package com.qrqy.dah.service;

import com.qrqy.dah.dto.ApiFormInfoListDTO;
import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.mysql.entity.FormCategoryEntity;
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
import java.util.Optional;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.FormInfoEntity;
import com.qrqy.mysql.manager.FormInfoManager;
import com.qrqy.dah.qo.FormInfoQueryQO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : api-form-info-query
 *
 * @author : QRQY
 * @date : 2021-06-24 16:15
 */
@Service
@Slf4j
@Validated
@Api(value = "数据大屏问卷列表", tags = {"管理端", "问卷", "这里放筛选标签"})
public class ApiFormInfoQueryService implements IBaseService<FormInfoQueryQO> {
    @Autowired
    private FormInfoManager manager;

    @Autowired
    private FormCategoryRepository formCategoryRepository;



    @Override
    public ICommonResult execute(@Valid FormInfoQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        if(qo.getFormCategoryId() == null){
            Specification query = new BaseMysqlQuery().append("type", FormCategoryTypeEnum.EXTERNAL);

            Optional<FormCategoryEntity> formCategory = formCategoryRepository.findOne(query);
            qo.setFormCategoryId(formCategory.get().getId());
        }
        Specification query = new BaseMysqlQuery(qo).append("status","1")
                .orderBy("createdAt", Sort.Direction.ASC);

        Page<FormInfoEntity> page = manager.query(query, qo.getPageable(), curUser);

        List<ApiFormInfoListDTO> content = page.getContent().stream().map(t -> {
            ApiFormInfoListDTO dto = new ApiFormInfoListDTO();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());

        return new CommonPagingResult<>(content, page);

    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(FormInfoQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
