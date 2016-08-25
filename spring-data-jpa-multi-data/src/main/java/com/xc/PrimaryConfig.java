/**
 * **********************************************************************
 * HONGLING CAPITAL CONFIDENTIAL AND PROPRIETARY
 * <p/>
 * COPYRIGHT (C) HONGLING CAPITAL CORPORATION 2012
 * ALL RIGHTS RESERVED BY HONGLING CAPITAL CORPORATION. THIS PROGRAM
 * MUST BE USED  SOLELY FOR THE PURPOSE FOR WHICH IT WAS FURNISHED BY
 * HONGLING CAPITAL CORPORATION. NO PART OF THIS PROGRAM MAY BE REPRODUCED
 * OR DISCLOSED TO OTHERS,IN ANY FORM, WITHOUT THE PRIOR WRITTEN
 * PERMISSION OF HONGLING CAPITAL CORPORATION. USE OF COPYRIGHT NOTICE
 * DOES NOT EVIDENCE PUBLICATION OF THE PROGRAM.
 * HONGLING CAPITAL CONFIDENTIAL AND PROPRIETARY
 * ***********************************************************************
 */
package com.xc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 *  主数据源配置。
 *
 *  @author xiachuan at 2016/8/25 9:34。
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryPrimary",
        transactionManagerRef="transactionManagerPrimary",
        basePackages= { "com.xc.domain.p" }) //设置Repository所在位置
public class PrimaryConfig {

        @Autowired
        @Qualifier("primaryDataSource")
        private DataSource primaryDataSource;

        @Primary
        @Bean(name = "entityManagerPrimary")
        public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
                return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
        }

        @Primary
        @Bean(name = "entityManagerFactoryPrimary")
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary (EntityManagerFactoryBuilder builder) {
                return builder
                        .dataSource(primaryDataSource)
                        .properties(getVendorProperties(primaryDataSource))
                        .packages("com.xc.domain.p") //设置实体类所在位置
                        .persistenceUnit("primaryPersistenceUnit")
                        .build();
        }

        @Autowired
        private JpaProperties jpaProperties;

        private Map<String, String> getVendorProperties(DataSource dataSource) {
                return jpaProperties.getHibernateProperties(dataSource);
        }

        @Primary
        @Bean(name = "transactionManagerPrimary")
        public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
                return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
        }

}

