package pws.engine;

import java.util.Random;

public class Timegeneration {
	public static int n = 40;
	private static int tt;//targettime van vliegtuig n;
	private static int lt;//latesttime van vliegtuig n;
	private static int v_cruise;
	private static int presenttime;
	private static int v_max;
	private static int klasse;
	static int ttlist[];
	static int v_cruiselist[];
	static int presenttimelist[];
	static int ltlist[];
	static int v_maxlist[];
	static int klasselist[];
	
	public static int getKlasse(int vn){
		klasse = klasselist[vn];
		return klasse;
	}
	
	public static int getV_max(int vn){
		v_max = v_maxlist[vn];
		return v_max;
	}
	
	public static int getlt(int vn){
		lt = ltlist[vn];
		return lt;
	}

	public static int gettt(int vn){
		tt = ttlist[vn];  
		return tt;			
	}
	
	public static int getV_cruise(int vn){
		v_cruise = v_cruiselist[vn];
		return v_cruise;
		
	}
	
	public static int getPresenttime(int vn){
		presenttime = presenttimelist[vn];
		return presenttime;
	}
	
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
			randomtt = randomint.nextInt(2000); // minimale tijd is 1960, maar ik neem 2100 omdat het anders door de random getallen kan zijn dat het niet kan anders, voornamelijk aan het begin of aan het eind, daar moeten we het nog even over hebben.
			ttlist[i] = randomtt;
			int randomcruise = randomint.nextInt(64);
			v_cruiselist[i] = randomcruise + 225;
			namelist[i] = i;
			
			
		}

		for(int i=1; i<=n; i++){
			
			presenttimelist[i] = ttlist[i]-(180000/v_cruiselist[i]);
			
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
