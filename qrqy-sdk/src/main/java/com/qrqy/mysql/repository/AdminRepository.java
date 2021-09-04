package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.AdminEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-10 16:40
 */
@Component
public interface AdminRepository extends IBaseMysqlRepository<AdminEntity, Integer> {

}
