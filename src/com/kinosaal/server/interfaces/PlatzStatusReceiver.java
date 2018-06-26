package com.kinosaal.server.interfaces;

public interface PlatzStatusReceiver {

	public void setReservierung( int reihe, int platz, int resnummer );
	public void receiveStatusMessage( String statusMessage );
	public void resetPlaetze();
}
