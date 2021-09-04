package com.qrqy.mysql.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.mysql.entity.FormInfoEntity;
import com.qrqy.mysql.entity.ImportExportTaskEntity;
import com.qrqy.mysql.repository.FormInfoRepository;
import com.qrqy.mysql.repository.ImportExportTaskRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.ColorStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author : Luis
 * @date : 2021-07-01 16:09
 */
@Component
@Slf4j
public class ImportExportTaskManager extends BaseMysqlManager<ImportExportTaskRepository, ImportExportTaskEntity> {

    @Value("${dp.errorResultPath}")
    private String errorResultPath;

    @Autowired
    private ImportExportTaskRepository repository;

    @Override
    protected ImportExportTaskRepository getRepository() {
        return repository;
    }


    /**
     * 将传入的数据导出excel表
     *  @param importlist     要导出的对象的集合
     * @param attributeNames 含有每个对象属性在excel表中对应的标题字符串的数组（请按对象中属性排序调整字符串在数组中的位置）
     * @return
     */
    public String exportErrorResultFile(List<List<String>> importlist, List<String> attributeNames) throws IOException {
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
        setData(workbook,sheet,importlist,attributeNames.size());


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

//        log.info("filePath {}:",filePath);
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
        String extension = ".xls";
        LocalDateTime now = LocalDateTime.now();
        String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String fileName = now.format(DateTimeFormatter.ofPattern("ddHHmm"))
                + "-" + UUID.randomUUID().toString().replace("-", "")
                + extension;

        File path = new File(errorResultPath, yearMonth);
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

        return "/"+yearMonth+"/"+fileName;
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
            font.setFontName("宋体");
            style.setFont(font);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd hh:mm:ss"));
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
    private void setData(HSSFWorkbook workbook,HSSFSheet sheet, List<List<String>> dataList,Integer attributeNamesSize) {
        try{
            int rowNum = 1;
            for (List<String> data:dataList) {
                log.info("data {}:",data);
                HSSFRow row = sheet.createRow(rowNum);
                for (int num = 0; num < attributeNamesSize; num++) {

                    row.createCell(num).setCellValue(data.get(num));
                    if(data.get(attributeNamesSize - 1) != "" && data.get(attributeNamesSize - 1) != null){
                        HSSFCellStyle style = workbook.createCellStyle();
//                        style.setFillBackgroundColor(HSSFColor(Color.RED));
                        row.setRowStyle(style);
                    }
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
