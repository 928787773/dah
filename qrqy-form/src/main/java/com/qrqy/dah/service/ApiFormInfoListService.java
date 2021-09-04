package com.qrqy.dah.service;

import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.dp.result.CommonListResult;
import com.qrqy.mysql.entity.FormCategoryEntity;
import com.qrqy.mysql.repository.FormCategoryRepository;
import com.qrqy.mysql.repository.FormInfoRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.FormInfoEntity;
import com.qrqy.mysql.manager.FormInfoManager;
import com.qrqy.dah.qo.ApiFormInfoListQO;
import com.qrqy.dah.dto.ApiFormInfoListDTO;

/**
 * route : api-form-info-list
 *
 * @author : QRQY
 * @date : 2021-08-23 11:51
 */

@Service
@Slf4j
@Validated
@Api(value = "不带分页的问卷列表", tags = {"运营端", "直播", "这里放筛选标签"})
public class ApiFormInfoListService implements IBaseService<ApiFormInfoListQO> {
    @Autowired
    private FormInfoRepository repository;

    @Autowired
    private FormCategoryRepository formCategoryRepository;



    @Override
    public ICommonResult execute(@Valid ApiFormInfoListQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        Specification query = new BaseMysqlQuery(qo)
                .append("status","1")
                .orderBy("createdAt", Sort.Direction.ASC);

        List<FormInfoEntity> list = repository.findAll(query);

        List<ApiFormInfoListDTO> content = list.stream().map(t -> {
            ApiFormInfoListDTO dto = new ApiFormInfoListDTO();
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
    public void validate(ApiFormInfoListQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
