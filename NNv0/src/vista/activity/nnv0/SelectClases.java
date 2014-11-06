package vista.activity.nnv0;

import gestion.nnv0.gestor_Strings;
import gestion.nnv0.gestor_Ont;
import java.util.Vector;
import com.example.nnv0.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectClases extends Activity {/* Declaracion de variables */
	LinearLayout arbol;
	Boolean add = false;
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
		setContentView(R.layout.select_clases);

		/*
		 * Get parametros enviados al iniciar la actividad y ponerlos en el
		 * directorio de clases.
		 */

		// Dibujamos el arbol de clases inicial.
		Intent iin = getIntent();
		Bundle b = iin.getExtras();

		if (b != null) {
			String etiquetas = (String) b.get("etiquetas");
			TextView dir = (TextView) findViewById(R.id.textViewClases);
			String clases = dir.getText().toString();
			Log.e("!!", etiquetas);
			clases = clases + " " + etiquetas;
			dir.setText(clases);

		}

		draw("", false);

		findViewById(R.id.buttonback).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						goBack();
					}
				});

		findViewById(R.id.buttoncancel).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent returnIntent = new Intent();
						setResult(RESULT_CANCELED, returnIntent);
						finish();
					}
				});

		findViewById(R.id.buttonhecho).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						TextView dir = (TextView) findViewById(R.id.textViewClases);
						String clases = dir.getText().toString();
						if (clases.equals("Clases:"))
							clases = "";

						Intent returnIntent = new Intent();
						returnIntent.putExtra("result", clases);
						setResult(RESULT_OK, returnIntent);
						finish();

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

	private void error() {
		// TODO Auto-generated method stub
		Toast.makeText(this, this.getString(R.string.MO_clases_error),
				Toast.LENGTH_SHORT).show();
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
			llp.setMargins(25, 10, 0, 0); // llp.setMargins(left, top, right,
											// bottom);

			ImageButton but = new ImageButton(this);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.ico_mas);
			RelativeLayout.LayoutParams lbp = new RelativeLayout.LayoutParams(
					43, 43);
			lbp.setMargins(0, 5, 0, 0);
			but.setLayoutParams(lbp);

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

			CheckBox cb = new CheckBox(getApplicationContext());
			TextView dir = (TextView) findViewById(R.id.textViewClases);
			String clases = dir.getText().toString();
			if (gestor_Strings.checked(clases, miV.get(i))) {
				cb.setChecked(true);
			}
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					50, 50);
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp.setMargins(0, 0, 5, 0);
			cb.setLayoutParams(lp);
			cb.setId(i + 3);
			cb.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0,
						boolean isChecked) {
					if (isChecked) {

						int x = arg0.getId();
						String nombre = miV.get(x - 3);
						addClase(nombre);
						Log.e("1", "Checkjat");
					} else {
						int x = arg0.getId();
						String nombre = miV.get(x - 3);
						removeClase(nombre);
						Log.e("1", "NO Checkjat");
					}

				}

			});

			uno.addView(but);
			uno.addView(valueTV);
			uno.addView(cb);
			((LinearLayout) arbol).addView(uno);
			uno = new RelativeLayout(this);
		}
	}

	public void goBack() {

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
		}
	}

	protected void removeClase(String nombre) {

		TextView dir = (TextView) findViewById(R.id.textViewClases);
		String clases = dir.getText().toString();
		String res = gestor_Strings.removeClass(nombre, clases);
		dir.setText(res);

	}

	protected void addClase(String nombre) {

		TextView dir = (TextView) findViewById(R.id.textViewClases);
		String clases = dir.getText().toString();
		if (clases.equals("Clases:")) {
			clases = clases + " ";
		}
		clases = clases + nombre + ", ";
		dir.setText(clases);
	}

}
