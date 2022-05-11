package it.blog.sqlornosql.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "store.datasource.mysql")
@EnableTransactionManagement
public class MySqlDataSourceConfig {
	
	String driverClassName;
	
	String password;
	
	String jdbcUrl;
	
	String username;
  
	@Primary
  @Bean  
  public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(jdbcUrl);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }
	
	@Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
      return new JpaTransactionManager(emf);
  }
	
	@Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
              new LocalContainerEntityManagerFactoryBean();
      localContainerEntityManagerFactoryBean.setDataSource(dataSource());
      localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter() );
      localContainerEntityManagerFactoryBean.setPackagesToScan("it.blog.sqlornosql");
      return localContainerEntityManagerFactoryBean;
  }

	@Bean
  public JpaVendorAdapter jpaVendorAdapter() {
      HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
      adapter.setDatabase(Database.MYSQL);
      adapter.setGenerateDdl(false);
      adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
      return adapter;
  }
	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
