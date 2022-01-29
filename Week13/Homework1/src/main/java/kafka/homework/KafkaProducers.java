package kafka.homework;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducers {
	
	public static KafkaProducer<String, String> createProducer(String servers){
		Properties props = new Properties();
		props.put("bootstrap.servers", servers);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16348);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serilization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serilization.StringSerializer");
		return new KafkaProducer<String,String>(props);

	}
	
	public static void sendMessage(KafkaProducer<String,String> producer, String topic,String message) {
		producer.send(new ProducerRecord<String,String>(topic, message));
		System.out.println("Sent message is "+message);
	}

}
