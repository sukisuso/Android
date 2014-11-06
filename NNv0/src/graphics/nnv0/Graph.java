/*****************************************************************************
 * Source code information
 * -----------------------
 * Original author    Jesus Juan Aguilar
 * Package            gestion.nnv0
 * Created            7-3-2014
 * Filename           #Graph.java
 *
 *****************************************************************************/

// Package
///////////////
package graphics.nnv0;

import gestion.nnv0.gestor_Ont;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
//Imports
///////////////
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import android.os.Environment;
import android.util.Log;

public class Graph {
	
	private Vector<Node> vector_nodos = new Vector<Node>();
	int cont = 1;
	
	public Graph() {
		
		vector_nodos.add(new Node("", "thing", 0 , 0));
		load("", 0);

	}

	private void load(String string, int x) {
		Vector<String> nodos = new Vector<String>();

		try {
			nodos = gestor_Ont.getDirectorio(string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < nodos.size(); i++) {

			vector_nodos.add(new Node(string, nodos.elementAt(i),cont ,x));
			cont++;
			load(nodos.elementAt(i),cont - 1 );
		}
	}

	public void toMString() {

		for (int i = 0; i < vector_nodos.size(); i++)
			Log.e("Nodo: " + i, vector_nodos.elementAt(i).getNombre());
	}

	public Vector<String> getElementsOf(String padre) {

		Vector<String> nodos = new Vector<String>();

		for (int i = 0; i < vector_nodos.size(); i++) {

			if (padre.equals(vector_nodos.elementAt(i).getPadre())) {

				nodos.add(vector_nodos.elementAt(i).getNombre());
			}
		}
		return nodos;
	}

	
}
