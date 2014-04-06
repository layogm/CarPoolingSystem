package com.Visdrotech.VehiclePoolingSystem.Activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.Visdrotech.VehiclePoolingSystem.Core.User;
import com.Visdrotech.VehiclePoolingSystem.Core.VehicleInfo;
import com.Visdrotech.VehiclePoolingSystem.Network.HttpPostReq;
import com.Visdrotech.VehiclePoolingSystem.Utils.Common_Utilities;
import com.Visdrotech.VehiclePoolingSystem.Utils.MyAdapter;

public class Activity_Select_Traveller extends Activity implements OnClickListener{
	
	ListView listView;
	MyAdapter myAdapter;
	ArrayList<User> TravellerList;
	LinearLayout bConfirm;
	EditText etNumOfTravvellers;
	TimePicker tp;
	int numTravellers;
	String time,vehicleNum,source;
	double vehicleMilage,ownerSourceDistance;
	ArrayList<User> selectedTravellerList;
	HashMap<String, Double> travellersDistanceList;
	HashMap<String, Double> selectedTravellersFareList;
	HashMap<String, String> travellersTimeList;
	SharedPreferences sharedPrefs,SharedPrefsTravllerSelectDetails;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.layout_select_traveller);
	
	
	numTravellers = getIntent().getExtras().getInt("NumTravellers");
	time = getIntent().getExtras().getString("Time");
	vehicleNum =  getIntent().getExtras().getString("VehicleNum");
	vehicleMilage = getIntent().getExtras().getDouble("VehicleMilage");
	ownerSourceDistance = getIntent().getExtras().getDouble("SourceDistance");
	source =   getIntent().getExtras().getString("Source");
	
	
	travellersDistanceList = new HashMap<String, Double>();
	selectedTravellersFareList = new HashMap<String, Double>();
	travellersTimeList = new HashMap<String, String>();
	TravellerList = new ArrayList<User>();
	sharedPrefs = getSharedPreferences(Common_Utilities.fileNameSharedPrefsUserDetails, 0);
	SharedPrefsTravllerSelectDetails = getSharedPreferences(Common_Utilities.fileNameSharedPrefsTravellerSelectDetails, 0);
	
	bConfirm = (LinearLayout) findViewById(R.id.bConfirm);
	bConfirm.setOnClickListener(this);
	
	listView = (ListView) findViewById(R.id.lvSelectTraveller);
	
	
	new postRecieveTravellerList().execute(sharedPrefs.getString(Common_Utilities.UserDetailsVariableUniversityRegNum, null),source,time,sharedPrefs.getString(Common_Utilities.UserDetailsVariableGenderPrefs, null));
	
	
	
	
	
	
	
}


@Override
public void onClick(View v) {
if(v.getId() == R.id.bConfirm)
{
	selectedTravellerList = ((MyAdapter)listView.getAdapter()).getSelectedTravellers();
	
	if(selectedTravellerList.size()>numTravellers)
	{
		Toast.makeText(Activity_Select_Traveller.this, "You can select maximum of "+numTravellers+" travellers.", Toast.LENGTH_SHORT).show();
	}
	else{
		
		if(checkGenderPreferences())
		{
		calculateFares();
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		for(int i = 0 ; i < selectedTravellerList.size() ; i++)
		{
			sb1.append(selectedTravellerList.get(i).getUniversityRegNum()+"|");
			sb2.append(selectedTravellersFareList.get(selectedTravellerList.get(i).getUniversityRegNum())+"|");
		}
		
		Log.d("TAG1", sb1.toString());
		Log.d("TAG2", sb2.toString());
		
		new postSendSelectedTravellers().execute(sharedPrefs.getString(Common_Utilities.UserDetailsVariableUniversityRegNum, null),sb1.toString(),sb2.toString(),vehicleNum);

		}
		else
		{
			Toast.makeText(Activity_Select_Traveller.this, "Cannnot send the details... Selected travellers does not satisfy the preferences of all the commuters.", Toast.LENGTH_SHORT).show();
		}
		
	}
}
}




