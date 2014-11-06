/*****************************************************************************
 * Source code information
 * -----------------------
 * Original author    Jesus Juan Aguilar
 * Package            vista.activity.nnv0
 * Created            8-02-2014
 * Filename           #ModOntologia.java
 *
 *****************************************************************************/
// Package
///////////////
package vista.activity.nnv0;

//Imports
///////////////

import gestion.nnv0.gestor_Strings;
import gestion.nnv0.gestor_Ont;
import java.util.Vector;
import com.example.nnv0.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ModOntologia extends Activity {
	/* Declaracion de variables */
	LinearLayout arbol;
	Boolean add = false;
	int i = 0;
	Vector<String> miV = new Vector<String>();

	/**
	 * Metodo que genera la vista de un Activity.
	 * 
	 * @param savedInstanceState
	 *            : instancia inicial.
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mod_ontology);

		// Dibujamos el arbol de clases inicial.
		draw("", false);

		findViewById(R.id.buttonadd).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						addAdd();
					}
				});
		findViewById(R.id.buttonback).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						boolean ok = goBack();
						if(!ok)
							if(i == 2){
								i = 0;
								finish();
							}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onPause() {
		super.onPause();

	}

	/**
	 * Metodo para añadir un EditText Para que el usuario pueda añadir nuevas
	 * clases.
	 * */
	public void addAdd() {
		if (add)
			Toast.makeText(ModOntologia.this,
					ModOntologia.this.getString(R.string.MNJ_T_add_ont_clas),
					Toast.LENGTH_SHORT).show();

		if (!add) {
			add = true;
			final EditText nuevo = new EditText(this);
			final Button b = new Button(this);

			arbol = (LinearLayout) findViewById(R.id.Layoutont);
			arbol.addView(nuevo);

			RelativeLayout menub = (RelativeLayout) findViewById(R.id.menub);
			b.setText("Save");
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					135, 72);
			params.addRule(RelativeLayout.RIGHT_OF, R.id.buttonadd);
			b.setLayoutParams(params);
			b.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String clase = nuevo.getText().toString();
					if (clase.equals("")) {
						Toast.makeText(
								ModOntologia.this,
								ModOntologia.this
										.getString(R.string.MNJ_T_add_ont_clas),
								Toast.LENGTH_SHORT).show();
					} else {

						String g_clase = gestor_Strings.checkClase(clase);
						try {
							gestor_Ont.addClass(g_clase, getDad());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						draw(getDad(), true);
						add = false;
						arbol.removeView(nuevo);
						ViewGroup parentView = (ViewGroup) v.getParent();
						parentView.removeView(v);
					}

				}
			});
			menub.addView(b);
		}
	}

	/**
	 * Metodo Para dibujar el arbol de clases.
	 * 
	 * @param nombre2
	 *            : Clase de la que se parte.
	 * */
	public void draw(final String nombre2, boolean act) {
		arbol = (LinearLayout) findViewById(R.id.Layoutont);
		arbol.removeAllViews();
		if (!nombre2.equals("") && !act) {
			TextView dir = (TextView) findViewById(R.id.textViewdirectori);
			String direccion_v = (String) dir.getText();
			direccion_v = direccion_v + nombre2 + "/";
			dir.setText(direccion_v);
		}

		try {
			miV = gestor_Ont.getDirectorio(nombre2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RelativeLayout uno = new RelativeLayout(this);
		for (int i = 0; i < miV.size(); i++) {

			TextView valueTV = new TextView(this);
			valueTV.setText(miV.get(i));
			valueTV.setId(i + 1);
			RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.setMargins(25, 0, 0, 0); // llp.setMargins(left, top, right,
											// bottom);

			ImageButton but = new ImageButton(this);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.ico_mas);
			but.setLayoutParams(new LayoutParams(43, 43));
			but.setId(i + 2);
			but.setImageBitmap(bmp);
			but.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int x = v.getId();
					String nombre = miV.get(x - 2);
					draw(nombre, false);

				}
			});
			llp.addRule(RelativeLayout.RIGHT_OF, but.getId());
			valueTV.setLayoutParams(llp);

			ImageButton but2 = new ImageButton(this);
			Bitmap bmp2 = BitmapFactory.decodeResource(getResources(),
					R.drawable.delete);
			but2.setId(i + 3);
			but2.setImageBitmap(bmp2);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					43, 43);
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			but2.setLayoutParams(lp);
			but2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(final View v) {

					AlertDialog.Builder builder = new AlertDialog.Builder(
							ModOntologia.this);
					builder.setMessage(R.string.MNJ_Alert_elim)
							.setTitle(R.string.MNJ_Alert_al)
							.setCancelable(false)
							.setNegativeButton(R.string.MNJ_Alert_can,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									})
							.setPositiveButton(R.string.MNJ_Alert_cont,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											int x = v.getId();
											String nombre = miV.get(x - 3);
											deleteClassOfOnt(nombre);
										}
									});
					AlertDialog alert = builder.create();
					alert.show();
				}
			});

			uno.addView(but);
			uno.addView(valueTV);
			uno.addView(but2);
			((LinearLayout) arbol).addView(uno);
			uno = new RelativeLayout(this);
		}
	}

	public boolean goBack() {

		TextView dir = (TextView) findViewById(R.id.textViewdirectori);
		String direccion_v = (String) dir.getText();
		String reverseS = "";
		int tam = 0;
		int tam2 = 0;
		add = false;

		if (direccion_v.length() > 1) {

			for (int i = direccion_v.length() - 1; i >= 0; i--) {

				reverseS = reverseS + direccion_v.charAt(i);
			}
			for (int j = 1; j < reverseS.length(); j++) {

				if (reverseS.charAt(j) == '/') {
					if (tam == 0)
						tam = j;
					else {
						tam2 = j;
						j = reverseS.length();
					}
				}
			}
			String subS = "";
			String subS2 = "";
			String nueva_dir = "";
			String nueva_dir2 = "";
			if (tam2 == 0) {
				nueva_dir = "";
				nueva_dir2 = "/";
			} else {
				subS = reverseS.substring(tam + 1, tam2);
				subS2 = reverseS.substring(tam2 + 1);
				for (int i = subS.length() - 1; i >= 0; i--) {
					nueva_dir = nueva_dir + subS.charAt(i);

				}
				for (int i = subS2.length() - 1; i >= 0; i--) {
					nueva_dir2 = nueva_dir2 + subS2.charAt(i);
				}
				nueva_dir2 = nueva_dir2 + "/";
			}

			dir.setText(nueva_dir2);
			draw(nueva_dir, false);
		} else {

			Toast.makeText(this, this.getString(R.string.MNJ_T_Mod_Ont_Raiz),
					Toast.LENGTH_SHORT).show();
					i++;
					return false;
		}
		return true;
	}

	public void deleteClassOfOnt(String nombre) {
		Toast.makeText(ModOntologia.this,
				this.getString(R.string.MNJ_T_elim_ont_clas),
				Toast.LENGTH_SHORT).show();
		try {
			String padre = gestor_Ont.deleteClass(nombre);
			draw(padre, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getDad() {

		TextView dir = (TextView) findViewById(R.id.textViewdirectori);
		String direccion_v = (String) dir.getText();
		int tam = 0;
		int tam2 = 0;
		String reverseS = "";
		String nueva_dir = "";

		if (direccion_v.length() > 1) {
			for (int i = direccion_v.length() - 1; i >= 0; i--) {

				reverseS = reverseS + direccion_v.charAt(i);
			}
			reverseS = "/" + reverseS;
			for (int j = 0; j < reverseS.length(); j++) {

				if (reverseS.charAt(j) == '/') {
					if (tam == 0)
						tam = j;
					else {
						tam2 = j;
						j = reverseS.length();
					}
				}
			}

			String subS = "";
			subS = reverseS.substring(tam + 1, tam2);

			for (int i = subS.length() - 1; i >= 0; i--) {
				nueva_dir = nueva_dir + subS.charAt(i);

			}
		} else {

			nueva_dir = "";

		}
		return nueva_dir;
	}
}
