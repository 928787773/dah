package com.qrqy.mysql.manager;

import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.*;
import com.qrqy.mysql.enumeration.CaseInfoVisitTypeEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.repository.*;
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

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author : Luis
 * @date : 2021-06-25 14:08
 */
@Component
@Slf4j
@EnableAsync
public class CaseInfoManager extends BaseMysqlManager<CaseInfoRepository, CaseInfoEntity> {

    @Value("${dp.uploadPath}")
    private String uploadPath;

    @Autowired
    private CaseInfoRepository repository;


    @Autowired
    private ImportExportTaskManager importExportTaskManager;
    @Autowired
    private CaseInfoRepository caseInfoRepository;


    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private DiseaseTypeRepository diseaseTypeRepository;
    @Autowired
    private DiseaseOutcomeRepository diseaseOutcomeRepository;

    @Autowired
    private PastPhysicalConditionRepository pastPhysicalConditionRepository;

    @Autowired
    private TreatmentInterventRepository treatmentInterventRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private ImportExportTaskRepository importExportTaskRepository;


    @Override
    protected CaseInfoRepository getRepository() {
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
        List<List<String>> list= getUploadFileInfos(entity);
        log.info("结束，list:{}",list);
        if(list != null){
            //导入开始
            entity.setTaskStatus(ImportExportTaskTaskStatusEnum.start);
            importExportTaskRepository.save(entity);
            for (List<String> list1:list) {
                CaseInfoEntity caseInfoEntity = new CaseInfoEntity();
                caseInfoEntity.setStatus("1");
                caseInfoEntity.setName(list1.get(0));
                caseInfoEntity.setCountryId(Integer.parseInt(list1.get(1)));
                caseInfoEntity.setGender(list1.get(2));
                caseInfoEntity.setBirthday(LocalDate.parse(list1.get(3)));
                caseInfoEntity.setUserTypeId(Integer.parseInt(list1.get(4)));
                caseInfoEntity.setDiseaseTypeId(Integer.parseInt(list1.get(5)));
                caseInfoEntity.setDiseaseContent(list1.get(6));
                caseInfoEntity.setDiseaseOutcomeId(Integer.parseInt(list1.get(7)));
                caseInfoEntity.setJoinProject(list1.get(8));
                caseInfoEntity.setDiseaseSeverity(list1.get(9));
                caseInfoEntity.setIsTcmIntervention(Integer.parseInt(list1.get(10)));
                caseInfoEntity.setInspectionResult(list1.get(11));
                caseInfoEntity.setPastPhysicalConditionId(Integer.parseInt(list1.get(12)));
                caseInfoEntity.setPastPhysicalConditionContent(list1.get(13));
                caseInfoEntity.setDrugUseInfo(list1.get(14));
                caseInfoEntity.setTreatmentInterventId(Integer.parseInt(list1.get(15)));
                caseInfoEntity.setVisitType(CaseInfoVisitTypeEnum.parse(list1.get(16)));
                caseInfoEntity.setDiseaseHistory(list1.get(17));
                caseInfoEntity.setNote(list1.get(18));
                caseInfoRepository.save(caseInfoEntity);
            }

            entity.setTaskStatus(ImportExportTaskTaskStatusEnum.finish);
            entity.setFinishAt(LocalDateTime.now());
            importExportTaskRepository.save(entity);

            log.info("导入文件解析成功！");
        }
        log.info("导入文件数据有错误！");
    }


