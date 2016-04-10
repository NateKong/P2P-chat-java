/**
 * A UDP server
 * UDP
 * @author Nathan Kong, Ardeshir Bastani, Y
 *
 */

import java.net.*;
import java.io.*;

public class STUN extends Thread{
	private ServerSocket serverSocket;
	
	/**
	 * constructor
	 * @param port number to listen to
	 * @throws IOException
	 */
	public STUN(int port) throws IOException{
		serverSocket = new ServerSocket(port);
	}
	
	/**
	 * Runs the server socket thread
	 */
	public void run(){
		while(true){
			try{
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + " ...");
				
				//gets a client with the ip and port number
				Socket server = serverSocket.accept();
				System.out.println( "Just connected to " + server.getRemoteSocketAddress() );
				
				//creates a stream to talk out of the socket
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
		        
	            //tells the client their IP and port
	            out.writeUTF("Your IP and port is: " + server.getRemoteSocketAddress() );
	            
	            
				
			}catch(IOException e){
				e.printStackTrace();
				break;
			}
		}
	}
	
	/**
	 * Runs the server bye creating a server and listens
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 54545;
		try{
			Thread t = new STUN(port);
			t.run();
		}catch(IOException e){
			e.printStackTrace();
		}

	}

}
