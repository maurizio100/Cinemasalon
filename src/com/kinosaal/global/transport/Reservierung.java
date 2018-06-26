package com.kinosaal.global.transport;

import java.io.Serializable;

public class Reservierung implements Serializable{

	private int plaetze;
	private int reihe;
	
	public Reservierung( int reihe, int plaetze ){
		this.plaetze = plaetze;
		this.reihe = reihe;
	}

	public int getPlaetze() {
		return plaetze;
	}
	
	public int getReihe() {
		return reihe;
	}
	
}
