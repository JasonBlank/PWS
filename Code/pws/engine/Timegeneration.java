package pws.engine;

import java.util.Random;

public class Timegeneration {

	/*-----------------------------
	|    VARIABLE DECLARATIONS    |
	-----------------------------*/

	private static int n = 20;
	private static int tt;                            // Target time van vliegtuig n
	private static int lt;                            // Latest time van vliegtuig n
	private static int v_cruise;                      // Optimale (cruise) snelheid van vliegtuig n
	private static int presenttime;                   // Vgm de tijd waarop het vliegtuig announced wordt bij het algoritme
	private static int v_max;                         // Maximale snelheid van vliegtuig n
	private static int klasse;                        // Gewichtsklasse (zie SepTime) van vliegtuig n
	static int ttlist[];                              // Lijst van target times (tt) van alle vliegtuigen
	static int v_cruiselist[];                        // Lijst van cruisesnelheden (v_cruise) van alle vliegtuigen
	static int presenttimelist[];                     // Lijst van aanmeldtijden (presenttime) van alle vliegtuigen
	static int ltlist[];                              // Lijst van laatste landingstijden (lt) van alle vliegtuigen
	static int v_maxlist[];                           // Lijst van maximale snelheden (v_max) van alle vliegtuigen
	static int klasselist[];                          // Lijst van gewichtsklasses van alle vliegtuigen
	private int presenttimeline[] = new int[2801];    // Tijdlijn met de indeling van de planning van vliegtuigen
	

	/*-------------------
	|    CONSTRUCTOR    |
	-------------------*/

	public Timegeneration(){
		v_cruiselist = new int[n+1];	//cruisesnelheid
		ttlist = new int[n+1];	//target time
		int[] namelist = new int[n+1];
		ltlist = new int[n+1];	//latest time
		presenttimelist = new int[n+1];
		v_maxlist = new int[n+1];
		klasselist = new int[n+1];
		int randomtt = 0;        //dat is het getal dat gegenereerd wordt per vliegtuig waarop ft tt en lt gebaseerd worden. Dit getal wordt zo geproduceerd dat alles als het goed is binnen de perken blijft

		//bedacht me dat de array niet gesort hoeft te worden, omdat we een lijst hebben met welk vliegtuig zich wanneer presenteerd en we dus al alle in de goede volgorde hebben
		Random randomint = new Random();

		for(int i=1; i<=n; i++ ){
			randomtt = randomint.nextInt(2000) + 800; // 2000 voor de target time. De timeline verlengen we met 800 want dat is hoeveel de maximale tijd tussen melden en target time is. En we moeten zorgen dat de index time 800 eerder begint want anders wordt het lastig van het maken van de vliegtuigen die in de eerste 800 sec de tt hebben
			ttlist[i] = randomtt;
			int randomcruise = randomint.nextInt(64);
			v_cruiselist[i] = randomcruise + 225;
			namelist[i] = i;


		}

		for(int i=1; i<=n; i++){

			presenttimelist[i] = ttlist[i]-(180000/v_cruiselist[i]);
			presenttimeline[presenttimelist[i]] = i;					//als het goed is zou dit er voor moeten zorgen dat er een timeline is met de moementen dat het bepaalde vliegtuig zich plaatst
			v_maxlist[i] = v_cruiselist[i] + 16;

			if(i<=2){
				klasselist[i] = 1;
			}
			if(i>2 && i<=26){
				klasselist[i] = 2;
			}

			if(i>26 && i<=38){
				klasselist[i] = 3;
			}
			if(i>38){
				klasselist[i] = 4;
			}

			if(ttlist[i]>=2500){
				ltlist[i] = 2800;
			}
			else{
				ltlist[i] = ttlist[i]+300;
			}

			int zt = ttlist[i]/60;
			int at = ttlist[i] - (zt*60);
			System.out.println("Target time: " + zt + ":" + at);

			int zl = ltlist[i]/60;
			int al = ltlist[i] - (zl*60);
			System.out.println("Latest time: " + zl + ":" + al);

			System.out.println(" ");
		}

	}


	/*-----------------------
	|    GETTING METHODS    |
	-----------------------*/

	public static int getKlasse(int vn) {
		klasse = klasselist[vn];
		return klasse;
	}

	public static int getV_max(int vn) {
		v_max = v_maxlist[vn];
		return v_max;
	}

	public static int getLt(int vn) {
		lt = ltlist[vn];
		return lt;
	}

	public static int getTt(int vn) {
		tt = ttlist[vn];
		return tt;
	}

	public static int getV_cruise(int vn) {
		v_cruise = v_cruiselist[vn];
		return v_cruise;

	}

	public static int getPresenttime(int vn) {
		presenttime = presenttimelist[vn];
		return presenttime;
	}

	public int[] getPresentTimeLine(){
		return presenttimeline;
	}

}
