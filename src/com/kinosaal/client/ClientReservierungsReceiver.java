package com.kinosaal.client;

import com.kinosaal.global.interfaces.FeedbackNotifier;

public interface ClientReservierungsReceiver extends FeedbackNotifier{

	public void reservieren( int reihe, int plaetze );
	
}
