var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var socketnames = [];

app.get('/', function(req, res){
  res.sendFile( __dirname + '/client.html');
});

io.on('connection', function(socket){
	//console.dir(socket);
	var addy = socket.request.connection.remoteAddress;
	var ip = addy.substring(7);
	console.log("hello " + ip);
	socketnames.push(addy);
  socket.on('chat message', function(msg){
    io.emit('chat message', ip + ': ' + msg);
	console.log(ip + ': ' + msg);
  });
  
  socket.on('disconnect', function(data){
	socketnames.splice(socketnames.indexOf(addy),1);
  	console.log("goodbye " + ip);  
  });
  
});


http.listen(54545, function(){
  console.log('listening on Port:54545');
});