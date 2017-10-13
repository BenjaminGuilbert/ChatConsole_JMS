package iad.rmi.chat;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.jms.MessageListener;

public class ChatClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			Registry registry = LocateRegistry.getRegistry(args[0]);
			
			
			
			ChatJMSConference conf = (ChatJMSConference) registry.lookup("ConfALaCon");
			System.out.println("Conf : " + conf.getName() + " " + conf.getDescription());
			
			//conf.start();
			
			ChatParticipantImpl part1 = new ChatParticipantImpl(args[1]);
			System.out.println(part1.getName());
			
			part1.join(conf);
			
			ChatClientConsole chat = new ChatClientConsole(conf, part1);
			chat.run();
			
			
			
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
