/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adriannava
 */
import java.net.MalformedURLException;
import java.rmi.*;
 
public class StartServer {
	public static void main(String[] args) {
		try {
				//System.setSecurityManager(new RMISecurityManager());
			 	java.rmi.registry.LocateRegistry.createRegistry(1099);
			 	
				ChatServerInt b=new ChatServer();	
				Naming.rebind("rmi://192.168.0.20/myabc", b);
				System.out.println("[System] Chat Server is ready.");
			}catch (MalformedURLException | RemoteException e) {
					System.out.println("Chat Server failed: " + e);
			}
	}
}
