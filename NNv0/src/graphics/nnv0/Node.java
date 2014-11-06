/*****************************************************************************
 * Source code information
 * -----------------------
 * Original author    Jesus Juan Aguilar
 * Package            gestion.nnv0
 * Created            7-3-2014
 * Filename           #Node.java
 *
 *****************************************************************************/

// Package
///////////////
package graphics.nnv0;

public class Node {
	
	private String nombre;
	private String padre;
	public int id_pa;
	public int id_yo;
	
	public Node(String x , String y, int z, int w){
		
		setPadre(x); 
		setNombre(y);
		id_yo = z;
		id_pa = w;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPadre() {
		return padre;
	}

	public void setPadre(String padre) {
		this.padre = padre;
	}
	
	
	
}
