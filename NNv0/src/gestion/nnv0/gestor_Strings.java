/*****************************************************************************
 * Source code information
 * -----------------------
 * Original author    Jesus Juan Aguilar
 * Package            gestion.nnv0
 * Created            14-2-2014
 * Filename           #gestor_Directorio.java
 *
 *****************************************************************************/

// Package
///////////////
package gestion.nnv0;

//Imports
///////////////
import graphics.nnv0.Graph;
import java.util.Vector;
import android.annotation.SuppressLint;
import android.text.Editable;
import android.util.Log;

public class gestor_Strings {
	public static Graph grafo = new Graph();

	@SuppressLint("DefaultLocale")
	public static String checkClase(String directorio) {

		directorio = directorio.trim();
		String mayuscula = directorio.charAt(0) + "";
		mayuscula = mayuscula.toUpperCase();
		directorio = directorio.replaceFirst(directorio.charAt(0) + "",
				mayuscula);
		directorio = directorio.replace(' ', '_');

		return directorio;
	}

	public static String removeClass(String nombre, String clases) {

		String res = clases.replace(" " + nombre + ",", "");

		return res;
	}

	public static boolean checked(String clases, String nametofind) {

		Boolean x = clases.contains(nametofind);

		return x;
	}

	public static String deleteFirstPart(int x, String y) {

		return y.substring(x);
	}

	public static String setEtiquetas(String string, Vector<String> v_texto,
			Vector<String> v_clase, int x) {

		for (int i = 0; i < v_clase.size(); i++) {

			String etiquetas = v_clase.elementAt(i);
			etiquetas = etiquetas.substring(0, etiquetas.length() - x);

			string = string.replaceFirst(
					v_texto.elementAt(i),
					"<instanceOf = \"" + etiquetas + "\">"
							+ v_texto.elementAt(i) + "</instanceOf>");
		}

		return string;
	}

	public static String removeEtiquetas(String tex_Net) {

		Vector<Integer> inicio = new Vector<Integer>();
		Vector<Integer> fin = new Vector<Integer>();

		for (int i = 0; i < tex_Net.length(); i++) {

			if (tex_Net.charAt(i) == '<') {

				inicio.addElement(i);
				i++;

				while (!(tex_Net.charAt(i) == '>')) {
					i++;
				}
				fin.addElement(i);
			}
		}
		int i = 0;
		int z = 0;
		for (int j = 0; j < inicio.size(); j++) {

			String toremove = tex_Net.substring(inicio.elementAt(j) - i,
					fin.elementAt(j) + 1 - z);
			int sum = fin.elementAt(j) - inicio.elementAt(j);
			i += sum + 1;
			z += sum + 1;
			tex_Net = tex_Net.replaceFirst(toremove, "");

		}
		tex_Net.trim();
		return tex_Net;
	}

	public static Vector<String> loadVectorText(String x) {
		Vector<String> V_texto = new Vector<String>();
		String texto = "";
		Boolean first = true;

		for (int i = 0; i < x.length(); i++) {

			if ((x.charAt(i) == '>') && first) {
				if (i < x.length() - 1)
					i++;

				while (!(x.charAt(i) == '<')) {
					texto = texto + x.charAt(i);
					i++;
				}
				texto.trim();
				V_texto.addElement(texto);
				texto = "";
				first = false;

			} else if (x.charAt(i) == '>') {
				first = true;

			}
		}
		return V_texto;
	}

	public static Vector<String> loadVectorEt(String x) {

		Vector<String> V_et = new Vector<String>();
		String texto = "";

		for (int i = 0; i < x.length(); i++) {

			if ((x.charAt(i) == '"')) {
				i++;
				while (!(x.charAt(i) == '"')) {
					texto = texto + x.charAt(i);
					i++;
				}
				texto.trim();
				texto = texto + ",";
				V_et.addElement(texto);
				texto = "";
			}
		}
		return V_et;
	}

	public static int getIntSX(String palabra, String frase, int j3) {

		int x = -1;
		int j = 0;
		int j2 = 0;
		int cont = 0;
		if (frase.contains(palabra)) {
			for (int i = 0; i < frase.length(); i++) {

				if ((frase.charAt(i) == palabra.charAt(0))
						&& (frase.charAt(i + 1) == palabra.charAt(1))) {
					x = i;
					for (j = 0; j < palabra.length(); j++) {
						if ((frase.charAt(i) != palabra.charAt(j))) {
							x = 0;
							break;

						}
						i++;
					}
					if (j == palabra.length()) {
						if (x > 0)
							if (Character.isLetter(frase.charAt(x - 1))) {
								x = -1;
							}
						cont++;
					}
					if (cont == j3)
						break;
				}
			}
		} else {
			return -1;
		}
		return x;
	}

