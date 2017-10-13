package iad.rmi.chat;

import java.rmi.RemoteException;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;


public class ChatJMSConferenceImpl extends ChatConferenceImpl implements ChatJMSConference {
	
	private String _password;
	private Boolean _isLogged;
	private QueueConnection _connection;
	private QueueSession _session;
	private QueueSender _sender;
	private Queue _queue;

	public ChatJMSConferenceImpl(String name, String desc, String pwd) throws RemoteException {
		super(name, desc);
		_password = pwd;
		_isLogged = false;
	}

	@Override
	public void activateLog(String pwd) throws RemoteException {
		if(!_isLogged && pwd.equals(_password)) {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
			try {
				_connection = factory.createQueueConnection();
				_session = _connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				_queue = _session.createQueue("QueueDuChat");
				_sender = _session.createSender(_queue);
				_isLogged = true;				
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void desactivateLog(String pwd) throws RemoteException {
		if(_isLogged && pwd.equals(_password)) {
			try {
				_sender.close();
				_session.close();
				_connection.close();
				_isLogged = false;
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void addParticipant(ChatParticipant p) throws RemoteException {
		try {
			super.addParticipant(p);
			if(_isLogged) {
				sendMessage(p.getName(),"join");				
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void removeParticipant(ChatParticipant p) throws RemoteException {
		try {
			super.removeParticipant(p);
			if(_isLogged) {
				sendMessage(p.getName(),"quit");				
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	protected void sendMessage(String pName, String action) throws JMSException, RemoteException {
		MapMessage message = _session.createMapMessage();
		message.setString("pName", pName);
		message.setString("conf", this.getName());
		message.setString("action", action);
		message.setString("date", new Date().toString());
		_sender.send(message);
	}

}
