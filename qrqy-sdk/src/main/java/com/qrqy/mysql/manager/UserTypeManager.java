package com.qrqy.mysql.manager;

import com.qrqy.mysql.entity.UserTypeEntity;
import com.qrqy.mysql.repository.UserTypeRepository;
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-25 14:26
 */
@Component
public class UserTypeManager extends BaseMysqlManager<UserTypeRepository, UserTypeEntity> {
    @Autowired
    private UserTypeRepository repository;

    @Override
    protected UserTypeRepository getRepository() {
        return repository;
    }

    //todo 鍙湪杩欓噷澧炲姞涓氬姟鏂规硶锛屼笉闄愪簬 BaseManager 鎻愪緵鐨勫熀纭�鏂规硶

}
