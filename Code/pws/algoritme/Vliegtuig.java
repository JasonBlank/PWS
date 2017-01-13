package pws.algoritme;

import pws.engine.Timegeneration;
import pws.algoritme.Rij;

public class Vliegtuig {


	/*-----------------------------
	|    VARIABLE DECLARATIONS    |
	------------------------------*/

	private double afstand_tot_landingsbaan, v_current;					//Afstand in m; Snelheid in m/s
	// zie paar regels later								//Gewichtsklasse (1-4); Zuinigste snelheid in m/s 
	private int lt, ft, at;												//Last time, first time en assigned time
	private int name;												//Naam van vliegtuig om te kunnen gebruiken in console output
	private Rij rij;
	private int Beginafstand, v_cruise;							//De afstand waar het vliegtuig op begint
	private boolean tedichtbij = false;
	private int klasse = Timegeneration.getKlasse(name); 	// final is nu weg, anders gaf error //Weet niet of dit werkt. Maar ik heb gezorgd dat bij timegeneration ook de verdeling van klasses wordt gedaan
	public boolean linksmuur = false, rechtsmuur = false;
	public String toString(){
		return "Vliegtuig " + Integer.toString(name);
	}




	/*-------------------
	|    CONSTRUCTOR    |
	-------------------*/

	Vliegtuig(int name,double afstand_tot_landingsbaan, int klasse, Rij rij) {
		this.afstand_tot_landingsbaan = afstand_tot_landingsbaan;
		this.klasse = klasse;
		v_current = Timegeneration.getV_cruise(name); 
		this.rij = rij;
		this.name = name;
		System.out.println(toString() + " reporting for duty");
		rij.checknPlace((int)(this.getAfstand()/Timegeneration.getV_cruise(name))+rij.getCt(),this);  
	}


	/*---------------
	|    SETTERS    |
	---------------*/

	void assignTime(int newat){at = newat;}

	private void setAfstand(double afstand) {
		afstand_tot_landingsbaan = afstand;//System.out.println("Afstand van "+toString()+" tot landingsbaan: "+afstand);
	}

	/*---------------
	|    GETTERS    |
	---------------*/

	double getAfstand() {return afstand_tot_landingsbaan;}

	double getV_current(){return v_current;}

	void setV_current(double v_current){this.v_current = v_current;}

	int getKlasse(){return klasse;}

	int getAt(){return at;}

	int getName(){return name;}

	public int getBeginafstand(int vn){
		Beginafstand = (Timegeneration.getTt(name)-rij.getCt())*Timegeneration.getV_cruise(vn);
		return Beginafstand;
	}

	public int getFirsttime(double afstand_tot_landingsbaan){
		ft = (int) (afstand_tot_landingsbaan/Timegeneration.getV_max(name));
		return ft;
	}

	public boolean getTeDichtbij(){
		return tedichtbij;
	}


	void update(double dtime){ // 500 meter is wel erg weinig afstand om nog dingen nog te gaan veranderen, we kunnen het beter iets van 1 minuut maken of 30 seconden, omdat de snelheid ook verschilt en dus een afstand niet optimaal is
		if(afstand_tot_landingsbaan > (30*v_current)) { //Vanaf 30 seconden van de landingsbaan gaat hij 'van de kaart'.
			afstand_tot_landingsbaan = afstand_tot_landingsbaan - v_current * dtime;
			setAfstand(afstand_tot_landingsbaan);
			ft = (int) afstand_tot_landingsbaan * Timegeneration.getV_max(name);

		}
		else{
			tedichtbij = true;// dit zou genoeg moeten zijn. Allen is het miss nodig om nog een get method te maken, voor als we checken of iets verschoven kan worden maar dat zien we dan wel.
			//Vliegtuig is te dichtbij de landingsbaan om target time nog te wijzigen


		}
	}
}