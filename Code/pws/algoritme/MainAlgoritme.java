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
	boolean running = false;
	private Rij rij;


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
		System.out.println("Passed first run()");
		//try{run();}catch (Exception e){System.out.println(e);}
	}

	public void run(){
		Timegeneration tg = new Timegeneration();
		rij = new Rij();
		Vliegtuig vtg = new Vliegtuig(1,60000,2, rij);
		Vliegtuig vtd = new Vliegtuig(2,90000,2, rij);
		double now, lasttime = System.currentTimeMillis();



		while(true) {	//Main loop
			if(running) {
				rij.timeLoop();
				now = System.currentTimeMillis();
				update(now-lasttime);
				lasttime = now;

			}
		}
	}

	private void update(double dtime){
		Vliegtuig[] vtl = rij.getvtl();

		for(int i = 0; i < vtl.length;i++){
			System.out.println("Update, dtime: "+dtime);
			if(vtl[i] != null){
				vtl[i].update(dtime);
			}
		}
	}


}