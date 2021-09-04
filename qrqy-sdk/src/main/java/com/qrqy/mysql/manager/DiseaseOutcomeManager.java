package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.DiseaseOutcomeEntity;
import com.qrqy.mysql.repository.DiseaseOutcomeRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-25 14:45
 */
@Component
public class DiseaseOutcomeManager extends BaseMysqlManager<DiseaseOutcomeRepository, DiseaseOutcomeEntity> {
    @Autowired
    private DiseaseOutcomeRepository repository;

    @Override
    protected DiseaseOutcomeRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
