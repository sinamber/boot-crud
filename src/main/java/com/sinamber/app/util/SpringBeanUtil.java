package com.sinamber.app.util;

import org.springframework.context.ApplicationContext;

/**
 * 直接通过Spring 上下文获取SpringBean,Spring容器启动后需要注入ApplicationContext
 * @Description:
 * @Author:Sine Chen
 * @Date:Feb 5, 2015 6:55:35 PM
 * @Copyright: All Rights Reserved. Copyright(c) 2015
 */
public class SpringBeanUtil {
	private static ApplicationContext context;

	public static void setContext(ApplicationContext contextinject) {
		if (context != null) {
			return;
		}
		context = contextinject;
	}

	public static Object getBeanByName(String beanName) {
		return context.getBean(beanName);
	}

	public static <T> T getBean(Class<T> type) {
		return context.getBean(type);
	}

}