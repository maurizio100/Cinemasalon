package com.kinosaal.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.kinosaal.server.interfaces.ClientConnectionManager;

public class ServerConnection extends Thread{

	private ServerSocket socket;
	private ClientConnectionManager clientmanger;

	public ServerConnection( ClientConnectionManager clientmanager ){

		try {
			this.socket = new ServerSocket(20000);
			this.clientmanger = clientmanager;
			this.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run(){

		try {
			while(true){
				Socket newclient = this.socket.accept();
				this.clientmanger.addClientConnection( new ClientConnection(newclient) );
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
