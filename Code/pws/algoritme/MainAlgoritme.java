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

		try{run();}catch (Exception e){
			System.out.println("Error starting run() on MainAlgoritme");
		}
	}

	public void run(){
		Rij rij = new Rij();
		while(running) {       				//Main loop
			rij.timeLoop();
			rij.update();
			update();

		}
	}

	public void update(){

	}


}