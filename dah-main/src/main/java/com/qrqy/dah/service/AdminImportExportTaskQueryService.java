package com.qrqy.dah.service;

import com.qrqy.mysql.enumeration.ImportExportTaskFileTypeEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTypeEnum;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import com.qrqy.mysql.entity.ImportExportTaskEntity;
import com.qrqy.mysql.manager.ImportExportTaskManager;
import com.qrqy.dah.qo.ImportExportTaskQueryQO;
import com.qrqy.dah.dto.ImportExportTaskListDTO;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * route : admin-import-export-task-query
 *
 * @author : QRQY
 * @date : 2021-07-09 17:36
 */

@Service
@Slf4j
@Validated
@Api(value = "导入导出记录", tags = {"管理端", "导入记录", "导出记录"})
public class AdminImportExportTaskQueryService implements IBaseService<ImportExportTaskQueryQO> {
    @Value("${dp.uploadUrl}")
    private String uploadUrl;

    @Value("${dp.exportUrl}")
    private String exportUrl;

    @Value("${dp.errorResultUrl}")
    private String errorResultUrl;



    @Autowired
    private ImportExportTaskManager manager;

    @Override
    public ICommonResult execute(@Valid ImportExportTaskQueryQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        Specification query = new BaseMysqlQuery(qo)
                .append("taskStatusNeq",ImportExportTaskTaskStatusEnum.noStart)
                .orderBy("createdAt", Sort.Direction.DESC);

        Page<ImportExportTaskEntity> page = manager.query(query, qo.getPageable(), curUser);

        List<ImportExportTaskListDTO> content = page.getContent().stream().map(t -> {
            ImportExportTaskListDTO dto = new ImportExportTaskListDTO();
            BeanUtils.copyProperties(t, dto);
            if(dto.getType() == ImportExportTaskTypeEnum.IMPORT){
                if(dto.getTaskStatus() == ImportExportTaskTaskStatusEnum.dataError){
                    dto.setFilePath(errorResultUrl + dto.getFilePath());
                }
                if (dto.getTaskStatus().equals(ImportExportTaskTaskStatusEnum.finish)){
                    dto.setFilePath(uploadUrl + dto.getFilePath());
                }
            }
            if(dto.getType() == ImportExportTaskTypeEnum.EXPORT && dto.getTaskStatus().equals(ImportExportTaskTaskStatusEnum.finish)){
                dto.setFilePath(exportUrl + dto.getFilePath());
            }

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
    public void validate(ImportExportTaskQueryQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}
