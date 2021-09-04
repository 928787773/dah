package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.FormQuestionOptionEntity;
import com.qrqy.mysql.repository.FormQuestionOptionRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-17 16:51
 */
@Component
public class FormQuestionOptionManager extends BaseMysqlManager<FormQuestionOptionRepository, FormQuestionOptionEntity> {
    @Autowired
    private FormQuestionOptionRepository repository;

    @Override
    protected FormQuestionOptionRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
