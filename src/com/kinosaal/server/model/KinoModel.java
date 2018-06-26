package com.kinosaal.server.model;

import com.kinosaal.global.interfaces.NotificationReceiver;
import com.kinosaal.global.transport.Platz;
import com.kinosaal.global.transport.ReservierungFeedback;
import com.kinosaal.server.KinoSaalConfigs;
import com.kinosaal.server.interfaces.KinosaalCommandReceiver;
import com.kinosaal.server.interfaces.PlatzStatusChanger;
import com.kinosaal.server.interfaces.PlatzStatusReceiver;
import com.kinosaal.server.interfaces.ServerReservierungsReceiver;

public class KinoModel 
implements PlatzStatusChanger, ServerReservierungsReceiver, KinosaalCommandReceiver{

	
	private PlatzStatusReceiver receiver;
	private PlatzStatus[][] platzstats;
	private int reihenauslastung[];
	private int resnummer;
	
	private final int anzreihen = KinoSaalConfigs.ANZREIHEN;
	private final int anzplaetze = KinoSaalConfigs.ANZPLAETZE;
	
	
	public KinoModel(){
		this.init();
		this.resnummer = 100;
	}
	
	private void init(){
		this.initPlaetze();
		this.initStats();
	}
	
	private void initStats(){
		this.reihenauslastung = new int[anzreihen];

		for( int i = 0; i < anzreihen; i++ ){
			reihenauslastung[i] = 0;
		}
		
	}
	
	private void initPlaetze(){
		int anzreihen = KinoSaalConfigs.ANZREIHEN;
		int anzplaetze = KinoSaalConfigs.ANZPLAETZE;
		this.platzstats = new PlatzStatus[anzreihen][anzplaetze];
		
		for( int reihe = 0; reihe < anzreihen; reihe++ ){	
			for( int platz = 0; platz < anzplaetze; platz++ ){
				platzstats[reihe][platz] = PlatzStatus.LEER;	
			}
		}
	}
	
	private ReservierungFeedback reservierePlaetze( int reihe, int plaetze ){
		ReservierungFeedback feedback = new ReservierungFeedback(resnummer);
		
		
		for( int i = reihe; plaetze > 0; i++ ){
			for( int j = 0; j < anzplaetze && plaetze > 0; j++ ){
				if( this.platzstats[i][j] == PlatzStatus.LEER ){
					plaetze--;
					this.platzstats[i][j] = PlatzStatus.RESERVIERT;
					this.receiver.setReservierung(i, j, this.resnummer);
					this.receiver.receiveStatusMessage( "Kunde " + resnummer + " bekommt Karte in Reihe " + i + " Platz " + j );
					feedback.addPlatz( new Platz((i+1),(j+1)) );
				}				
			}
			if( i == (anzreihen-1) ){
				i = -1;
			}
		}
		
		return feedback;
	}
	
	private void countAuslastung(){
		
		this.receiver.receiveStatusMessage("--------------------\nAuswertung der Reihen: " + "\n--------------------");
		for( int i = 0; i < anzreihen; i++ ){
			int resplaetze = 0;
			for( int j = 0; j < anzplaetze; j++ ){
				if( platzstats[i][j] == PlatzStatus.RESERVIERT ){
					resplaetze++;
				}
			}
			
			this.reihenauslastung[i] += resplaetze;
			this.receiver.receiveStatusMessage("Plaetze in Reihe " + (i+1) + ": " + resplaetze );
			this.receiver.receiveStatusMessage("Gesamte Auslastung: " + reihenauslastung[i]);
		}
	}
	
	@Override
	public void registerPlatzStatusReceiver(PlatzStatusReceiver receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public void reservieren(NotificationReceiver client, int reihe, int plaetze) {
		resnummer++;
		this.receiver.receiveStatusMessage("Kunde " + resnummer + " verlangt Plaetze in Reihe " + reihe );
		ReservierungFeedback feedback = this.reservierePlaetze( (reihe -1), (plaetze) );
		client.receiveNotification(feedback);
	}

	private enum PlatzStatus {
		LEER, RESERVIERT
	}

	@Override
	public void resetSaal() {
		this.countAuslastung();
		this.initPlaetze();
		this.receiver.resetPlaetze();
	}

}
