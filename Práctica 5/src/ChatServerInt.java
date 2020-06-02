/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.*;
import java.util.*;
 
public interface ChatServerInt extends Remote{	
	public boolean login (ChatClientInt a)throws RemoteException ;
	public void publish (String s)throws RemoteException ;
	public Vector getConnected() throws RemoteException ;
}
