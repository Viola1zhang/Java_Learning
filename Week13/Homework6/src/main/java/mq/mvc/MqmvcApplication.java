package mq.mvc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mq.mvc.common.Constants;
import mq.mvc.consumer.Consumer;
import mq.mvc.consumer.HttpConsumer;
import mq.mvc.producer.Producer;
import mq.mvc.producer.WebsocketProducer;

//@SpringBootApplication
public class MqmvcApplication {

	public static void main(String[] args) {
		//SpringApplication.run(MqmvcApplication.class, args);
		int messageAmount = 10;
		String topic = "testTopic";
		int getRate = 10;
		startWebSocketMQProducer(messageAmount, topic);
		startHttpMQConsumer(messageAmount, topic, getRate);
	}

	private static void startHttpMQConsumer(int messageAmount, String topic, int getRate) {
		// TODO Auto-generated method stub
		Map<String, Object> props = new HashMap<>(1);
		props.put("url", "http//localhost:8080");
		props.put("topic", topic);
		props.put("group", "grouptest");
		Consumer consumer = new HttpConsumer(props);
		int amount = messageAmount;
		
		System.out.println("====Start consumer test===");
        long start = System.currentTimeMillis();
        
        while(amount > 0) {
        	amount -= consumer.poll(getRate).size();
        }
        
        System.out.println("Consumer " + messageAmount + " messages spend time : " + (System.currentTimeMillis() - start) + " " +
                "ms");
	}

	private static void startWebSocketMQProducer(int messageAmount, String topic) {
		// TODO Auto-generated method stub
		Map<String, Object> props = new HashMap<>();
		props.put(Constants.URL, "ws://localhost:8080/producer");
		props.put(Constants.SEND_TIMEOUT, 1000);
		Producer producer = new WebsocketProducer(props);
		
		int amount = messageAmount;
		
		System.out.println("====start producer test====");
		long start = System.currentTimeMillis();
		
		while(amount > 0) {
			if(producer.send(topic, "producerMessage")) {
				amount-=1;
			}else {
				System.out.println("send failed");
			}
			
				
		}
		
		System.out.println("Producer " + messageAmount + " messages spend time : " +
                (System.currentTimeMillis() - start) + " ms ");
	}

}
