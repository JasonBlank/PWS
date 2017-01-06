package pws.algoritme;

import javax.swing.*;

import pws.engine.Rij;
import pws.engine.Vliegtuig;

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
		System.out.println("Alg start");
		System.out.println(SepTime.getSepTime(1,4));
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

class SepTime {
	static private int[][] septime = new int[][]{{60,145,167,189},{60,98,122,145},{60,60,60,122},{60,60,60,60}};

	static int getSepTime(int x, int y){
		return septime[x-1][y-1];
	}
}