	public static int palabrasrepetidas(String palabra, String frase2) {
		String frase = frase2 + " !";
		String[] palabras = frase.split(palabra);
		int cantidad = palabras.length;
		cantidad--;

		return cantidad;
	}

	public static int getIntFY(String palabra, String frase, int x, int j2) {

		int y = 0;
		int j;
		int cont = 0;
		if (x != -1)
			if (frase.contains(palabra)) {
				for (int i = 0; i < frase.length(); i++) {

					if ((frase.charAt(i) == palabra.charAt(0))
							&& (frase.charAt(i + 1) == palabra.charAt(1))) {
						y = 0;
						for (j = 0; j < palabra.length(); j++) {
							if ((frase.charAt(i) == palabra.charAt(j))) {
								y = i;

							} else
								break;
							i++;
						}
						if (j == palabra.length()) {
							cont++;
							if (y < frase.length() - 1)
								if (Character.isLetter(frase.charAt(y + 1))) {
									y = 0;
								}

						}
						if (cont == j2)
							break;
					}
				}
				y++;
			}

		return y;
	}

	public static boolean exist(Vector<String> v_texto, String substring) {
		// TODO Auto-generated method stub
		boolean x = false;
		for (int i = 0; i < v_texto.size(); i++) {

			if (v_texto.elementAt(i).equals(substring))
				x = true;

		}

		return x;
	}

	public static int existGetInt(Vector<String> v_texto, String substring) {
		// TODO Auto-generated method stub
		int x = 0;
		for (int i = 0; i < v_texto.size(); i++) {

			if (v_texto.elementAt(i).equals(substring))
				x = i;

		}

		return x;
	}

	public static String getClasesofStringinText(int puntero, String texto,
			Vector<String> v_texto, Vector<String> v_et) {

		texto = texto + " ";
		int i = puntero;
		int j = puntero;
		String res = "";
		String word = "";
		if (!(texto.charAt(puntero) == ' ')
				&& (Character.isLetter(texto.charAt(puntero)))) {
			while (!(texto.charAt(i) == ' ')
					&& (Character.isLetter(texto.charAt(i)))) {
				if (i != texto.length() - 1)
					i++;
				else
					break;
			}
			while ((texto.charAt(j) != ' ')
					&& (Character.isLetter(texto.charAt(j))) && (j > 0)) {
				j--;
			}
			if (j != 0)
				j++;
			word = texto.substring(j, i);
			word.trim();
			for (i = 0; i < v_texto.size(); i++) {
				if (v_texto.elementAt(i).equals(word)) {
					res = v_et.elementAt(i);

				}
			}
			try {

				if (res.equals(""))
					for (i = 0; i < v_texto.size(); i++) {
						String[] palabras = v_texto.elementAt(i).split(" ");
						if (palabras.length > 1)
							for (int j2 = 0; j2 < palabras.length; j2++) {

								if (palabras[j2].equals(word)) {
									res = v_et.elementAt(i);
								}
							}
					}
			} catch (Exception e) {
				Log.e("Excep", e.toString());
			}

		} else {
			res = "";
		}

		return res;
	}

	public static boolean haveAllEt(String etiquetas, String texto) {

		String[] palabras = etiquetas.split(" ");
		boolean okey = true;

		String palabra = "";

		for (int i = 1; i < palabras.length; i++) {

			palabra = palabras[i].substring(0, palabras[i].length() - 1);

			okey = containsAllEt(palabra, texto);
			if (okey == false)
				i = palabras.length + 1;
		}

		return okey;
	}

	static Boolean containsAllEt(String et, String text) {

		Boolean esta = false;

		// Log.e(et, text);
		Vector<String> vectorprueba = loadVectorEt(text);

		for (int i = 0; i < vectorprueba.size(); i++) {
			String[] palabras = vectorprueba.elementAt(i).split(" ");
			for (int j = 0; j < palabras.length; j++) {

				if (palabras[j].substring(0, palabras[j].length() - 1).equals(
						et))
					esta = true;
			}
		}
		if (esta == false) {

			Vector<String> miVector = new Vector<String>();
			try {
				miVector = grafo.getElementsOf(et);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int z = 0; z < miVector.size(); z++) {

				esta = containsAllEt(miVector.elementAt(z), text);
				if (esta)
					return true;
			}

		}

		return esta;
	}

	public static boolean wordsCompleted(int ini, int fin, String text) {
		// TODO Auto-generated method stub
		boolean ok = false;
		text = " " + text + " ";
		ini += 1;
		fin += 1;
		if ((!Character.isLetter(text.charAt(ini - 1)))
				&& (!Character.isLetter(text.charAt(fin))))
			if ((Character.isLetter(text.charAt(ini)))
					&& (Character.isLetter(text.charAt(fin - 1))))
				ok = true;
		return ok;
	}

}
