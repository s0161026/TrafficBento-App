package com.example.xmlpullparser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class SplashActivity extends Activity{
	/*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_shot);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent().setClass(SplashActivity.this, MainActivity.class));
            }
        }, 3000);
    }*/
	@Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.logo_shot);
        Animation anim = null;  
        anim = new RotateAnimation(+210.0f,+360.0f);
        //Animation am = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());  
        anim.setDuration(1500);  
        findViewById(R.id.logo).startAnimation(anim);  

        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 3000);//3¬í¸õÂà

    }

    private static final int GOTO_MAIN_ACTIVITY = 0;

    private Handler mHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {

 

            switch (msg.what) {

                case GOTO_MAIN_ACTIVITY:

                    Intent intent = new Intent();

                    intent.setClass(SplashActivity.this, MainActivity.class);

                    startActivity(intent);

                    finish();

                    break;

 

                default:

                    break;

            }

        };

    };
}
