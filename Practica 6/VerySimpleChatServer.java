import java.io.*;
import java.net.*;
import java.util.*;

public class VerySimpleChatServer {
	
	ArrayList<ObjectOutputStream> clientObjectOutputStreams;
	ArrayList<UserSession> us;


	public class ClientHandler implements Runnable {

		ObjectOutputStream writer;
		ObjectInputStream reader;
		Socket sock;
		//Constructor
		public ClientHandler(Socket clientSocket, ObjectOutputStream writer) {
			try {
			//4. Las dos partes se comunican via Output e Input
				this.writer= writer;
				sock = clientSocket;
				//3.-Obtiene InputStream
				reader = new ObjectInputStream(sock.getInputStream());      
			} catch(Exception ex) {
				System.out.println("Exce Servidor reader " + ex);
				ex.printStackTrace();
			}
		} // Cierrre constructor

		public void run() {
		//Object obj;
			try {
				while (true) {
					//obtiene el mensaje
					UserSession obj = (UserSession) reader.readObject();
					System.out.println("NEWclient>" + obj.getUser() +" "+ obj.getLoginTime());
					us.add(obj); //agrega al arreglo
					
					tellEveryone(obj, writer);
				}
			} catch(Exception ex) {ex.printStackTrace();}
		} // close run
	} // close inner class
	
	
	public static void main (String[] args) {
		new VerySimpleChatServer().go();
	}
	
	public void go() {

		clientObjectOutputStreams = new ArrayList<ObjectOutputStream>();
		us=new ArrayList<UserSession>();
		try {
		//1.- creating a serverSocket
			ServerSocket serverSock = new ServerSocket(5000);

			while(true) {
			//2.-Wait for connection
				Socket clientSocket = serverSock.accept();
				
				//3.- Get OutputStream y lo agrega al arreglo
				ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());        
				clientObjectOutputStreams.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientSocket, writer));
				t.start();
				
				System.out.println("got a conexion");
			}
		// now if I get here I have a connection          
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void tellEveryone(Object obj, ObjectOutputStream writerp) {
		Iterator it = clientObjectOutputStreams.iterator();
		while(it.hasNext()) {
			try {
				ObjectOutputStream writer = (ObjectOutputStream) it.next();
				if(writer.equals(writerp)){
					for(int i=0;i<us.size();i++){
						writer.writeObject(us.get(i));
						writer.flush();
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		} // end while 
	} // close tellEveryone

}
