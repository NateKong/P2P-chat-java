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
var socketnames = {};

/** 
When a client calls the server client.html is initialized
*/
app.get('/', function(req, res){
  res.sendFile( __dirname + '/client.html');
});

/**
	Creates the connection from the client to the server
*/
io.on('connection', function(socket){
  //console.dir(socket);//sends to console the socket information

  var addy = socket.request.connection.remoteAddress;//the IP address of the client socket
  var ip = addy.substring(7);//the IP address formatted
  console.log(ip + " has connected to the server");

  socket.on('chat message', function(msg){
    io.emit('chat message', ip + ': ' + msg);
	console.log(ip + ': ' + msg);
  });
  //sends the user names of everyone on the server
  /*socket.on('username', function(name){
	var usernames = false;
	if (!(name in socketnames)){
		socketnames.name = socket;//adds to object
		console.log( Object.keys(socketnames) );
		//usernames = Object.keys(socketnames);
		usernames = [bob, john];
	}
	console.log(usernames);
	io.emit('username', usernames); 
  });
  */
/*  
  //sends the P2P information
  socket.on('p2p', function(username){
	if(username in socketnames){
		//io.emit('p2p', socketnames.username);
		console.dir(socketnames.username);
	}
  });
 */  
  socket.on('disconnect', function(data){
	//socketnames.splice(socketnames.indexOf(addy),1);
	//console.dir(socketnames);
	//console.log(socketnames.indexOf(addy));
  	console.log(ip + ' has disconneted from the server');  
  });
 
});

/**
Listens to this port when the progarm begins
*/
http.listen(54545, function(){
  console.log('STUN server is listening on Port: 54545');
});