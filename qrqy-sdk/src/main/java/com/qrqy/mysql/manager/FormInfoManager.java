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
        attributeNames.add("时间");
        attributeNames.add("方向");
        attributeNames.add("满意度");

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

        //获取问卷方向的信息
        FormCategoryEntity formCategoryEntity = formCategoryManager.get(formCategoryId);

        //取出该方向下的所有问卷
        Specification queryFormInfo = new BaseMysqlQuery()
                .append("formCategoryId", formCategoryId).orderBy("createdAt",Sort.Direction.ASC);

        List<FormInfoEntity> formInfoEntityList = repository.findAll(queryFormInfo);
        if(!formInfoEntityList.isEmpty()){
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<Integer> formInfoIdList = formInfoEntityList.stream().map(FormInfoEntity::getId).distinct().collect(Collectors.toList());


            //取出问卷答题记录
            Specification queryFormRecord = new BaseMysqlQuery()
                    .append("formInfoIdIn", formInfoIdList)
                    .append("createdAtGte", createdAtGte)
                    .append("createdAtLte", createdAtLte)
                    .append("status", "1").orderBy("createdAt",Sort.Direction.ASC);

            List<FormRecordEntity> formRecordEntityList = formRecordRepository.findAll(queryFormRecord);
            if(!formRecordEntityList.isEmpty()){
                //存入表头
                Integer index = 3;
                formInfoEntityList.forEach(a->{
                    attributeNames.add(index,"问卷名称");
                    attributeNames.add(index+1,"满意度");
                });

                //取出所有日期
                List<String> formInfoDateList = formRecordEntityList.stream().map(c->dtf2.format(c.getCreatedAt())).distinct().collect(Collectors.toList());
                log.info("formInfoDateList {}:",formInfoDateList);

                // 获取满意度标准
                BaseMysqlQuery queryFormLevelRule = new BaseMysqlQuery()
                        .append("formInfoIdIn",formInfoIdList)
                        .append("status","1");
                List<FormLevelRuleEntity> formLevelRuleEntityList = formLevelRuleRepository.findAll(queryFormLevelRule);
                //根据问卷id给问卷满意分数数据分组
                Map<Integer, List<FormLevelRuleEntity>> formLevelRuleMap= formLevelRuleEntityList.stream().collect(Collectors.groupingBy(FormLevelRuleEntity::getFormInfoId));



                Map<Integer, List<FormRecordEntity>> formRecordMap= formRecordEntityList.stream().collect(Collectors.groupingBy(FormRecordEntity::getFormInfoId));

                //循环日期
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
                        //计算满意度
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
                    //方向的满意度
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
     * 将传入的数据导出excel表
     *  @param importlist     要导出的对象的集合
     * @param attributeNames 含有每个对象属性在excel表中对应的标题字符串的数组（请按对象中属性排序调整字符串在数组中的位置）
     * @return
     */
    public String export(List<List<String>> importlist, List<String> attributeNames) throws IOException {
        //获取数据集

        //声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        //设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 18);

        //设置表头
        setTitle(workbook, sheet, attributeNames);

        //设置单元格并赋值
        setData(sheet,importlist);


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] barray=os.toByteArray();
        InputStream is=new ByteArrayInputStream(barray);
        MultipartFile multipartFile = new MockMultipartFile("文件名","文件名","文件名",is);

        String filePath = saveFileToLocal(multipartFile);

//        log.info("fileName {}:",fileName);
        return filePath;
    }

    /**
     * 保存批量导入的文件
     *
     * @author tiany
     */
    /**
     * 将批量导入的文件保存到本地
     * @param file 文件
     * @return
     */
    public String saveFileToLocal(MultipartFile file) {
        String extension = "xls";
        //添加时间，防止文件覆盖
        LocalDateTime now = LocalDateTime.now();
        String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String fileName = now.format(DateTimeFormatter.ofPattern("ddHHmm"))
                + "-" + UUID.randomUUID().toString().replace("-", "")
                + "." + extension;
        File path = new File(exportPath, yearMonth);
        if (!path.exists()) {
            path.mkdirs();
        }

        //新文件名
        File newFile = new File(path, fileName);
        try {
            file.transferTo(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/" + yearMonth + "/" +fileName;
    }

    /**
     * 方法名：setTitle
     * 功能：设置表头
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 10:20
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    public void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, List<String> str) {
        try {
            HSSFRow row = sheet.createRow(0);
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            for (int i = 0; i <= str.size(); i++) {
                sheet.setColumnWidth(i, 15 * 256);
            }
            //设置为居中加粗,格式化时间格式
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            //创建表头名称
            HSSFCell cell;
            for (int j = 0; j < str.size(); j++) {
                cell = row.createCell(j);
                cell.setCellValue(str.get(j));
                cell.setCellStyle(style);
            }
        } catch (Exception e) {
            log.info("导出时设置表头失败！");
            e.printStackTrace();
        }
    }


    /**
     * 方法名：setData
     * 功能：表格赋值
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:11
     * 修改人：
     * 修改描述：
     * 修改时间：
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

            log.info("表格赋值成功！");
        }catch (Exception e){
            log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }




}
