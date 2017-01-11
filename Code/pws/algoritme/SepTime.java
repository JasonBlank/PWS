package pws.algoritme;

public class SepTime {


	/*-----------------------
	|    SEPERATION TIME    |
	-----------------------*/

	final static private int[][] septime = new int[][]{{60,145,167,189},{60,98,122,145},{60,60,60,122},{60,60,60,60}};

	public static int getSepTime(int x, int y){
		return septime[x-1][y-1];
	}
}