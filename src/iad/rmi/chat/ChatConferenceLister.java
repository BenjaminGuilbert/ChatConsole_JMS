package iad.rmi.chat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatConferenceLister {

	public static void main(String[] args) {

		try {
			System.setProperty("java.rmi.server.hostname", "172.27.88.117");
			
			Registry registry = LocateRegistry.getRegistry("172.27.91.151");
			//172.27.84.118 : confKoloina
			
			
			String[] remoteRefs = registry.list();
			for(String ref : remoteRefs){
				System.out.println(ref);
			}
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
