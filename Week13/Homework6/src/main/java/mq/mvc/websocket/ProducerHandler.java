package mq.mvc.websocket;

import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;

import lombok.SneakyThrows;
import mq.mvc.common.Constants;
import mq.mvc.messagequeue.Broker;

public class ProducerHandler implements WebSocketHandler {
	
	private final Broker broker;
	private Gson gson = new Gson();
	
	ProducerHandler(Broker broker){
		this.broker = broker;
	}
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub

	}
    
	@SneakyThrows
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
		// TODO Auto-generated method stub
		String topic = Constants.TOPIC;
		String message = Constants.MESSAGE;
		Map map = gson.fromJson(webSocketMessage.getPayload().toString(), Map.class);
		if(broker.send(map.get(topic).toString(), map.get(message).toString())) {
			session.sendMessage(new TextMessage(map.get(Constants.SENG_ID).toString()));
		}else {
			session.sendMessage(new TextMessage("null"));
		}

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
