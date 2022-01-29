package mq.mvc.producer;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import mq.mvc.common.Constants;
import mq.mvc.messagequeue.Message;

public class HttpProducer implements Producer {
private final RestTemplate restTemplate = new RestTemplate();
	
	private Map<String, Object> properties;
	
	public HttpProducer(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	@Override
	public boolean send(String topic, String message) {
		// TODO Auto-generated method stub
		String url = properties.get(Constants.URL).toString();
		String brokerUrl = url +"/send";
		HttpEntity<Message> request = new HttpEntity<>(new Message(topic, message));
		
		ResponseEntity<Boolean> response = restTemplate.postForEntity(brokerUrl, request, Boolean.class);
		if(response.getBody() == null) {
			return false;
		}
		
		
		return response.getBody();
	}

}
