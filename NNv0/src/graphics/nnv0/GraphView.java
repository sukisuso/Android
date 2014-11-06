package graphics.nnv0;

import graphics.nnv0.GraphView.MyView.direcction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import vista.activity.nnv0.MainActivity;
import vista.activity.nnv0.Mod_elim_notas;
import vista.activity.nnv0.NewNote;
import vista.activity.nnv0.VerNotas;

import com.example.nnv0.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GraphView extends Activity {

	static final private String color = "#CD5C5C";
	Vector<Integer> ancho = new Vector<Integer>();
	Boolean ok = true;
	int MARGEN = 40;
	int SPACE = 15;
	private Graph grafo = new Graph();
	private int radius = 35;
	HashMap<String, direcction> listapadres = new HashMap<String, direcction>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		for (int i = 0; i < 30; i++)
			ancho.add(i, 0);

		setContentView(R.layout.graphviewlayout);
		LinearLayout l = (LinearLayout) findViewById(R.id.layout);
		l.addView(new MyView(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menugraohview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.busq:
			Intent in = new Intent(GraphView.this, FindbyEt.class);
			startActivity(in);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public class MyView extends View {

		private ScaleGestureDetector detector;
		GestureDetector gestureDetector;
		private static final float MIN_ZOOM = 1f;
		private static final float MAX_ZOOM = 5f;
		private float scaleFactor = 1.f;

		public MyView(Context context) {
			super(context);
			setLayoutParams(new RelativeLayout.LayoutParams(5000, 5000));
			detector = new ScaleGestureDetector(getContext(),
					new ScaleListener());
			gestureDetector = new GestureDetector(context,
					new GestureListener());
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {

			detector.onTouchEvent(event);
			gestureDetector.onTouchEvent(event);
			return true;
		}

		@SuppressLint("DrawAllocation")
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			canvas.save();
			canvas.scale(scaleFactor, scaleFactor);

			for (int i = 0; i < 30; i++)
				ancho.set(i, 0);
			int px = 40;
			int py = (radius * 3) + 15;
			int x = 550 / 2;
			int y = radius + 10;
			// Pintamos nodo Thing
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.WHITE);
			canvas.drawPaint(paint);
			paint.setColor(Color.parseColor(color));
			canvas.drawCircle(x, radius + 10, radius, paint);
			paint.setColor(Color.BLACK);
			canvas.drawText("Thing", x - (radius / 2), radius + 10, paint);

			Vector<String> nodos = grafo.getElementsOf("");
			nodos.remove(0);
			for (int i = 0; i < nodos.size(); i++) {

				paint.setColor(Color.GREEN);
				canvas.drawLine(px, py, x, y, paint);
				paint.setColor(Color.parseColor(color));
				canvas.drawCircle(px, py, radius, paint);
				paint.setColor(Color.BLACK);
				canvas.drawText(nodos.elementAt(i), px - (radius / 2), py,
						paint);
				direcction dir = new direcction(px, py);
				listapadres.put(nodos.elementAt(i), dir);
				px += (radius * 2) + 15;
			}

			drawRecursive(canvas, paint, nodos, 6);
			canvas.restore();

		}

		// x e y = son las coordenadas del padre.
		public void drawRecursive(Canvas canvas, Paint paint,
				Vector<String> nodos, int prof) {

			Vector<String> nodosf = new Vector<String>();
			Vector<String> nodos2;
			int px = MARGEN;
			int py = (radius * prof) + SPACE;
			for (int i = 0; i < nodos.size(); i++) {
				nodos2 = grafo.getElementsOf(nodos.elementAt(i));
				for (int j = 0; j < nodos2.size(); j++) {
					paint.setColor(Color.GREEN);
					direcction pos = listapadres.get(nodos.elementAt(i));

					canvas.drawLine(px, py, pos.x, pos.y, paint);
					paint.setColor(Color.parseColor(color));
					canvas.drawCircle(px, py, radius, paint);
					paint.setColor(Color.BLACK);
					canvas.drawText(nodos2.elementAt(j), px - (radius / 2), py,
							paint);
					direcction dir = new direcction(px, py);
					listapadres.put(nodos2.elementAt(j), dir);
					px += (radius * 2) + SPACE;

				}
				nodosf.addAll(nodos2);
			}
			if (!nodosf.isEmpty())
				drawRecursive(canvas, paint, nodosf, prof + 3);

		}

		public class direcction {

			int x, y;

			direcction(int x, int y) {
				this.x = x;
				this.y = y;
			}

			public void toLog() {
				Log.e("Dir", x + " - " + y);
			}

		}

		private class ScaleListener extends
				ScaleGestureDetector.SimpleOnScaleGestureListener {

			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				scaleFactor *= detector.getScaleFactor();
				scaleFactor = Math.max(MIN_ZOOM,
						Math.min(scaleFactor, MAX_ZOOM));
				invalidate();
				return true;
			}
		}

		private class GestureListener extends
				GestureDetector.SimpleOnGestureListener {

			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}

			// event when double tap occurs
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				float x =  e.getX()/scaleFactor ;
				float y =  e.getY()/scaleFactor ;

				Log.d("Double Tap", "Tapped at: (" + x + "," + y + ")");
				final String res = checkdoubleTab(x, y);
				if (!res.equals("")) {

					AlertDialog.Builder builder = new AlertDialog.Builder(
							GraphView.this);
					builder.setMessage(getResources().getString(R.string.Alert_FBE) +" "+ res +"?")
							.setCancelable(false)
							.setNegativeButton(R.string.Alert_FBE_no,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {

										}
									})
							.setPositiveButton(R.string.Alert_FBE_yes,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {

											Intent in = new Intent(
													GraphView.this,
													VerNotas.class);
											in.putExtra("result", "Clases: " + res + ",");
											startActivity(in);
										}
									});
					AlertDialog alert = builder.create();
					alert.show();
				}
				return true;
			}
		}

		private String checkdoubleTab(float x, float y) {

			String res = "";
			double ca = 0;
			double z = 0;
			double w = 0;

			Iterator it = listapadres.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				direcction dir = (direcction) e.getValue();

				z = (dir.x - x);
				w = dir.y - y;
				z = Math.pow(z, 2);
				w = Math.pow(w, 2);
				z = z + w;
				ca = Math.sqrt(z);

				if (ca < radius) {
					res = (String) e.getKey();
					break;
				}
			}
			return res;
		}

	}
}