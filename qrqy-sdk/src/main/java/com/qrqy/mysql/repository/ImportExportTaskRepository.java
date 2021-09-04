package com.qrqy.mysql.repository;

import com.qrqy.mysql.entity.ImportExportTaskEntity;
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : 2021-07-01 16:09
 */
@Component
public interface ImportExportTaskRepository extends IBaseMysqlRepository<ImportExportTaskEntity, Integer> {

}
