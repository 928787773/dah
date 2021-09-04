package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.FormRecordEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Luis
 * @date : 2021-06-22 15:33
 */
@Component
public interface FormRecordRepository extends IBaseMysqlRepository<FormRecordEntity, Integer> {

//    @Query(value = "SELECT DISTINCT ROUND( ( COUNT( * ) / ( SELECT DISTINCT COUNT( * ) FROM form_record WHERE form_info_id = ?1 ) ) * 100, 2 ) FROM form_record r, form_level_rule lr  WHERE lr.form_info_id = ?1  AND r.form_info_id = ?1  AND r.total_score > lr.start_score",nativeQuery = true);
//    String insertUser(Integer formInfoId);

}
