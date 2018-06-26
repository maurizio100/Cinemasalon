package com.kinosaal.server.interfaces;

import com.kinosaal.global.interfaces.NotificationReceiver;

public interface ServerReservierungsReceiver {

	public void reservieren( NotificationReceiver client, int reihe, int plaetze );
	
}
