# P2P-chat
A peer to peer chat using java

There are 2 parts to this project:<br>
1.Client side<br>
2.Server side<br>

The server gets turned on.<br>
Client 1 sends a datagram to the server through UDP.<br>
The server pulls the ip and port from the datagram.<br>
Client 2 sends a datagram to the server through UDP.<br>
The server pulls the ip and port from the datagram.<br>
The server sends a datagram to client 1 -> client1IP:client1PORT:client2IP:client2PORT:end<br>
The server sends a datagram to client 2 -> client2IP:client2PORT:client1IP:client1PORT:end<br>

Client 1 contacts Client 2 and sends a message "Can you hear me?"<br>
Client 2 recieves the message and sends a message "I can hear you."<br>

Client 2 contacts Client 1 and sends a message "Can you hear me?"<br>
Client 1 recieves the message and sends a message "I can hear you."<br>
