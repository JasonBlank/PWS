package pws.algoritme;

public class MainAlgoritme implements Runnable{

	private Object window;
	private Vliegtuig vt;
	boolean running = false;

	public MainAlgoritme(Object window){
		this.window = window;
	}


	public void start(){
		System.out.println("Alg start");
		run();
		try{run();}catch (Exception e){
			System.out.println(e);
		}
	}

	public void run(){
		Rij rij = new Rij();
		Vliegtuig vtg = new Vliegtuig(6000, 3, rij);
		while(running) {       				//Main loop
			rij.timeLoop();
			rij.update();
			update();

		}
	}

	public void update(){

	}


}