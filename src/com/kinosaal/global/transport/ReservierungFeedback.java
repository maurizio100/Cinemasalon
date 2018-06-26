package com.kinosaal.global.transport;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class ReservierungFeedback implements Serializable{

	private int resNr;
	private List<Platz> plaetze;
	
	public ReservierungFeedback( int resnummer ){
		this.resNr = resnummer;
		this.plaetze = new LinkedList<Platz>();
	}
	
	public void addPlatz( Platz platz ){
		plaetze.add(platz);
		
	}
	
	public String[] getPlaetze() {
		String[] plaetze = new String[this.plaetze.size()];
		int i = 0;
		
		for( Platz p : this.plaetze ){
			plaetze[i] = p.toString();
			i++;
		}
		
		return plaetze;
	}
	
	public int getResNr(){
		return resNr;
	}

}
