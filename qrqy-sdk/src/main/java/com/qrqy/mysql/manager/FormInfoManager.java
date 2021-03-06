package com.qrqy.mysql.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.*;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.repository.*;
import com.qrqy.dp.mysql.BaseMysqlManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * @author : Luis
 * @date : 2021-06-17 16:32
 */
@Component
@Slf4j
@EnableAsync
public class FormInfoManager extends BaseMysqlManager<FormInfoRepository, FormInfoEntity> {

    @Value("${dp.exportPath}")
    private String exportPath;

    @Autowired
    private FormInfoRepository repository;


    @Autowired
    private FormCategoryManager formCategoryManager;


    @Autowired
    private FormRecordRepository formRecordRepository;


    @Autowired
    private ImportExportTaskManager importExportTaskManager;

    @Autowired
    private ImportExportTaskRepository importExportTaskRepository;

    @Autowired
    private FormLevelRuleRepository formLevelRuleRepository;





    @Override
    protected FormInfoRepository getRepository() {
        return repository;
    }


    /**
     *
     * @param id
     */
    @Async
    public void exportable(Integer id) throws IOException {

        ImportExportTaskEntity entity = importExportTaskManager.get(id);
        entity.setTaskStatus(ImportExportTaskTaskStatusEnum.start);
        importExportTaskRepository.save(entity);

        List<List<String>> importlist = new ArrayList<>();
        List<String> attributeNames = new ArrayList<>();
        attributeNames.add("??????");
        attributeNames.add("??????");
        attributeNames.add("?????????");

        JSONObject noteJson = JSON.parseObject(entity.getNote());
        Integer formCategoryId= noteJson.getInteger("formCategoryId");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createdAtGte = null;
        LocalDateTime createdAtLte = null;
        if(noteJson.containsKey("startDate")){
            createdAtGte= LocalDateTime.parse(noteJson.getString("startDate")+" 00:00:00",df);
        }

        if(noteJson.containsKey("endDate")){
            createdAtLte= LocalDateTime.parse(noteJson.getString("endDate")+" 00:00:00",df);
        }

        //???????????????????????????
        FormCategoryEntity formCategoryEntity = formCategoryManager.get(formCategoryId);

        //?????????????????????????????????
        Specification queryFormInfo = new BaseMysqlQuery()
                .append("formCategoryId", formCategoryId).orderBy("createdAt",Sort.Direction.ASC);

        List<FormInfoEntity> formInfoEntityList = repository.findAll(queryFormInfo);
        if(!formInfoEntityList.isEmpty()){
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<Integer> formInfoIdList = formInfoEntityList.stream().map(FormInfoEntity::getId).distinct().collect(Collectors.toList());


            //????????????????????????
            Specification queryFormRecord = new BaseMysqlQuery()
                    .append("formInfoIdIn", formInfoIdList)
                    .append("createdAtGte", createdAtGte)
                    .append("createdAtLte", createdAtLte)
                    .append("status", "1").orderBy("createdAt",Sort.Direction.ASC);

            List<FormRecordEntity> formRecordEntityList = formRecordRepository.findAll(queryFormRecord);
            if(!formRecordEntityList.isEmpty()){
                //????????????
                Integer index = 3;
                formInfoEntityList.forEach(a->{
                    attributeNames.add(index,"????????????");
                    attributeNames.add(index+1,"?????????");
                });

                //??????????????????
                List<String> formInfoDateList = formRecordEntityList.stream().map(c->dtf2.format(c.getCreatedAt())).distinct().collect(Collectors.toList());
                log.info("formInfoDateList {}:",formInfoDateList);

                // ?????????????????????
                BaseMysqlQuery queryFormLevelRule = new BaseMysqlQuery()
                        .append("formInfoIdIn",formInfoIdList)
                        .append("status","1");
                List<FormLevelRuleEntity> formLevelRuleEntityList = formLevelRuleRepository.findAll(queryFormLevelRule);
                //????????????id?????????????????????????????????
                Map<Integer, List<FormLevelRuleEntity>> formLevelRuleMap= formLevelRuleEntityList.stream().collect(Collectors.groupingBy(FormLevelRuleEntity::getFormInfoId));



                Map<Integer, List<FormRecordEntity>> formRecordMap= formRecordEntityList.stream().collect(Collectors.groupingBy(FormRecordEntity::getFormInfoId));

                //????????????
                formInfoDateList.forEach(d->{
                    List<String> dataList = new ArrayList<>();
                    dataList.add(0,d);
                    dataList.add(1,formCategoryEntity.getName());
                    dataList.add(2,"0%");
                    Integer num = 3;
                    List<BigDecimal> itemSatisfactionNumList = new ArrayList<>();
                    BigDecimal totalWeight = new BigDecimal(formInfoEntityList.stream().mapToInt(FormInfoEntity::getWeight).sum());
                    for (FormInfoEntity formInfo:formInfoEntityList) {
                        dataList.add(num,formInfo.getName());
                        //???????????????
                        BigDecimal satisfactionNum = new BigDecimal(0);
                        if(formLevelRuleMap.containsKey(formInfo.getId())){
                            BigDecimal formLevelRuleScore = formLevelRuleMap.get(formInfo.getId()).get(0).getStartScore();
                            if(formRecordMap.containsKey(formInfo.getId())){
                                List<FormRecordEntity> formRecordList =  formRecordMap.get(formInfo.getId()).stream().filter(it->dtf2.format(it.getCreatedAt()).equals(d)).collect(Collectors.toList());
                                long totalSize = formRecordList.size();
                                if(totalSize >0){
                                    long levelSize = formRecordList.stream().filter(item->item.getTotalScore().compareTo(formLevelRuleScore) >= 0).count();
                                    satisfactionNum = new BigDecimal(levelSize).divide(new BigDecimal(totalSize),4,BigDecimal.ROUND_HALF_UP);
                                    BigDecimal weight = new BigDecimal(formInfo.getWeight()).divide(totalWeight,4,BigDecimal.ROUND_HALF_UP);
                                    BigDecimal itemSatisfactionNum = weight.multiply(satisfactionNum).setScale(4,BigDecimal.ROUND_HALF_UP);
                                    itemSatisfactionNumList.add(itemSatisfactionNum);
                                }
                            }


                        }
                        dataList.add(num+1,satisfactionNum.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"%");
                        num = num+2;
                    };
                    //??????????????????
                    BigDecimal formCategorySatisfactionNum = BigDecimal.valueOf(itemSatisfactionNumList.stream().flatMapToDouble(s -> DoubleStream.of(s.doubleValue())).sum()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
                    dataList.set(2,formCategorySatisfactionNum.toString()+"%");

                    importlist.add(dataList);
                });
            }


        }

        log.info("importlist {}:",importlist);

        String filePath = export(importlist, attributeNames);

        entity.setFilePath(filePath);
        entity.setTaskStatus(ImportExportTaskTaskStatusEnum.finish);
        entity.setFinishAt(LocalDateTime.now());
        importExportTaskRepository.save(entity);
    }

    /**
     * ????????????????????????excel???
     *  @param importlist     ???????????????????????????
     * @param attributeNames ???????????????????????????excel????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @return
     */
    public String export(List<List<String>> importlist, List<String> attributeNames) throws IOException {
        //???????????????

        //?????????????????????
        HSSFWorkbook workbook = new HSSFWorkbook();
        //??????????????????
        HSSFSheet sheet = workbook.createSheet();
        //??????????????????????????????15?????????
        sheet.setDefaultColumnWidth((short) 18);

        //????????????
        setTitle(workbook, sheet, attributeNames);

        //????????????????????????
        setData(sheet,importlist);


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] barray=os.toByteArray();
        InputStream is=new ByteArrayInputStream(barray);
        MultipartFile multipartFile = new MockMultipartFile("?????????","?????????","?????????",is);

        String filePath = saveFileToLocal(multipartFile);

//        log.info("fileName {}:",fileName);
        return filePath;
    }

