package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.FormInfoEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-17 16:32
 */
@Component
public interface FormInfoRepository extends IBaseMysqlRepository<FormInfoEntity, Integer> {

}
