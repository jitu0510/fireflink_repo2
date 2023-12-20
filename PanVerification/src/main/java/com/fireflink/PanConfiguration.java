package com.fireflink;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PanConfiguration {
	
	@Bean
	@Primary
	public Properties getProperties() throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getResourceAsStream("/application.properties");
		properties.load(inputStream);
		return properties;
	}

}
