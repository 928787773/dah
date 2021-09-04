package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.CaseInfoEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-25 14:08
 */
@Component
public interface CaseInfoRepository extends IBaseMysqlRepository<CaseInfoEntity, Integer> {

}
