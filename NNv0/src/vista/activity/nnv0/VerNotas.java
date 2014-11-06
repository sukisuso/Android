package vista.activity.nnv0;

import gestion.nnv0.gestor_Notas;

import java.util.Vector;
import com.example.nnv0.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class VerNotas  extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ver_nota);
		
		Vector<String> datos = new Vector();
		Intent iin= getIntent();
        Bundle b = iin.getExtras();

	
        if(b!=null)
        {
        	String etiquetas =(String) b.get("result");
        	Log.e("Veamos", etiquetas);
        	datos = gestor_Notas.getListaNotasbyEt(etiquetas);
        	
        	if(datos.size()== 0)
        		Toast.makeText(this, this.getString(R.string.MNJ_T_no_notas),
    					Toast.LENGTH_LONG).show();
        }else {	
		datos = gestor_Notas.getListaNotas();}
    	
		
		ArrayAdapter<String> adap = new ArrayAdapter<String>(VerNotas.this,
				R.layout.element_list,
				R.id.textView1,
				datos);
		
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setAdapter(adap);
		if(b == null)
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				String item = ((TextView) arg1).getText().toString();
				Intent in = new Intent(VerNotas.this,Mod_elim_notas.class);
				in.putExtra("Nota", item);
				
				startActivity(in);
				finish();
			
			}});
		else
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {

					String item = ((TextView) arg1).getText().toString();
					Intent in = new Intent(VerNotas.this,Mod_elim_notas.class);
					in.putExtra("Nota", item);
					
					startActivity(in);
				
				}});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
