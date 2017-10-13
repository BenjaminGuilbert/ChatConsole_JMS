package iad.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Set;

public class ChatConferenceImpl extends UnicastRemoteObject implements ChatConference{

	private String _name;
	private String _description;
	private HashMap<String, ChatParticipant> _participants;
	private boolean _isStarted;

	public ChatConferenceImpl(String name, String desc) throws RemoteException {
		_name = name;
		_description = desc;
		_participants = new HashMap<String, ChatParticipant>();
		_isStarted = false;
	}

	@Override
	public String getName() throws RemoteException {
		return _name;
	}

	@Override
	public String getDescription() throws RemoteException {
		return _description;
	}

	@Override
	public boolean isStarted() throws RemoteException {
		return _isStarted;
	}

	@Override
	public void addParticipant(ChatParticipant p) throws RemoteException {
		_participants.put(p.getName(), p);
		broadcast(new ChatMessage(_name, "Participant "+p.getName()+" joines the conference."));
	}

	@Override
	public void removeParticipant(ChatParticipant p) throws RemoteException {
		_participants.remove(p.getName());
		broadcast(new ChatMessage(_name, "Participant "+p.getName()+" left the conference."));
	}

	@Override
	public String[] participants() throws RemoteException {
		return _participants.keySet().toArray(new String[0]);
	}

	@Override
	public void broadcast(ChatMessage message) throws RemoteException {
		if(_isStarted){
			for(ChatParticipant p : _participants.values()){
				if(!message.emitter.equals(p.getName())){
					try{
						p.process(message);
					}
					catch (Exception e) {
						// TODO: handle exception
					}
				}
					
					
			}
		}
		
	}

	@Override
	public void start() throws RemoteException {
		if(!_isStarted){
			_isStarted = true;
			broadcast(new ChatMessage(_name, "Conference Started"));
		}
	}

	@Override
	public void stop() throws RemoteException {
		if(_isStarted){
			_isStarted = false;
			broadcast(new ChatMessage(_name, "Conference Stopped"));
		}
	}

}
