import java.net.*;
import java.util.*;

public class SendClient {

	public static void main(String[] args) throws Exception {
		//String serverName = "teamone.onthewifi.com";
		  String serverName = "10.0.0.232";
		  int port = 54545;

	      // prepare Socket and data to send
	      DatagramSocket clientSocket = new DatagramSocket();
	      byte[] sendData = "Hello".getBytes();
	      System.out.println("sending Hello to server");
	      
	      for (int i=0; i<30; i++){
		      // send Data to Server
		      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(serverName), port);
		      clientSocket.send(sendPacket);
		      Thread.sleep(1000*i);
	      }
	}

}
