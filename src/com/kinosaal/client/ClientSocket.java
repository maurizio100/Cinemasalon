package com.kinosaal.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.kinosaal.global.transport.Reservierung;
import com.kinosaal.global.transport.ReservierungFeedback;

public class ClientSocket {

	private Socket connection;
	/* Fuer Stringuebertragung 
	private BufferedReader reader;
	private PrintWriter writer;*/
	private ObjectInputStream reader;
	private ObjectOutputStream writer;

	public ClientSocket(){
		try {
			this.connection = new Socket("127.0.0.1", 20000);
			this.writer = new ObjectOutputStream( connection.getOutputStream() );
			this.reader = new ObjectInputStream( connection.getInputStream() );
			/* this.reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
			 * this.writer = new PrintWriter(connection.getOutputStream()); */
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public ReservierungFeedback reservieren(int reihe, int plaetze){
		try {
			Reservierung res = new Reservierung( reihe , plaetze  );
			this.writer.writeObject(res);
			this.writer.flush();

			Object o = reader.readObject();
			if( o instanceof ReservierungFeedback ){
				ReservierungFeedback feedback = (ReservierungFeedback) o;
				return feedback;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
