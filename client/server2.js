/*****************************************
STUN Server for P2P chat
using Socket.io with TCP connection

Port Forwarding must be enabled to port 54545

By: Nathan Kong, Ardeshir Bastani, Yangchaho

3/30/2016
****************************************/
var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var socketnames = [];

/*ON HTTP REQUEST SEND HTML FILE*/
app.get('/', function(req, res){
  res.sendFile( __dirname + '/client2.html');
});

/*MAKE CONNECTION*/
io.on('connection', function(socket){
	//console.dir(socket);
	//get information and say what ip entered the server
	var addy = socket.request.connection.remoteAddress;
	var port = socket.request.connection.remotePort;
	var mySocket = addy.substring(7) + ':' + port;
	console.log(mySocket + " is now connected");
	
  //gets the name of the user	
  socket.on('Senduser', function(name){
	console.log(mySocket + ": " + name);
	var obj = new Object();
	obj[name] = mySocket;
	socketnames.push(obj);
	
    var names =[];	
	for(var i = 0; i<socketnames.length; i++){
		names.push(Object.keys(socketnames[i]));
	}
		
	//sends all usernames to clients
	io.emit('nameList', names);
	 
  });
	
  //get user for p2p
  socket.on('p2p', function(person){
	  var peerSocket;
	  
	  for (var i=0; i<socketnames.length; i++){
		for(person in socketnames[i]){
	      //console.log(socketnames[i][person]);
		  peerSocket = socketnames[i][person];
		}
	  }

	  io.emit('p2pInfo', peerSocket);
  });  
	
  socket.on('chat message', function(msg){
    io.emit('chat message', mySocket + ': ' + msg);
	console.log(mySocket + ': ' + msg);
  });
  
  //connection has stopped
  socket.on('disconnect', function(data){
  	console.log(mySocket + " has left");  
  });
  
});

/*LISTEN ON PORT*/
http.listen(54545, function(){
  console.log('listening on Port:54545');
});