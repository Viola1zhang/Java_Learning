package activemq.test.jms;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsume {
	public static final String ACTIVEMQ_URL= "tcp://127.0.0.1:61616";
	public static final String QUEUE_NAME =	"queue01";

	public static void main(String[] args) throws JMSException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Consumer No 1");
		ActiveMQConnectionFactory activeMQFac = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		//GET COONECION
		Connection connection = activeMQFac.createConnection();
		connection.start();
		
		//create session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//create destination
		//Queue
		Queue queue = session.createQueue(QUEUE_NAME);
		//Destination des= session.createQueue(QUEUE_NAME);
		
		//create consumer
		MessageConsumer ms = session.createConsumer(queue);
		
		//method1: use receive method
		while(true) {
			TextMessage textMessage = (TextMessage)ms.receive();
			
			if(null!=textMessage ) {
				System.out.println("consumer consume message:" + textMessage.getText());
			}else {
				break;
			}
		}
		
		ms.close();
		session.close();
		connection.close();
		
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
