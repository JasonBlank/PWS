package pws.algoritme;

public class Rij {


	/*-----------------------------
	|    VARIABLE DECLARATIONS    |
	-----------------------------*/

	private long bt = System.currentTimeMillis() / 1000;                //Begin time 						R E A L T I M E
	static int ct, wt;                                                    //Current time en Wanted time:		I N D E X T I M E
	private static Vliegtuig[] vtl = new Vliegtuig[2800];                        //Vliegtuig timeline
	private int sep, maxsep = 189;                                        //Seperation
	private Vliegtuig plane_found;                                            //Willekeurige naam want ik had daar zin in. Dit is de
	private final static int costearly = 100;                                        //variabele waar het gevonden al ingeplande vliegtuig
	private final static int costlate = 139;                                                                    //tijdelijk in komt voor berekeningen enzo.
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
	private int KostL1waardes[] = new int[3];
	private int KostL2waardes[] = new int[3];
	
	
	/*---------------
	|    GETTERS    |
	---------------*/
	static int getCostearly(){
		return costearly;
	}
	
	static int getCostlate(){
		return costlate;
	}

	int getCt() {
		return ct;
	}

	static Vliegtuig[] getVtl() {
		return vtl;
	}


	/*------------------------
	|    CHECKING METHODS    |
	------------------------*/
	private int[] CosttotalL1(int dtime, long wt, Vliegtuig org) {
		int x = 0;
		int xn = 1;
		int y = 0;
		int yn = 1;
		int tijddan = 0;
		int totaltimeleft = 0;
		int totaltimeright = 0;
		while (dtime > 0 & x < gekkeFunctieVroeg(wt,org).length) {
			if ((costearly * xn) < (yn * costlate)) {
				int[] var = gekkeFunctieVroeg(wt, org);
				if (var != null) {
					tijddan = var[x];
					dtime -= tijddan;
					xn++;
					x++;
					totaltimeleft += tijddan;
					totalcostL11 += tijddan * costearly;
				} else {
					totaltimeleft += dtime;
					break;
				}
			} else {
				int[] var = gekkeFunctieLaat(wt, org);
				if (var != null) {
					tijddan = gekkeFunctieLaat(wt, org)[y];
					dtime -= tijddan;
					yn++;
					y++;
					totaltimeright += tijddan;
					totalcostL11 += tijddan * costlate;
				} else {
					totaltimeright += dtime;
					break;
				}

			}


		}
		KostL1waardes[0] = totalcostL11;
		KostL1waardes[1] = totaltimeleft;
		KostL1waardes[2] = totaltimeright;
		return KostL1waardes;
	}

	int CosttotalL2(int dtime, long wt, Vliegtuig org) {
		int x = 1;
		int y = 1;
		int kostendan = 0;
		while (dtime > 0) {
			if ((costearly * x) < (y * costlate)) {
				kostendan = gekkeFunctieVroeg(wt, org)[x]; // zorgen dat kan kijken in verschillende banen
				dtime -= kostendan;
				x++;
				totalcostL21 += kostendan;
			} else {
				kostendan = gekkeFunctieLaat(wt, org)[y]; // zorgen dat kan kijken in verschillende banen
				dtime -= (kostendan / costlate);
				y++;
				totalcostL21 += kostendan;
			}


		}
		return totalcostL21;
	}


	// hoofdfunctie moet nog een keer want anders kunnen we niet voor twee banenchecken. Dit kunnen we ook later doen, eerst zien of dit lukt

