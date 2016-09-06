package pws.engine;

import pws.engine.Vliegtuig;

public class Rij {
	private int putloc, getloc;
	Vliegtuig vtrij[] = new Vliegtuig[0];
	
	public Rij(){
		putloc = 0;
		getloc = 0;
	}
	
	public void put(Vliegtuig vt){
		Vliegtuig vtrij2[] = new Vliegtuig[vtrij.length+1];
		vtrij = vtrij2;
		vtrij2[putloc] = vt;
		putloc++;
		System.out.println(vt + " added");
	}
	
	public Vliegtuig get(){
		if(putloc == getloc){
			System.out.println("Rij leeg.");
			return new Vliegtuig(300.0,300);
		}
		else{
		Vliegtuig vgt = vtrij[getloc];
		getloc++;
		System.out.println(vtrij[getloc-1] + " gotten");
		return vgt;
		}
	}
}
