package sss.game.iylc;

import sss.game.solutions.Solutions;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class game extends Activity {

	public static int N_img = 11;
	public int actual_img;
	public ImageView imagen;
	public Button resolver;
	public Solutions s;
	MediaPlayer media_p = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		s = new Solutions();
		reable();

		final Button resolver = (Button) findViewById(R.id.button1);
		resolver.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText et = (EditText) findViewById(R.id.editText1);
				String text = et.getText().toString();

				try {
					int entero = Integer.parseInt(text);
					if (entero == s.get(actual_img)) {
						realIMG();
						Toast toast1 = Toast.makeText(getApplicationContext(),
								"Success!", Toast.LENGTH_SHORT);
						toast1.show();
						media_p = MediaPlayer.create(v.getContext(), R.raw.win);
						media_p.start();
						// activar next
						Button next = (Button) findViewById(R.id.button2);
						next.setEnabled(true);
						// desactivar responder
						resolver.setEnabled(false);
						// ADD Point
						TextView points = (TextView) findViewById(R.id.textView2);
						String p = points.getText().toString();
						int res = Integer.parseInt(p);
						res++;
						points.setText(res+"");
					} else {
						realIMG();
						Toast toast1 = Toast.makeText(getApplicationContext(),
								"Fail!", Toast.LENGTH_SHORT);
						toast1.show();
						media_p = MediaPlayer.create(v.getContext(), R.raw.fail);
						media_p.start();
						// desactivar responder
						resolver.setEnabled(false);
					}

				} catch (Exception e) {
					Log.e("exception", e.toString());
				}

			}
		});

		final Button next = (Button) findViewById(R.id.button2);
		next.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				reable();
				next.setEnabled(false);
				resolver.setEnabled(true);
			}
		});

		Button exit = (Button) findViewById(R.id.button3);
		exit.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
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

	public void reable() {

		imagen = (ImageView) findViewById(R.id.imageView1);
		actual_img = (int) (Math.random() * N_img);
		Log.e("TTTT", actual_img + "");

		Resources res = getResources();
		String mDrawableName = "init_" + actual_img;
		int resID = res.getIdentifier(mDrawableName, "drawable",
				getPackageName());
		Drawable drawable = res.getDrawable(resID);
		imagen.setImageDrawable(drawable);
	}

	public void realIMG() {

		Resources res = getResources();
		String mDrawableName = "sol_" + actual_img;
		int resID = res.getIdentifier(mDrawableName, "drawable",
				getPackageName());
		Drawable drawable = res.getDrawable(resID);
		imagen.setImageDrawable(drawable);

	}
}
