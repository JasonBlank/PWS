package pws.engine;

public class Vliegtuig {
	private double afstand_tot_landingsbaan, v_current;					//Afstand in m; Snelheid in m/s
	private final int klasse, v_cruise;									//Gewichtsklasse (1-4); Zuinigste snelheid in m/s
	private String name = "v1";

	public Vliegtuig(double afstand_tot_landingsbaan, int klasse, int v_cruise) {
		this.afstand_tot_landingsbaan = afstand_tot_landingsbaan;
		this.v_cruise = v_cruise;
		this.klasse = klasse;
	}


	double getAfstand() {
		return afstand_tot_landingsbaan;
	}
	void setAfstand(double afstand) {
		afstand_tot_landingsbaan = afstand;
	}
	double getV_current(){return v_current;}
	void setV_current(double v_current){this.v_current = v_current;}

	public void update(double dtime){
		if(afstand_tot_landingsbaan > 0) {
			afstand_tot_landingsbaan -= v_current * dtime;
		}else{
			System.out.println("--------------------\n|      plane " + name +"    |\n| Plane has landed |\n--------------------");
		}
	}
}