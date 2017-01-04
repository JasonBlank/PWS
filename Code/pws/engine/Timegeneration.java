package pws.engine;

import java.util.Random;

public class Timegeneration {
	public Timegeneration(){
		int n = 40;
		int[] ft = new int[n+1];
		int[] tt = new int[n+1];
		int[] lt = new int[n+1];
		int randomtt = 0;
		
		Random randomint = new Random();
		
		for(int i=1; i<=n; i++ ){
			randomtt = randomint.nextInt(2100); // minimale tijd is 1960, maar ik neem 2100 omdat het anders door de random getallen kan zijn dat het niet kan anders, voornamelijk aan het begin of aan het eind, daar moeten we het nog even over hebben.
		tt[i] = randomtt;
	
			
		}
		for(int i=1; i<=n; i++){
			if(tt[i]<=60){
				ft[i] = 0;
			}
			else {
				ft[i] = tt[i]-60;
			}
			if(tt[i]>=1800){
				lt[i] = 2100;  
			}
			else{
				lt[i] = tt[i]+300;
			}
			
			
			int zf = ft[i]/60;
			int af = ft[i] - (zf*60);
			System.out.println(zf + ":" + af);
			
			int zt = tt[i]/60;
			int at = tt[i] - (zt*60);
			System.out.println(zt + ":" + at);
			
			int zl = lt[i]/60;
			int al = lt[i] - (zl*60);
			System.out.println(zl + ":" + al);
			
			System.out.println(" ");
		}
		
	}

}
