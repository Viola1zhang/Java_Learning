package activemq.test.jms.topic;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class JmsProduce_Topic {
	
	public static final String ACTIVEMQ_URL= "tcp://127.0.0.1:61616";
	public static final String TOPIC_NAME =	"queue01";

	public static void main(String[] args) throws JMSException {
		// TODO Auto-generated method stub
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
		
		//create producer
		MessageProducer mp = session.createProducer(topic);
		
		//
		for(int i =1; i<6;i++) {
			TextMessage tm = session.createTextMessage("new message listener "+i);
			mp.send(tm);
		}
		
		mp.close();
		session.close();
		connection.close();
		
		System.out.println("----complete send messgae to MQ-----");
		
		
	

	}

}

