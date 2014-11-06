package vista.activity.nnv0;

import java.io.IOException;
import java.util.Vector;

import org.jdom2.JDOMException;

import gestion.nnv0.Nota;
import gestion.nnv0.StyleSpanRemover;
import gestion.nnv0.gestor_Notas;
import gestion.nnv0.gestor_Strings;

import com.example.nnv0.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Mod_elim_notas extends Activity{
	Nota nota = new Nota("","");
	static int REQUEST = 1;
	Vector <String> V_texto = new Vector<String>();
	Vector <String> V_clase = new Vector<String>();
	int ini = 0;
	int fin =0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mon_elim_layout);
		
		
		
	Intent in = getIntent();
		
		final String val = in.getExtras().getString("Nota");
		EditText titulo = (EditText) findViewById(R.id.editText1);
		EditText texto = (EditText)findViewById(R.id.editText2);
		nota = gestor_Notas.getNota(val);
		loadVectors(nota);
		
		String tex_Net = nota.getTexto();
		tex_Net = gestor_Strings.removeEtiquetas(tex_Net);
		
		titulo.setText(nota.getTitulo());
		texto.setText(tex_Net + " ");
		colorText();
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				try {long time_start, time_end;
				time_start = System.currentTimeMillis();
            	
					modiNote(val);
					time_end = System.currentTimeMillis();
					Log.e("Modif Nota: ",
							( time_end - time_start ) +" milliseconds");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
			}});
		
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
					
					AlertDialog.Builder builder = new AlertDialog.Builder(Mod_elim_notas.this);
					builder.setMessage(R.string.MNJ_Alert_elimN)
					        .setTitle(R.string.MNJ_Alert_alN)
					        .setCancelable(false)
					        .setNegativeButton(R.string.MNJ_Alert_canN,
					                new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int id) {
					                        dialog.cancel();
					                    }
					                })
					        .setPositiveButton(R.string.MNJ_Alert_contN,
					                new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int id) {
					                    	long time_start, time_end;
					        				time_start = System.currentTimeMillis();
					                    	deleteNote();
					                    	time_end = System.currentTimeMillis();
					        				Log.e("Delete Nota: ",
					        						( time_end - time_start ) +" milliseconds");
					                    }
					                });
					AlertDialog alert = builder.create();
					alert.show();				
			}			
		});
		
		findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				addClases();
				
			}});
		findViewById(R.id.editText2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setClases();
			}			
		});
		
	}

	protected void setClases() {
		EditText texto = (EditText)findViewById(R.id.editText2);
		int x = texto.getSelectionStart();
		String text = texto.getText().toString();

		if(x != 0 && x != text.length()){
			
			String resultado = gestor_Strings.getClasesofStringinText(x, text, V_texto, V_clase);
			TextView dir =(TextView) findViewById(R.id.textViewClases);
			dir.setText("\\: " + resultado);
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
	private void deleteNote(){
		try {
		gestor_Notas.borrarNota(nota.getTitulo());
		Toast.makeText(this, this.getString(R.string.MNJ_T_NoteElim), Toast.LENGTH_SHORT).show();
		finish(); 
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	}
	private void modiNote(String titulo) throws JDOMException, IOException{
		EditText titulo_new = (EditText) findViewById(R.id.editText1);
		EditText texto = (EditText)findViewById(R.id.editText2);
		
		if((!titulo_new.getText().toString().equals(""))&& (!texto.getText().toString().equals("")) ){
			
			String res= gestor_Strings.setEtiquetas((String)texto.getText().toString(), V_texto, V_clase,1);
		
		Nota nueva = new Nota(titulo_new.getText().toString(),res);
		gestor_Notas.modificarNota(titulo, nueva);
		Toast.makeText(this, this.getString(R.string.MNJ_T_NoteModi), Toast.LENGTH_SHORT).show();
		finish(); 
		}else{
			Toast.makeText(this, this.getString(R.string.MNJ_T_ErrorCreated),
					Toast.LENGTH_SHORT).show();
			}		
		
	}
	
	private void loadVectors(Nota note) {
		
		V_texto = gestor_Strings.loadVectorText(note.getTexto());
		V_clase = gestor_Strings.loadVectorEt(note.getTexto());
	}
	
	private void colorText() {
		EditText texto = (EditText)findViewById(R.id.editText2);
		Spannable span = texto.getText();	
		String frase = texto.getText().toString();
		
		for(int i = 0; i < V_texto.size(); i++){
			
			int x = 0;
			int y = 0;
			
			for(int j = 0; j< gestor_Strings.palabrasrepetidas(V_texto.elementAt(i), frase);j++){
				x = gestor_Strings.getIntSX(V_texto.elementAt(i), frase,j+1);
				y = gestor_Strings.getIntFY(V_texto.elementAt(i), frase,x,j+1);
				Log.e(V_texto.elementAt(i) + " x-y",x+ " - "+ y );
			if((x != y) && (y > 1)){
			span.setSpan(new ForegroundColorSpan(Color.parseColor("#0000FF")), 
					x, y, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			span.setSpan(new StyleSpan(Typeface.BOLD), x, y, 0);}
		}}
	}
	
private void addClases(){
		
		EditText txtTexto = (EditText)findViewById(R.id.editText2);
		ini = txtTexto.getSelectionStart();
		fin = txtTexto.getSelectionEnd(); 
		
		/*If el texto ya esta en el Vector de clases, eliminarlo del vector
		 * y pasarle a la actividad las clases que contiene ya.*/
		
		
		if(!(ini == fin)){
			if(gestor_Strings.wordsCompleted(ini,fin,txtTexto.getText().toString())){
			Intent in = new Intent(this, SelectClases.class);
			if(gestor_Strings.exist(V_texto, txtTexto.getText().toString().substring(ini,fin) )){
				int x = gestor_Strings.existGetInt(V_texto, txtTexto.getText().toString().substring(ini,fin));
				in.putExtra("etiquetas",V_clase.elementAt(x));
				V_texto.remove(x);
				V_clase.remove(x);
			}else{
				in.putExtra("etiquetas","" );		
			}
			startActivityForResult(in, REQUEST);
			}else
				Toast.makeText(this, this.getString(R.string.MNJ_T_WordCom),
						Toast.LENGTH_SHORT).show();
			
		}else{
			
			Toast.makeText(this, this.getString(R.string.MNJ_T_add_class_note),
					Toast.LENGTH_SHORT).show();
		}
		
	}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	EditText txtTexto = (EditText)findViewById(R.id.editText2);
	Spannable texto = txtTexto.getText();				
	/*int ini = txtTexto.getSelectionStart();
	int fin = txtTexto.getSelectionEnd();*/
	String result;
	  if (requestCode == 1) {
	     if(resultCode == RESULT_OK){  
	    	 try{
	    		 result = data.getStringExtra("result"); 
	    		 result = gestor_Strings.deleteFirstPart(8, result );
	    	 }catch(Exception e){
	        	  result = "";
	    	 }	         
	    	 
	         if(!result.equals("")){
		         texto.setSpan(new ForegroundColorSpan(Color.parseColor("#0000FF")), 
							ini, fin, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				 texto.setSpan(new StyleSpan(Typeface.BOLD), ini, fin, 0);
				 String text = texto.toString();
				 text = text.substring(ini, fin);
				 int x = result.length();
				 
				 
				 if(result.charAt(x-2)==','){
					result = result.substring(0,x-1);}
				 V_texto.addElement(text);
				 V_clase.addElement(result);
				 colorText();
			 }else{
				 StyleSpanRemover spanRemover = new StyleSpanRemover();
				// to remove all styles
				 spanRemover.RemoveAll(texto,ini,fin);				 
				 texto.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 
							ini, fin, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				 texto.setSpan(new StyleSpan(Typeface.ITALIC), ini, fin, 0);
				 
			 }
	     }
	     if (resultCode == RESULT_CANCELED) {    
	         //Write your code if there's no result
	    	 Log.e("CANCELED","CANCER");
	    	 
	     }
	  }
	}
}