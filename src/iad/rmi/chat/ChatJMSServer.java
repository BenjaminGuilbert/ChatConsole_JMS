package iad.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatJMSServer {

	public static void main(String[] args) {
		try {
			
			//System.setProperty("java.rmi.server.hostname", "172.27.88.117");
			
			Registry registry = LocateRegistry.createRegistry(1099);
			
			ChatJMSConferenceImpl conf = new ChatJMSConferenceImpl("NomALaCon", "DescALaCon", "SuperPassword");
			System.out.println("Conf : "+conf.getName() + " " + conf.getDescription());
			
			registry.rebind("ConfALaCon", conf);
			
			conf.activateLog("SuperPassword");
			conf.start();

			
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
