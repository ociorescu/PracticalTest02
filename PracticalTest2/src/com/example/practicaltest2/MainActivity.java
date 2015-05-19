package com.example.practicaltest2;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LogPrinter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final String tag = "MY_TEST2";
	private LogPrinter lp = new LogPrinter(Log.DEBUG, tag);
	
	private EditText elem2_edit , elem4_edit1 , elem4_edit2 , elem5_edit;
	private Spinner elem5_spinner;
	private TextView elem8_text;
	
	private ServerThread serverThread = null;
	private ClientThread clientThread = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lp.println("onCreate");
		
		DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width_display = metrics.widthPixels;
        int height_display = metrics.heightPixels;
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
        	statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        lp.println("statusBarHeight: " + statusBarHeight);
        height_display -= statusBarHeight;
        
        lp.println("width_display: "+ width_display + " height_display: " + height_display);
		
        RelativeLayout rootPage = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_rootPage = new RelativeLayout.LayoutParams(width_display,height_display);
        rootPage.setLayoutParams(params_rootPage);
        rootPage.setBackgroundColor(Color.GRAY);
        
        int height_div = (int)(height_display * 0.1);
        
        RelativeLayout elem1 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem1 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem1.setMargins(0, 0 * height_div, 0, 0);
        elem1.setLayoutParams(params_elem1);
        elem1.setBackgroundColor(Color.GREEN);
        
        TextView elem1_text = new TextView(elem1.getContext());
        RelativeLayout.LayoutParams params_elem1_text = new RelativeLayout.LayoutParams(width_display,height_div);
        elem1_text.setLayoutParams(params_elem1_text);
        elem1_text.setText("SERVER");
        elem1_text.setTextColor(Color.WHITE);
        elem1_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, height_div / 2);
        elem1_text.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);       
        elem1.addView(elem1_text);
        
        RelativeLayout elem2 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem2 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem2.setMargins(0, 1 * height_div, 0, 0);
        elem2.setLayoutParams(params_elem2);
        //elem2.setBackgroundColor(Color.BLUE);
        
        int width_display_half = (int) width_display / 2;
        
        elem2_edit = new EditText(elem2.getContext());
        RelativeLayout.LayoutParams params_elem2_edit = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem2_edit.setMargins(0, 0, 0, 0);
        elem2_edit.setLayoutParams(params_elem2_edit);
        elem2_edit.setHint("PORT");        
        elem2.addView(elem2_edit);
        
        Button elem2_button = new Button(elem2.getContext());
        RelativeLayout.LayoutParams params_elem2_button = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem2_button.setMargins(width_display_half, 0, 0, 0);
        elem2_button.setLayoutParams(params_elem2_button);
        elem2_button.setText("CONNECT");
        elem2_button.setId(2);
        elem2_button.setOnClickListener(this);
        elem2.addView(elem2_button);
        
        RelativeLayout elem3 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem3 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem3.setMargins(0, 2 * height_div, 0, 0);
        elem3.setLayoutParams(params_elem3);
        elem3.setBackgroundColor(Color.GREEN);
        
        TextView elem3_text = new TextView(elem3.getContext());
        RelativeLayout.LayoutParams params_elem3_text = new RelativeLayout.LayoutParams(width_display,height_div);
        elem3_text.setLayoutParams(params_elem3_text);
        elem3_text.setText("CLIENT");
        elem3_text.setTextColor(Color.WHITE);
        elem3_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, height_div / 2);
        elem3_text.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);       
        elem3.addView(elem3_text);
        
        RelativeLayout elem4 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem4 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem4.setMargins(0, 3 * height_div, 0, 0);
        elem4.setLayoutParams(params_elem4);
        
        elem4_edit1 = new EditText(elem4.getContext());
        RelativeLayout.LayoutParams params_elem4_edit1 = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem4_edit1.setMargins(0, 0, 0, 0);
        elem4_edit1.setLayoutParams(params_elem4_edit1);
        elem4_edit1.setHint("ADDRESS");
        elem4_edit1.setText("127.0.0.1");
        elem4.addView(elem4_edit1);
        
        elem4_edit2 = new EditText(elem4.getContext());
        RelativeLayout.LayoutParams params_elem4_edit2 = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem4_edit2.setMargins(width_display_half, 0, 0, 0);
        elem4_edit2.setLayoutParams(params_elem4_edit2);
        elem4_edit2.setHint("PORT");        
        elem4.addView(elem4_edit2);
        
        RelativeLayout elem5 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem5 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem5.setMargins(0, 4 * height_div, 0, 0);
        elem5.setLayoutParams(params_elem5);
        
        elem5_edit = new EditText(elem5.getContext());
        RelativeLayout.LayoutParams params_elem5_edit = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem5_edit.setMargins(0, 0, 0, 0);
        elem5_edit.setLayoutParams(params_elem5_edit);
        elem5_edit.setHint("CITY");        
        elem5.addView(elem5_edit);
        
        elem5_spinner = new Spinner(elem5.getContext());
        RelativeLayout.LayoutParams params_elem5_spinner = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem5_spinner.setMargins(width_display_half, 0, 0, 0);
        elem5_spinner.setLayoutParams(params_elem5_spinner);
        
        ArrayList<String> elem5_spinner_list = new ArrayList<String>();
        elem5_spinner_list.add("all");
        elem5_spinner_list.add("aaaa");
        elem5_spinner_list.add("bbbb");
        elem5_spinner_list.add("cccc");
        ArrayAdapter<String> elem5_spinner_adaptar = new ArrayAdapter<String>(elem5.getContext(), android.R.layout.simple_spinner_item , elem5_spinner_list);
        
        elem5_spinner.setAdapter(elem5_spinner_adaptar);
        elem5.addView(elem5_spinner);
        
        
        RelativeLayout elem6 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem6 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem6.setMargins(0, 5 * height_div, 0, 0);
        elem6.setLayoutParams(params_elem6);
        
        Button elem6_button = new Button(elem6.getContext());
        RelativeLayout.LayoutParams params_elem6_button = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem6_button.setMargins(0, 0, 0, 0);
        elem6_button.setLayoutParams(params_elem6_button);
        elem6_button.setText("GET DATA");
        elem6_button.setId(3);
        elem6_button.setOnClickListener(this);
        elem6.addView(elem6_button);
        
        RelativeLayout elem8 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem8 = new RelativeLayout.LayoutParams(width_display,3 * height_div);
        params_elem8.setMargins(0, 7 * height_div, 0, 0);
        elem8.setLayoutParams(params_elem8);
        
        elem8_text = new TextView(elem8.getContext());
        RelativeLayout.LayoutParams params_elem8_text = new RelativeLayout.LayoutParams(width_display,3 * height_div);
        params_elem8_text.setMargins(0, 0, 0, 0);
        elem8_text.setLayoutParams(params_elem8_text);
        elem8_text.setText("DATA");
        elem8.addView(elem8_text);
        
        
        rootPage.addView(elem1);
        rootPage.addView(elem2);
        rootPage.addView(elem3);
        rootPage.addView(elem4);
        rootPage.addView(elem5);
        rootPage.addView(elem6);
        
        rootPage.addView(elem8);
               
		setContentView(rootPage , params_rootPage);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		lp.println("onStart");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lp.println("onResume");
	}	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		lp.println("onPause");
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		lp.println("onRestart");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		lp.println("onStop");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		/*
		if (serverThread != null) {
			serverThread.stopThread();
		}
		*/
		super.onDestroy();
		lp.println("onDestroy");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case 2:
				lp.println("server start");
				//server start
				String serverPort = elem2_edit.getText().toString();
				if((serverPort == null) || (serverPort.isEmpty())){
					Toast.makeText(getApplicationContext(),"Server port should be filled!",Toast.LENGTH_SHORT).show();
					return;
				}
				
				serverThread = new ServerThread(Integer.parseInt(serverPort));
				if(serverThread.getServerSocket() != null) {
					serverThread.start();
				}else{
					lp.println("Could not creat server thread!");
				}
				
				break;
			case 3:
				//client start
				lp.println("client start");
				String clientAddress = elem4_edit1.getText().toString();
				String clientPort    = elem4_edit2.getText().toString();
				if(clientAddress == null || clientAddress.isEmpty() || clientPort == null || clientPort.isEmpty()) {
					Toast.makeText(getApplicationContext(),"Client connection parameters should be filled!",Toast.LENGTH_SHORT).show();
					return;
				}
				
				if (serverThread == null || !serverThread.isAlive()) {
					lp.println("There is no server to connect to!");
					return;
				}
				
				String city = elem5_edit.getText().toString();
				String informationType = elem5_spinner.getSelectedItem().toString();
				if (city == null || city.isEmpty() || informationType == null || informationType.isEmpty()) {
					Toast.makeText(getApplicationContext(),"Parameters from client (city / information type) should be filled!",Toast.LENGTH_SHORT).show();
					return;
				}
				
				elem8_text.setText("");
				
				clientThread = new ClientThread(clientAddress,Integer.parseInt(clientPort),city,informationType,elem8_text);
				clientThread.start();
				
				break;
			
		}
	}
	
}
