package org.home.petclinic2;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.home.petclinic2.domain.User;
import org.home.petclinic2.repository.auditor.AuditorAwareSpringSecurityImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Initializes persistence (model) layer
 * 
 * @author Phillip
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("**.repository")
@EnableJpaAuditing
@PropertySource("classpath:db.properties")
public class DataConfig {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_GENERATE_DDL = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env
				.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env
				.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env
				.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());

		entityManagerFactoryBean
				.setPackagesToScan(env
						.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(env.getRequiredProperty(
				PROPERTY_NAME_HIBERNATE_SHOW_SQL, Boolean.class));
		hibernateJpaVendorAdapter.setGenerateDdl(env.getRequiredProperty(
				PROPERTY_NAME_HIBERNATE_GENERATE_DDL, Boolean.class));
		hibernateJpaVendorAdapter.setDatabase(Database.H2);

		return hibernateJpaVendorAdapter;
	}

	// for finer control, use hibproperties. See:
	// http://www.javacodegeeks.com/2013/05/spring-jpa-data-hibernate-mysql-maven.html
	// private Properties hibProperties() {
	// Properties properties = new Properties();
	// properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
	// env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
	// properties.put(,
	// env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
	// properties.put(PROPERTY_NAME_HIBERNATE_GENERATE_DDL,
	// env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_GENERATE_DDL));
	// return properties;
	// }

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory()
				.getObject());
		return transactionManager;
	}

	// TODO: Need to figure out how to run this once
	// @Bean
	// @DependsOn("entityManagerFactory")
	// public ResourceDatabasePopulator initDatabase(DataSource dataSource)
	// throws Exception {
	// ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	// populator.addScript(new ClassPathResource("data.sql"));
	// populator.populate(dataSource.getConnection());
	// return populator;
	// }

	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditorAwareSpringSecurityImpl();
	}

}
