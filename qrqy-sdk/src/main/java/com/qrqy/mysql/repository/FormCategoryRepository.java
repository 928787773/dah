package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.FormCategoryEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-16 17:40
 */
@Component
public interface FormCategoryRepository extends IBaseMysqlRepository<FormCategoryEntity, Integer> {

}
