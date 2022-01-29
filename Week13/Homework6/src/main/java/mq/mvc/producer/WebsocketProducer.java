package mq.mvc.producer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.Gson;

import lombok.SneakyThrows;
import mq.mvc.common.Constants;
import mq.mvc.common.UUIDUtil;

public class WebsocketProducer implements Producer {
	
	private Map<String, Object> properties;
	private Map<String, Object> resultCache = new HashMap<>();
	private WebSocketClient client;
	private Gson gson = new Gson();
	
	public WebsocketProducer(Map<String, Object> properties) {
		this.properties = properties;
		connect();
	}
	
	@SneakyThrows
	private void connect() {
		// TODO Auto-generated method stub
		try {
			client = new WebSocketClient(new URI(properties.get(Constants.URL).toString())) {
				

				@Override
				public void onOpen(ServerHandshake handshakedata) {
					// TODO Auto-generated method stub
					 System.out.println("producer websocket client connected");
				}

				@Override
				public void onMessage(String message) {
					// TODO Auto-generated method stub
					if(!message.equals("null")) {
						resultCache.put(message, true);
					}
					notifyAll();
					
				}

				@Override
				public void onClose(int code, String reason, boolean remote) {
					// TODO Auto-generated method stub
					System.out.println("producer websocket client close");
					
				}

				@Override
				public void onError(Exception ex) {
					// TODO Auto-generated method stub
					
				}
			};
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 try {
			System.out.println(client.connectBlocking());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SneakyThrows
	@Override
	public synchronized boolean send(String topic, String message)  {
		// TODO Auto-generated method stub
		String id = UUIDUtil.getUUID();
		String sendTiemout = Constants.SEND_TIMEOUT;
		int defaultTimeout = Constants.DEFAULT_SEND_TIMEOUT;
		long timeout = Long.parseLong(properties.getOrDefault(sendTiemout, defaultTimeout).toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put(Constants.TOPIC, topic);
		map.put(Constants.MESSAGE, message);
		map.put(Constants.SENG_ID, id);
		
		client.send(gson.toJson(map));
		while(!(boolean) resultCache.getOrDefault(id, false)) {
			try {
				this.wait(timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!(Boolean)resultCache.getOrDefault(id, false)) {
			return false;
		}
		
		
		resultCache.remove(id);
		
		return true;
	}

}
