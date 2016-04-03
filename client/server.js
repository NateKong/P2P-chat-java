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

app.get('/', function(req, res){
  res.sendFile( __dirname + '/client.html');
});

io.on('connection', function(socket){
	//console.dir(socket);
	var addy = socket.request.connection.remoteAddress;
	var ip = addy.substring(7);
	console.log(ip + " is now connected");
	
  socket.on('Senduser', function(name){
	  console.log(name);
  });
	
  socket.on('chat message', function(msg){
    io.emit('chat message', ip + ': ' + msg);
	console.log(ip + ': ' + msg);
  });
  
  socket.on('disconnect', function(data){
  	console.log(ip + " has left");  
  });
  
});


http.listen(54545, function(){
  console.log('listening on Port:54545');
});