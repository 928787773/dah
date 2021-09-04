package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.FormQuestionEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-17 16:51
 */
@Component
public interface FormQuestionRepository extends IBaseMysqlRepository<FormQuestionEntity, Integer> {

}
