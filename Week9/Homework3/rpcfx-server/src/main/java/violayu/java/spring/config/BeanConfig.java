package violayu.java.spring.config;

import org.springframework.context.annotation.Bean;

import violayu.java.spring.service.UserService;
import violayu.java.spring.serviceImpl.OrderServiceImpl;
import violayu.java.spring.serviceImpl.UserServiceImpl;
import violayu.java.spring.service.OrderService;

public class BeanConfig {
	@Bean("violayu.java.spring.service.UserService")
	public UserService userService() {
		return new UserServiceImpl();
	}
	
	@Bean("violayu.java.spring.service.OrderService")
	public OrderService OrderService() {
		return new OrderServiceImpl();
	}

}
