package it.blog.sqlornosql.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration(proxyBeanMethods = false)
public class MongoDataSourceConfig{

	private String getDatabaseName() {
		return "store";
	}
	
  public MongoClient mongo() {
      ConnectionString connectionString = new ConnectionString(String.format("mongodb://localhost:27017/%s", getDatabaseName()));
      MongoCredential credential = MongoCredential.createCredential("store_manager", getDatabaseName(), "store_password".toCharArray());
  		
      MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .credential(credential)
        .build();
      
      return MongoClients.create(mongoClientSettings);
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
      return new MongoTemplate(mongo(), getDatabaseName());
  }

}
