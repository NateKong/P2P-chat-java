/**
 * Client side P2P chat
 * 
 * Creates UDP connection to server
 * Gets IP and Port
 * Creates TCP connection with peer
 * listens on port
 * @author Nathan Kong, Ardeshir Bastani, Yang Chao
 *
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {

	public static void main(String[] args) {
      //String serverName = "teamone.onthewifi.com";
	  String serverName = "10.0.0.232";
	  int port = 54545;
	  
      try
      {
    	 //connect to server
         System.out.println("Connecting to " + serverName + ":" + port );
         Socket client = new Socket(serverName, port);
         System.out.println("Just connected with " + client.getRemoteSocketAddress());
         
         //OutputStream outToServer = client.getOutputStream();
         //DataOutputStream out = new DataOutputStream(outToServer);
         //out.writeUTF("Hello from " + client.getLocalSocketAddress());
         
         //Get info from server
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         //Get your network info
         ArrayList<String> myInfo = new ArrayList<String>();
         String mInfo = in.readUTF();
         System.out.println("Server says: " + mInfo);
         for (String retval: mInfo.split(":") ){
        	 myInfo.add(retval);
         }
         
         //listen on socket
         
         
         //Get peer info
         ArrayList<String> peerInfo = new ArrayList<String>();
         String pInfo = in.readUTF();
         System.out.println("Server says: " + pInfo );
         for (String retval: pInfo.split(":") ){
        	 peerInfo.add(retval);
         }

      }catch(IOException e)
      {
         e.printStackTrace();
      }

	}//main
	
	private static void peerChat(ArrayList<String> socketAddress) {

		try {
			Socket peer = new Socket(socketAddress.get(1), Integer.parseInt(socketAddress.get(2)) );

			//output to peer
			OutputStream outToPeerB = peer.getOutputStream();
	        DataOutputStream out = new DataOutputStream(outToPeerB);
	        out.writeUTF("Hello from peer A");
	        
	        //input from peer
	        InputStream inFromServer = peer.getInputStream();
	        DataInputStream in = new DataInputStream(inFromServer);
	        System.out.println("B: " + in.readUTF());
	        
	        peer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
