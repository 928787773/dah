package com.qrqy.mysql.manager;

import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.CountryEntity;
import com.qrqy.mysql.entity.DeviceEntity;
import com.qrqy.mysql.entity.DrugEntity;
import com.qrqy.mysql.entity.ImportExportTaskEntity;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.repository.DeviceRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import com.qrqy.mysql.repository.ImportExportTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.codehaus.stax2.ri.typed.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
public class DeviceManager extends BaseMysqlManager<DeviceRepository, DeviceEntity> {
    @Value("${dp.uploadPath}")
    private String uploadPath;

    @Autowired
    private DeviceRepository repository;

    @Autowired
    private ImportExportTaskManager importExportTaskManager;

    @Autowired
    private ImportExportTaskRepository importExportTaskRepository;

    @Override
    protected DeviceRepository getRepository() {
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
                DeviceEntity deviceEntity = new DeviceEntity();
                deviceEntity.setStatus("1");
                deviceEntity.setName(list1.get(0));
                deviceEntity.setType(list1.get(1));
                deviceEntity.setDeviceNo(list1.get(2));
                deviceEntity.setAmount(Integer.parseInt(list1.get(3)));
                deviceEntity.setUnitPrice(new BigDecimal(list1.get(4)));
                deviceEntity.setNote(list1.get(5));
                repository.save(deviceEntity);
            }

            entity.setTaskStatus(ImportExportTaskTaskStatusEnum.finish);
            entity.setFinishAt(LocalDateTime.now());
            importExportTaskRepository.save(entity);
        }

        log.info("导入文件解析成功！");
    }

    public List<List<String>> getUploadFileInfos(ImportExportTaskEntity importExportTaskEntity) throws IOException, InvalidFormatException {
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
                        if(index == 3 || index == 4){
                            if(cell.getCellTypeEnum() != CellType.NUMERIC){
                                isError = true;
                                notFoundData = " 缺少必填数据";
                            }
                        }

                        if(index == 3){
                            if(cell.getCellTypeEnum().equals(CellType.NUMERIC)){
                                DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                                data = decimalFormat.format(cell.getNumericCellValue());
                            }else{
                                cell.setCellType(CellType.STRING);
                                data = cell.toString().trim();
                                isError = true;
                                errorData = errorData + " 数量请输入数字";
                            }
                        }else{
                            cell.setCellType(CellType.STRING);
                            data = cell.toString().trim();
                        }
                    }
                    objects.add(index,data);

                    if(data == null && index != 5){
                        isError = true;
                        notFoundData = " 缺少必填数据";
                    }
                    if(index == 0){
                        //名称
                        Specification query = new BaseMysqlQuery().append("name",data);
                        Optional<DeviceEntity> beanOptional = repository.findOne(query);
                        if (beanOptional.isPresent()) {
                            isError = true;
                            errorData = errorData + " 设备名称已存在";
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
