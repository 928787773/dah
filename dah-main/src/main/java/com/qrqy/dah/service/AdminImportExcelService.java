package com.qrqy.dah.service;

import com.alibaba.fastjson.JSONObject;
import com.qrqy.dah.qo.AdminImportExcelQO;
import com.qrqy.dp.exception.BizException;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import com.qrqy.mysql.entity.ImportExportTaskEntity;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTypeEnum;
import com.qrqy.mysql.manager.*;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * route : admin-import-excel
 *
 * @author : QRQY
 * @date : 2021-07-01 16:04
 */
@Service
@Slf4j
@Validated
@Api(value = "导入", tags = {"管理端", "导入", "这里放筛选标签"})
public class AdminImportExcelService implements IBaseService<AdminImportExcelQO> {

    @Value("${dp.uploadPath}")
    private String uploadPath;

    @Autowired
    private CaseInfoManager caseInfoManager;
    @Autowired
    private MedicalStaffManager medicalStaffManager;

    @Autowired
    private MaterialManager materialManager;

    @Autowired
    private DrugManager drugManager;

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private ImportExportTaskManager importExportTaskManager;


    @SneakyThrows
    @Override
    public ICommonResult execute(@Valid AdminImportExcelQO qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);
        String filPath = qo.getFilePath();

        //简单校验列数是否对应
        InputStream inputStream = new FileInputStream(uploadPath + filPath);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        //总列数
        int col = sheet.getRow(0).getLastCellNum();

        ImportExportTaskEntity entity = new ImportExportTaskEntity();
        BeanUtils.copyProperties(qo, entity, "status");
        entity.setFilePath(filPath);
        entity.setStatus("1");
        entity.setTaskStatus(ImportExportTaskTaskStatusEnum.noStart);
        entity.setType(ImportExportTaskTypeEnum.IMPORT);
        String json = JSONObject.toJSONString(qo);
        entity.setNote(json);
        importExportTaskManager.save(entity, curUser);

        switch (qo.getFileType()){
            case caseInfo:
                //病例
                if(col != 19){
                    throw new BizException(5001, "导入文件格式错误");
                }else{
                    caseInfoManager.importExcel(entity.getId());
                }
                break;
            case medicalStaff:
                //医务人员
                if(col != 10){
                    throw new BizException(5001, "导入文件格式错误");
                }else{
                    medicalStaffManager.importExcel(entity.getId());
                }
                break;
            case material:
                //物资
                if(col != 5){
                    throw new BizException(5001, "导入文件格式错误");
                }else {
                materialManager.importExcel(entity.getId());
                }
                break;
            case drug:
                //药品
                if(col != 6){
                    throw new BizException(5001, "导入文件格式错误");
                }else {
                    drugManager.importExcel(entity.getId());
                }
                break;
            case device:
                //设备
                if(col!= 6){
                    throw new BizException(5001, "导入文件格式错误");
                }else {
                    deviceManager.importExcel(entity.getId());
                }
                break;
            default:
        }
        return new CommonObjectResult<>("success");

    }




}
