package iad.rmi.chat;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ChatJMSListenerClient {

	public static void main(String[] args) {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
			QueueConnection connection = factory.createQueueConnection();
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("QueueDuChat");
			QueueReceiver receiver = session.createReceiver(queue);
			connection.start();
			
			receiver.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					if(message != null && message instanceof MapMessage) {
						MapMessage mg = (MapMessage)message;
						try {
							System.out.println(mg.getString("pName") + " : " + mg.getString("action"));
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
