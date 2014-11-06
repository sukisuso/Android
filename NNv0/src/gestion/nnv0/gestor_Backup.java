package gestion.nnv0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.nnv0.R;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class gestor_Backup {

	static File folder = new File(Environment.getExternalStorageDirectory()
			+ "/nonet");
	static File folder_back = new File(folder + "/backup");
	static File backup = new File(folder_back + "/backup.txt");
	static File last_backup = new File(folder_back + "/lastCopia.txt");
	static File copias = new File(folder_back + "/copias");
	static File notas = new File(folder + "/notas.xml");
	static File owl = new File(folder + "/ontologia.xml");
	String period = "";

	public static void doBackup() {

		if (!copias.exists())
			copias.mkdirs();
		SimpleDateFormat sf = new SimpleDateFormat("dd_MM_yyyy");
		Date date = new Date();
		
		if (!last_backup.exists()) {
			try {
				OutputStreamWriter oswidio = new OutputStreamWriter(
						new FileOutputStream(last_backup));
				oswidio.write(sf.format(date));
				oswidio.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			doCopies();
		} else {
			String last_b = cargarLastBackup();
			String periodo = cargarPeriodo();
			if (compare(last_b, periodo)) {
				doCopies();

				try {
					OutputStreamWriter oswidio = new OutputStreamWriter(
							new FileOutputStream(last_backup));
					oswidio.write(sf.format(date));
					oswidio.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	private static boolean compare(String last_b, String periodo) {
		// TODO Auto-generated method stub
		SimpleDateFormat sf = new SimpleDateFormat("dd_MM_yyyy");
		Date date = new Date();
		Date date2 = null;
		try {
			date2 = sf.parse(last_b);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean ok = false;
		if (!periodo.equals("non")) {
			long dias = diasDiferencia(date2, date);
			if (periodo.equals("dia")) {
				if(dias >= 1)
					ok = true;
			} else if (periodo.equals("sem")) {
				if(dias >= 7)
					ok = true;
			} else if (periodo.equals("year")) {
				if(dias >= 31)
					ok = true;
			}

		}

		return ok;
	}

	private static void doCopies() {
		// TODO Auto-generated method stub

		BufferedReader bf = null;
		String sCadena = "";
		String texto = "";

		try {
			bf = new BufferedReader(new FileReader(notas));

			while ((sCadena = bf.readLine()) != null) {
				texto += sCadena + "\r\n";
			}
			bf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Excepcion al leer", e.toString());
		}
		SimpleDateFormat sf = new SimpleDateFormat("dd_MM_yyyy");
		Date date = new Date();

		texto += "\r\n\r\n\r\n";
		texto += "************************ Backup_" + sf.format(date)
				+ "************************";
		texto += "\r\n\r\n\r\n";
		try {
			bf = new BufferedReader(new FileReader(owl));

			while ((sCadena = bf.readLine()) != null) {
				texto += sCadena + "\r\n";
			}
			bf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Excepcion al leer", e.toString());
		}

		File fichero = new File(copias + "/" + sf.format(date) + ".txt");
		OutputStreamWriter osw;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(fichero));
			osw.write(texto);
			osw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String cargarPeriodo() {
		// TODO Auto-generated method stub
		String peri = "";
		if (folder_back.exists()) {

			BufferedReader bf = null;
			String sCadena = "";
			try {
				bf = new BufferedReader(new FileReader(backup));

				while ((sCadena = bf.readLine()) != null) {
					peri = sCadena;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("Excepcion al leer", e.toString());
			}
		}
		return peri;
	}

	public static String cargarLastBackup() {
		// TODO Auto-generated method stub
		String peri = "";
		if (folder_back.exists()) {
			BufferedReader bf = null;
			String sCadena = "";
			try {
				bf = new BufferedReader(new FileReader(last_backup));
				while ((sCadena = bf.readLine()) != null) {
					peri = sCadena;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("Excepcion al leer", e.toString());
			}
		}
		return peri;
	}

	public static void writePeriodo(String per) {

		OutputStreamWriter oswidio;
		try {
			oswidio = new OutputStreamWriter(new FileOutputStream(backup));
			if (per.equals("dia"))
				oswidio.write("dia");
			else if (per.equals("sem"))
				oswidio.write("sem");
			else if (per.equals("year"))
				oswidio.write("year");
			else
				oswidio.write("non");
			oswidio.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static long diasDiferencia(Date d1, Date d2){
		long MILISEGUNDOS_EN_UN_DIA = 1000 * 60 * 60 * 24;
		long dias = (d2.getTime() - d1.getTime()) / MILISEGUNDOS_EN_UN_DIA ;
		return dias;
		}

	public static void cargarBackup(String x, Context c) {
		// TODO Auto-generated method stub
		
		
		File copia = new File(copias +"/"+x);
		String text = "";
		String text2 = "";
		Boolean ok = true;
		BufferedReader bf = null;
		String sCadena = "";
		try {
			bf = new BufferedReader(new FileReader(copia));
			while ((sCadena = bf.readLine()) != null) {
				if(sCadena.equals("************************ Backup_" +x.substring(0,x.length()-4)
				+ "************************"))
					ok = false;
				else{
				
				if(ok)
					text += sCadena;
				else
					text2 += sCadena;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Excepcion al leer", e.toString());
		}
		
		OutputStreamWriter osw,osw2;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(notas));
			osw.write(text);
			osw.close();
			osw2 = new OutputStreamWriter(new FileOutputStream(owl));
			osw2.write(text2);
			osw2.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Toast.makeText(c,  c.getString(R.string.Toast_Backup), Toast.LENGTH_SHORT).show();
        
		
	}
		
		
	
}
