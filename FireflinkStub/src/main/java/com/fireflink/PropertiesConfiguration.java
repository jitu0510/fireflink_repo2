package com.fireflink;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PropertiesConfiguration {

	@Bean
	@Primary
	public Properties getProperties()  {
		Properties properties = new Properties();
		try {
			InputStream inputStream = getClass().getResourceAsStream("/application.properties");
		//FileInputStream fileInputStream = new FileInputStream("./application.properties");
		//	FileInputStream fileInputStream = new FileInputStream("https://github.com/jitu0510/fireflink_repo2/blob/main/application.properties");
		properties.load(inputStream);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	
}
