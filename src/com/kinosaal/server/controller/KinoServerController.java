package com.kinosaal.server.controller;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.kinosaal.global.interfaces.NotificationReceiver;
import com.kinosaal.server.interfaces.ClientConnectionManager;
import com.kinosaal.server.interfaces.KinosaalCommandReceiver;
import com.kinosaal.server.interfaces.ServerReservierungsReceiver;
import com.kinosaal.server.network.ClientConnection;
import com.kinosaal.server.network.ServerConnection;

public class KinoServerController 
implements ServerReservierungsReceiver, ClientConnectionManager, KinosaalCommandReceiver{

	
	private Executor clientpool;
	private ServerReservierungsReceiver resreceiver;
	private ServerConnection connection;
	private KinosaalCommandReceiver commandreceiver;
	
	public KinoServerController(
			ServerReservierungsReceiver resreceiver,  KinosaalCommandReceiver commandreceiver ){
		
		this.resreceiver = resreceiver;
		this.commandreceiver = commandreceiver;
		this.connection = new ServerConnection(this);
		this.clientpool = Executors.newCachedThreadPool();
	}

	
	@Override
	public void reservieren(NotificationReceiver client, int reihe, int plaetze) {
		this.resreceiver.reservieren(client, reihe, plaetze);
	}

	@Override
	public void addClientConnection(ClientConnection connection) {
		connection.setController(this);
		clientpool.execute(connection);
	}


	@Override
	public void resetSaal() {
		this.commandreceiver.resetSaal();
	}

}
