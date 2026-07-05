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
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.app.primary.repository", 
entityManagerFactoryRef = "primaryLocalContainerEntityManagerFactoryBean", 
transactionManagerRef = "primaryPlatformTransactionManager")
public class PrimaryDataBaseConfig {
	@Autowired
	private Environment environment;
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.primary")
	public DataSourceProperties primaryDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@Primary
	public DataSource primaryDatasource() {
		DataSource dataSource = primaryDataSourceProperties()
				.initializeDataSourceBuilder()
				.build();
		
		return dataSource;
	}
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean primaryLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.dialect", environment.getProperty("spring.jpa.primary.database-platform"));
		properties.put("hibernate.show-sql", "true");
		properties.put("hibernate.format_sql", environment.getProperty("spring.jpa.primary.properties.hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.primary.hibernate.ddl-auto"));
		
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = entityManagerFactoryBuilder
				.dataSource(primaryDatasource())
				.packages("com.app.primary.model")
				.properties(properties)
				.build();
		
		//localContainerEntityManagerFactoryBean.setJpaProperties(properties);
		
		return localContainerEntityManagerFactoryBean;
	}
	
	@Bean
	@Primary
	public PlatformTransactionManager primaryPlatformTransactionManager(@Qualifier("primaryLocalContainerEntityManagerFactoryBean") EntityManagerFactory entityManagerFactory){
		return new JpaTransactionManager(entityManagerFactory);
	}
}
