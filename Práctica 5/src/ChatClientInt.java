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
 
public interface ChatClientInt extends Remote{	
	public void tell (String name)throws RemoteException ;
	public String getName()throws RemoteException ;
}
