package pws.algoritme;

public class Rij {


	/*-----------------------------
	|    VARIABLE DECLARATIONS    |
	-----------------------------*/

	private long bt = System.currentTimeMillis()/1000;				//Begin time 						R E A L T I M E
	static int ct, wt;													//Current time en Wanted time:		I N D E X T I M E
	private Vliegtuig[] vtl = new Vliegtuig[2800];						//Vliegtuig timeline
	private int sep, maxsep = 189;										//Seperation
	private Vliegtuig abraham;											//Willekeurige naam want ik had daar zin in. Dit is de
	private final int costearly = 100;										//variabele waar het gevonden al ingeplande vliegtuig	
	private final int costlate = 139;																	//tijdelijk in komt voor berekeningen enzo.
	private int totalcost;
	private int totalcostL1;
	private int totalcostL11 = 0;
	private int totalcostL12 = 0;
	private int totalcostL13 = 0;
	private int totalcostL14 = 0;
	private int totalcostL2;
	private int totalcostL21 = 0;
	private int totalcostL22 = 0;
	private int totalcostL23 = 0;
	private int totalcostL24 = 0;
	public boolean landingsbaanok;
	private int landingsbaan;
	/*---------------
	|    GETTERS    |
	---------------*/
	
	int getCt(){
		return ct;
	}

	Vliegtuig[] getVtl(){
		return vtl;
	}




	/*------------------------
	|    CHECKING METHODS    |
	------------------------*/
	double KosttotalL1(int dtime, long wt, Vliegtuig org){
		int x = 1;
		int y = 1;
		double kostendan = 0;
		while(dtime > 0){
			if((costearly*x)<(y*costlate)){
				kostendan = gekkeFunctieVroeg(wt,org)[x];
				dtime -= kostendan;
				x++;
				totalcostL11 += kostendan;
			}
			else{
				kostendan = gekkeFunctieLaat(wt,org)[y];
				dtime -= (kostendan/costlate);
				y++;
				totalcostL11 += kostendan;
			}
				
			
		}
		return totalcostL11;
	}
	
	double KosttotalL2(int dtime, long wt, Vliegtuig org){
		int x = 1;
		int y = 1;
		double kostendan = 0;
		while(dtime > 0){
			if((costearly*x)<(y*costlate)){
				kostendan = gekkeFunctieVroeg(wt,org)[x]; // zorgen dat kan kijken in verschillende banen
				dtime -= kostendan;
				x++;
				totalcostL21 += kostendan;
			}
			else{
				kostendan = gekkeFunctieLaat(wt,org)[y]; // zorgen dat kan kijken in verschillende banen
				dtime -= (kostendan/costlate);
				y++;
				totalcostL21 += kostendan;
			}
				
			
		}
		return totalcostL21;
	}
	
	
    // hoofdfunctie moet nog een keer want anders kunnen we niet voor twee banenchecken. Dit kunnen we ook later doen, eerst zien of dit lukt

