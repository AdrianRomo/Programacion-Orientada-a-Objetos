/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adriannava
 */
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
 
public class ChatServer  extends UnicastRemoteObject implements ChatServerInt{
	
	private final Vector v=new Vector();	
	public ChatServer() throws RemoteException{}
		
        @Override
	public boolean login(ChatClientInt a) throws RemoteException{	
		System.out.println(a.getName() + "  got connected....");	
		a.tell("You have Connected successfully.");
		publish(a.getName()+ " has just connected.");
		v.add(a);
		return true;		
	}
	
        @Override
	public void publish(String s) throws RemoteException{
	    System.out.println(s);
		for(int i=0;i<v.size();i++){
		    try{
		    	ChatClientInt tmp=(ChatClientInt)v.get(i);
			tmp.tell(s);
		    }catch(RemoteException e){
		    	//problem with the client not connected.
		    	//Better to remove it
		    }
		}
	}
 
    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
	public Vector getConnected() throws RemoteException{
		return v;
	}
}