    public List<List<String>> getUploadFileInfos(ImportExportTaskEntity importExportTaskEntity) throws IOException, InvalidFormatException {
        //数据校验中
        importExportTaskEntity.setTaskStatus(ImportExportTaskTaskStatusEnum.check);
        importExportTaskRepository.save(importExportTaskEntity);

        String filPath = uploadPath + importExportTaskEntity.getFilePath();
        log.info("校验数据开始，filPath:{}",filPath);
        List<List<String>> list = new ArrayList<>();
        List<List<String>> errorObjectsList = new ArrayList<>();
        InputStream inputStream = new FileInputStream(filPath);
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
                List<String> errorObjects = new ArrayList<>();
                String errorData = "";
                String notFoundData = "";
                for (int index = 0; index < col; index++) {
                    log.info("index {}:",index);
                    Cell cell = row.getCell(index);
                    String data = null;
                    log.info("cell {}:",cell);
                    if(cell != null){
                        if (index == 3){

                            if(DateUtil.isCellDateFormatted(cell)){
                                data = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                            }else{
                                isError = true;
                                errorData = errorData+" 生日请填写日期";
                            }

                        }else{
                            if(cell.getCellTypeEnum().equals(CellType.NUMERIC)){
                                DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                                data = decimalFormat.format(cell.getNumericCellValue());
                            }else{
                                cell.setCellType(CellType.STRING);
                                data = cell.toString().trim();
                            }
                        }

                    }
                    log.info("data {}:",data);
                    objects.add(index,data);
                    errorObjects.add(index,data);

                    if(data == null && index != 18 && index != 13){
                        isError = true;
                        notFoundData = " 缺少必填数据";
                    }
                    if (index == 1 && data != null) {
                        //国籍
                        Specification query = new BaseMysqlQuery().append("shortName",data);
                        Optional<CountryEntity> beanOptional = countryRepository.findOne(query);
                        if (!beanOptional.isPresent()) {
                            isError = true;
                            errorData = errorData + " 国籍错误";
                        }else{
                            objects.add(index,beanOptional.get().getId().toString());
                        }
                    }
                    if (index == 2 && data != null) {
                        //性别
                        if (!"男".equals(data) && !"女".equals(data)) {
                            isError = true;
                            errorData = errorData + " 性别错误";
                        }else{
                            if ("男".equals(data)) {
                                objects.add(index,"1");
                            }
                            if ("女".equals(data)) {
                                objects.add(index,"2");
                            }
                        }


                    }
                    if (index == 4 && data != null) {
                        //人员性质
                        Specification query = new BaseMysqlQuery().append("name",data);
                        Optional<UserTypeEntity> beanOptional = userTypeRepository.findOne(query);
                        if (!beanOptional.isPresent()) {
                            isError = true;
                            errorData = errorData + " 人员性质错误";
                        }else{
                            objects.add(index,beanOptional.get().getId().toString());
                        }
                    }
                    if (index == 5 && data != null) {
                        //疾病范畴
                        Specification query = new BaseMysqlQuery().append("name",data);
                        Optional<DiseaseTypeEntity> beanOptional = diseaseTypeRepository.findOne(query);
                        if (!beanOptional.isPresent()) {
                            isError = true;
                            errorData = errorData + " 疾病范畴错误";
                        }else{
                            objects.add(index,beanOptional.get().getId().toString());
                        }
                    }
                    if (index == 7 && data != null) {
                        //疾病转归
                        Specification query = new BaseMysqlQuery().append("name",data);
                        Optional<DiseaseOutcomeEntity> beanOptional = diseaseOutcomeRepository.findOne(query);
                        if (!beanOptional.isPresent()) {
                            isError = true;
                            errorData = errorData + " 疾病转归错误";
                        }else{
                            objects.add(index,beanOptional.get().getId().toString());
                        }
                    }
                    if (index == 9 && data != null) {
                        //严重程度
                        String diseaseSeverity = null;
                        if("其他".equals(data)){
                            diseaseSeverity = "其他";
                        }
                        if ("1".equals(data)) {
                            diseaseSeverity = "1";
                        }
                        if ("2".equals(data)) {
                            diseaseSeverity = "2";
                        }
                        if ("3".equals(data)) {
                            diseaseSeverity = "3";
                        }
                        if ("4".equals(data)) {
                            diseaseSeverity = "4";
                        }
                        if ("5".equals(data)) {
                            diseaseSeverity = "5";
                        }
                        objects.add(index,diseaseSeverity);
                        if(diseaseSeverity == null){
                            errorData = errorData + " 疾病严重程度错误";
                        }
                    }
                    if (index == 10 && data != null) {
                        //是否为中医干预
                        if ("是".equals(data)) {
                            objects.add(index,"1");
                        }
                        if ("否".equals(data)) {
                            objects.add(index,"0");
                        }

                    }


                    if (index == 12 && data != null) {
                        //既往情况
                        Specification query = new BaseMysqlQuery().append("name",data);
                        Optional<PastPhysicalConditionEntity> beanOptional = pastPhysicalConditionRepository.findOne(query);
                        if (!beanOptional.isPresent()) {
                            isError = true;
                            errorData = errorData + " 既往情况错误";
                        }else{
                            objects.add(index,beanOptional.get().getId().toString());
                        }
                    }
                    if (index == 15 && data != null) {
                        //治疗干预方式
                        Specification query = new BaseMysqlQuery().append("name",data);
                        Optional<TreatmentInterventEntity> beanOptional = treatmentInterventRepository.findOne(query);
                        if (!beanOptional.isPresent()) {
                            isError = true;
                            errorData = errorData + " 治疗干预方式错误";
                        }else{
                            objects.add(index,beanOptional.get().getId().toString());
                        }
                    }
                    if (index == 16 && data != null) {
                        //就诊情况
                        if (data.equals("初诊")) {
                            objects.add(index,"FIRST");
                        }
                        if (data.equals("复诊")) {
                            objects.add(index,"RETURN");
                        }
                    }
                }
                errorData = errorData + notFoundData;
                objects.add(19,errorData);
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
            String errorFileName = importExportTaskManager.exportErrorResultFile(errorObjectsList,arr);
            importExportTaskEntity.setFilePath(errorFileName);
            importExportTaskEntity.setTaskStatus(ImportExportTaskTaskStatusEnum.dataError);
            importExportTaskRepository.save(importExportTaskEntity);
            return null;
        }
        log.info("导入文件数据校验结束！");
        return list;
    }

}
