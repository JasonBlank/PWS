package pws.algoritme;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Container;

public class MainAlgoritme implements Runnable{
	Object window;
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
		while(running){		//Main loop

		}
	}

	public void update(){

	}


}
