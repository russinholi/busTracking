package core;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

import core.utils.WSBusProperties;

@Configuration
@ComponentScan("core")
public class ApplicationConfig {

	
	@Bean
	public MongoClient mongoClient() throws UnknownHostException {
		return new MongoClient();
	}
	
	@Bean 
	public MongoDbFactory mongoDbFactory() throws Exception {
		   return new SimpleMongoDbFactory(mongoClient(), WSBusProperties.getInstance().getDatabaseName());
	}
	
	@Bean
	public MongoOperations mongoOperations() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}

}
