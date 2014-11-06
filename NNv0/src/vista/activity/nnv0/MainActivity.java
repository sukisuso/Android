package vista.activity.nnv0;

import gestion.nnv0.Nota;
import gestion.nnv0.gestor_Backup;
import gestion.nnv0.gestor_Notas;
import graphics.nnv0.GraphView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import org.apache.commons.io.IOUtils;
import org.jdom2.JDOMException;

import com.example.nnv0.R;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {
	Locale myLocale;
	File folder = new File(Environment.getExternalStorageDirectory()+"/nonet");
	File notas = new File(folder + "/notas.xml");
    File owl = new File(folder + "/ontologia.xml");
    File folder_lang = new File(folder +"/language");
    File idioma = new File(folder_lang + "/idioma.txt");
    File folder_back = new File(folder +"/backup");
    File backup = new File(folder_back +"/backup.txt");
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 if(folder_lang.exists()){
			 if(idioma.exists())
				 cargarIdioma();
		}
		setContentView(R.layout.activity_main);
		gestor_Backup.doBackup();
		init();

	}

	protected void onStart(){
		super.onStart();
		/*comprobamos Directorio en la SD*/
        
        if(!folder.exists()){
            if(folder.mkdirs()){          	
            Toast.makeText(this,  this.getString(R.string.MNJ_T_DirCreated), Toast.LENGTH_SHORT).show();
           
            
            	try {
					OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(notas));
					OutputStreamWriter osw2 = new OutputStreamWriter(new FileOutputStream(owl));
					
					osw.write("<notas>\r\n</notas>");
					osw.close();
		            
		            osw2.write(getTextAsset("Ont.xml"));
		            osw2.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}          
            }
        }                  
        else{
        	
        	if (!notas.exists()) {

            	try {
            		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(notas));
            		osw.write("<notas>\r\n</notas>");
		            osw.close();			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            if(!owl.exists()){

            	try {		
            		OutputStreamWriter osw2 = new OutputStreamWriter(new FileOutputStream(owl));		
		            osw2.write(getTextAsset("Ont.xml"));
		            osw2.close();
            	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        
        /*Comprobar directorio de idioma y directorio /backup 
         * y crear archivos correspondientes.             
         * */
        
        String idioma_disp = Locale.getDefault().getDisplayLanguage();
        Log.e("Lo", idioma_disp);
        // English  español
        if(!folder_lang.exists()){
            if(folder_lang.mkdirs()){
            	OutputStreamWriter oswidio;
				try {
					oswidio = new OutputStreamWriter(new FileOutputStream(idioma));
					if(idioma_disp.equals("English"))
						oswidio.write("en");
					else
						oswidio.write("es");
	            	oswidio.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 				
            	
            }
            
        }else{
        	if (!idioma.exists()) {

            	try {
            		OutputStreamWriter oswidio = new OutputStreamWriter(new FileOutputStream(idioma));
            		if(idioma_disp.equals("English"))
						oswidio.write("en");
					else
						oswidio.write("es");
		            oswidio.close();			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        	
        }
        
        if(!folder_back.exists()){
            if(folder_back.mkdirs()){
            	OutputStreamWriter oswidio;
				try {
					oswidio = new OutputStreamWriter(new FileOutputStream(backup));
					oswidio.write("sem");
	            	oswidio.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 				
            	
            }
            
        }else{
        	if (!backup.exists()) {
            	try {
            		OutputStreamWriter oswidio = new OutputStreamWriter(new FileOutputStream(backup));
					oswidio.write("sem");
		            oswidio.close();			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        	
        }
    	
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
	
	@Override
	protected void onRestart(){
		
		super.onRestart();
		setContentView(R.layout.activity_main);
		
		init();
	}
	
		
	@Override
	public void onResume(){
	super.onResume();
	
			//Comprobar Backup
		
	}
	
	/**
     * Metodo para cargar el texto de un Asset
     * @param n_fichero: nombre del fichero Asset a cargar.
     * @return theString: texto que contiene el Asset
     */
	public String getTextAsset(String n_fichero){

		String theString = "";
		AssetManager am = getResources().getAssets();
		InputStream is;
		try {
				is = am.open(n_fichero);

				StringWriter writer = new StringWriter();
				IOUtils.copy(is, writer, "UTF-8");
				theString = writer.toString();
		} catch (Exception e) {
		}
		
		return theString;
	};
	

	 public void setLocale(String lang) {
		  
	        myLocale = new Locale(lang);
	        Resources res = getResources();
	        DisplayMetrics dm = res.getDisplayMetrics();
	        Configuration conf = res.getConfiguration();
	        conf.locale = myLocale;
	        res.updateConfiguration(conf, dm);
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
						setLocale("es");
					}else {
						setLocale("en");}
			 }
		}
		
		
		private void init(){
			/* Boton Nueva Nota*/
			findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
						Intent in = new Intent(MainActivity.this,NewNote.class);	
						startActivity(in);				
				}			
			});
			/* Boton Ver Notas*/
			findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
						Intent in = new Intent(MainActivity.this,VerNotas.class);	
						startActivity(in);				
				}			
			});
			/* Boton Nueva Opciones*/
			findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
						Intent in = new Intent(MainActivity.this,OptionActivity.class);	
						startActivity(in);
				}			
			});
			
			/* Boton Modificar Ontologia*/
			findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
						Intent in = new Intent(MainActivity.this,ModOntologia.class);	
						startActivity(in);
				}			
			});
			/* Boton Salir*/
			findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish(); 
				}			
			});
			
			/* Boton Red Semantica*/
			findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					final ProgressDialog progressDialog = ProgressDialog.show(
							MainActivity.this, "Loading" , "Loading Graph", true);
				      new Thread() {
				          public void run() {
				                try{
				                	Intent in = new Intent(MainActivity.this,GraphView.class);	
				                	startActivity(in);	
				                   } 
				                catch (Exception e) {
				                    Log.e("tag", e.getMessage());
				                   }
				                // dismiss the progress dialog
				                       progressDialog.dismiss();
				                   }
				                }.start();
								
				}			
			});
			
			
		}
		
}
