package sss.game.solutions;

import java.util.Vector;

import android.util.Log;

public class Solutions {
	
	Vector<Integer> soluciones = new Vector<Integer>();
	
	public Solutions() {
		soluciones.add(4);
		soluciones.add(8);
		soluciones.add(55);
		soluciones.add(4);
		soluciones.add(180);
		soluciones.add(100);
		soluciones.add(163);
		soluciones.add(501);
		soluciones.add(-8);
		soluciones.add(105);
		soluciones.add(0);
		
	}
	
	public int get(int x){
		Log.e("soluciones.get",soluciones.get(x)+" -- "+ x);
		return soluciones.get(x);
	}
}
