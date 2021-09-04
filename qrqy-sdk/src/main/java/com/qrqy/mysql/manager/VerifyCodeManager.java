package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.VerifyCodeEntity;
import com.qrqy.mysql.repository.VerifyCodeRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-16 10:36
 */
@Component
public class VerifyCodeManager extends BaseMysqlManager<VerifyCodeRepository, VerifyCodeEntity> {
    @Autowired
    private VerifyCodeRepository repository;

    @Override
    protected VerifyCodeRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
