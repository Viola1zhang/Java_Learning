package kafka.homework;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaConsumers {
	
	public static KafkaConsumer<String,String> createConsumer(String servers, String topic){
		Properties props = new Properties();
		props.put("bootstrap.servers", servers);
		props.put("group.id", "group-1");
		props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "10000");
		props.put("auto.offset.reset", "earliest");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serilization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serilization.StringDeserializer");
		
		KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(props);
		kafkaConsumer.subscribe(Arrays.asList(topic));
		return kafkaConsumer;
	}
	
	public static void consumeMessage(KafkaConsumer<String,String> kafkaConsumer, int timeout) {
		while(true) {
			ConsumerRecords<String,String> records = kafkaConsumer.poll(timeout);
			for(ConsumerRecord<String,String> record:records) {
				String recordValue = record.value();
				kafkaConsumer.commitAsync();
				System.out.println("Consume record value is  "+recordValue);
				
			}
		}
	}

}
