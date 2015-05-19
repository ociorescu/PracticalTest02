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
	
	private EditText elem2_edit , elem4_edit1 , elem4_edit2 , elem5_edit , elem2_edit2 , elem5_edit2 ;
	private TextView elem4_text , elem3_text;
	
	
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
        elem2_edit.setHint("op1");        
        elem2.addView(elem2_edit);
        
        elem2_edit2 = new EditText(elem2.getContext());
        RelativeLayout.LayoutParams params_elem2_edit2 = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem2_edit2.setMargins(width_display_half, 0, 0, 0);
        elem2_edit2.setLayoutParams(params_elem2_edit2);
        elem2_edit2.setHint("op2");        
        elem2.addView(elem2_edit2);
        
        RelativeLayout elem3 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem3 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem3.setMargins(0, 2 * height_div, 0, 0);
        elem3.setLayoutParams(params_elem3);
        elem3.setBackgroundColor(Color.GREEN);
        
        Button elem3_button = new Button(elem3.getContext());
        RelativeLayout.LayoutParams params_elem3_button = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem3_button.setMargins(0, 0, 0, 0);
        elem3_button.setLayoutParams(params_elem3_button);
        elem3_button.setText("+");
        elem3_button.setId(2);
        elem3_button.setOnClickListener(this);
        elem3.addView(elem3_button);
        
        elem3_text = new TextView(elem3.getContext());
        RelativeLayout.LayoutParams params_elem3_text = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem3_text.setMargins(width_display_half, 0, 0, 0);
        elem3_text.setLayoutParams(params_elem3_text);
        elem3_text.setText("ccc");
        elem3.addView(elem3_text);
        
        RelativeLayout elem4 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem4 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem4.setMargins(0, 3 * height_div, 0, 0);
        elem4.setLayoutParams(params_elem4);
        
        Button elem4_button2 = new Button(elem4.getContext());
        RelativeLayout.LayoutParams params_elem4_button2 = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem4_button2.setMargins(0, 0, 0, 0);
        elem4_button2.setLayoutParams(params_elem4_button2);
        elem4_button2.setText("*");
        elem4_button2.setId(3);
        elem4_button2.setOnClickListener(this);
        elem4.addView(elem4_button2);
        
        elem4_text = new TextView(elem4.getContext());
        RelativeLayout.LayoutParams params_elem4_text = new RelativeLayout.LayoutParams(width_display_half,height_div);
        params_elem4_text.setMargins(width_display_half, 0, 0, 0);
        elem4_text.setLayoutParams(params_elem4_text);
        elem4_text.setText("ddd");
        elem4.addView(elem4_text);

        
        RelativeLayout elem5 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem5 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem5.setMargins(0, 4 * height_div, 0, 0);
        elem5.setLayoutParams(params_elem5);       
        
        elem5_edit2 = new EditText(elem5.getContext());
        RelativeLayout.LayoutParams params_elem5_edit2 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem5_edit2.setMargins(0, 0, 0, 0);
        elem5_edit2.setLayoutParams(params_elem5_edit2);
        elem5_edit2.setHint("port_server");        
        elem5.addView(elem5_edit2);
        
        RelativeLayout elem6 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem6 = new RelativeLayout.LayoutParams(width_display,height_div);
        params_elem6.setMargins(0, 5 * height_div, 0, 0);
        elem6.setLayoutParams(params_elem6);
        

        
        RelativeLayout elem8 = new RelativeLayout(this);
        RelativeLayout.LayoutParams params_elem8 = new RelativeLayout.LayoutParams(width_display,3 * height_div);
        params_elem8.setMargins(0, 7 * height_div, 0, 0);
        elem8.setLayoutParams(params_elem8);

        
        
        rootPage.addView(elem1);
        rootPage.addView(elem2);
        rootPage.addView(elem3);
        rootPage.addView(elem4);
        rootPage.addView(elem5);
        rootPage.addView(elem6);
        
        rootPage.addView(elem8);
        
        int port = 2525;
        elem5_edit2.setText("" + port);
        
        serverThread = new ServerThread(port);
		if(serverThread.getServerSocket() != null) {
			serverThread.start();
		}else{
			lp.println("Could not creat server thread!");
		}
        
        
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
		
		if (serverThread != null) {
			serverThread.stopThread();
		}
		
		super.onDestroy();
		lp.println("onDestroy");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case 2:
				lp.println("+ start");
				//+
				String op1 = elem2_edit.getText().toString();
				String op2 = elem2_edit2.getText().toString();
				if (op1.isEmpty()||op2.isEmpty()){
					Toast.makeText(getApplicationContext(),"op1/op2 gol",Toast.LENGTH_SHORT).show();
					return;
				}
				
				clientThread = new ClientThread(op1,op2,"add",elem3_text);
				clientThread.start();
				
				break;
			case 3:
				lp.println("* start");
				//*
				String op3 = elem2_edit.getText().toString();
				String op4 = elem2_edit2.getText().toString();
				if (op3.isEmpty()||op4.isEmpty()){
					Toast.makeText(getApplicationContext(),"op1/op2 gol",Toast.LENGTH_SHORT).show();
					return;
				}
				
				clientThread = new ClientThread(op3,op4,"mul",elem4_text);
				clientThread.start();
				
				break;
			
		}
	}
	
}
