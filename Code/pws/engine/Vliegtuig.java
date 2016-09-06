package pws.engine;

public class Vliegtuig {
	private double aankomsttijd_ms;
	private final int passagiers_aant;
	
	public Vliegtuig(double aankomsttijd_ms, int passagiers_aant){
		this.aankomsttijd_ms = aankomsttijd_ms;
		this.passagiers_aant = passagiers_aant;
	}
	
	double getAankTijd(){
		return aankomsttijd_ms;
	}
	
	void setAankTijd(double aanktijd){
		aankomsttijd_ms = aanktijd;
	}
	
	int getPassAant(){
		return passagiers_aant;
	}
}
