package com.examly.healthCareManagement.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.examly"})
public class HealthAppConfig implements WebMvcConfigurer {
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource driver = new DriverManagerDataSource();
		driver.setUsername("root");
		driver.setPassword("AaBb33@@");
		driver.setUrl("jdbc:mysql://localhost:3306/virtusa?useSSL=false");
		driver.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return driver;
	}

}
