package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.VerifyCodeEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : Luis
 * @date : 2021-06-16 10:36
 */
@Component
public interface VerifyCodeRepository extends IBaseMysqlRepository<VerifyCodeEntity, Integer> {

//    @Query(value = "{'$and': [{ 'phonenum':?0},{'status':?1},{'deleted_flag':false}]}")
//    List findPhonenumAndStatus(String phonenum, String status);

}
