package com.app.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.app.secondary.repository", 
entityManagerFactoryRef = "secondaryLocalContainerEntityManagerFactoryBean", 
transactionManagerRef = "secondaryPlatformTransactionManager")
public class SecondaryDataBaseConfig {
	@Autowired
	private Environment environment;
	
	@Bean
	@ConfigurationProperties("spring.datasource.secondary")
	public DataSourceProperties secondaryDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	public DataSource secondaryDataSource() {
		DataSource dataSource = secondaryDataSourceProperties()
				.initializeDataSourceBuilder()
				.build();
		
		return dataSource; 
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean secondaryLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.dialect", environment.getProperty("spring.jpa.secondary.database-platform"));
		properties.put("hibernate.show-sql", environment.getProperty("spring.jpa.secondary.show-sql"));
		properties.put("hibernate.format_sql", environment.getProperty("spring.jpa.secondary.properties.hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.secondary.hibernate.ddl-auto"));
		
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = entityManagerFactoryBuilder
				.dataSource(secondaryDataSource())
				.packages("com.app.secondary.model")
				.properties(properties)
				.build();
		
		return localContainerEntityManagerFactoryBean;
	}
	
	@Bean
	public PlatformTransactionManager secondaryPlatformTransactionManager(@Qualifier("secondaryLocalContainerEntityManagerFactoryBean") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
