package com.kinosaal.server.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.kinosaal.global.interfaces.NotificationReceiver;
import com.kinosaal.global.transport.Reservierung;
import com.kinosaal.global.transport.ReservierungFeedback;
import com.kinosaal.server.interfaces.ServerReservierungsReceiver;

public class ClientConnection 
implements Runnable, NotificationReceiver{

	private Socket connection;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private ServerReservierungsReceiver controller;

	public ClientConnection( Socket connection ){
		this.connection = connection;
		try {
			this.reader = new ObjectInputStream( this.connection.getInputStream() );
			this.writer = new ObjectOutputStream( this.connection.getOutputStream() );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setController( ServerReservierungsReceiver controller ){
		this.controller = controller;
	}
	
	@Override
	public void run() {
		Object o = null;
		try {
			while( true ){

				if( (o = reader.readObject()) != null ){
					if( o instanceof Reservierung ){
						Reservierung res = (Reservierung) o;
						this.controller.reservieren(this, res.getReihe(), res.getPlaetze() );
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Client has made order and is disconnected again!");
		}
	}

	@Override
	public void receiveNotification(ReservierungFeedback notification) {
		try {
			this.writer.writeObject( notification );
			this.writer.flush();
	/*Um Clients wieder zu disconnecten:
			this.connection.close();
	*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
