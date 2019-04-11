package com.example.xmlpullparser;

import java.util.List;
import java.util.Locale;

import com.example.xmlpullparser.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Location_GPS extends Activity implements LocationListener {
	
	private boolean getService = false;		//�O�_�w�}�ҩw��A��
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_gps);
        
        testLocationProvider();		//�ˬd�w��A��
    }
    
    public void goBack(View view){         
		
	    finish();
	}
    
    private void testLocationProvider() {
        //���o�t�Ωw��A��
        LocationManager status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
        if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        	//�p�GGPS�κ����w��}�ҡA�I�slocationServiceInitial()��s��m
        	getService = true;	//�T�{�}�ҩw��A��
        	locationServiceInitial();
        } else {
        	Toast.makeText(this, "�ж}�ҩw��A��", Toast.LENGTH_LONG).show();
        	startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//�}�ҳ]�w����
        }
    }
    
    private LocationManager lms;
    private String bestProvider = LocationManager.GPS_PROVIDER;	//�̨θ�T���Ѫ�
    private void locationServiceInitial() {
    	lms = (LocationManager) getSystemService(LOCATION_SERVICE);	//���o�t�Ωw��A��
    	Criteria criteria = new Criteria();	//��T���Ѫ̿���з�
    	bestProvider = lms.getBestProvider(criteria, true);	//��ܺ�ǫ׳̰������Ѫ�
    	Location location = lms.getLastKnownLocation(bestProvider);
    	getLocation(location);
    }

    private void getLocation(Location location) {	//�N�w���T��ܦb�e����
    	if(location != null) {
    		TextView longitude_txt = (TextView) findViewById(R.id.longitude);
    		TextView latitude_txt = (TextView) findViewById(R.id.latitude);
    		TextView address_txt = (TextView) findViewById(R.id.address);
    		TextView address_match = (TextView) findViewById(R.id.match);
   		
    		Double longitude = location.getLongitude();	//���o�g��
    		Double latitude = location.getLatitude();	//���o�n��
    		
    		longitude_txt.setText(String.valueOf(longitude));
    		latitude_txt.setText(String.valueOf(latitude));
    		address_txt.setText(getAddressByLocation(location));
    		if(matchAddress(longitude, latitude)){
    			address_match.setText("���b�A�Ƚd��");
    		} else {
    			address_match.setText("�ŦX�I�Шϥθ��p���R");
    			Button button = (Button)findViewById(R.id.button1);
    			button.setVisibility(View.VISIBLE);
    	        button.setOnClickListener(new ImageButton.OnClickListener(){
    	        	public void onClick(View v) {
    	            	Intent i = new Intent(Location_GPS.this, BusData.class);  //your class
    	        	    startActivity(i);
    	        	    finish();
    	            }           
    	        });
    		}
    	}
    	else {
    		Toast.makeText(this, "�L�k�w��y��", Toast.LENGTH_LONG).show();
    	}
    }
    
    public boolean matchAddress(double lo, double la) {
    	double[] alo = {120.637898, 120.697312};
    	double[] ala = {24.149128, 24.512438};
    	if(lo < alo[0] || lo > alo[1]){
    		return true;
    	} else if(la < ala[0] || la > ala[1]){
    		return true;
    	} else{
    		return false;
    	}
    }
    
   
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(getService) {
			//Toast.makeText(this, "���o�w���T���K", Toast.LENGTH_SHORT).show();
			lms.requestLocationUpdates(bestProvider, 1000, 1, this);
			//�A�ȴ��Ѫ̡B��s�W�v60000�@��=1�����B�̵u�Z���B�a�I���ܮɩI�s����
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(getService) {
			lms.removeUpdates(this);	//���}�����ɰ����s
		}
	}

	@Override
	protected void onRestart() {	//�q�䥦�������^��
		// TODO Auto-generated method stub
		super.onRestart();
		testLocationProvider();
	}

	@Override
	public void onLocationChanged(Location location) {	//��a�I���ܮ�
		// TODO Auto-generated method stub
		getLocation(location);
	}

	@Override
	public void onProviderDisabled(String arg0) {	//��GPS�κ����w��\��������
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String arg0) {	//��GPS�κ����w��\��}��
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {	//�w�쪬�A����
		// TODO Auto-generated method stub
	}
	public String getAddressByLocation(Location location) {
		String returnAddress = "";
		try {
				if (location != null) {
		    		Double longitude = location.getLongitude();	//���o�g��
		    		Double latitude = location.getLatitude();	//���o�n��

		    		//�إ�Geocoder����: Android 8 �H�W�Һþ������|����
		    		Geocoder gc = new Geocoder(this, Locale.TRADITIONAL_CHINESE);	//�a��:�x�W
		    		//�۸g�n�ר��o�a�}
		    		List<Address> lstAddress = gc.getFromLocation(latitude, longitude, 1);

		    	//	if (!Geocoder.isPresent()){ //Since: API Level 9
		    	//		returnAddress = "Sorry! Geocoder service not Present.";
		    	//	}
		    		returnAddress = lstAddress.get(0).getAddressLine(0);
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return returnAddress;
	}
}

