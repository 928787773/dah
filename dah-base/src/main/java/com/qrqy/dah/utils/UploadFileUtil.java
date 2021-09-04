package com.qrqy.dah.utils;

import com.qrqy.dp.exception.BizValidationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zfz
 * @version 1.0
 * @Package com.qrqy.ibd.utils
 * @date 2021/4/14
 */
@Slf4j
@Component
public class UploadFileUtil {

//    @Value("${uploadImportExcelFilePath}")
//    private static String uploadImportExcelFilePath;

    /**
     * 上传文件到服务器
     *
     * @param file
     * @return
     */
    @SneakyThrows
    public static String uploadExcelFileToSERVER(MultipartFile file) {
        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀名
        String extension = "." + FilenameUtils.getExtension(originalFilename);
//        if(extension != ".xls" && extension != ".xlsx"){
//            throw new BizValidationException("message", "文件类型错误");
//        };
        //获取新文件名称 命名：时间戳+后缀
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + extension;
        //获取资源路径 classpath的项目路径+/static/files  classpath就是resources的资源路径
        //String dataDir = ResourceUtils.getURL("classpath:").getPath() + "video/files";
        String uploadImportExcelFilePath = "D:\\uploadImportExcelFilePath";
        log.info("dataDir:" + uploadImportExcelFilePath);
        //全路径存放在文件类中，判断文件夹是否存在不存在就创建
        File dataFile = new File(uploadImportExcelFilePath);  //也可以直接放进去进行拼接 File dataFile = new File(path,format);
        if (!dataFile.exists()) {
            dataFile.mkdirs();
        }

        //文件上传至指定路径
        file.transferTo(new File(dataFile, newFileName));
        return uploadImportExcelFilePath + "/" + newFileName;
    }



    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            System.out.println("===========删除成功=================");
        } else {
            System.out.println("===============删除失败==============");
        }

    }


}
