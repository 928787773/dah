package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.OpCoreDepartmentEntity;
import com.qrqy.mysql.repository.OpCoreDepartmentRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-07-26 13:44
 */
@Component
public class OpCoreDepartmentManager extends BaseMysqlManager<OpCoreDepartmentRepository, OpCoreDepartmentEntity> {
    @Autowired
    private OpCoreDepartmentRepository repository;

    @Override
    protected OpCoreDepartmentRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
