package pws.engine;

import pws.engine.Vliegtuig;

public class Rij {
	private int putloc, getloc;
	private Vliegtuig vtrij[] = new Vliegtuig[0];
	public boolean empty = true;
	
	public Rij(){
		putloc = 0;
		getloc = 0;
	}
	
	public void put(Vliegtuig vt){
		Vliegtuig vtrij2[] = new Vliegtuig[vtrij.length+1];
		vtrij = vtrij2;
		vtrij2[putloc] = vt;
		putloc++;
		empty = false;
		if(putloc == getloc)empty = true;
		System.out.println(vt + " added");
	}
	
	public Vliegtuig get(){
		Vliegtuig vgt = vtrij[getloc];
		getloc++;
		System.out.println(vtrij[getloc-1] + " gotten");
		return vgt;
	}
}
