package com.cero.cm.config.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@RequiredArgsConstructor
public class DslConfig {

    @PersistenceContext
    private EntityManager entityManager;



    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public JPASQLQuery jpasqlQuery(){
            SQLTemplates templates = PostgreSQLTemplates.builder()
                    .printSchema()
                    .quote()
                    .newLineToSingleSpace()
                    .build(); //change to your Templates
            com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
            SpringExceptionTranslator springExceptionTranslator = new SpringExceptionTranslator();
            configuration.setExceptionTranslator(springExceptionTranslator);
            return new JPASQLQuery(entityManager,configuration);
    }

}
