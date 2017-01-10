package pws.algoritme;

import pws.engine.Timegeneration;
import pws.algoritme.Rij;

public class Vliegtuig {
	private double afstand_tot_landingsbaan, v_current;					//Afstand in m; Snelheid in m/s
	public final int klasse, v_max = 271;								//Gewichtsklasse (1-4); Zuinigste snelheid in m/s
	private int lt, ft, at;												//Last time, first time en assigned time
	private int name;												//Naam van vliegtuig om te kunnen gebruiken in console output
	private Rij rij;
	public int Beginafstand, v_cruise;							//De afstand waar het vliegtuig op begint
	private boolean tedichtbij = false;

	public String toString(){
		return "Vliegtuig " + Integer.toString(name);
	}

	public int getName(){
		return name;
	}

<<<<<<< HEAD
	public int getBeginafstand(int vn){
		Beginafstand = (Timegeneration.gettt(name )-rij.ct)*Timegeneration.getV_cruise(vn); //hier moeten we nog even naar kijken
=======
	public int getBeginafstand(){
		Beginafstand = (Timegeneration.getttn(name)-Rij.ct)*v_cruise;
>>>>>>> origin/master
		return Beginafstand;
	}

	public int getFirsttime(double afstand_tot_landingsbaan){
		ft = (int) (afstand_tot_landingsbaan/v_max);
		return ft;
	}


<<<<<<< HEAD
	public Vliegtuig(int name,double afstand_tot_landingsbaan, int klasse, Rij rij) {
		this.afstand_tot_landingsbaan = afstand_tot_landingsbaan;
		this.klasse = klasse;
		v_current = Timegeneration.getV_cruise(name); // hier ook
		this.rij = rij;
		this.name = name;
		toString();
		rij.checknPlace((int)(this.getAfstand()/Timegeneration.getV_cruise(name))+rij.getCt(),this); // hier lukt het met niet om de v_cruise op te vragen uit Timegeneration en bij andere wel. 
=======
	public Vliegtuig(String name,double afstand_tot_landingsbaan, int klasse, Rij rij, int v_cruise) {
		this.afstand_tot_landingsbaan = afstand_tot_landingsbaan;
		this.klasse = klasse;
		this.v_cruise = v_cruise;
		v_current = v_cruise;
		this.rij = rij;
		this.name = name;
		System.out.println(toString());
		rij.checknPlace((int)(this.getAfstand()/this.v_cruise)+rij.getCt(),this);
>>>>>>> origin/master
	}

	public void assignTime(int newat){
		at = newat;
	}

	public double getAfstand() {
		return afstand_tot_landingsbaan;
	}
	public void setAfstand(double afstand) {
		if(afstand > 500){
			afstand_tot_landingsbaan = afstand;
		}

	}
	public double getV_current(){return v_current;}
	void setV_current(double v_current){this.v_current = v_current;}
	int getKlasse(){return klasse;}
	int getAt(){return at;}


	void update(double dtime){
		if(afstand_tot_landingsbaan > 500) { //Vanaf 500 meter van de landingsbaan gaat hij 'van de kaart'.
			afstand_tot_landingsbaan -= v_current * dtime;
			ft = (int) afstand_tot_landingsbaan * v_max;

		}
		else{
			//Vliegtuig is te dichtbij de landingsbaan om target time nog te wijzigen


		}
	}
}