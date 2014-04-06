package com.Visdrotech.VehiclePoolingSystem.Activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ProgressBar;

public class Activity_Splash extends Activity {
	ProgressBar progressBar;
	private int progressBarStatus = 0;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);

		

		progressBar = (ProgressBar) findViewById(R.id.pbarLaunch);
		
		Thread timer = new Thread(){
	        public void run(){
	            try{
	                while(progressBarStatus<100){
		                sleep(20);
	                    progressBar.setProgress(progressBarStatus);
	                    progressBarStatus+= 2;
	                }
	                if(progressBarStatus==100)
	                {
	                	Intent OpenStarting = new Intent("com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_HOME");
						startActivity(OpenStarting);	
	                }
	            }catch(InterruptedException e){
	                e.printStackTrace();
	            }
	        }
	    };
	    timer.start();
		
		
	}

	@Override
	protected void onPause() {
		this.finish();
		super.onPause();
	}
		

}
