package pws.algoritme;

import pws.engine.Timegeneration;

import java.awt.event.ActionEvent;
import java.sql.Time;

public class MainAlgoritme implements Runnable{


	/*-----------------------------
	|    VARIABLE DECLARATIONS    |
	-----------------------------*/

	private Object window;
	private Vliegtuig vt;
	boolean running = true;
	private Rij rij;
	static int cycles_per_second = 1;


	/*-------------------
	|    CONSTRUCTOR    |
	-------------------*/

	public MainAlgoritme(Object window){
		this.window = window;
	}


	/*---------------
	|    METHODS    |
	---------------*/

	public void start(){
		System.out.println("Alg start");
		run();
	}

	public void run(){
		Timegeneration tg = new Timegeneration();
		rij = new Rij();
		double now, lasttime = System.currentTimeMillis();
		System.out.println("Continuing to main loop");



		while(true) {	//Main loop
			if(running) {
				now = System.currentTimeMillis();
				if(now-lasttime >= 1000/cycles_per_second) {
					rij.timeLoop();
					update((now - lasttime)*cycles_per_second, tg);
					System.out.println(rij.getCt());
					lasttime = now;
				}
			}
		}
	}

	private void update(double dtime, Timegeneration tg){
		Vliegtuig[] vtl = rij.getVtl();
		for(int i = 0; i < vtl.length;i++){
			if(vtl[i] != null){
				vtl[i].update(dtime);
			}
		}

		int[] ptl = tg.getPresentTimeLine();
		int ct = rij.getCt();
		if(ptl[ct] != 0){
			Vliegtuig vt = new Vliegtuig(ptl[ct], 180000, Timegeneration.getKlasse(ptl[ct]), rij);
		}

	}


}