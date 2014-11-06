
/*****************************************************************************
 * Source code information
 * -----------------------
 * Original author    Jesus Juan Aguilar
 * Package            gestion.nnv0
 * Created            02-12-2013
 * Filename           #Nota.java
 *
 *****************************************************************************/
// Package
///////////////
package gestion.nnv0;

public class Nota {
	
	private String titulo;
	private String texto;
	
		public Nota(String titulo, String texto){
		
		this.titulo = titulo;
		this.texto = texto;
	}

	public Nota() {
			// TODO Auto-generated constructor stub
		this.titulo = "";
		this.texto = "";
		}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
