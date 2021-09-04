package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.MedicalStaffEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-06-29 11:32
 */
@Component
public interface MedicalStaffRepository extends IBaseMysqlRepository<MedicalStaffEntity, Integer> {

}
