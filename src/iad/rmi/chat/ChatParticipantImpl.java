package iad.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatParticipantImpl extends UnicastRemoteObject implements ChatParticipant {

	private String _name;
	private ChatConference _currentConf;
	private BlockingQueue<ChatMessage> _msgQueue;
	
	public ChatParticipantImpl(String name) throws RemoteException {
		_name = name;
		_msgQueue = new LinkedBlockingQueue<ChatMessage>();
		_currentConf = null;
	}

	@Override
	public boolean join(ChatConference conference) throws RemoteException {
		if(!isConnected()){
			try{
				conference.addParticipant(this);
				_currentConf = conference;
				return true;
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		else
			return false;
	}

	@Override
	public void leave(ChatConference conference) throws RemoteException {
		if(isConnected()){
			try{
				conference.removeParticipant(this);
				_currentConf = null;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void send(String txt) throws RemoteException {
		if(isConnected()){
			ChatMessage msg = new ChatMessage(_name, txt);
			_currentConf.broadcast(msg);
		}
		
	}

	@Override
	public void process(ChatMessage msg) throws RemoteException {
		if(isConnected())
			_msgQueue.add(msg);
		//System.out.println(msg.emitter + " : "+ msg.content);
	}

	@Override
	public boolean isConnected() throws RemoteException {
		return _currentConf != null;
	}

	@Override
	public String getName() throws RemoteException {
		return _name;
	}

	@Override
	public ChatMessage next() throws RemoteException {
		if(isConnected())
			return _msgQueue.poll();
		return null;
	}

}