private boolean checkGenderPreferences() {

	int minMale = 0,minFemale = 0;
	for(int i = 0 ; i < selectedTravellerList.size() ; i++)
	{
		if(selectedTravellerList.get(i).getGenderPrefs().equals("At least 1 from same gender"))
		{
			if(selectedTravellerList.get(i).getGender().equals("Male"))
			{
				minMale = 2 ;
			}
			else
			{
				minFemale = 2;
			}
		}
		else if(selectedTravellerList.get(i).getGenderPrefs().equals("All from same gender"))
		{
			if(sharedPrefs.getString(Common_Utilities.UserDetailsVariableGender, null).equals(selectedTravellerList.get(i).getGender()))
				{
				return false;
				}
		
			if(selectedTravellerList.get(i).getGender().equals("Male"))
			{
					
				minMale = 100 ;
			}
			else
			{
				minFemale = 100;
			}
		}
	}
	
	int selectedMales = 0 , selectedFemales = 0;
	for(int i = 0 ; i < selectedTravellerList.size() ; i++)
	{
			if(selectedTravellerList.get(i).getGender().equals("Male"))
			{
				selectedMales++ ;
			}
			else
			{
				selectedFemales++;
			}
	}
	
	
	if(selectedMales<minMale||selectedFemales<minFemale)
	return false;
	else
	return true;
}


private void calculateFares() {
	double perKmCost = 70/vehicleMilage;
	int totalNumberOfTravellers = selectedTravellerList.size()+1;
	
	ArrayList<Double> selectedTravllersDistanceList = new ArrayList<Double>();

	for(int i = 0 ; i < selectedTravellerList.size() ; i++)
	{
		selectedTravllersDistanceList.add(travellersDistanceList.get(selectedTravellerList.get(i).getDefaultSource()));
	}
	
	
	for(int i = 0 ; i < selectedTravellerList.size() ; i++)
	{
		for(int j = 0 ; j < i ; j++)
		{
			if(selectedTravllersDistanceList.get(j)>selectedTravllersDistanceList.get(j+1))
			{
				double d2  = selectedTravllersDistanceList.get(j);
				double d  = selectedTravllersDistanceList.get(j+1);

				selectedTravllersDistanceList.remove(j);
				selectedTravllersDistanceList.remove(j);
				selectedTravllersDistanceList.add(j,d2);
				selectedTravllersDistanceList.add(j,d);
				
				User u2   = selectedTravellerList.get(j);
				User u = selectedTravellerList.get(j+1);
				
				selectedTravellerList.remove(j);
				selectedTravellerList.remove(j);
				selectedTravellerList.add(j,u2);
				selectedTravellerList.add(j,u);
			}
		}
	}
	
	for(int i = 0 ; i < selectedTravellerList.size() ; i++)
	{
		Log.d("TAG3",selectedTravellerList.get(i).getDefaultSource()+" : "+selectedTravllersDistanceList.get(i));
	}
	
	for(int i = 0 ; i < selectedTravellerList.size() ; i++)
	{
		if(i > 0)
		{
			selectedTravellersFareList.put(selectedTravellerList.get(i).getUniversityRegNum(),((selectedTravllersDistanceList.get(i)*perKmCost)/(totalNumberOfTravellers-i) ) - selectedTravellersFareList.get(selectedTravellerList.get(i-1).getUniversityRegNum()));
			Log.d("TAG"," : "+selectedTravellersFareList.get(selectedTravellerList.get(i).getUniversityRegNum()));
		}
		else 
		{
			selectedTravellersFareList.put(selectedTravellerList.get(i).getUniversityRegNum(),(selectedTravllersDistanceList.get(i)*perKmCost)/(totalNumberOfTravellers-i));
			Log.d("TAG"," : "+selectedTravellersFareList.get(selectedTravellerList.get(i).getUniversityRegNum()));
		}
	}
	
	
}



public class postRecieveTravellerList extends AsyncTask<String, String, String> {

	ProgressDialog dialog;

