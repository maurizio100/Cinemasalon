package com.kinosaal.global.interfaces;

import com.kinosaal.global.transport.ReservierungFeedback;


public interface NotificationReceiver {

	public void receiveNotification( ReservierungFeedback notification );
	
}
