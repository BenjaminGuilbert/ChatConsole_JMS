package iad.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {


	public static void main(String[] args) {
		try {
			
			//System.setProperty("java.rmi.server.hostname", "172.27.88.117");
			
			Registry registry = LocateRegistry.createRegistry(1099);
			
			ChatConferenceImpl conf = new ChatConferenceImpl("NomALaCon", "DescALaCon");
			System.out.println("Conf : "+conf.getName() + " " + conf.getDescription());
			
			registry.rebind("ConfALaCon", conf);
			conf.start();

			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
