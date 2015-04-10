package com.sinamber.app;

import java.io.File;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

@Configuration
@MapperScan(basePackages = "com.sinamber.app.persistence")
public class MybatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		File file = ResourceUtils.getFile("classpath:mybatis-config.xml");
		sessionFactory.setConfigLocation(new FileSystemResource(file));
		return sessionFactory.getObject();
	}

}
