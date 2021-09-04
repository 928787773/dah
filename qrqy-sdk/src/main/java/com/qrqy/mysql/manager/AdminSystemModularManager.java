package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.AdminSystemModularEntity;
import com.qrqy.mysql.repository.AdminSystemModularRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-16 15:12
 */
@Component
public class AdminSystemModularManager extends BaseMysqlManager<AdminSystemModularRepository, AdminSystemModularEntity> {
    @Autowired
    private AdminSystemModularRepository repository;

    @Override
    protected AdminSystemModularRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
