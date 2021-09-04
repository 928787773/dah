package com.qrqy.mysql.manager;

import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.DrugEntity;
import com.qrqy.mysql.entity.ImportExportTaskEntity;
import com.qrqy.mysql.entity.MaterialEntity;
import com.qrqy.mysql.entity.MedicalStaffEntity;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.repository.ImportExportTaskRepository;
import com.qrqy.mysql.repository.MaterialRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : Luis
 * @date : 2021-06-29 14:37
 */
@Slf4j
@Component
@EnableAsync
public class MaterialManager extends BaseMysqlManager<MaterialRepository, MaterialEntity> {

    @Value("${dp.uploadPath}")
    private String uploadPath;

    @Autowired
    private MaterialRepository repository;

    @Autowired
    private ImportExportTaskManager importExportTaskManager;

    @Autowired
    private ImportExportTaskRepository importExportTaskRepository;



    @Override
    protected MaterialRepository getRepository() {
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
        entity.setTaskStatus(ImportExportTaskTaskStatusEnum.check);
        importExportTaskRepository.save(entity);

        //校验数据
        List<List<String>> list= this.getUploadFileInfos(entity);
        log.info("校验结束，list:{}",list);
        if(list != null){
            entity.setTaskStatus(ImportExportTaskTaskStatusEnum.start);
            importExportTaskRepository.save(entity);
            for (List<String> list1:list) {
                MaterialEntity materialEntity = new MaterialEntity();
                materialEntity.setStatus("1");
                materialEntity.setName(list1.get(0));
                materialEntity.setSpecs(list1.get(1));
                materialEntity.setAmount(Integer.parseInt(list1.get(2)));
                materialEntity.setUnitPrice(new BigDecimal(list1.get(3)));
                materialEntity.setNote(list1.get(4));
                repository.save(materialEntity);
            }

            entity.setTaskStatus(ImportExportTaskTaskStatusEnum.finish);
            entity.setFinishAt(LocalDateTime.now());
            importExportTaskRepository.save(entity);
            log.info("导入文件解析成功！");
        }else{
            log.info("导入文件数据错误！");
        }
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
            ){
                i = rows;
            }else{
                List<String> objects = new ArrayList<>();
                String errorData = "";
                String notFoundData = "";
                for (int index = 0; index < col; index++) {
                    log.info("index {}:",index);
                    Cell cell = row.getCell(index);
                    String data = null;
                    if(cell != null){
                        cell.setCellType(CellType.STRING);
                        data = cell.toString().trim();
                    }
                    log.info("data {}:",data);
                    log.info("cell {}:",cell);
                    objects.add(index,data);

                    log.info("cell {}:",cell);
                    if(data == null && index != 4){
                        isError = true;
                        notFoundData = " 缺少必填数据";
                    }
                    if(index == 0){
                        //名称
                        Specification query = new BaseMysqlQuery().append("name",data);
                        Optional<MaterialEntity> beanOptional = repository.findOne(query);
                        if (beanOptional.isPresent()) {
                            isError = true;
                            errorData = errorData + " 物资名称已存在";
                        }
                    }
                }
                errorData = errorData + notFoundData;
                objects.add(5,errorData);
                list.add(objects);
            }

        }
        if(isError){
            Row row = sheet.getRow(0);
            List<String> arr = new ArrayList<>();
            for (Cell cell:row) {
                arr.add(cell.toString());
            }
            arr.add("错误信息");
            log.info("结果表头{}:",arr);
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
