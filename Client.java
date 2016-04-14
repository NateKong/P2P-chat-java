/**
 * Client side P2P chat
 * 
 * Creates UDP connection to server
 * Gets IP and Port
 * Creates TCP connection with peer
 * listens on port
 * @author Nathan Kong, Ardeshir Bastani, Yangcha Ho
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

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
	
	/**************************************************
	 * runs the threads to listen to the port and talk to the peer
	 */
	public void run(){
	  try{
		if(activity == "listen"){
			peerListen();
		}else{
			peerSend();
		}
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  
	}
	
	/**************************************************
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
      //System.out.println(clientSocket.isBound());
      clientSocket.setReuseAddress(true);
      
      
      byte[] sendData = "Hello".getBytes();
      System.out.println("sending Hello to server");

      // send Data to Server
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(serverName), port);
      clientSocket.send(sendPacket);

      // receive Data 
      DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
      clientSocket.receive(receivePacket);
      System.out.println("received data from server");
      clientSocket.close();
      // Convert Response to IP and Port
      String response = new String(receivePacket.getData());
      String[] splitResponse = response.split(":");
      myIp = splitResponse[0];
      myPort = Integer.parseInt(splitResponse[1]);
  	  peerIp = splitResponse[2];
  	  peerPort = Integer.parseInt(splitResponse[3]);
      System.out.println("my Info: " + myIp + ":" + myPort );
      System.out.println("peer Info: " + peerIp + ":" + peerPort );
	  System.out.println("\n\n");

	//clientSocket.bind(myPort);
	  //clientSocket.close();
	 
      //listen to port
      Thread listen = new Client("listen");
      listen.start();
      
      Thread.sleep(2000);
      
      //send datagram
      Thread send = new Client("send");
      send.start();
      
	  //waits for thread to end
	  listen.join();
	  send.join();
	}//main
	
	/**************************************************
	 * sends the p2p chat
	 */
	private static void peerSend() {

		try {
			//create socket
			//Socket mySoc = new Socket(peerIp, peerPort);
			Socket mySoc = new Socket();
			mySoc.setReuseAddress(true);
			//mySoc.bind( new InetSocketAddress("myIp", myPort) );
			mySoc.connect( new InetSocketAddress(peerIp, peerPort) );
			//System.out.println(mySoc.isBound());
			pause();
					
			//create streams
			DataOutputStream out = new DataOutputStream( mySoc.getOutputStream() );
			DataInputStream in = new DataInputStream( mySoc.getInputStream() );
			
			//create and send message
			System.out.println("Sending to Socket: " + peerPort);
			String msg = getTime() + "\t" + myPort + ": Can you hear me?";
			out.writeUTF(msg);
			System.out.println(msg);
			
			//get string from client B
			String newMsg = in.readUTF();
			System.out.println(newMsg + "\n");
			
			//close socket
			mySoc.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**************************************************
	 * The listens to the socket
	 * @throws Exception 
	 */
	private static void peerListen() throws Exception{
		//create and listen to socket
		System.out.println("Listening on Socket: " + myPort);
		ServerSocket peerSocket = new ServerSocket();
		peerSocket.setReuseAddress(true);
		peerSocket.bind( new InetSocketAddress(myIp, myPort) );
		peerSocket.setReuseAddress(true);
		Socket peer = peerSocket.accept();
		
		System.out.println("Just connected with peer");
		
		//create a stream to talk to other peer
		DataInputStream in = new DataInputStream(peer.getInputStream());
		DataOutputStream out = new DataOutputStream(peer.getOutputStream());
		
		//get string from client A
		String msg = in.readUTF();
		System.out.println(msg);
		
		//create a message and send it to Client A
		String newMsg = getTime() + "\t" +myPort+ ": Yes I can hear you!";
		out.writeUTF(newMsg);
		System.out.println(newMsg + "\n");
		
		//close socket
		peerSocket.close();
	}
	
	/**************************************************
	 * Creates a time stamp
	 */
	private static String getTime(){
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        Date dateobj = new Date();
        return df.format(dateobj);
	}
	
	/**************************************************
	 * Creates a random timer to wait
	 */
	private static void pause()throws Exception{
		//create random number between 1 and 15
		Random rand = new Random();
		int breath = rand.nextInt(5) + 1;
		Thread.sleep(breath*1000);
	}
}
