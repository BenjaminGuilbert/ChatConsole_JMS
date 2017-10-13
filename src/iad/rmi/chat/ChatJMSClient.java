package iad.rmi.chat;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ChatJMSClient {

	public static void main(String[] args) {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
			QueueConnection connection = factory.createQueueConnection();
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("QueueDuChat");
			QueueReceiver receiver = session.createReceiver(queue);
			connection.start();
			
			while(true) {
				Message message = receiver.receive(5000);
				if(message != null && message instanceof MapMessage) {
					MapMessage mg = (MapMessage)message;
					System.out.println(mg.getString("pName") + " : " + mg.getString("action"));
				}
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}


	}

}
