package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.FormCategoryEntity;
import com.qrqy.mysql.repository.FormCategoryRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-16 17:40
 */
@Component
public class FormCategoryManager extends BaseMysqlManager<FormCategoryRepository, FormCategoryEntity> {
    @Autowired
    private FormCategoryRepository repository;

    @Override
    protected FormCategoryRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
