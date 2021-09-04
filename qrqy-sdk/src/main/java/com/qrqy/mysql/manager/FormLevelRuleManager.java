package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.FormLevelRuleEntity;
import com.qrqy.mysql.repository.FormLevelRuleRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-18 10:46
 */
@Component
public class FormLevelRuleManager extends BaseMysqlManager<FormLevelRuleRepository, FormLevelRuleEntity> {
    @Autowired
    private FormLevelRuleRepository repository;

    @Override
    protected FormLevelRuleRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
