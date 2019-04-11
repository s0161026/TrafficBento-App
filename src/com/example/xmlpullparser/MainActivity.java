package com.example.xmlpullparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MainActivity extends Activity {
	
	//Thread busParse;
	private ImageButton button1, button2, button3;
	private Button button;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ConnectivityManager CM;
		CM = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

		if (CM.getNetworkInfo(1).getState() != NetworkInfo.State.CONNECTED){
			Utility.showToast(MainActivity.this, "�Х��s������");
			new Handler().postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
	            }
	        }, 1500);
		} 
			
		
		button1 =(ImageButton) findViewById(R.id.imageButton1);
		button1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent().setClass(MainActivity.this, BusData.class));
			}
		});
		
		button2 =(ImageButton) findViewById(R.id.imageButton2);
		button2.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent().setClass(MainActivity.this, Location_GPS.class));
			}
		});
		
		button3 =(ImageButton) findViewById(R.id.imageButton3);
		button3.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				toAbout();
			}
		});
		

	}
	
	public void toAbout(){

        setContentView(R.layout.about_us);

        ImageButton button = (ImageButton)findViewById(R.id.imageButton4);
        button.setOnClickListener(new ImageButton.OnClickListener(){

            public void onClick(View v) {
            	Intent i = new Intent(MainActivity.this, MainActivity.class);  //your class
        	    startActivity(i);
        	    finish();

            }           

        });

    }
	
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        // TODO Auto-generated method stub
	 
	        if (keyCode == KeyEvent.KEYCODE_BACK) { // �d�I��^��
	            new AlertDialog.Builder(MainActivity.this)
	                    .setTitle("���p�K��")
	                    .setMessage("�T�w�n���}���ε{���ܡH")
	                    .setIcon(R.drawable.ic_launcher)
	                    .setPositiveButton("�T�{",
	                            new DialogInterface.OnClickListener() {
	 
	                                @Override
	                                public void onClick(DialogInterface dialog,
	                                        int which) {
	                                    finish();
	                                }
	                            })
	                    .setNegativeButton("���n",
	                            new DialogInterface.OnClickListener() {
	 
	                                @Override
	                                public void onClick(DialogInterface dialog,
	                                        int which) {
	                                    // TODO Auto-generated method stub
	 
	                                }
	                            }).show();
	        }
	        return true;
	    }
	
	

}
