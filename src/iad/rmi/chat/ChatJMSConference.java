package iad.rmi.chat;

import java.rmi.RemoteException;

public interface ChatJMSConference extends ChatConference {

	// active le placement en file des messages
	public void activateLog(String pwd) throws RemoteException;
	
	// desactive le placement en file des messages
	public void desactivateLog(String pwd) throws RemoteException;
	
}
