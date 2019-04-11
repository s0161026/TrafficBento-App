package com.example.xmlpullparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class BusData extends Activity {
	Thread busParse;
	private Toast myToast;
	private ImageView image;
	private Context context;
	private ImageButton button1;
	private ImageButton button2;
	Spinner spinner;
	Spinner spinner2;
	ArrayAdapter adapter;
	ArrayAdapter adapter2;
	int[] p= {118,16,160,224,257,311,342,424};
	int[] a = new int[8];
	int[] c =  {R.drawable.gray, R.drawable.blue, R.drawable.green, R.drawable.red};
	int[] s = {4,4,2,2,1,1,2,2};
	int[] n = {21,20,24,19,14,16,12,7};
	private String[] road = new String[] {"��߸�","���q��","�T����","���M��"};
	private String[] drct = new String[]{"�F�V","��V"};
	private String[][] type2 = new String[][]{{"�F�V","��V"},{"�F�V","��V"},{"�_�V","�n�V"},{"�_�V","�n�V"}};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_traffic);
		
		/*ImageView iv = (ImageView)this.findViewById(R.id.imageView2);
		iv.setImageResource( R.drawable.road );
		Animation bg = new TranslateAnimation( 100, -100, 0, 0 );
		bg.setDuration( 6000 );
		bg.setRepeatCount( -1 );
		iv.setAnimation(bg);
		bg.startNow();*/
	    
		/*button1 =(ImageButton) findViewById(R.id.imageButton4);
		button1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = getIntent();
			    finish();
			    startActivity(intent);
			}
		});*/
		
		button2 =(ImageButton) findViewById(R.id.imageButton2);
		button2.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				toLegend();
			}
		});
		
		context = this;
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		image = (ImageView)findViewById(R.id.gray);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,road);
		adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,drct);
		
		myToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		// XML���������U���A�����ާ@�@�w�n�b�s�������
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO �۰ʲ��ͪ���k Stub
				try {
					// XMLŪ�����|�o��@��ArrayList
					final ArrayList<BusObj> data = parse();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							calculateData(data);
						}
					});
				} catch (URISyntaxException e) {
					// TODO �۰ʲ��ͪ� catch �϶�
					e.printStackTrace();
				}
			}
		}).start();
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(slistener);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(listener);
        
        
     }
	
	
	
	
	
	public void Refresh(View view){          //refresh is onClick name given to the button
		Intent i = new Intent(BusData.this, BusData.class);  //your class
	    startActivity(i);
	    finish();
	}
	
	public void goBack(View view){         
		
	    finish();
	}
	
	public void toLegend(){
		setContentView(R.layout.legend);
		ImageButton button01 = (ImageButton)findViewById(R.id.imageButton1);
        button01.setOnClickListener(new ImageButton.OnClickListener(){
        	public void onClick(View v) {
            	Intent i = new Intent(BusData.this, BusData.class);  //your class
        	    startActivity(i);
        	    finish();
            }           
        });
	}

    
    
	private Spinner.OnItemSelectedListener slistener = new Spinner.OnItemSelectedListener(){
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
			int pos = spinner.getSelectedItemPosition();
			adapter2 = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, type2[pos]);
			spinner2.setAdapter(adapter2);
		}
		public void onNothingSelected(AdapterView<?> arg0) {
			myToast.setText("�п�ܹD��");
			myToast.show();
			image.setImageResource(c[3]);
			}
	};
	
	private Spinner.OnItemSelectedListener listener = new Spinner.OnItemSelectedListener(){
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
			int Pos = spinner.getSelectedItemPosition();
			int cvt = Pos*2 + arg2;
			//try {
			//if(count==8 || a[cvt]==0) display=false;
			if(count[cvt]==1){
				Utility.showToast(BusData.this, "�w�L������ƥi�Ѥ��R");
				image.setImageResource(c[0]);
			} else{
				if (a[cvt]-n[cvt]>0 && Math.abs(a[cvt]-n[cvt]) >s[cvt]){
					image.setImageResource(c[3]);
				}
				else if(a[cvt]-n[cvt]<0 && Math.abs(a[cvt]-n[cvt]) >s[cvt]){
					image.setImageResource(c[1]);
				}
				else{
					image.setImageResource(c[2]);
				}
			}
				
			/*} catch (Exception ex) {
				myToast.setText("�w�L������ƥi�Ѥ��R�A���n�N��I");
				myToast.show();
				image.setImageResource(c[3]);
			}*/
			
		}
		public void onNothingSelected(AdapterView<?> arg0) {
			myToast.setText("�п�ܹD��");
			myToast.show();
			image.setImageResource(c[3]);
			}
	};
	
	int[] count = new int[8];
	//boolean display = true;
	
	public void calculateData(ArrayList<BusObj> data){
		
		
			for (int i=0;i<=7;i++){
				
				if(data.get(p[i]).getcomeTime().isEmpty()){
					count[i]=1;
					continue;
				}
					
				
				int hour =
						Integer.parseInt(data.get(p[i]+13).getcomeTime().substring(0,2))-Integer.parseInt(data.get(p[i]).getcomeTime().substring(0,2));
				int min =
						Integer.parseInt(data.get(p[i]+13).getcomeTime().substring(3,5))-Integer.parseInt(data.get(p[i]).getcomeTime().substring(3,5));
				if(hour == 1 ){
					
					a[i] = min + 60;
					
				}
				else if(hour == 0 ) {
					a[i] = min;
				}
			}
	       /* myToast.setText("���A����: "+count[0]);
			myToast.show();*/
		
		//int gb1 = Integer.parseInt(data.get(5).getGoBack());
		
		
	}
	
	public InputStream getUrlData(String url) throws URISyntaxException,
		ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet method = new HttpGet(new URI(url));
		HttpResponse res = client.execute(method);
		return res.getEntity().getContent();
	}
	
	public ArrayList<BusObj> parse() throws URISyntaxException {
		String tagName = null;
		ArrayList<BusObj> myData = new ArrayList<BusObj>();

		try {
			// �w�q�u�t XmlPullParserFactory
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			// �w�q�ѪR�� XmlPullParser
			XmlPullParser parser = factory.newPullParser();
			// ���xml��J�ƾ�
			parser.setInput(new InputStreamReader(
					getUrlData("http://citybus.taichung.gov.tw/xmlbus/GetEstimateTime.xml?routeIds=53,81,1,6")));
			// �}�l�ѪR�ƥ�
			int eventType = parser.getEventType();
			// �B�z�ƥ�A���I����ɵ����N�@���B�z
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// �]��XmlPullParser�w���w�q�F�@���R�A�`�q�A�ҥH�o�̥i�H��switch
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					// ����e���Ұ_�ӦW�r
					tagName = parser.getName();
					if (tagName.equals("EstimateTime")) {
						myData.add(new BusObj(parser.getAttributeValue(null,
								"GoBack"), parser.getAttributeValue(null,
								"comeTime"), parser.getAttributeValue(null,
								"IVRNO")));
					}

					break;
				case XmlPullParser.TEXT:

					break;
				case XmlPullParser.END_TAG:

					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				}
				// �O�ѤF�@�w�n��next��k�B�z�U�@�Өƥ�A�ѤF�����G�N���L�a����#_#
				eventType = parser.next();
			}
			return myData;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			myToast.setText("���A�����`");
			myToast.show();
		} catch (IOException e) {
			e.printStackTrace();
			myToast.setText("���A�����`");
			myToast.show();
		} catch (Exception e) {
			myToast.setText("���A�����`");
			myToast.show();
		}
		return myData;
	}
	
	protected void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	   
	    if (busParse != null) {
	        if (!busParse.isInterrupted()) {
	            busParse.interrupt();
	        }
	    }
	}
	
}
