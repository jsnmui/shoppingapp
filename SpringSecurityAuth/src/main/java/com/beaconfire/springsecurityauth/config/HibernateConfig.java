package com.beaconfire.springsecurityauth.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private final HibernateProperty hibernateProperty;

    @Autowired
    public HibernateConfig(HibernateProperty hibernateProperty) {
        this.hibernateProperty = hibernateProperty;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(hibernateProperty.getDriver());
        ds.setUrl(hibernateProperty.getUrl());
        ds.setUsername(hibernateProperty.getUsername());
        ds.setPassword(hibernateProperty.getPassword());
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource());
        sf.setPackagesToScan("com.beaconfire.springsecurityauth.entity");
        Properties props = new Properties();
        props.put("hibernate.dialect", hibernateProperty.getDialect());
        props.put("hibernate.show_sql", hibernateProperty.getShowsql());
        props.put("hibernate.hbm2ddl.auto", "update");
        sf.setHibernateProperties(props);
        return sf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(sessionFactory);
        return tx;
    }
}