	void checknPlace(int wt, Vliegtuig vt){												//Hoofd controle functie; wt is Wanted Time
			//---------------------------------------------------------------------------------------------------
		if (checkBefore(wt, vt)) {                                    					//true is problematisch; false is ruimte
			if (checkAfter(wt, vt)) {
				System.out.println("Vliegtuigen links en rechts gevonden van "+vt);
				int neededspaceRight = -(abraham.getAt()-wt-SepTime.getSepTime(vt.getKlasse(),abraham.getKlasse()));
				checkBefore(wt,vt);
				int neededspaceLeft = -(wt- abraham.getAt() - SepTime.getSepTime(abraham.getKlasse(),vt.getKlasse()));
				int neededspaceTotal = -(neededspaceLeft + neededspaceRight);

				if(neededspaceLeft > neededspaceRight * costlate){
					System.out.println("Plaats "+vt+" naar rechts vanwege kosten");
					checkAfter(wt,vt);
					checknPlace(abraham.getAt() + neededspaceTotal - 1,abraham);
				}else{
					System.out.println("Plaats "+vt+" naar links vanwege kosten");
					checknPlace(abraham.getAt() - neededspaceTotal + 1,abraham);
					checknPlace(wt,vt);
				}
			}
			//---------------------------------------------------------------------------------------------------
			else {//Rechts is ruimte, links niet.
				if (checkBefore(wt - SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse()), abraham)) {            //hier kijken of er ruimte is is voor abraham om naar links te gaan
					System.out.println("Abraham kan en gaat naar links zodat "+vt+" geplaatst kan worden om "+wt);
					vtl[abraham.getAt()] = null;
					abraham.assignTime(abraham.getAt() - ((abraham.getAt() + sep) - wt));
					vtl[abraham.getAt()] = abraham;
					abraham.setV_current(abraham.getAfstand()/(abraham.getAt()-ct));
					printShit(abraham);
					//Extra kosten van deze stap = (abraham.getAt()+sep-wt)*kosten van te vroeg;
					totalcostL12 = (abraham.getAt() + SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse()) - wt)*costearly;
					vtl[wt] = vt;
					vt.assignTime(wt);
					vt.setV_current(vt.getAfstand()/(vt.getAt()-ct));
					printShit(vt);
				} else {
					if (checkAfter(abraham.getAt() + sep, vt)) {
						vtl[abraham.getAt() + sep] = vt;
						vt.assignTime(abraham.getAt() + sep);
						vt.setV_current(vt.getAfstand() / (vt.getAt() - ct));
						printShit(vt);
								//extra kosten deze stap = (wt-abraham.getAt()+sep)*Kosten van te laat;\
						totalcostL12 = ((wt + SepTime.getSepTime(vt.getKlasse(), abraham.getKlasse()) - abraham.getAt())*costlate);
					} else {
						checknPlace(abraham.getAt() + sep + 1, vt);
						checknPlace(wt,vt);
						//geen kosten functie wat wordt doorgestuurd
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
				if (!checkBefore(abraham.getAt() - sep, vt)){
					vtl[abraham.getAt() - sep] = vt;
					vt.assignTime(abraham.getAt() - sep);
					vt.setV_current(vt.getAfstand() / (vt.getAt() - ct));
					printShit(vt);
									//extra kosten deze stap = ((abraham.getAt()-sep)-wt)*Kosten van te vroeg;
					totalcostL13 = (wt - SepTime.getSepTime(vt.getKlasse(), abraham.getKlasse()))*costearly;
				} else {
					checknPlace(abraham.getAt() - sep + 1, vt);

					if(checkBefore(wt,vt)){
						//HIER MOET NOG IETS; ER STOND MOVELEFT DUS ZOIETS WSS
					}
				}
				//-----------------------------------------------------------------------------------------------
			} else{
					//Jee alle ruimte die er is. Nu lekker plaatsen op de optimale tijd.
					//done
					int diff = (int)wt;
					vtl[diff] = vt;
					vt.assignTime(diff);
					vt.setV_current(vt.getAfstand()/(vt.getAt()-ct));
					printShit(vt);
					//extra kosten = 0
					totalcostL14 = 0;
			}
		}
		//-------------------------------------------------------------------------------------------------------

	}
   
