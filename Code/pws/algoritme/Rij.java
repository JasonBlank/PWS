package pws.algoritme;

public class Rij {
	private int bt = (int) System.currentTimeMillis()/1000;				//Begin time 						R E A L T I M E
	static int ct, wt;													//Current time en Wanted time:		I N D E X T I M E
	private Vliegtuig[] vtl = new Vliegtuig[2100];						//Vliegtuig timeline
	private int sep, maxsep = 189;										//Seperation
	//private int[] maxsepn = {0, 189, 145, 122, 60};					//Ik stel voor om het zo te doen omdat er anders is een gebied gezocht wordt dat niet nodig is.
	//private int[] maxsepv = {0, 60, 145, 167, 189}; 					//De 0 is om te zorgen dat je bij klasse 1 plek 1 kan opvragen
	private Vliegtuig abraham;											//Willekeurige naam want ik had daar zin in. Dit is de
																		//variabele waar het gevonden al ingeplande vliegtuig
																		//tijdelijk in komt voor berekeningen enzo.
	int getCt(){
		return ct;
	}

	Vliegtuig[] getvtl(){
		return vtl;
	}

	void timeLoop(){													//Tijdbijhouding om benodigde snelheid te kunnen berekenen
		ct = (int) System.currentTimeMillis()/1000 - bt;
	}

	void checknPlace(int wt, Vliegtuig vt){												//Hoofd controle functie; wt is Wanted Time
			//---------------------------------------------------------------------------------------------------
		if (checkBefore(wt, vt)) {                                    					//true is problematisch; false is ruimte
			if (checkAfter(wt, vt)) {
				//nu moeten we gaan schuiven. Aan allebei de kanten zit een vliegtuig.

			}
			//---------------------------------------------------------------------------------------------------
			else {//Rechts is ruimte, links niet.
				if (checkBefore(wt - SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse()), abraham)) {            //hier kijken of er ruimte is is voor abraham om naar links te gaan
					abraham.assignTime(abraham.getAt() - ((abraham.getAt() + sep) - wt));
					//Extra kosten van deze stap = (abraham.getAt()+sep-wt)*kosten van te vroeg;
					vtl[wt - bt] = vt;
				} else {
					if (checkAfter(abraham.getAt() + sep, vt)) {
						vtl[abraham.getAt() + sep] = vt;
						vt.assignTime(abraham.getAt() + sep);
						vt.setV_current(vt.getAfstand() / (vt.getAt() - ct));
						//extra kosten deze stap = ((wt-abraham.getAt()+sep)*Kosten van te laat;
					} else {
						checknPlace(abraham.getAt() + sep, vt);
						//chenknPlace(abraham.getAt()+sep -1, vt);
						//we willen dus dat we hier sws naar case false-false gaan. Zie voor uitleg true.true
						//dit kunnen we doen zoals bij true.true uitgelegd. Maar ook mis door ze ze meteen door te sturen naar false.false
						//maar ik denk dat het makkelijkst is om manier te doen zoals bij true.true omdat het anders wel erg moeilijk wordt om het te programmeren in false.false
					}
				}
			}
			//---------------------------------------------------------------------------------------------------
		} else{
			if(checkAfter(wt,vt)) {
				//Links is ruimte dus op naar links met Vx.
				//Zo verder kijken
				if (checkBefore(abraham.getAt() - sep, vt)) {
					vtl[abraham.getAt() - sep] = vt;
					vt.assignTime(abraham.getAt() - sep);
					vt.setV_current(vt.getAfstand() / (vt.getAt() - ct));
					//extra kosten deze stap = ((abraham.getAt()-sep)-wt)*Kosten van te vroeg;
				} else {
					checknPlace(abraham.getAt() - sep, vt);
					//chenknPlace(abraham.getAt()-sep +1, vt);
					//hier gaat het fout nu. Want als je hem nu op nieuw laat lopen komt hij als het goed is uit bij false.true omdat er precies genoeg ruimte zou zijn om geen last te hebben van die van rechts.
					//In de true.false als hij niet naar links kan schuiven gaat hij weer terug naar wanneer hij precies geen lans meer heeft van links. En op die manier blijft hij dan bounchen tussen die twee.
					//We moeten er dus voor zorgen dat we de wt niet veranderen in precies sep er vanaf maar iets minder dat hij bij false.false komt zodat we het kunnen oplossen.
				}
				//-----------------------------------------------------------------------------------------------
			} else{
					//Jee alle ruimte die er is. Nu lekker plaatsen op de optimale tijd.
					//done
					int diff = (int)wt;
					vtl[diff] = vt;
					vt.assignTime(diff);
					vt.setV_current(vt.getAfstand()/(vt.getAt()-ct));
					System.out.println("Placement successful. Assigned time: "+vt.getAt());
					//extra kosten = 0
			}
		}
		//-------------------------------------------------------------------------------------------------------

	}



	private boolean checkBefore(long wt, Vliegtuig vt){		//Is er al een vliegtuig gepland op het gewenste tijdstip?

		for(int i = 0; i <= maxsep; i++) {
			if(wt-i >= 0 && wt < 2100) {
				if (vtl[(int) wt - i] != null) {
					abraham = vtl[(int) wt - bt - i];
					System.out.println(abraham + " haha hoi");
					sep = SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse());

					for(int j = 0; j<= sep; j++) {
						if (vtl[(int) wt - bt - j] != null) {
							return true;
						}
					}
					return false;
				}
			}
		}


		return false;
	}

	private boolean checkAfter(long wt, Vliegtuig vt){							//Nog verbeteren
		for(int i = 0; i <= maxsep; i++) {
			if(wt+i < 2100) {
				if (vtl[(int) wt + i] != null) {
					abraham = vtl[(int) wt + i];
					System.out.println(abraham);
					sep = SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse());
					for (int j = 0; j <= sep; j++) {
						if (vtl[(int) wt + j] != null) {
							return true;
						}
					}
					return false;
				}
			}
		}
		return false;
	}
}
