package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.UserTypeEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-25 14:26
 */
@Component
public interface UserTypeRepository extends IBaseMysqlRepository<UserTypeEntity, Integer> {

}
