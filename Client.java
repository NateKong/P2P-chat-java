/**
 * Client side P2P chat
 * 
 * Creates UDP connection to server
 * Gets IP and Port
 * Creates TCP connection with peer
 * listens on port
 * @author Nathan Kong, Ardeshir Bastani,
 *
 */

import java.io.*;
import java.net.*;

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
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         
         //OutputStream outToServer = client.getOutputStream();
         //DataOutputStream out = new DataOutputStream(outToServer);
         //out.writeUTF("Hello from " + client.getLocalSocketAddress());
         
         //Get info from server
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         System.out.println("Server says: " + in.readUTF());

      }catch(IOException e)
      {
         e.printStackTrace();
      }

	}//main

}
