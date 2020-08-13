package com.invoice.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.intuit.invoice")
@EntityScan("com.intuit.invoice.entities")
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    @Autowired
    Environment environment;

    @Bean(name = "dataSource")
    public DataSource getDataSource(){
        DataSource dataSource = DataSourceBuilder.create()
                .username(environment.getRequiredProperty("app.datasource.username"))
                .password(environment.getRequiredProperty("app.datasource.password"))
                .url(environment.getRequiredProperty("app.datasource.url"))
                .driverClassName(environment.getRequiredProperty("app.datasource.driverClassName"))
                .build();
        return dataSource;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource);
        localSessionFactoryBuilder.scanPackages("com.intuit.invoice.entities");
        return localSessionFactoryBuilder.buildSessionFactory();
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return  transactionManager;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        return dataSourceInitializer;
    }

}
