package vista.activity.nnv0;

import gestion.nnv0.gestor_Backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.Vector;

import com.example.nnv0.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;


public class OptionActivity extends Activity{
	
	 Locale myLocale;
	 Boolean change = false;
	 private static File folder = new File(Environment.getExternalStorageDirectory()+"/nonet");
	 private File folder_lang = new File(folder +"/language");
	 private File idioma = new File(folder_lang + "/idioma.txt");
	 static File folder_back = new File(folder + "/backup");
	 String lang_est = "";
	 String per = ""; 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cargarIdioma();
		setContentView(R.layout.opivity);
		
		
		
		final Spinner spinner = (Spinner)findViewById(R.id.CmbOpciones);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
	    (this, android.R.layout.simple_spinner_dropdown_item, listFilesInDir()		);
		spinner.setAdapter(spinnerArrayAdapter);
		
		final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1);
		final CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
		final CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
		final CheckBox cb4 = (CheckBox) findViewById(R.id.checkBox4);
		final CheckBox cb5 = (CheckBox) findViewById(R.id.checkBox5);
		final CheckBox cb6 = (CheckBox) findViewById(R.id.checkBox6);
		per = gestor_Backup.cargarPeriodo();
		
		if(per.equals("dia"))
			cb3.setChecked(true);
		else if(per.equals("sem"))
			cb4.setChecked(true);
		else if(per.equals("year"))
			cb5.setChecked(true);
		else
			cb6.setChecked(true);
		
		if(lang_est.equals("es"))
			cb1.setChecked(true);
		else
			cb2.setChecked(true);
		
		//Español 
		cb1.setOnCheckedChangeListener(
	    	    new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						if (isChecked) {
	    	                cb2.setChecked(false);
	    	                change = true;
	    	                lang_est = "es";
	    	            }
	    	            else {
	    	            	cb2.setChecked(true);
	    	            	change = true;
	    	            	lang_est = "en";
	    	            	
	    	            }
						
					}

	   });
		//Ingles
		cb2.setOnCheckedChangeListener(
	    	    new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						if (isChecked) {
							cb1.setChecked(false);
							change = true;
							lang_est = "en";
							
						}
	    	            else {
	    	            	cb1.setChecked(true);
	    	            	change = true;
	    	            	lang_est = "es";
	    	            }
						
					}

	    	        });
		
		cb3.setOnCheckedChangeListener(
	    	    new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
					if (isChecked) {
						cb4.setChecked(false);
						cb5.setChecked(false);
						cb6.setChecked(false);
						per = "dia";
					}
					}

	    	        });
		
		cb4.setOnCheckedChangeListener(
	    	    new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						if (isChecked) {
							cb3.setChecked(false);
							cb5.setChecked(false);
							cb6.setChecked(false);
							per = "sem";
						}
					}

	    	        });
		cb5.setOnCheckedChangeListener(
	    	    new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						if (isChecked) {
							cb4.setChecked(false);
							cb3.setChecked(false);
							cb6.setChecked(false);
							per = "year";
						}
					}

	    	        });
		cb6.setOnCheckedChangeListener(
	    	    new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						if (isChecked) {
							cb4.setChecked(false);
							cb3.setChecked(false);
							cb5.setChecked(false);
							per = "non";
						}
						
					}

	    	        });
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 finish();
			}
		});
		
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(cb3.isChecked() || cb4.isChecked() || cb5.isChecked() || cb6.isChecked()){
				 write();
				 gestor_Backup.writePeriodo(per);
				 setLocale(lang_est);
				
				 canvios();
				}else{
					errorcanvios();
				}
			}
		});
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 final String x = (String)spinner.getItemAtPosition(spinner.getSelectedItemPosition());
				AlertDialog.Builder builder = new AlertDialog.Builder(OptionActivity.this);
				builder.setMessage(R.string.OA_Talert_1)
				        .setTitle(R.string.OA_Talert_2)
				        .setCancelable(false)
				        .setNegativeButton(R.string.OA_Talert_4,
				                new DialogInterface.OnClickListener() {
				                    public void onClick(DialogInterface dialog, int id) {
				                        dialog.cancel();
				                    }
				                })
				        .setPositiveButton(R.string.OA_Talert_3,
				                new DialogInterface.OnClickListener() {
				                    public void onClick(DialogInterface dialog, int id) {
				                    	callBackup(x);
				                    }
				                });
				AlertDialog alert = builder.create();
				alert.show();		
			}
		});
		
		
	}
	public void canvios(){
		Toast.makeText(this,  this.getString(R.string.MNJ_T_changes),
				 Toast.LENGTH_SHORT).show();
	}
	public void errorcanvios(){
		Toast.makeText(this,  this.getString(R.string.OA_checkPS),
				 Toast.LENGTH_SHORT).show();
	}
	public void callBackup(String x){
		gestor_Backup.cargarBackup(x,this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void onPause(){
		super.onPause();
		
	}
	
	private void cargarIdioma() {
		// TODO Auto-generated method stub
		 if(folder_lang.exists()){
			 	
			 BufferedReader bf = null;
				String sCadena= "";
				String idioma1 = "";
				
				try {
					 bf = new BufferedReader(new FileReader(idioma));
					 
						while ((sCadena = bf.readLine())!=null) {
								idioma1 = sCadena;
							}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("Excepcion al leer", e.toString());
				}
				if(idioma1.equals("es")){
					lang_est = "es";
				}else {
					lang_est = "en";
				}
		 }
	}
	
	public void setLocale(String lang) {
		  
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }	
	
	private void write(){
		
		OutputStreamWriter oswidio;
		try {
			oswidio = new OutputStreamWriter(new FileOutputStream(idioma));
			if(lang_est.equals("en"))
				oswidio.write("en");
			else
				oswidio.write("es");
        	oswidio.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	 public static String[] listFilesInDir(){
		 File copias = new File(folder_back + "/copias");
	      
	      File[] fichs = copias.listFiles();
	      
	      Vector<String> v = new Vector<String>();
	      for (int i=0;i<fichs.length;i++){
	         if (fichs[i].isFile())
	         v.add(fichs[i].getName());
	         Log.e("Python", fichs[i].getName());
	      }
	      return v.toArray(new String[]{});
	   }
}
