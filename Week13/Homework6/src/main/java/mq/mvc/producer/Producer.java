package mq.mvc.producer;

public interface Producer {
	
	boolean send(String topic, String message);

}
