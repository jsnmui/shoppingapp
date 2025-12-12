package com.superdupermart.shopping_app.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.superdupermart.shopping_app.entity.Product;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;



@Configuration
public class HibernateConfig {

    @Value("${database.hibernate.url}")
    private String dbUrl;

    @Value("${database.hibernate.username}")
    private String dbUsername;

    @Value("${database.hibernate.password}")
    private String dbPassword;

    @Value("${database.hibernate.driver}")
    private String dbDriver;

    @Value("${database.hibernate.dialect}")
    private String dbDialect;

    @Value("${database.hibernate.showsql}")
    private String showSql;

    @Value("${database.hibernate.ddlauto}")
    private String ddlAuto;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(dbUrl);
        ds.setUsername(dbUsername);
        ds.setPassword(dbPassword);
        ds.setDriverClassName(dbDriver);
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.superdupermart.shopping_app.entity");

        Properties props = new Properties();
        props.setProperty("hibernate.dialect", dbDialect);
        props.setProperty("hibernate.show_sql", showSql);
        props.setProperty("hibernate.hbm2ddl.auto", ddlAuto);

        factoryBean.setHibernateProperties(props);
        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
