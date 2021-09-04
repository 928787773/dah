package com.qrqy.dah.service;

import com.alibaba.fastjson.JSONObject;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.mysql.entity.ImportExportTaskEntity;
import com.qrqy.mysql.enumeration.ImportExportTaskFileTypeEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTypeEnum;
import com.qrqy.mysql.manager.ImportExportTaskManager;
import com.qrqy.mysql.repository.FormInfoRepository;
import com.qrqy.mysql.repository.ImportExportTaskRepository;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.qrqy.mysql.manager.FormInfoManager;
import com.qrqy.dah.qo.AdminExportExcelQO;
/**
 * route : admin-export-excel
 *
 * @author : QRQY
 * @date : 2021-07-01 16:04
 */
@Service
@Slf4j
@Validated
@Api(value = "导出", tags = {"管理端", "导出", "这里放筛选标签"})
public class AdminExportExcelService implements IBaseService<AdminExportExcelQO> {
    @Autowired
    private FormInfoManager manager;

    @Autowired
    private ImportExportTaskRepository repository;


    @SneakyThrows
    @Override
    public ICommonResult execute(@Valid AdminExportExcelQO qo,HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);

        ImportExportTaskEntity entity = new ImportExportTaskEntity();
        BeanUtils.copyProperties(qo, entity, "status");
        entity.setStatus("1");
        entity.setTaskStatus(ImportExportTaskTaskStatusEnum.noStart);
        entity.setType(ImportExportTaskTypeEnum.EXPORT);
        entity.setFileType(ImportExportTaskFileTypeEnum.formCategory);
        String json = JSONObject.toJSONString(qo);
        entity.setNote(json);
        repository.save(entity);

        //导出excel
        manager.exportable(entity.getId());

        return new CommonObjectResult<>("success");

    }


    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(AdminExportExcelQO qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }



}
