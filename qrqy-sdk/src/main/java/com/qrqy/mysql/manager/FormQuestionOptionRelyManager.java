package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.FormQuestionOptionRelyEntity;
import com.qrqy.mysql.repository.FormQuestionOptionRelyRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import com.qrqy.mysql.repository.FormQuestionOptionRepository;
import com.qrqy.mysql.repository.FormQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author : Luis
 * @date : 2021-06-18 13:47
 */
@Component
@Slf4j
public class FormQuestionOptionRelyManager extends BaseMysqlManager<FormQuestionOptionRelyRepository, FormQuestionOptionRelyEntity> {
    @Autowired
    private FormQuestionOptionRelyRepository repository;




    @Override
    protected FormQuestionOptionRelyRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶



}
