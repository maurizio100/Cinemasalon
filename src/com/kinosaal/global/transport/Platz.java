package com.kinosaal.global.transport;

import java.io.Serializable;

public class Platz implements Serializable{

	private int platznr;
	private int reihe;
	
	public Platz( int reihe, int nummer ){
		this.reihe = reihe;
		this.platznr = nummer;
	}
	
	public int getPlatzNr(){
		return platznr;
	}
	
	public int getReihe(){
		return reihe;
	}
	
	public String toString(){
		return "Reihe " + reihe + " Platz " + platznr;
	}
	
}


