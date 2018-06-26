package com.kinosaal.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.kinosaal.global.interfaces.NotificationReceiver;
import com.kinosaal.global.transport.ReservierungFeedback;

public class ClientView extends JFrame 
implements NotificationReceiver{

	private JPanel panCenter;
	private JPanel panEast;
	private JPanel panSouth;
	
	private JLabel lblReihe;
	private JLabel lblPlaetze;
	
	private JTextField txtReihe;
	private JTextField txtPlaetze;

	private JTextArea txtLog;
	
	private JButton btnReservieren;
	private ClientReservierungsReceiver controller;
	
	public ClientView( ClientReservierungsReceiver controller){
		this.controller = controller;
		this.controller.registerNotificationReceiver(this);
		init();
	}

	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 400);
		
		this.add( this.createCenter(), BorderLayout.NORTH);
		this.add( this.createEast(), BorderLayout.EAST);
		this.add(this.createSouth(), BorderLayout.SOUTH);
	}

	private Component createSouth() {
		this.panSouth = new JPanel();
		this.txtLog = new JTextArea(20,15);
		
		this.panSouth.setLayout( new GridLayout(1,1) );
		this.txtLog.setEditable(false);
		
		this.panSouth.add( this.txtLog );
		
		return this.panSouth;
	}

	private Component createEast() {
		this.panEast = new JPanel();
		this.btnReservieren = new JButton("Reservieren");
		this.btnReservieren.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int reihe = Integer.parseInt(txtReihe.getText());
				int plaetze = Integer.parseInt(txtPlaetze.getText());
				
				controller.reservieren( reihe, plaetze );
			}
		});
		
		panEast.add(btnReservieren);
		return panEast;
	}

	private Component createCenter() {
		this.panCenter = new JPanel();
		this.lblReihe = new JLabel("Reihe");
		this.lblPlaetze = new JLabel("Plaetze");
		this.txtReihe = new JTextField();
		this.txtPlaetze = new JTextField();
		
		this.panCenter.setLayout(new GridLayout(2,2));
		this.panCenter.add(lblReihe);
		this.panCenter.add(txtReihe);
		this.panCenter.add(lblPlaetze);
		this.panCenter.add(txtPlaetze);
		
		return this.panCenter;
	}

	@Override
	public void receiveNotification(ReservierungFeedback notification) {
		String[] plaetze = notification.getPlaetze();
		int resNummer = notification.getResNr();
		
		txtLog.setText("Ihr Reservierungscode ist: ");
		txtLog.append(resNummer + "\n");	
		txtLog.append("Ihre Karten sind:\n");
		for( String s : plaetze ){
			txtLog.append(s + "\n");
		}
		
	}
}
