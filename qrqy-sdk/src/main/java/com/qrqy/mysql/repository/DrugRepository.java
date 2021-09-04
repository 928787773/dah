package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.DrugEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-29 14:37
 */
@Component
public interface DrugRepository extends IBaseMysqlRepository<DrugEntity, Integer> {

}
