package com.qrqy.dah.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author lk
 * @date 2020/6/16 13:42
 */
@ConditionalOnProperty({"qiniu.kodo.accessKey"})
@Component
public class QiniuUtils {

    @Value("${qiniu.kodo.accessKey}")
    private String accessKey;

    @Value("${qiniu.kodo.secretKey}")
    private String secretKey;

    private Auth auth;

    @PostConstruct
    public void init() {
        this.auth = Auth.create(this.accessKey, this.secretKey);
    }

    /**
     * 上传token
     */
    public String uploadToken(String bucket){
        return auth.uploadToken(bucket);
    }

    public String uploadImgToQiNiu(InputStream file, String filename, String bucket) {
        // 构造一个带指定Zone对象的配置类，注意后面的zone各个地区不一样的
        Configuration cfg = new Configuration();
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(file, filename, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String returnPath =putRet.key;
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
