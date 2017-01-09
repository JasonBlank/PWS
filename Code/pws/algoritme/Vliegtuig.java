package pws.algoritme;

import pws.engine.Timegeneration;
import pws.algoritme.Rij;

public class Vliegtuig {
	private double afstand_tot_landingsbaan, v_current;					//Afstand in m; Snelheid in m/s
	public final int klasse, v_cruise = 255, v_max = 271;				//Gewichtsklasse (1-4); Zuinigste snelheid in m/s
	private int lt, ft, at;												//Last time, first time en assigned time
	private String name = "v1";											//Naam van vliegtuig om te kunnen gebruiken in console output
	private Rij rij;
	public int Beginafstand;											//De afstand waar het vliegtuig op begint
		
	public int getBeginafstand(){
		Beginafstand = (Timegeneration.getttn[/*nummer vliegtuig*/]-ct)*v_cruise;
		return Beginafstand;
	}
	
	public int getFristtime(double afstand_tot_landingsbaan){
		ft = (int) (afstand_tot_landingsbaan/v_max);
		return ft;
		
	}


	public Vliegtuig(double afstand_tot_landingsbaan, int klasse, Rij rij) {
		this.afstand_tot_landingsbaan = afstand_tot_landingsbaan;
		this.klasse = klasse;
		v_current = v_cruise;
		rij.checknPlace((int)(this.getAfstand()/this.v_cruise)+rij.getCt(),this);
	}

	public void assignTime(int newat){
		at = newat;
	}

	public double getAfstand() {
		return afstand_tot_landingsbaan;
	}
	public void setAfstand(double afstand) {
		afstand_tot_landingsbaan = afstand;
	}
	public double getV_current(){return v_current;}
	public void setV_current(double v_current){this.v_current = v_current;}
	public int getKlasse(){return klasse;}
	public int getAt(){return at;}


	public void update(double dtime){
		if(afstand_tot_landingsbaan > 0) {

			afstand_tot_landingsbaan -= v_current * dtime;
			ft = (int) afstand_tot_landingsbaan * v_max;

		}
		else{
			//Vliegtuig is te dichtbij de landingsbaan om nog te wijzigen


		}
	}
}