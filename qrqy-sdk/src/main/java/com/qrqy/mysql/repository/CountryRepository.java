package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.CountryEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-25 14:18
 */
@Component
public interface CountryRepository extends IBaseMysqlRepository<CountryEntity, Integer> {

}
