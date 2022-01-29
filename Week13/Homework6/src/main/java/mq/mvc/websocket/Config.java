package mq.mvc.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import mq.mvc.messagequeue.Broker;

public class Config implements WebSocketConfigurer {
	
	private final Broker broker;
	
	public Config(Broker broker) {
		this.broker = broker;
	}
	
	@Bean
	public WebSocketHandler producerHandler() {
		return new ProducerHandler(broker);
	}
	
	@Bean
	public WebSocketHandler conSumerHandler() {
		return new ConsumerHandler();
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(producerHandler(), "/producer")
			.addInterceptors(new HttpSessionHandshakeInterceptor())
			.setAllowedOrigins("*");
		registry.addHandler(producerHandler(), "/consumer")
		.addInterceptors(new HttpSessionHandshakeInterceptor())
		.setAllowedOrigins("*");

	}

}
