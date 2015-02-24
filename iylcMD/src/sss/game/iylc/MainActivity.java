package sss.game.iylc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button b1;
	Button b2;
	Button b3;
	Intent i ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		i = new Intent(this, game.class);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		
		b1.setOnClickListener(myhandler1);
		b2.setOnClickListener(myhandler2);
		b3.setOnClickListener(myhandler3);

	}

	View.OnClickListener myhandler1 = new View.OnClickListener() {
		public void onClick(View v) {
			startActivity(i); 
		}
	};
	View.OnClickListener myhandler2 = new View.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
	View.OnClickListener myhandler3 = new View.OnClickListener() {
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
			 
		    builder.setTitle("HELP")
		            .setIcon(
		                    getResources().getDrawable(
		                            android.R.drawable.ic_dialog_info))
		            .setMessage("Contesta las ecuaciones si no aciertas entonces te toca beber un chupito.");		 
		    builder.show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
