package com.qrqy.mysql.manager;

import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.*;
import com.qrqy.mysql.enumeration.CaseInfoVisitTypeEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.repository.ImportExportTaskRepository;
import com.qrqy.mysql.repository.MedicalStaffRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import com.qrqy.mysql.repository.PastPhysicalConditionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : Luis
 * @date : 2021-06-29 11:32
 */
@Component
@Slf4j
@EnableAsync
public class MedicalStaffManager extends BaseMysqlManager<MedicalStaffRepository, MedicalStaffEntity> {

    @Value("${dp.uploadPath}")
    private String uploadPath;

    @Autowired
    private MedicalStaffRepository repository;
    @Autowired
    private ImportExportTaskManager importExportTaskManager;

    @Autowired
    private ImportExportTaskRepository importExportTaskRepository;
    @Autowired
    private MedicalStaffRepository medicalStaffRepository;



    @Override
    protected MedicalStaffRepository getRepository() {
        return repository;
    }

    /**
     * 方法名：importExcel
     * 功能：导入
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 11:45
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Async
    public void importExcel(Integer id) throws IOException, InvalidFormatException {
        log.info("导入解析开始，id:{}",id);
        ImportExportTaskEntity entity = importExportTaskManager.get(id);

        //校验数据
        List<List<String>> list= this.getUploadFileInfos(entity);
        log.info("结束，list:{}",list);
        if(list != null){
            //导入开始
            entity.setTaskStatus(ImportExportTaskTaskStatusEnum.start);
            importExportTaskRepository.save(entity);

            for (List<String> list1:list) {
                MedicalStaffEntity medicalStaffEntity = new MedicalStaffEntity();
                medicalStaffEntity.setStatus("1");
                medicalStaffEntity.setName(list1.get(0));
                String genderName = list1.get(1);
                int gender = 1;
                if ("男".equals(genderName)) {
                    gender = 1;
                }
                if ("女".equals(genderName)) {
                    gender = 2;
                }
                medicalStaffEntity.setGender(gender);
                medicalStaffEntity.setHospital(list1.get(2));
                medicalStaffEntity.setDepartment(list1.get(3));
                medicalStaffEntity.setPoliticCountenance(list1.get(4));
                medicalStaffEntity.setPositionContent(list1.get(5));
                medicalStaffEntity.setPosition(list1.get(6));
                medicalStaffEntity.setPhonenum(list1.get(7));
                medicalStaffEntity.setForeignLanguageLevel(list1.get(8));
                medicalStaffEntity.setNote(list1.get(9));
                medicalStaffRepository.save(medicalStaffEntity);
            }

            entity.setTaskStatus(ImportExportTaskTaskStatusEnum.finish);
            entity.setFinishAt(LocalDateTime.now());
            importExportTaskRepository.save(entity);
            log.info("导入文件解析成功！");
        }

        log.info("导入文件数据错误！");
    }

    public List<List<String>> getUploadFileInfos(ImportExportTaskEntity importExportTaskEntity) throws IOException, InvalidFormatException {
        importExportTaskEntity.setTaskStatus(ImportExportTaskTaskStatusEnum.check);
        importExportTaskRepository.save(importExportTaskEntity);

        String filePath = uploadPath + importExportTaskEntity.getFilePath();
        log.info("校验数据开始，filePath:{}",filePath);
        List<List<String>> list = new ArrayList<>();
        InputStream inputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        //数据格式是否有错误
        Boolean isError = false;
        //总列数
        int col = sheet.getRow(0).getLastCellNum();
        log.info("总列数 col{}:",col);
        //获取sheet的行数
        int rows = sheet.getPhysicalNumberOfRows();
        log.info("总行数 rows{}:",rows);
        for (int i = 0; i < rows; i++) {
            //过滤表头行
            if (i == 0) {
                continue;
            }
            //获取当前行的数据
            Row row = sheet.getRow(i);
            if(row.getCell(0) == null
                    && row.getCell(1) == null
                    && row.getCell(2) == null
                    && row.getCell(3) == null
                    && row.getCell(4) == null
            ){
                i = rows;
            }else{
                List<String> objects = new ArrayList<>();
//                Object[] objects = new Object[row.getPhysicalNumberOfCells()];
                String errorData = "";
                String notFoundData = "";
                for (int index = 0; index < col; index++) {
                    log.info("index {}:",index);
                    Cell cell = row.getCell(index);
                    log.info("cell {}:",cell);
                    String data = null;
                    if(cell != null){
                        log.info("cellStyle {}:",cell.getCellStyle());
                        cell.setCellType(CellType.STRING);
                        data = cell.toString().trim();
                    }
                    log.info("data {}:",data);
                    objects.add(index,data);

                    log.info("cell {}:",cell);
                    if(data == null && index != 9){
                        isError = true;
                        notFoundData = " 缺少必填数据";
                    }
                    if (index == 1 && data != null) {
                        //性别
                        if (!"男".equals(data) && !"女".equals(data)) {
                            isError = true;
                            errorData = errorData + " 性别错误";
                        }


                    }
                    if (index == 7 && data != null) {
                        //电话
                        Specification query = new BaseMysqlQuery().append("phonenum",data);
                        Optional<MedicalStaffEntity> beanOptional = medicalStaffRepository.findOne(query);
                        if (beanOptional.isPresent()) {
                            isError = true;
                            errorData = errorData + " 电话已存在";
                        }
                    }
                }
                errorData = errorData + notFoundData;
                objects.add(10,errorData);
                list.add(objects);
            }

        }
        if(isError){
            Row row = sheet.getRow(0);
            List<String> arr = new ArrayList<>();
            for (Cell cell:row) {
                arr.add(cell.toString());
            }
            arr.add("结果");
            log.info("结果{}:",arr);
            String errorFileName = importExportTaskManager.exportErrorResultFile(list,arr);
            importExportTaskEntity.setFilePath(errorFileName);
            importExportTaskEntity.setTaskStatus(ImportExportTaskTaskStatusEnum.dataError);
            importExportTaskRepository.save(importExportTaskEntity);
            return null;
        }
        log.info("导入文件数据校验结束！");
        return list;
    }

}
