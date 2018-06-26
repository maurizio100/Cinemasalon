package com.kinosaal.server.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import com.kinosaal.server.KinoSaalConfigs;
import com.kinosaal.server.interfaces.KinosaalCommandReceiver;
import com.kinosaal.server.interfaces.PlatzStatusChanger;
import com.kinosaal.server.interfaces.PlatzStatusReceiver;

public class Kinosaal extends JFrame
implements PlatzStatusReceiver{

	
	private JScrollPane panLogarea;
	private JPanel panSaal;
	private JPanel panSouth;
	private JPanel panlog = new JPanel();
	
	private JTextArea txtLog;
	
	private JButton btnReset;
	
	private JButton[][] plaetze;
	
	private KinosaalCommandReceiver controller;
	private PlatzStatusChanger model;
	
	public Kinosaal( KinosaalCommandReceiver controller, PlatzStatusChanger model ){
		this.controller = controller;
		this.model = model;
		this.model.registerPlatzStatusReceiver(this);
		this.init();
	}
	
	
	private void init(){
		this.setBounds(250, 100, 800, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(this.createLogArea(), BorderLayout.EAST );
		this.add(this.createSaalArea(), BorderLayout.CENTER );
		this.add(this.createSouthArea(), BorderLayout.SOUTH );
		
	}


	private Component createLogArea() {
		this.panlog.setLayout(new GridLayout(1,1));
		
		this.txtLog = new JTextArea(30,25);
		this.txtLog.setEditable(false);
		this.panlog.add(this.txtLog);
		
		this.panLogarea = new JScrollPane(panlog);
		this.panLogarea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.panLogarea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.panLogarea.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.ORANGE));

		return this.panLogarea;
	}
	
	
	private Component createSaalArea(){
		this.panSaal = new JPanel();
		int anzreihe = KinoSaalConfigs.ANZREIHEN;
		int anzplaetze = KinoSaalConfigs.ANZPLAETZE;
		
		this.panSaal.setLayout(new GridLayout( anzreihe, anzplaetze ) );
		this.plaetze = new JButton[anzreihe][anzplaetze];
	
		
		for( int reihe = 0; reihe < anzreihe; reihe++ ){
			
			for( int platz = 0; platz < anzplaetze; platz++ ){
				JButton btnPlatz = new JButton();
				btnPlatz.setEnabled(false);
				plaetze[reihe][platz] = btnPlatz;
				this.panSaal.add(btnPlatz);
			}
			
		}
		
		return this.panSaal;
	}
	
	private Component createSouthArea(){
		
		this.panSouth = new JPanel();
		this.btnReset = new JButton("Reset");
		
		this.panSouth.setLayout( new GridLayout(1,1) );
		this.btnReset.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.resetSaal();
			}
		});
		
		this.panSouth.add(this.btnReset);
		
		return this.panSouth;
	}


	@Override
	public void setReservierung(int reihe, int platz, int resnummer) {
		this.plaetze[reihe][platz].setBackground(Color.BLUE);
		this.plaetze[reihe][platz].setText( resnummer + "");
	}


	@Override
	public void receiveStatusMessage(String statusMessage) {
		this.txtLog.append( statusMessage + "\n" );
	}


	@Override
	public void resetPlaetze() {
		int anzreihe = KinoSaalConfigs.ANZREIHEN;
		int anzplaetze = KinoSaalConfigs.ANZPLAETZE;

		for( int reihe = 0; reihe < anzreihe; reihe++ ){
			
			for( int platz = 0; platz < anzplaetze; platz++ ){
				this.plaetze[reihe][platz].setText("");
				this.plaetze[reihe][platz].setBackground(null);
			}
			
		}
	}
	
}
