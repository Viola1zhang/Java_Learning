package activemq.test.jms.topic;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsume_Topic {

	public static final String ACTIVEMQ_URL= "tcp://127.0.0.1:61616";
	public static final String TOPIC_NAME =	"queue01";

	public static void main(String[] args) throws JMSException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Top Subscriber No 1");
		//craete factionry
		ActiveMQConnectionFactory activeMQFac = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		//GET COONECION
		Connection connection = activeMQFac.createConnection();
		connection.start();
		
		//create session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//create destination
		//Queue
		Topic topic = session.createTopic(TOPIC_NAME);
		//Destination des= session.createQueue(QUEUE_NAME);
		//create consumer
	    MessageConsumer ms = session.createConsumer(topic);
				
				
				
		//mehod2:use listener
		
		ms.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				if(null!=message && message instanceof TextMessage) {
					TextMessage tm = (TextMessage)message;
					try {
						System.out.println("consumer consume message:" + tm.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		});
		System.in.read();
		ms.close();
		session.close();
		connection.close();
		
	}
	

}
