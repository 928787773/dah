package com.qrqy.dah;

import com.qrqy.dp.mysql.BaseMysqlRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.qrqy"},exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EntityScan(basePackages = {"com.qrqy.mysql.entity"})
@EnableJpaRepositories(
        basePackages = {"com.qrqy.mysql.repository"},
        repositoryFactoryBeanClass = BaseMysqlRepositoryFactoryBean.class
)
@EnableJpaAuditing
@EnableTransactionManagement
public class DahApplication {

    public static void main(String[] args) {
        SpringApplication.run(DahApplication.class, args);
    }

}
