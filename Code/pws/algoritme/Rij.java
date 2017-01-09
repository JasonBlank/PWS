package pws.algoritme;

public class Rij {
	private int bt = (int) System.currentTimeMillis()/1000;				//Begin time, current time
	static int ct;
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

	void timeLoop(){													//Tijdbijhouding om benodigde snelheid te kunnen berekenen
		ct = (int) System.currentTimeMillis()/1000 - bt;
	}

	void checknPlace(int wt, Vliegtuig vt){								//Hoofd controle functie; wt is Wanted Time


		switch (checkBefore(wt, vt)){									//0 is problematisch; 1 is ruimte
			case 0:
				switch (checkAfter(wt,vt)){
					case 0:
						//nu moeten we gaan schuiven. Aan allebei de kanten zit een vliegtuig.
						break;
					case 1: //Rechts is ruimte, links niet.
						if(checkBefore(wt-SepTime.getSepTime(abraham.getKlasse(),vt.getKlasse()),abraham) == 1){			//hier kijken of er ruimte is is voor abraham om naar links te gaan
							abraham.assignTime(abraham.getAt() - ((abraham.getAt()+sep)-wt));
							//Extra kosten van deze stap = (abraham.getAt()+sep-wt)*kosten van te vroeg;
							vtl[wt-bt] = vt;
						}
					else{
						if(checkAfter(abraham.getAt()+sep,vt) == 1){
							vtl[abraham.getAt()+sep] = vt;
							vt.assignTime(abraham.getAt()+sep);
							vt.setV_current(vt.getAfstand()/(vt.getAt()-ct));
							//extra kosten deze stap = ((wt-abraham.getAt()+sep)*Kosten van te laat;
					} 	else{
							checknPlace(abraham.getAt()+sep,vt);
							//chenknPlace(abraham.getAt()+sep -1, vt);
							//we willen dus dat we hier sws naar case 0.0 gaan. Zie voor uitleg 1.1
							//dit kunnen we doen zoals bij 1.1 uitgelegd. Maar ook mis door ze ze meteen door te sturen naar 0.0
							//maar ik denk dat het makkelijkst is om manier te doen zoals bij 1.1 omdat het anders wel erg moeilijk wordt om het te programmeren in 0.0
						}
					}
						//Rechts is ruimte maar kijk eerst of V1 naar links kan; is goedkoper.
						break;
				}
				break;

			case 1:
				switch (checkAfter(wt,vt)){
					case 0:
						//Links is ruimte dus op naar links met Vx.
						//Zo verder kijken
						if(checkBefore(abraham.getAt()-sep,vt) == 1){
								vtl[abraham.getAt()-sep] = vt;
								vt.assignTime(abraham.getAt()-sep);
								vt.setV_current(vt.getAfstand()/(vt.getAt()-ct));
								//extra kosten deze stap = ((abraham.getAt()-sep)-wt)*Kosten van te vroeg;
						} else{
							checknPlace(abraham.getAt()-sep,vt);
							//chenknPlace(abraham.getAt()-sep +1, vt);
							//hier gaat het fout nu. Want als je hem nu op nieuw laat lopen komt hij als het goed is uit bij 0.1 omdat er precies genoeg ruimte zou zijn om geen last te hebben van die van rechts.
							//In de 1.0 als hij niet naar links kan schuiven gaat hij weer terug naar wanneer hij precies geen lans meer heeft van links. En op die manier blijft hij dan bounchen tussen die twee.
							//We moeten er dus voor zorgen dat we de wt niet veranderen in precies sep er vanaf maar iets minder dat hij bij 0.0 komt zodat we het kunnen oplossen.
						}


						break;
					case 1:
						//Jee alle ruimte die er is. Nu lekker plaatsen op de optimale tijd.
						//done
						int diff = (int)wt;
						vtl[diff] = vt;
						vt.assignTime(diff);
						vt.setV_current(vt.getAfstand()/(vt.getAt()-ct));
						System.out.println("Placement successful. Assigned time: "+vt.getAt());
						//extra kosten = 0
						break;
				}
				break;
		}


	}



	private int checkBefore(long wt, Vliegtuig vt){		//Is er al een vliegtuig gepland op het gewenste tijdstip?

		for(int i = 0; i <= maxsep; i++) {
			if(wt-i >= 0 && wt < 2100) {
				if (vtl[(int) wt - i] != null) {
					abraham = vtl[(int) wt - bt - i];
					sep = SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse());

					for(int j = 0; j<= sep; j++) {
						if (vtl[(int) wt - bt - j] != null) {
							return 0;
						}
					}
					return 1;
				}
			}
		}


		return 1;
	}

	private int checkAfter(long wt, Vliegtuig vt){							//Nog verbeteren
		for(int i = 0; i <= maxsep; i++) {
			if(wt-bt+i < 2100) {
				if (vtl[(int) wt - bt + i] != null) {
					abraham = vtl[(int) wt - bt + i];
					sep = SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse());
					for (int j = 0; j <= sep; j++) {
						if (vtl[(int) wt - bt + j] != null) {
							return 0;
						}
					}
					return 1;
				}
			}
		}


		return 1;
	}



	public void update(){										//Ook nog verbeteren
		for(int i = 0; i < vtl.length; i++){
			if(vtl[i] != null){
				Vliegtuig v = vtl[i];
				v.update(ct-bt);
			}
		}
	}
}
