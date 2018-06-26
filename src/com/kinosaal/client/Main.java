package com.kinosaal.client;

public class Main {
	
	public static void main(String[] args) {
		KinoClientController controller = new KinoClientController();
		new ClientView(controller).setVisible(true);
	}
	
}
