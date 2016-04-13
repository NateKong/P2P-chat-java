/**
 * A UDP server
 * Listens to port 54545 and sends the ip addresses and ports to the clients
 * @author Nathan Kong, Ardeshir Bastani, Yangcha Ho
 *
 */

import java.net.*;
import java.io.*;

public class STUN{

	public static void main(String[] args) throws IOException {

		//create a socket for udp on port 54545
		DatagramSocket serverSocket = new DatagramSocket(54545);	
		
		while(true){
			/** First client **/
			//create packet to receive data
			System.out.println("Waiting for Clients on Port 54545...");
			DatagramPacket receivePacket1 = new DatagramPacket(new byte[1024], 1024);
			serverSocket.receive(receivePacket1);
			
			//get address and port from datagram
			InetAddress p1IPAddress = receivePacket1.getAddress();
			int p1Port = receivePacket1.getPort();
			System.out.println("RECEIVED: " + p1IPAddress.toString() + ":" + p1Port);
			
			/** Second client **/
			//DatagramSocket serverSocket2 = new DatagramSocket(54545);
			System.out.println("Waiting for Clients on Port 54545...");
			//create packet to receive data
			DatagramPacket receivePacket2 = new DatagramPacket(new byte[1024], 1024);
			serverSocket.receive(receivePacket2);
			
			//get address and port from datagram
			InetAddress p2IPAddress = receivePacket2.getAddress();
			int p2Port = receivePacket2.getPort();
			String msgInfoOfClient2 = p2IPAddress.toString().substring(1) + ":" + p2Port + ":" + p1IPAddress.toString().substring(1) + ":"+ p1Port + ":end";
			System.out.println("RECEIVED: " + p2IPAddress.toString() + ":" + p2Port);
			
			/** Send data to each other **/
			//Structure of message -> myIp:myPort:peerIp:peerPort:end
			String msgInfoOfClient1 = p1IPAddress.toString().substring(1) + ":" + p1Port + ":" + p2IPAddress.toString().substring(1) + ":"+ p2Port + ":end";
			
			// Send Information of Client2 to Client1
	        serverSocket.send(new DatagramPacket(msgInfoOfClient1.getBytes(), msgInfoOfClient1.getBytes().length, p1IPAddress, p1Port));

	        // Send Information of Client1 to Client2
	        serverSocket.send(new DatagramPacket(msgInfoOfClient2.getBytes(), msgInfoOfClient2.getBytes().length, p2IPAddress, p2Port));
		}


	}

}