	@Override
	protected void onPreExecute() {
		Log.d("Down", "On PreExecute : DownloadRestaurant");

		dialog = new ProgressDialog(Activity_Select_Traveller.this);
		Log.d("Down", "DD");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("Loading...\nPlease Wait...");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				Activity_Select_Traveller.this.finish();
			}});
		dialog.show();

	}

	@Override
	protected String doInBackground(String... arg0) {

		 return HttpPostReq.getTravellerListToSelect(arg0[0], arg0[1], arg0[2], arg0[3]);

		 
	}

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	protected void onPostExecute(String result) {
		
		dialog.dismiss();

		if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_SUCCESS)) {
			
			
			StringTokenizer tokenizer = new StringTokenizer(result,"|");
			tokenizer.nextToken();
			
			
			Log.d("TAG",""+(tokenizer.countTokens()-1)/7);
			
			for(int i = 0 ; i < (tokenizer.countTokens()-1)/7 ; i++ )
			{
				
				User tempTraveller = new User();
				tempTraveller.setUniversityRegNum(tokenizer.nextToken());
				tempTraveller.setName(tokenizer.nextToken());
				tempTraveller.setGender(tokenizer.nextToken());
				tempTraveller.setGenderPrefs(tokenizer.nextToken());
				tempTraveller.setDefaultSource(tokenizer.nextToken());
				travellersDistanceList.put(tempTraveller.getDefaultSource(),Double.parseDouble( tokenizer.nextToken()));
				travellersTimeList.put(tempTraveller.getDefaultSource(),tokenizer.nextToken());
				tempTraveller.setCategory("Traveller");
				tempTraveller.setEmail("Email");
				tempTraveller.setPassword("Password");
				tempTraveller.setPhoneNumber("00000000000");
				tempTraveller.setSecAns("Ans");
				tempTraveller.setSecQues("Ques");
				TravellerList.add(tempTraveller);
			}
			
			
			myAdapter = new MyAdapter(Activity_Select_Traveller.this, R.layout.listview_spinner_gender_pref, R.id.textView1, TravellerList , travellersTimeList);
			listView.setAdapter(myAdapter);
			
			Toast.makeText(Activity_Select_Traveller.this,
					"Successful!! ", Toast.LENGTH_SHORT)
					.show();
			
		} else if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_ERROR)){
			Toast.makeText(Activity_Select_Traveller.this,
					"Unsuccessful!! Error Occured!!",
					Toast.LENGTH_SHORT).show();
		}

	}
}















public class postSendSelectedTravellers extends AsyncTask<String, String, String> {

	ProgressDialog dialog;

	@Override
	protected void onPreExecute() {
		Log.d("Down", "On PreExecute : DownloadRestaurant");

		dialog = new ProgressDialog(Activity_Select_Traveller.this);
		Log.d("Down", "DD");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("Sending Details...\nPlease Wait...");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				Activity_Select_Traveller.this.finish();
			}});
		dialog.show();

	}

	@Override
	protected String doInBackground(String... arg0) {
		 return HttpPostReq.sendSelectedTravellers(arg0[0], arg0[1], arg0[2],arg0[3]);
	}

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	protected void onPostExecute(String result) {
		dialog.dismiss();

		if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_SUCCESS)) {
			Toast.makeText(Activity_Select_Traveller.this,
					"Successful!! ", Toast.LENGTH_SHORT)
					.show();
			SharedPreferences.Editor editor = SharedPrefsTravllerSelectDetails.edit();
			editor.putBoolean(Common_Utilities.SharedPrefsVariableTravelerSelected, true);
			editor.putInt("TimeHour", Calendar.HOUR_OF_DAY);
			editor.putString("Date", (Calendar.DAY_OF_MONTH+"/"+Calendar.MONTH+"/"+Calendar.YEAR));
			editor.commit();
			
			Activity_Select_Traveller.this.finish();
			
			Intent intentSelectTravellers = new Intent(
					"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_COMMUTERINFO_OWNER");
			startActivity(intentSelectTravellers);
			
			
			
			
			
			
		} else if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_ERROR)){
			Toast.makeText(Activity_Select_Traveller.this,
					"Unsuccessful!! Error Occured!!",
					Toast.LENGTH_SHORT).show();
		}

	}
}


}
