package com.Visdrotech.VehiclePoolingSystem.Activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.Visdrotech.VehiclePoolingSystem.Core.User;
import com.Visdrotech.VehiclePoolingSystem.Core.VehicleInfo;
import com.Visdrotech.VehiclePoolingSystem.Network.HttpPostReq;
import com.Visdrotech.VehiclePoolingSystem.Utils.Common_Utilities;
import com.Visdrotech.VehiclePoolingSystem.Utils.MyAdapter_TravellerInfo_Owner;

public class Activity_CommuterInfo_Owner extends Activity{

	SharedPreferences SharedPrefs,sharedPrefesTravellerSelectDetails;
	User user;
	ListView lv; 
	MyAdapter_TravellerInfo_Owner adapter;
	ArrayList<User> travellerList;
	ArrayList<String> meetingPointList,timeList,fareList;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("TAG","Activity_Owner");
		setContentView(R.layout.layout_view_commuter_info_owner);
		
		SharedPrefs = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsUserDetails, 0);
		sharedPrefesTravellerSelectDetails = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsTravellerSelectDetails, 0);
		
		
		if(!sharedPrefesTravellerSelectDetails.getBoolean(Common_Utilities.SharedPrefsVariableTravelerSelected, false))
		{
			Toast.makeText(Activity_CommuterInfo_Owner.this, "You have not selected any traveller. An error occured. Please Clear data for the App from Settings and try Again.", Toast.LENGTH_LONG).show();
			Activity_CommuterInfo_Owner.this.finish();
		}
		
		
		
		lv = (ListView) findViewById(R.id.lvTravellers);
		
		travellerList = new ArrayList<User>();
		meetingPointList = new ArrayList<String>();
		fareList = new ArrayList<String>();
		timeList = new ArrayList<String>();
		
		new postLogin().execute(SharedPrefs.getString(Common_Utilities.UserDetailsVariableUniversityRegNum, null));
		
	}
	
	public class postLogin extends AsyncTask<String, String, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			Log.d("Down", "On PreExecute : DownloadRestaurant");

			dialog = new ProgressDialog(Activity_CommuterInfo_Owner.this);
			Log.d("Down", "DD");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Loading...\nPlease Wait...");
			dialog.setCanceledOnTouchOutside(false);	
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

				@Override
				public void onCancel(DialogInterface dialog) {
					Activity_CommuterInfo_Owner.this.finish();
				}});
			dialog.show();
		}
		
		@Override
		protected String doInBackground(String... arg0) {

			 return HttpPostReq.getSelectedTravellerList(arg0[0]);
		}

		@SuppressWarnings({ "deprecation", "unused" })
		@Override
		protected void onPostExecute(String result) {

			dialog.dismiss();

			if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_SUCCESS)) {

				StringTokenizer tokenizer = new StringTokenizer(result, "|");
				tokenizer.nextToken();
				for(int i = 0 ; i < (tokenizer.countTokens()-1)/7 ; i++)
				{	
				User tempUser = new User();
				tempUser.setUniversityRegNum(tokenizer.nextToken());
				tempUser.setName(tokenizer.nextToken());
				tempUser.setPhoneNumber(tokenizer.nextToken());
				tempUser.setEmail(tokenizer.nextToken());
				timeList.add(tokenizer.nextToken());
				tempUser.setDefaultSource(tokenizer.nextToken());
				travellerList.add(tempUser);
				fareList.add(tokenizer.nextToken());
				}
				adapter = new MyAdapter_TravellerInfo_Owner(Activity_CommuterInfo_Owner.this, R.layout.listview_travel_info_owner_view, R.id.tvNameOwnerInfo, travellerList	,  timeList , fareList);
				lv.setAdapter(adapter);
			} else if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_ERROR)){
				Toast.makeText(Activity_CommuterInfo_Owner.this,
						"Unsuccessful!! Error Occured!!",
						Toast.LENGTH_SHORT).show();
				Activity_CommuterInfo_Owner.this.finish();
			}else if (result.startsWith("NIL")){
				Toast.makeText(Activity_CommuterInfo_Owner.this,
						"Unsuccessful!! Error Occured!!",
						Toast.LENGTH_SHORT).show();
				Activity_CommuterInfo_Owner.this.finish();
			}

		}
	}
	
}