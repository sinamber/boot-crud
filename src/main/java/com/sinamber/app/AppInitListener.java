package com.sinamber.app;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.sinamber.app.model.User;
import com.sinamber.app.persistence.UserMapper;
import com.sinamber.app.util.Lists;
import com.sinamber.app.util.SpringBeanUtil;

@Component
public class AppInitListener implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger LOG = Logger.getLogger(AppBootstrap.class);

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		LOG.info("Inject ApplicationContext to SpringBeanUtil");
		SpringBeanUtil.setContext(event.getApplicationContext());

		//After Spring Container Initlized,Do something:init your app's static data、cache... 
		initUsers();
	}

	/**
	 * 初始化用户
	 */
	private void initUsers() {
		UserMapper userMapper = SpringBeanUtil.getBean(UserMapper.class);

		if (Lists.isEmpty(userMapper.list())) {
			User u = new User();
			u.setName("Sinamber");
			userMapper.add(u);

			//再来一些用户呀
			for (int i = 0; i < 100; i++) {
				User user = new User();
				user.setName("USER" + i);
				userMapper.add(user);
			}
			LOG.info("INIT USER SUCCESS...");
		}
	}
}
