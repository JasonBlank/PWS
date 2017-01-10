package pws.engine;

import java.util.Random;

public class Timegeneration {
	public static int n = 40;
	private static int tt;//targettime van vliegtuig n;
	private static int lt;//latesttime van vliegtuig n;
	private static int v_cruise;
	static int ttlist[];
	static int v_cruiselist[];

	public static int gettt(int vn){
		tt = ttlist[vn];  //????
		return tt;			
	}
	
	public static int getV_cruise(int vn){
		v_cruise = v_cruiselist[vn];
		return v_cruise;
		
	}
	
	public Timegeneration(){
		int[] v_cruiselist = new int[n+1];	//first time
		ttlist = new int[n+1];	//target time
		int[] namelist = new int[n+1];
		int[] ltlist = new int[n+1];	//latest time
		int randomtt = 0;        //dat is het getal dat gegenereerd wordt per vliegtuig waarop ft tt en lt gebaseerd worden. Dit getal wordt zo geproduceerd dat alles als het goed is binnen de perken blijft
		
		
		Random randomint = new Random();
		
		for(int i=1; i<=n; i++ ){
			randomtt = randomint.nextInt(2100); // minimale tijd is 1960, maar ik neem 2100 omdat het anders door de random getallen kan zijn dat het niet kan anders, voornamelijk aan het begin of aan het eind, daar moeten we het nog even over hebben.
			ttlist[i] = randomtt;
			int randomcruise = randomint.nextInt(64);
			v_cruiselist[i] = randomcruise + 225;
			namelist[i] = i;
		}

		for(int i=1; i<=n; i++){
			
			if(ttlist[i]>=1800){
				ltlist[i] = 2100;  
			}
			else{
				ltlist[i] = ttlist[i]+300;
			}
							
			int zt = ttlist[i]/60;
			int at = ttlist[i] - (zt*60);
			System.out.println(zt + ":" + at);
			
			int zl = ltlist[i]/60;
			int al = ltlist[i] - (zl*60);
			System.out.println(zl + ":" + al);
			
			System.out.println(" ");
		}
		
	}

}