	void checknPlace(int wt, Vliegtuig vt) {                                                //Hoofd controle functie; wt is Wanted Time
		checkAlles();
		//---------------------------------------------------------------------------------------------------
		if (checkBefore(wt, vt)) {                                                        //true is problematisch; false is ruimte
			if (checkAfter(wt, vt)) {
				System.out.println("\nSituatie 1");
				System.out.println("Vliegtuigen links en rechts gevonden van " + vt);
				int neededspaceRight, neededspaceLeft = getLeftVliegtuig(wt).getAt() - wt + SepTime.getSepTime(getLeftVliegtuig(wt).getKlasse(), vt.getKlasse());
				if(getRightVliegtuig(wt) == null){
					neededspaceRight = 0;
				}else {
					neededspaceRight = SepTime.getSepTime(vt.getKlasse(), getRightVliegtuig(wt).getKlasse());
				}

				int neededspaceTotal = -(neededspaceLeft + neededspaceRight);
				int[] mooiedingen = CosttotalL1(neededspaceTotal, wt, vt);
				int x = mooiedingen[1];
				int y = mooiedingen[2];

				plaatsLinks(wt, x);
				System.out.println("Alles naar links verplaatst speciaal voor " + vt);
				plaatsRechts(wt, y);
				System.out.println("Alles naar rechts verplaatst speciaal voor " + vt);

				vtl[wt] = vt;
				vt.assignTime(wt);
				vt.setV_current(vt.getAfstand() / (wt - ct));
				printShit(vt);

				// - (x - y - z) = y - x + z
				
				/* int neededspaceRight = -(plane_found.getAt()-wt-SepTime.getSepTime(vt.getKlasse(),plane_found.getKlasse()));
				checkBefore(wt,vt);
				int neededspaceLeft = -(wt- plane_found.getAt() - SepTime.getSepTime(plane_found.getKlasse(),vt.getKlasse()));
				int neededspaceTotal = -(neededspaceLeft + neededspaceRight);

				if(neededspaceLeft > neededspaceRight * costlate){
					System.out.println("Plaats "+vt+" naar rechts vanwege kosten");
					checkAfter(wt,vt);
					checknPlace(plane_found.getAt() + neededspaceTotal - 1,plane_found);
				}else{
					System.out.println("Plaats "+vt+" naar links vanwege kosten");
					checknPlace(plane_found.getAt() - neededspaceTotal + 1,plane_found);
					checknPlace(wt,vt);
				}*/
			}
			//---------------------------------------------------------------------------------------------------
			else {//Rechts is ruimte, links niet.

				System.out.println("\nSituatie 2");
				sep = SepTime.getSepTime(getLeftVliegtuig(wt).getKlasse(), vt.getKlasse());
				System.out.println(getLeftVliegtuig(wt) + " gevonden in situatie 2");
				Vliegtuig temp = getLeftVliegtuig(wt);
				if (!checkBefore(wt - sep, temp)) {                                                            //hier kijken of er ruimte is is voor plane_found om naar links te gaan
					System.out.println(temp + " kan en gaat naar links zodat " + vt + " geplaatst kan worden om " + wt);
					vtl[temp.getAt()] = null;
					temp.assignTime(wt - sep);
					vtl[temp.getAt()] = temp;
					temp.setV_current(temp.getAfstand() / (temp.getAt() - ct));
					printShit(temp);
					//Extra kosten van deze stap = (temp.getAt()+sep-wt)*kosten van te vroeg;
					totalcostL12 = (temp.getAt() + SepTime.getSepTime(temp.getKlasse(), vt.getKlasse()) - wt) * costearly;
					vtl[wt] = vt;
					vt.assignTime(wt);
					vt.setV_current(vt.getAfstand() / (wt - ct));
					printShit(vt);
				}

				else {
					if (!checkAfter(getLeftVliegtuig(wt).getAt() + sep, vt)) { //Check of vt iets naar rechts kan zodat er sep zit tussen plane_found en sep
						vtl[getLeftVliegtuig(wt).getAt() + sep] = vt;
						vt.assignTime(getLeftVliegtuig(wt).getAt() + sep);
						vt.setV_current(vt.getAfstand() / (wt - ct));
						printShit(vt);

						totalcostL12 = ((wt + SepTime.getSepTime(vt.getKlasse(), getLeftVliegtuig(wt).getKlasse()) - getLeftVliegtuig(wt).getAt()) * costlate);
					} else {														//Er is geen ruimte voor beide om te verschuiven. Op naar situatie True-True (copypasta'd)
						int neededspaceRight, neededspaceLeft = getLeftVliegtuig(wt).getAt() - wt + SepTime.getSepTime(getLeftVliegtuig(wt).getKlasse(), vt.getKlasse());
						if(getRightVliegtuig(wt) == null){
							neededspaceRight = 0;
						}else {
							neededspaceRight = SepTime.getSepTime(vt.getKlasse(), getRightVliegtuig(wt).getKlasse());
						}

						int neededspaceTotal = -(neededspaceLeft + neededspaceRight);
						int[] mooiedingen = CosttotalL1(neededspaceTotal, wt, vt);
						int x = mooiedingen[1];
						int y = mooiedingen[2];

						plaatsLinks(wt, x);
						System.out.println("Alles naar links verplaatst speciaal voor " + vt);
						plaatsRechts(wt, y);
						System.out.println("Alles naar rechts verplaatst speciaal voor " + vt);

						checknPlace(wt, vt);
					}
				}
			}
			//---------------------------------------------------------------------------------------------------
		} else {
			if (checkAfter(wt, vt)) {
				System.out.println("\nSituatie 3");
				//Links is ruimte dus op naar links met Vx.
				//Zo verder kijken
				sep = SepTime.getSepTime(vt.getKlasse(), plane_found.getKlasse());
				if (!checkBefore(plane_found.getAt() - sep, vt)) {					//Kijken of vt naar links kan met sep tussen plane_found en vt
					checkAfter(wt,vt);
					vtl[plane_found.getAt() - sep] = vt;
					vt.assignTime(plane_found.getAt() - sep);
					vt.setV_current(vt.getAfstand() / (vt.getAt() - ct));
					printShit(vt);
					//extra kosten deze stap = ((plane_found.getAt()-sep)-wt)*Kosten van te vroeg;
					totalcostL13 = (wt - vt.getAt()) * costearly;
				} else {																//Vt kan niet naar links en dus gaan we naar True.True (copypasta'd)
					int neededspaceRight, neededspaceLeft = wt - getLeftVliegtuig(wt).getAt() - SepTime.getSepTime(getLeftVliegtuig(wt).getKlasse(), vt.getKlasse());
					if(getRightVliegtuig(wt) == null){
						neededspaceRight = 0;
					}else {
						neededspaceRight = getRightVliegtuig(wt).getAt() - wt - SepTime.getSepTime(vt.getKlasse(), getRightVliegtuig(wt).getKlasse());
					}

					int neededspaceTotal = -(neededspaceLeft + neededspaceRight);
					int[] list = CosttotalL1(neededspaceTotal, wt, vt);
					int x = list[1];
					int y = list[2];

					plaatsLinks(wt, x);
					System.out.println("Alles naar links verplaatst speciaal voor " + vt);
					plaatsRechts(wt, y);
					System.out.println("Alles naar rechts verplaatst speciaal voor " + vt);

					vtl[wt] = vt;
					vt.assignTime(wt);
					vt.setV_current(vt.getAfstand() / (wt - ct));
					printShit(vt);
				}
				//-----------------------------------------------------------------------------------------------
			} else {
				System.out.println("\nSituatie 4");
				//Jee alle ruimte die er is. Nu lekker plaatsen op de optimale tijd.
				//done
				int diff = (int) wt;
				vtl[diff] = vt;
				vt.assignTime(diff);
				vt.setV_current(vt.getAfstand() / (wt - ct));
				printShit(vt);
				//extra kosten = 0
				totalcostL14 = 0;
			}
		}
		//-------------------------------------------------------------------------------------------------------

	}

