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
	private Timegeneration tg;
	static int cycles_per_second = 100;


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
		tg = new Timegeneration();
		rij = new Rij();
		double now, lasttime = System.currentTimeMillis();
		System.out.println("Continuing to main loop");



		while(true) {	//Main loop
			if(running) {
				now = System.currentTimeMillis();
				if(now-lasttime >= 1000/cycles_per_second) {
					rij.timeLoop();
					update((now - lasttime)/1000*cycles_per_second, tg);
					//System.out.println(rij.getCt());
					lasttime = now;
				}
			}
		}
	}

	private void update(double dtime, Timegeneration tg) {
		Vliegtuig[] vtl = rij.getVtl();
		for (int i = 0; i < vtl.length; i++) {
			if (vtl[i] != null) {
				vtl[i].update(dtime);
			}
		}

		int[] ptl = tg.getPresentTimeLine();
		int ct = rij.getCt();
		if (ct < 2800) {
			if (ptl[ct] != 0) {
				if (ct < 2800) {
					Vliegtuig vt = new Vliegtuig(ptl[ct], 180000, Timegeneration.getKlasse(ptl[ct]), rij);
				}
			}

		}else{
			int kost = 0;
			for(int i=0; i<2800; i++){
				int verschil = 0;
				if(Rij.getVtl()[i] != null){
					verschil = i - Timegeneration.getTt(Rij.getVtl()[i].getName());
					if(verschil <= 0){
						kost -= verschil*Rij.getCostearly();
					}
					else{
						kost += verschil*Rij.getCostlate();
					}
				}
			}
			System.out.println(kost);
			System.out.println("Klaaaaaaarrrrrr");
			running = false;
		}
	}


}