package com.shanghai.wifishare.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages={
		"com.shanghai.wifishare.ordermgmt.repository",
		"com.shanghai.wifishare.usermgmt.repository",
		"com.shanghai.wifishare.wifimgmt.repository"
		})
@EnableTransactionManagement
@EnableConfigurationProperties
public class ApplicationConfig {


	/**
	 * entityManagerFactory:(创建entity管理工厂). <br/>
	 *
	 * @author zoub
	 * @param dataSource
	 * @return
	 * @since JDK 1.8
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(
        		"com.shanghai.wifishare.ordermgmt.domain",
        		"com.shanghai.wifishare.usermgmt.domain",
        		"com.shanghai.wifishare.wifimgmt.domain");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }
	
	/**
	 * transactionManager:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author zoub
	 * @param dataSource
	 * @return
	 * @since JDK 1.8
	 */
	@Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory(dataSource));
        return txManager;
    }
	
	
}
