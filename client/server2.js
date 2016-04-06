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
	var ip = addy.substring(7);
	console.log(ip + " is now connected");
	
  //gets the name of the user	
  socket.on('Senduser', function(name){
	  //console.log(name);
	/*  socketnames.push({name:name, socket:socket});
	  
		console.log(Object.keys(socketnames[0]));
		console.dir(socketnames);
	 */
	 socketnames.push(name);
	 for(var i = 0; i<socketnames.length; i++){console.log(socketnames[i]);}
	
	//send user name back to 
	io.emit('nameList', socketnames);
	 
  });
	
  socket.on('chat message', function(msg){
    io.emit('chat message', ip + ': ' + msg);
	console.log(ip + ': ' + msg);
  });
  
  socket.on('disconnect', function(data){
  	console.log(ip + " has left");  
  });
  
});

/*LISTEN ON PORT*/
http.listen(54545, function(){
  console.log('listening on Port:54545');
});