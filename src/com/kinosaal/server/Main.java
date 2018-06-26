package com.kinosaal.server;

import com.kinosaal.server.controller.KinoServerController;
import com.kinosaal.server.model.KinoModel;
import com.kinosaal.server.view.Kinosaal;


public class Main {

	public static void main(String[] args) {
		KinoModel model = new KinoModel();
		KinoServerController controller = new KinoServerController(model, model);
		Kinosaal view = new Kinosaal(controller, model);
		view.setVisible(true);
	}

}