    /**
     * ???????????????????????????
     *
     * @author tiany
     */
    /**
     * ???????????????????????????????????????
     * @param file ??????
     * @return
     */
    public String saveFileToLocal(MultipartFile file) {
        String extension = "xls";
        //?????????????????????????????????
        LocalDateTime now = LocalDateTime.now();
        String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String fileName = now.format(DateTimeFormatter.ofPattern("ddHHmm"))
                + "-" + UUID.randomUUID().toString().replace("-", "")
                + "." + extension;
        File path = new File(exportPath, yearMonth);
        if (!path.exists()) {
            path.mkdirs();
        }

        //????????????
        File newFile = new File(path, fileName);
        try {
            file.transferTo(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/" + yearMonth + "/" +fileName;
    }

    /**
     * ????????????setTitle
     * ?????????????????????
     * ?????????
     * ????????????typ
     * ???????????????2018/10/19 10:20
     * ????????????
     * ???????????????
     * ???????????????
     */
    public void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, List<String> str) {
        try {
            HSSFRow row = sheet.createRow(0);
            //???????????????setColumnWidth???????????????????????????256???????????????????????????1/256???????????????
            for (int i = 0; i <= str.size(); i++) {
                sheet.setColumnWidth(i, 15 * 256);
            }
            //?????????????????????,?????????????????????
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            //??????????????????
            HSSFCell cell;
            for (int j = 0; j < str.size(); j++) {
                cell = row.createCell(j);
                cell.setCellValue(str.get(j));
                cell.setCellStyle(style);
            }
        } catch (Exception e) {
            log.info("??????????????????????????????");
            e.printStackTrace();
        }
    }


    /**
     * ????????????setData
     * ?????????????????????
     * ?????????
     * ????????????typ
     * ???????????????2018/10/19 16:11
     * ????????????
     * ???????????????
     * ???????????????
     */
    private void setData(HSSFSheet sheet, List<List<String>> dataList) {
        try{
            int rowNum = 1;
            for (List<String> datas:dataList) {
                log.info("datas {}:",datas);
                HSSFRow row = sheet.createRow(rowNum);
                int num = 0;
                for (String data:datas) {
                    row.createCell(num).setCellValue(data);
                    num++;
                }
                rowNum++;
            }

            log.info("?????????????????????");
        }catch (Exception e){
            log.info("?????????????????????");
            e.printStackTrace();
        }
    }




}