	//belangrijk
	private boolean checkBefore(long wt, Vliegtuig vt) {        //Is er al een vliegtuig gepland op het gewenste tijdstip?

		for (int i = 1; i <= maxsep; i++) {
			if (wt - i >= 0 && wt < 2800) {
				if (vtl[(int) wt - i] != null) {
					plane_found = vtl[(int) wt - i];
					sep = SepTime.getSepTime(plane_found.getKlasse(), vt.getKlasse());
					for (int j = 0; j <= sep; j++) {
						if (vtl[(int) wt - j] != null) {
							System.out.println("CheckBefore() heeft " + vtl[(int) wt - j] + " voor " + vt + " gevonden");
							return true;
						}
					}
				}
			}
		}
		System.out.println("CheckBefore() heeft geen vliegtuigen voor " + vt + " gevonden");
		return false;
	}

	private int checkLowestCost() {
		totalcostL1 = totalcostL11 + totalcostL12 + totalcostL13 + totalcostL14;
		totalcostL2 = totalcostL21 + totalcostL22 + totalcostL23 + totalcostL24;
		if (totalcostL1 < totalcostL2) { // pakt hij zo alle totalcost dingen. Want als het goed is, is er maar 1 die een waarde van niet 0 heeft.
			landingsbaan = 1;
		}
		if (totalcostL2 < totalcostL1) {
			landingsbaan = 2;
		}
		if (totalcostL1 < totalcostL2) {
			landingsbaan = 1;         //hier moeten we nog even kijken wat we hier mee doen
		}
		return landingsbaan;

	}

