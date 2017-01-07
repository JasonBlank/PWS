package pws.engine;

import pws.engine.Vliegtuig;
import pws.algoritme.*;

public class Rij {
	private int bt = (int) System.currentTimeMillis()/1000;		//Begin time
	private Vliegtuig[] vtl = new Vliegtuig[2100];				//Vliegtuig timeline
	private int maxsep = SepTime.getSepTime(1,4);


	public void checknPlace(Vliegtuig vt){				//Hoofd controle functie; wt is Wanted Time

		long wt = (int)(vt.getAfstand()/vt.getV_current());

		switch (checkBefore(wt)){								//0 is problematisch; 1 is ruimte
			case 0:
				switch (checkAfter(wt)){
					case 0:
						//nu moeten we gaan schuiven. Aan allebei de kanten zit een vliegtuig.
						break;
					case 1:
						//Rechts is ruimte maar kijk eerst of V1 naar links kan; is goedkoper.
						break;
				}
				break;

			case 1:
				switch (checkAfter(wt)){
					case 0:
						//Links is ruimte dus op naar links met Vx.
						break;
					case 1:
						//Jee alle ruimte die er is. Nu lekker plaatsen op de optimale tijd.
						int diff = (int)wt - bt;
						vtl[diff] = vt;
						break;
				}
				break;
		}


	}


	public int checkBefore(long wt){
		for(int i = 0; i <= maxsep; i++)
		if(vtl[(int)(wt - System.currentTimeMillis()/1000)-i] != null){
			return 1;
		}else {
			return 0;
		}
	}

	public int checkAfter(long wt){

		return 0;
	}



	public void dingen(){
		System.out.println(ct);
	}
}