			//belangrijk
	private boolean checkBefore(long wt, Vliegtuig vt){		//Is er al een vliegtuig gepland op het gewenste tijdstip?

		for(int i = 0; i <= maxsep; i++) {
			if(wt-i >= 0 && wt < 2800) {
				if (vtl[(int) wt - i] != null) {
					abraham = vtl[(int) wt - i];
					sep = SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse());
					for(int j = 0; j<= sep; j++) {
						if (vtl[(int) wt - j] != null) {
							System.out.println("CheckBefore() heeft " + vtl[(int)wt-j] + " voor " + vt +" gevonden");
							return true;
						}
					}
				}
			}
		}
		System.out.println("CheckBefore() heeft geen vliegtuigen voor " + vt + " gevonden");
		return false;
	}
		
	private int checkLowestCost(){
		totalcostL1 = totalcostL11 + totalcostL12 + totalcostL13 + totalcostL14;
		totalcostL2 = totalcostL21 + totalcostL22 + totalcostL23 + totalcostL24;
		if(totalcostL1 < totalcostL2){ // pakt hij zo alle totalcost dingen. Want als het goed is, is er maar 1 die een waarde van niet 0 heeft. 
			landingsbaan = 1;
		}
		if(totalcostL2 < totalcostL1){
			landingsbaan = 2;
		}
		if(totalcostL1 < totalcostL2){
			landingsbaan = 1;         //hier moeten we nog even kijken wat we hier mee doen
		}
		return landingsbaan;
		
	}
	
	private boolean checkAfter(long wt, Vliegtuig vt){							//Nog verbeteren
		for(int i = 0; i <= maxsep; i++) {										//hier moeten we zorgen dat als abraham te dicht bij is dat hij dan aan geeft dat hij niet de ruimte heeft om te verplaatsen anders moeten we kijken waar we het anders doen.
			if(wt+i < 2800) {													//Dit hier boven geld ook voor het checken van er na. Maar de kans dat het vliegtuig dan niet meer kan verplaatsen is zeer klein. Maar moeten het voor de zekerheid maar wel checken anders gaan we problemen krijgen
				if (vtl[(int) wt + i] != null) {
					abraham = vtl[(int) wt + i];
					sep = SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse());
					for (int j = 0; j <= sep; j++) {
						if (vtl[(int) wt + j] != null) {
							System.out.println("CheckAfter() heeft "+abraham+" na "+vt+" gevonden");
							return true;
						}
					}
					System.out.println("CheckAfter() heeft geen vliegtuigen gevonden na "+vt + " gevonden");
					return false;
				}
			}
		}
		return false;
	}
			//belangrijk

	/*private int moveLeftCost(long wt, int dtime, Vliegtuig vtg, Vliegtuig org){
		int movabletime = 0, n = 0;
		int lastnumber = vtg.getAt();
		Vliegtuig[] lijstje1 = new Vliegtuig[0];
		Vliegtuig[] lijstje2 = new Vliegtuig[0];

		while (movabletime < dtime){
			for(int i = lastnumber; lastnumber > 0; lastnumber--){
				if(vtl[i] != null){
					n++;
					lijstje2 = new Vliegtuig[n];
					for(int j = 0; j < lijstje1.length;j++){
						lijstje2[j] = lijstje1[j];
					}
					lijstje2[n-1] = vtl[i];
					lijstje1 = lijstje2;
					//Moet nog een if inplementeren met controle of het vliegtuig wel zo ver naar links kan als gewenst.
					if(lijstje1.length < 2){
						movabletime += (vtg.getAt()-lijstje1[0].getAt()-SepTime.getSepTime(lijstje1[0].getKlasse(),vtg.getKlasse()));
					}else{
						movabletime += ((lijstje1[n-1].getAt()-lijstje1[n].getAt()-SepTime.getSepTime(lijstje1[n].getKlasse(),lijstje1[n-1].getKlasse())));
					}

				}
			}
			if(movabletime < dtime){
				//Kan niet ver genoeg naar links schuiven
			}
		}
		//Het is gelukt!! De lijst met alle naar links te verplaatsen vliegtuigen staat in lijstje2!!

		return movabletime;
	}
	*/

	/*private int moveLeft(long wt, int dtime, Vliegtuig vtg, Vliegtuig org, boolean forced){
		Vliegtuig vt;
		int movedtimeleft = 0;

		checkBefore(wt, org);
		if(true) {
			if (org.getAt() - vt.getFirsttime(vt.getAfstand()) >= dtime) {
				if (!checkBefore(vt.getAt() - dtime, vt)) {
					vt.assignTime(vt.getAt() - dtime);
					System.out.println("Abraham wordt " + dtime + " naar links verplaatst");
					checknPlace(vt.getAt() + SepTime.getSepTime(vt.getKlasse(), org.getKlasse()), org);
				} else {
					int vp_max = vt.getAt() - abraham.getAt() - SepTime.getSepTime(abraham.getKlasse(), vt.getKlasse());
					int vp_right = dtime - vp_max;
					vt.assignTime(vt.getAt() - vp_max);
					System.out.println("vt wordt " + vp_max + " naar links verplaatst. " + dtime + " nog naar rechts te verplaatsen.");
					moveRight(vt.getAt() + SepTime.getSepTime(vt.getKlasse(), org.getKlasse()), vp_right, org);
				}
			}else{
				System.out.println("MoveLeft eerste else");
			}

		}else{
			//Check twee of meer vliegtuigen naar links.
		}
		return movedtimeleft;
	}
	*/

	/*private int moveRight(long wt, int dtime, Vliegtuig org){
		checkAfter(wt,org);
		Vliegtuig vtg = abraham;
		if(vtg.getAt()+300-wt  >= dtime){
			if(!checkAfter(wt+SepTime.getSepTime(org.getKlasse(),vtg.getKlasse()),vtg)){
				vtg.assignTime(vtg.getAt()+dtime);								//abraham.getAt()-vtg.getAt()
				org.assignTime((int)wt);
				System.out.println("MoveRight(): " + org + " is gepland om " + wt + "; " + vtg + " is gepland om " + vtg.getAt());
			}else{
				int vp_max = abraham.getAt()-vtg.getAt();
				int vp_left = dtime - vp_max;
				vtg.assignTime(vtg.getAt()+vp_max);
				checkBefore(wt-vp_left,org);
				System.out.println("MoveRight(): Kan ook niet genoeg naar recht schuiven. Nu links proberen.");
				moveLeft(wt-vp_left,vp_left,abraham,org, true);
			}
		}else{
			landingsbaanok = false;
		}

		return 0;
	}
	*/

	/*---------------------
	|    MISCELLANEOUS    |
	---------------------*/

	private void printShit(Vliegtuig vt){
		int zl = vt.getAt()/60;
		int al = vt.getAt() - (zl*60);
		System.out.println("Placement successful for plane no. "+vt.getName()+". Assigned time: "+zl + ":" + al+" Assigned speed: "+vt.getV_current()+" Distance from airport: "+vt.getAfstand());
	}

	void timeLoop(){							//Tijdbijhouding om benodigde snelheid te kunnen berekenen
		ct = (int) (((System.currentTimeMillis()/1000.0) - bt)*(double)MainAlgoritme.cycles_per_second);
	}

	int[] gekkeFunctieVroeg(long wt, Vliegtuig org){
		Vliegtuig vtg = org;
		Vliegtuig wyd = getLeftVliegtuig(wt);
		int n = 0;
		int[] list1 = new int[n];
		int[] list2;

		for(int i = 0; i < wt; i++){
			int sep = vtg.getAt()-wyd.getAt()-SepTime.getSepTime(wyd.getKlasse(),vtg.getKlasse());
			list2 = new int[n + 1];
			for(int j = 0; j < list1.length; j++){
				list2[j] = list1[j];
			}
			if(sep > 0){
				list2[n + 1] = sep;
			}else{
				list2[n + 1] = 0;
			}
			vtg = wyd;
			wyd = getLeftVliegtuig(vtg.getAt());
			list1 = list2;
			if(wyd == null){break;}
		}
		return list1;
	}

	int[] gekkeFunctieLaat(long wt, Vliegtuig org){
		Vliegtuig vtg = org;
		Vliegtuig wyd = getRightVliegtuig(wt);
		int n = 0;
		int[] list1 = new int[n];
		int[] list2;

		for(int i = 3100; i > wt; i--){
			int sep = wyd.getAt()-vtg.getAt()-SepTime.getSepTime(vtg.getKlasse(),wyd.getKlasse());
			list2 = new int[n + 1];
			for(int j = 0; j < list1.length; j++){
				list2[j] = list1[j];
			}
			if(sep > 0){
				list2[n + 1] = sep;
			}else{
				list2[n + 1] = 0;
			}
			vtg = wyd;
			wyd = getRightVliegtuig(vtg.getAt());
			list1 = list2;
			if(wyd == null){break;}
		}
		return list1;
	}



	Vliegtuig getLeftVliegtuig(long wt){ //Geeft het dichstbijzijnde geplande vliegtuig links van wt
		for(int i = (int)wt; i < 0; i --){
			if(vtl[(int)wt-i] != null){
				return vtl[(int)wt-i];
			}
		}
		return null;
	}

	Vliegtuig getRightVliegtuig(long wt){ //Geeft het dichstbijzijnde geplande vliegtuig rechts van wt
		for(int i = 0; i < 3100-wt; i++){
			if(vtl[(int)wt+i] != null){
				return vtl[(int)wt+i];
			}
		}
		return null;
	}
}
