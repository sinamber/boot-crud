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

		//项目启动之后做点啥？加载缓存资源、初始化默认用户?....
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

			//再来是个用户
			for (int i = 0; i < 5; i++) {
				User user = new User();
				user.setName("USER" + i);
				userMapper.add(user);
			}
			LOG.info("INIT USER SUCCESS...");
		}
	}
}
