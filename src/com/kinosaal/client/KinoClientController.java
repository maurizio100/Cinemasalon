package com.kinosaal.client;

import com.kinosaal.global.interfaces.NotificationReceiver;

public class KinoClientController 
implements ClientReservierungsReceiver{

	private NotificationReceiver receiver;
	private ClientSocket socket;
	
	public KinoClientController(){
		socket = new ClientSocket();
	}
	
	@Override
	public void reservieren(int reihe, int plaetze) {
		this.receiver.receiveNotification(socket.reservieren(reihe, plaetze));
	}

	@Override
	public void registerNotificationReceiver(NotificationReceiver rcv) {
		this.receiver = rcv;
	}

	
	
}
