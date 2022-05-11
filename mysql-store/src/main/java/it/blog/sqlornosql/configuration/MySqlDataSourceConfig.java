package it.blog.sqlornosql.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
public class MySqlDataSourceConfig {
	
	@Primary
  @Bean
  @ConfigurationProperties(prefix = "store.datasource.mysql")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }
}
