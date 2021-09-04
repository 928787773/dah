package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.DiseaseTypeEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-25 14:45
 */
@Component
public interface DiseaseTypeRepository extends IBaseMysqlRepository<DiseaseTypeEntity, Integer> {

}
