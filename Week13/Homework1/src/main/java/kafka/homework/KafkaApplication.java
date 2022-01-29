package kafka.homework;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class KafkaApplication {

	public static void main(String[] args) {
		//SpringApplication.run(KafkaApplication.class, args);
		String servers="localhost:9092,localhost:9093,localhost:9094";
		String topic="test topic for kafka";
		String message = "this is a test message for kafka";
		
		KafkaProducer<String,String> producer = KafkaProducers.createProducer(servers);
		KafkaProducers.sendMessage(producer, topic, message);
		
		KafkaConsumer<String,String> consumer = KafkaConsumers.createConsumer(servers, topic);
		KafkaConsumers.consumeMessage(consumer, 100);
	}

}
