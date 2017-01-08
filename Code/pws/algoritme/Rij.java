package pws.algoritme;

public class Rij {
	private int bt = (int) System.currentTimeMillis()/1000, ct = 0;		//Begin time, current time
	private Vliegtuig[] vtl = new Vliegtuig[2100];						//Vliegtuig timeline
	private int sep, maxsep = 189;										//Seperation
	//private int[] maxsepn = {0, 189, 145, 122, 60};					//Ik stel voor om het zo te doen omdat er anders is een gebied gezocht wordt dat niet nodig is.
	//private int[] maxsepv = {0, 60, 145, 167, 189}; 					//De 0 is om te zorgen dat je bij klasse 1 plek 1 kan opvragen
	private Vliegtuig abraham;											//Willekeurige naam want ik had daar zin in. Dit is de
																		//variabele waar het gevonden al ingeplande vliegtuig
																		//tijdelijk in komt voor berekeningen enzo.
	public int getCt(){
		return ct;
	}

	public void timeLoop(){												//Tijdbijhouding om benodigde snelheid te kunnen berekenen
		ct = (int) System.currentTimeMillis()/1000 - bt;
	}

	public void checknPlace(int wt, Vliegtuig vt){								//Hoofd controle functie; wt is Wanted Time


		switch (checkBefore(wt, vt)){											//0 is problematisch; 1 is ruimte
			case 0:
				switch (checkAfter(wt,vt)){
					case 0:
						//nu moeten we gaan schuiven. Aan allebei de kanten zit een vliegtuig.
						break;
					case 1:
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
						} else{
							checknPlace(abraham.getAt()-sep,vt);
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
