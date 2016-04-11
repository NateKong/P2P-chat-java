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

public class Client extends Thread{

	String activity;
	static String myIp;
	static int myPort;
	static String peerIp;
	static int peerPort;
	
	public Client(String activity){
		this.activity = activity;
		this.myIp = "";
		this.peerIp = "";
	}
	
	/**
	 * runs the threads to listen to the port and talk to the peer
	 */
	public void run(){
		if(activity == "listen"){
			peerListen();
		}else{
			peerSend();
		}
	}
	
	/**
	 * Starts the program
	 * @param args
	 * @throws SocketException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws Exception {
      //String serverName = "teamone.onthewifi.com";
	  String serverName = "10.0.0.232";
	  int port = 54545;

      // prepare Socket and data to send
      DatagramSocket clientSocket = new DatagramSocket();
      byte[] sendData = "Hello".getBytes();
      System.out.println("sending Hello to server");

      // send Data to Server
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(serverName), port);
      clientSocket.send(sendPacket);

      // receive Data 
      DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
      clientSocket.receive(receivePacket);
      System.out.println("received data from server");

      // Convert Response to IP and Port
      String response = new String(receivePacket.getData());
      String[] splitResponse = response.split(":");
      myIp = splitResponse[0];
      myPort = Integer.parseInt(splitResponse[1]);
  	  peerIp = splitResponse[2];
  	  peerPort = Integer.parseInt(splitResponse[3]);
      System.out.println("my Info: " + myIp + ":" + myPort );
      System.out.println("peer Info: " + peerIp + ":" + peerPort );

      //listen to port
      /*Thread listen = new Client("listen");
      listen.start();
      //send datagram
      Thread send = new Client("send");
      send.start();
      */
  	  
  	  clientSocket.close();
	}//main
	
	/**
	 * sends the p2p chat
	 */
	private static void peerSend() {

		try {
			/*Socket peer = new Socket(socketAddress.get(1), Integer.parseInt(socketAddress.get(2)) );

			//output to peer
			OutputStream outToPeerB = peer.getOutputStream();
	        DataOutputStream out = new DataOutputStream(outToPeerB);
	        out.writeUTF("Hello from peer A");
	        
	        //input from peer
	        InputStream inFromServer = peer.getInputStream();
	        DataInputStream in = new DataInputStream(inFromServer);
	        System.out.println("B: " + in.readUTF());
	        
	        peer.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The listens to the socket
	 */
	private static void peerListen(){
		
	}

}
