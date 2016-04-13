/**
 * A UDP server
 * Listens to port 54545 and sends the ip addresses and ports to the clients
 * @author Nathan Kong, Ardeshir Bastani, Yangcha Ho
 *
 */

import java.net.*;
import java.io.*;

public class Server2 {
	private static String c1IP;
	private static int c1Port;
	private static String c2IP;
	private static int c2Port;
	
	public static void main(String[] args) throws Exception{
		c1IP = "";
		c1Port = 0;
		c2IP = "";
		c2Port = 0;
		
		//create a socket for udp on port 54545
		DatagramSocket serverSocket = new DatagramSocket(54545);

		while(true){
			System.out.println("Waiting for Clients on Port 54545...");
			
			//create packet to receive data
			DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
			serverSocket.receive(receivePacket);
			
			//get address and port from datagram
			InetAddress ipAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String ip = ipAddress.toString();
			
			System.out.println("RECEIVED: " + ip + ":" + port);
		}
	}

}
