package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.FormAnswerRecordEntity;
import com.qrqy.mysql.repository.FormAnswerRecordRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-22 15:44
 */
@Component
public class FormAnswerRecordManager extends BaseMysqlManager<FormAnswerRecordRepository, FormAnswerRecordEntity> {
    @Autowired
    private FormAnswerRecordRepository repository;

    @Override
    protected FormAnswerRecordRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
