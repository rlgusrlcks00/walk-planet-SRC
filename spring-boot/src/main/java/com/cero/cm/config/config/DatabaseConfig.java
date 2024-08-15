package com.cero.cm.config.config;

import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories({"com.cero.cm.db.repository"})
@EnableJpaAuditing
public class DatabaseConfig {

    private final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    /* JDBC 정보 */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    /* Hibernate 정보 */
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;

    @Value("${spring.jpa.properties.hibernate.show_sql}")
    private String showSql;

    @Value("${spring.jpa.properties.hibernate.format_sql}")
    private String formatSql;

    @Value("${spring.jpa.properties.hibernate.ddl-auto}")
    private String hbm2ddlAuto;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(userName)
                .password(password).build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);

        return txManager;
    }

    @Bean
    public Properties hibernateProperties() {
        // println형태의 콘솔출력을 사용할때
        Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.format_sql", formatSql);
        properties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        return properties;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.cero.cm.db.entity");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setShowSql(true);
//		vendorAdapter.setGenerateDdl(true);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        em.afterPropertiesSet();

        return em.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(this.entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = PostgreSQLTemplates.builder()
                .printSchema()
                .quote()
                .newLineToSingleSpace()
            .build(); //change to your Templates
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        SpringExceptionTranslator springExceptionTranslator = new SpringExceptionTranslator();
        configuration.setExceptionTranslator(springExceptionTranslator);
        return configuration;
    }

    @Bean
    public SQLQueryFactory SQLQueryFactory() {
        SQLQueryFactory sqlQueryFactory = new SQLQueryFactory(querydslConfiguration(),
                dataSource()
        );
        return sqlQueryFactory;
    }

}
