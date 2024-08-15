package com.cero.cm.config.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Aspect
@Configuration
@EnableTransactionManagement
@MapperScan(value = {"com.cero.cm.db.mapper"}, sqlSessionFactoryRef = "mybatisDbSqlSessionFactory")
public class MybatisConfig {

    /* JDBC 정보 */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(userName)
                .password(password)
                .build();
    }

    public PlatformTransactionManager mybatisTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "mybatisDbSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        // TypeAliasesPackage 설정 (사용할 경우) 아직 필요성을 못느낌
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.cero.cm.db.vo");

        // MapperLocations 설정: resources/sql 디렉토리 아래의 모든 XML 파일을 로드
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:sql/**/*.xml"));

        // NULL 처리
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);

        return sqlSessionFactory;
    }

    @Bean(name = "mybatisDbSqlSessionTemplate")
    public SqlSessionTemplate mybatisDbSqlSessionTemplate(@Qualifier("mybatisDbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    private static final int TX_METHOD_TIMEOUT = 30;

    // Transaction AOP 설정
    @Bean(name = "mybatisDbTransactionIntercepter")
    public TransactionInterceptor mybatisDbTxAdvice() {
        TransactionInterceptor txAdvice = new TransactionInterceptor();

        // rollback 정책
        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));

        // 읽기 Transaction
        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        readOnlyAttribute.setReadOnly(true);
        readOnlyAttribute.setTimeout(TX_METHOD_TIMEOUT);
        String readOnlyTransactionAttributesDefinition = readOnlyAttribute.toString();

        Properties txAttributes = new Properties();
        txAttributes.setProperty("find*", readOnlyTransactionAttributesDefinition);
        txAttributes.setProperty("count*", readOnlyTransactionAttributesDefinition);

        txAdvice.setTransactionAttributes(txAttributes);
        txAdvice.setTransactionManager(mybatisTransactionManager());

        return txAdvice;
    }

    @Bean(name = "mybatisDbTxAdvisor")
    public Advisor mybatisTxAdviceAdvisor(@Qualifier("mybatisDbTransactionIntercepter") TransactionInterceptor transactionInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.cero.cm.biz.v1..*.service.*Service.*(..))");
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }
}
