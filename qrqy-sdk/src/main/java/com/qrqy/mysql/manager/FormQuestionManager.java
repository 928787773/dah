package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.FormQuestionEntity;
import com.qrqy.mysql.repository.FormQuestionRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-17 16:51
 */
@Component
public class FormQuestionManager extends BaseMysqlManager<FormQuestionRepository, FormQuestionEntity> {
    @Autowired
    private FormQuestionRepository repository;

    @Override
    protected FormQuestionRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
