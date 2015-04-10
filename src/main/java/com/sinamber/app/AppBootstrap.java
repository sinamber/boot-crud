package com.sinamber.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ AppInitListener.class, MybatisConfig.class })
@ComponentScan(basePackages = { "com.sinamber" })
@EnableAutoConfiguration
public class AppBootstrap {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppBootstrap.class, args);
	}
	
	/*
	 * 自定义数据源，否则默认用tomcat的
	 * @Value("${spring.datasource.url}")
	private String jdbcURL;
	@Value("${spring.datasource.driver-class-name}")
	private String jdbcDriver;
	@Value("${spring.datasource.username}")
	private String userName;
	@Value("${spring.datasource.password}")
	private String password;

	@Bean
	public DataSource dataSource() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", jdbcURL);
		map.put("driverClassName", jdbcDriver);
		map.put("username", userName);
		map.put("password", password);
		map.put("initialSize", "1");
		map.put("maxActive", "20");
		map.put("maxWait", "60000");
		map.put("timeBetweenEvictionRunsMillis", "60000");
		map.put("validationQuery", "SELECT 'x'");
		map.put("testWhileIdle", "true");
		map.put("testOnBorrow", "false");
		map.put("testOnReturn", "false");
		map.put("poolPreparedStatements", "false");
		DataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(map);
		return dataSource;
	}*/
}
