package pws.algoritme;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Container;

public class MainAlgoritme implements Runnable{
	Object window;
	private Vliegtuig vt;
	boolean running = false;

	public MainAlgoritme(Object window){
		this.window = window;
	}


	public void start(){
		try{run();}catch (Exception e){
			System.out.println("Error starting run() on MainAlgoritme");
		}
	}

	public void run(){
		Rij rij = new Rij();
		while(running) {       				//Main loop
			if(!rij.empty) vt = rij.get();

			update();

		}
	}

	public void update(){

	}


}