	private boolean checkAfter(long wt, Vliegtuig vt) {                            //Nog verbeteren
		for (int i = 1; i <= maxsep; i++) {                                        //hier moeten we zorgen dat als plane_found te dicht bij is dat hij dan aan geeft dat hij niet de ruimte heeft om te verplaatsen anders moeten we kijken waar we het anders doen.
			if (wt + i < 2800) {                                                    //Dit hier boven geld ook voor het checken van er na. Maar de kans dat het vliegtuig dan niet meer kan verplaatsen is zeer klein. Maar moeten het voor de zekerheid maar wel checken anders gaan we problemen krijgen
				if (vtl[(int) wt + i] != null) {
					plane_found = vtl[(int) wt + i];
					sep = SepTime.getSepTime(vt.getKlasse(),plane_found.getKlasse());
					for (int j = 0; j <= sep; j++) {
						if (vtl[(int) wt + j] != null) {
							System.out.println("CheckAfter() heeft " + plane_found + " na " + vt + " gevonden");
							return true;
						}
					}
				}
			}else{
				System.out.println("Zit tegen rechtergrens aan");
				return true;
			}
		}
		System.out.println("CheckAfter() heeft geen vliegtuigen na " + vt + " gevonden");
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
					System.out.println("plane_found wordt " + dtime + " naar links verplaatst");
					checknPlace(vt.getAt() + SepTime.getSepTime(vt.getKlasse(), org.getKlasse()), org);
				} else {
					int vp_max = vt.getAt() - plane_found.getAt() - SepTime.getSepTime(plane_found.getKlasse(), vt.getKlasse());
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
		Vliegtuig vtg = plane_found;
		if(vtg.getAt()+300-wt  >= dtime){
			if(!checkAfter(wt+SepTime.getSepTime(org.getKlasse(),vtg.getKlasse()),vtg)){
				vtg.assignTime(vtg.getAt()+dtime);								//plane_found.getAt()-vtg.getAt()
				org.assignTime((int)wt);
				System.out.println("MoveRight(): " + org + " is gepland om " + wt + "; " + vtg + " is gepland om " + vtg.getAt());
			}else{
				int vp_max = plane_found.getAt()-vtg.getAt();
				int vp_left = dtime - vp_max;
				vtg.assignTime(vtg.getAt()+vp_max);
				checkBefore(wt-vp_left,org);
				System.out.println("MoveRight(): Kan ook niet genoeg naar recht schuiven. Nu links proberen.");
				moveLeft(wt-vp_left,vp_left,plane_found,org, true);
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

	private void printShit(Vliegtuig vt) {
		int zl = vt.getAt() / 60;
		int al = vt.getAt() - (zl * 60);
		System.out.println("Placement successful for plane no. " + vt.getName() + ". Assigned time: " + zl + ":" + al + " Assigned speed: " + vt.getV_current() + " Distance from airport: " + vt.getAfstand());
	}

	void timeLoop() {                            //Tijdbijhouding om benodigde snelheid te kunnen berekenen
		ct = (int) (((System.currentTimeMillis() / 1000.0) - bt) * (double) MainAlgoritme.cycles_per_second);
	}

	private int[] gekkeFunctieVroeg(long wt, Vliegtuig org) {
		Vliegtuig vtg = org;
		Vliegtuig wyd = getLeftVliegtuig(wt);
		int n = 0;
		int[] list1 = new int[n];
		int[] list2;
		if (wyd == null) return list1;
		for (int i = 0; i < wt; i++) {
			int sep = vtg.getAt() - wyd.getAt() - SepTime.getSepTime(wyd.getKlasse(), vtg.getKlasse());
			list2 = new int[n + 1];
			for (int j = 0; j < list1.length; j++) {
				list2[j] = list1[j];
			}
			if (sep > 0) {
				list2[n] = sep;
			} else {
				list2[n] = 0;
			}
			vtg = wyd;
			wyd = getLeftVliegtuig(vtg.getAt());
			list1 = list2;
			if (wyd == null) {
				break;
			}
		}
		return list1;
	}

	private int[] gekkeFunctieLaat(long wt, Vliegtuig org) {
		Vliegtuig vtg = org;
		Vliegtuig wyd = getRightVliegtuig(wt);
		int n = 0;
		int[] list1 = new int[n];
		int[] list2;
		if (wyd == null) return list1;
		for (int i = 2800; i > wt; i--) {
			int sep = wyd.getAt() - vtg.getAt() - SepTime.getSepTime(vtg.getKlasse(), wyd.getKlasse());
			list2 = new int[n + 1];
			for (int j = 0; j < list1.length; j++) {
				list2[j] = list1[j];
			}
			if (sep > 0) {
				list2[n] = sep;
			} else {
				list2[n] = 0;
			}
			vtg = wyd;
			wyd = getRightVliegtuig(vtg.getAt());
			list1 = list2;
			if (wyd == null) {
				break;
			}
		}
		return list1;
	}


	private Vliegtuig getLeftVliegtuig(long wt) { //Geeft het dichstbijzijnde geplande vliegtuig links van wt
		for (int i = (int) wt; i > 0; i--) {
			if (vtl[i - 1] != null) {
				//System.out.println("Gotten left: " + vtl[i - 1]);
				return vtl[i - 1];
			}
		}
		return null;
	}

	private Vliegtuig getRightVliegtuig(long wt) { //Geeft het dichstbijzijnde geplande vliegtuig rechts van wt
		for (int i = 1; i < (2800 - wt); i++) {
			if (vtl[(int) wt + i] != null) {
				//System.out.println("Gotten right: " + vtl[(int)wt+i]);
				return vtl[(int) wt + i];
			}
		}
		return null;
	}

	//------------------------------------------------------------------------------------------------
	private void plaatsLinks(long wt, int dtime) {
		int movedTime = 0, n = 0;
		boolean broken = false;
		Vliegtuig vtR = getLeftVliegtuig(wt), vtL;
		Vliegtuig[] lijst1 = new Vliegtuig[1], lijst2;
		int[] dtime_list1 = new int[1], dtime_list2;

		if(vtR != null) {
			lijst1[0] = vtR;
		}
		for (int i = 1; true; i++) {
			if(vtR != null) vtL = getLeftVliegtuig(vtR.getAt());
			else break;
			if (!(wt - i >= 0)) {
				wt = wt + (dtime-movedTime);
				break;
			}
			else {
				if (vtL != null) {
					if (movedTime + vtR.getAt() - vtL.getAt() - SepTime.getSepTime(vtL.getKlasse(), vtR.getKlasse()) >= dtime){
						broken = true;
						break;
					}
					movedTime += vtR.getAt() - vtL.getAt() - SepTime.getSepTime(vtL.getKlasse(),vtR.getKlasse());
					n++;
					System.out.println("N: " + n);

					dtime_list2 = new int[n + 1];
					lijst2 = new Vliegtuig[n + 1];

					for (int j = 0; j < n; j++) {
						lijst2[j] = lijst1[j];
						dtime_list2[j] = dtime_list1[j];
					}

					lijst2[n] = vtL;
					dtime_list2[n - 1] = (vtR.getAt() - vtL.getAt() - SepTime.getSepTime(vtL.getKlasse(), vtR.getKlasse()));	//Check index

					System.out.println("plaatsLinks(): " + vtL + " gevonden. dtime t.o.v. " + vtR + ": " + dtime_list2[n-1]);
					lijst1 = lijst2;
					dtime_list1 = dtime_list2;
					vtR = vtL;
				}else{
					broken = true;
					break;
				}
			}
		}
		if(wt - dtime - movedTime >= 0 && broken){
			dtime_list1[n] = dtime - movedTime;
		}else{
			//Geen ruimte meer om naar links te schuiven
			plaatsRechts(wt + (dtime-movedTime), dtime - movedTime);
		}

		if(lijst1[0] != null && dtime_list1[0] == 0){
			dtime_list1[0] = dtime;
			n = 1;
		}

		System.out.println("plaatsLinks(): wat ik tot nu toe heb: ");
		for(int i = 0; i < n; i++){
			System.out.println("|" + i + "\t\t" + lijst1[i] + "\t\t" + dtime_list1[i]); // Check index
		}

			System.out.println("&&&&&&&&&&\nplaatsLinks(): " + n + " vliegtuigen gevonden");
		//Je hebt nu een lijst genaamd lijst1 met alle te verplaatsen vliegtuigen, en een lijst genaamd dtime_list1 met alle tijden tussen die vliegtuigen.
		int dt = 0;
		for (int i = 0; i < lijst1.length; i++) {
			if(n-i > 0) {
				Vliegtuig temp = lijst1[n - i - 1];
				System.out.println("Start of dt opstapeling no " + i);
				for (int j = lijst1.length; j > i; j--) {
					dt += dtime_list1[j - 1];
					System.out.println(dtime_list1[j - 1]);
				}
				System.out.println("Einde van dt opstapeling: " + dt);
				vtl[temp.getAt()] = null;
				vtl[temp.getAt() - dt] = temp;
				System.out.println("plaatsLinks(): " + temp + " " + dt + " seconden naar links verplaatst");
				temp.assignTime(temp.getAt() - dt);
				temp.setV_current(temp.getAfstand() / (temp.getAt() - ct));
				dt = 0;
			} else {
				System.out.println("No planes found left.");


			}
		}
	}

	private void plaatsRechts(long wt, int dtime) {
		int movedTime = 0, n = 0;
		boolean broken = false;
		Vliegtuig vtL = getRightVliegtuig(wt), vtR;
		Vliegtuig[] lijst1 = new Vliegtuig[1], lijst2 = new Vliegtuig[1];
		int[] dtime_list1 = new int[1], dtime_list2;

		if(vtL != null) {
			lijst1[0] = vtL;
		}
		for (int i = 1; true; i++) {
			if(vtL != null) vtR = getRightVliegtuig(vtL.getAt());
			else break;
			if (!(wt + i <= 2800)){
				wt += dtime - movedTime;
				break;
			} else {
				if (vtR != null) {
					if (movedTime + vtR.getAt() - vtL.getAt() - SepTime.getSepTime(vtL.getKlasse(), vtR.getKlasse()) >= dtime){
						broken = true;
						break;
					}
					movedTime += vtR.getAt() - vtL.getAt() - SepTime.getSepTime(vtL.getKlasse(),vtR.getKlasse());
					n++;
					System.out.println("N: " + n);

					dtime_list2 = new int[n + 1];
					lijst2 = new Vliegtuig[n + 1];

					for (int j = 0; j < n; j++) {
						lijst2[j] = lijst1[j];
						dtime_list2[j] = dtime_list1[j];
					}

					lijst2[n] = vtR;
					dtime_list2[n - 1] = (vtR.getAt() - vtL.getAt() - SepTime.getSepTime(vtL.getKlasse(), vtR.getKlasse()));

					System.out.println("plaatsRechts(): " + vtR + " gevonden. dtime t.o.v. " + vtL + ": " + dtime_list2[n-1]);
					lijst1 = lijst2;
					dtime_list1 = dtime_list2;
					vtL = vtR;
				}else{
					broken = true;
					break;
				}
			}
		}
		if(wt + dtime - movedTime >= 0 && broken){
			dtime_list1[n] = dtime - movedTime;
		}else{
			//Geen ruimte meer om naar Rechts te schuiven
			plaatsLinks(wt - dtime + movedTime, dtime - movedTime);
		}

		if(lijst1[0] != null && dtime_list1[0] == 0){
			dtime_list1[0] = dtime;
			n = 1;
		}

		System.out.println("plaatsRechts(): wat ik tot nu toe heb: ");
		for(int i = 0; i < n; i++){
			System.out.println("|" + i + "\t\t" + lijst1[i] + "\t\t" + dtime_list1[i]);
		}

		System.out.println("&&&&&&&&&&\nplaatsRechts(): " + n + " vliegtuigen gevonden");
		//Je hebt nu een lijst genaamd lijst1 met alle te verplaatsen vliegtuigen, en een lijst genaamd dtime_list1 met alle tijden tussen die vliegtuigen.
		int dt = 0;
		for (int i = 0; i < lijst1.length; i++) {
			if(n-i > 0) {
				Vliegtuig temp = lijst1[n - i - 1];
				for (int j = lijst1.length; j > i; j--) {
					dt += dtime_list1[j - 1];
					System.out.println(dtime_list1[j - 1]);
				}
				System.out.println("dt opstapeling no " + i + ": " + dt);
				vtl[temp.getAt()] = null;
				vtl[temp.getAt() + dt] = temp;
				System.out.println("plaatsRechts(): " + temp + " " + dt + " seconden naar rechts verplaatst");
				temp.assignTime(temp.getAt() + dt);
				temp.setV_current(temp.getAfstand() / (temp.getAt() - ct));
				dt = 0;
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------------
	private void checkAlles() {
		boolean foundsth = false;
		banaan:
		for (int i = 0; i < vtl.length; i++) {
			if (vtl[i] != null) {
				Vliegtuig vt1 = vtl[i];
				Vliegtuig vt2 = getRightVliegtuig(i);
				if (vt2 == null) {
					break banaan;
				}
				int sep = SepTime.getSepTime(vt1.getKlasse(), vt2.getKlasse());
				if (vt2.getAt() - vt1.getAt() < sep) {
					System.out.println("!!!!!!!!!!   " + vt1 + " en " + vt2 + " zijn te dicht op elkaar gepland. Seconden te kort: " + (vt2.getAt() - vt1.getAt() - sep));
					foundsth = true;
				}
				i = vt2.getAt();
			}
		}
		if (!foundsth) System.out.println("checkAll(): Alles oké");
	}
}