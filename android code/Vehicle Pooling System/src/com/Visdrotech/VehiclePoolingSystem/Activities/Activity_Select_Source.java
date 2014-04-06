package com.Visdrotech.VehiclePoolingSystem.Activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import com.Visdrotech.VehiclePoolingSystem.Core.VehicleInfo;
import com.Visdrotech.VehiclePoolingSystem.Network.HttpPostReq;
import com.Visdrotech.VehiclePoolingSystem.Utils.Common_Utilities;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

public class Activity_Select_Source extends Activity implements OnClickListener {

	Spinner spinnerSource, spinnerSelectVehicle;
	SpinnerAdapter spinnerAdapterSources, spinnerAdapterSelectVehicle;
	ArrayList<String> sourcesList, vehicleNumList;
	ArrayList<Double> distanceList, vehicleDoubleList;
	LinearLayout bConfirm;
	TimePicker timePicker;
	EditText etNumTravellers;
	SharedPreferences SharedPrefs,sharedPrefsTravellerSelectDetails;
	LinearLayout lVehicleInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_select_source);

		SharedPrefs = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsUserDetails, 0);
		sharedPrefsTravellerSelectDetails = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsTravellerSelectDetails, 0);
		bConfirm = (LinearLayout) findViewById(R.id.bConfirm);
		bConfirm.setOnClickListener(this);

		etNumTravellers = (EditText) findViewById(R.id.etNumOfTravellers);
		timePicker = (TimePicker) findViewById(R.id.tpSelectSource);
		timePicker.setIs24HourView(true);
		spinnerSource = (Spinner) findViewById(R.id.spinner_selectSource);

		lVehicleInfo = (LinearLayout) findViewById(R.id.lSpinnerVehicleSelect);
		sourcesList = new ArrayList<String>();
		distanceList = new ArrayList<Double>();

		spinnerSelectVehicle = (Spinner) findViewById(R.id.spinner_selectVehicle);
		vehicleNumList = new ArrayList<String>();
		vehicleDoubleList = new ArrayList<Double>();

		if (SharedPrefs.getString(Common_Utilities.UserDetailsVariableCategory,
				null).equals("Traveller")) {
			lVehicleInfo.setVisibility(View.GONE);
			etNumTravellers.setVisibility(View.GONE);
		}

		new postSelectSource().execute("String");

	}

	@Override
	protected void onPause() {
		super.onPause();
		this.finish();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bConfirm) {

			if (SharedPrefs.getString(
					Common_Utilities.UserDetailsVariableCategory, null).equals(
					"Owner")) {
				Intent intentSelectTravellers = new Intent(
						"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_SELECT_TRAVELLER");
				Bundle b = new Bundle();
				b.putInt("NumTravellers",
						Integer.parseInt(etNumTravellers.getText().toString()));

				b.putString("Time", timePicker.getCurrentHour() + ":"
						+ timePicker.getCurrentMinute());
				
				
				b.putString("Source", sourcesList.get(spinnerSource.getSelectedItemPosition()));
				b.putDouble("SourceDistance", distanceList.get(spinnerSource.getSelectedItemPosition()));
				
				b.putString("VehicleNum", vehicleNumList.get(spinnerSelectVehicle.getSelectedItemPosition()));
				
				b.putDouble("VehicleMilage", vehicleDoubleList.get(spinnerSelectVehicle.getSelectedItemPosition()));
				
				intentSelectTravellers.putExtras(b);
				startActivity(intentSelectTravellers);
			} else if (SharedPrefs.getString(
					Common_Utilities.UserDetailsVariableCategory, null).equals(
					"Traveller")) {
				new postTravellerDetails()
						.execute(
								SharedPrefs
										.getString(
												Common_Utilities.UserDetailsVariableUniversityRegNum,
												null),
								timePicker.getCurrentHour() + ":"
										+ timePicker.getCurrentMinute(),
								sourcesList.get(spinnerSource
										.getSelectedItemPosition()));
			}
		}
	}

	public class postSelectSource extends AsyncTask<String, String, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			Log.d("Down", "On PreExecute : DownloadRestaurant");
			dialog = new ProgressDialog(Activity_Select_Source.this);
			Log.d("Down", "DD");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Loading....\n Please Wait");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

				@Override
				public void onCancel(DialogInterface dialog) {
					Activity_Select_Source.this.finish();
				}});
			dialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {

			Log.d("TAG", "DoInBack");
			String result = HttpPostReq.getSourcesInfo();

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_ERROR)) {
				Toast.makeText(Activity_Select_Source.this, "Error Occured",
						Toast.LENGTH_SHORT).show();
				Activity_Select_Source.this.finish();
			} else {

				StringTokenizer tokenizer = new StringTokenizer(result, "|");
				Log.d("TAG", "" + tokenizer.countTokens());
				while (tokenizer.hasMoreTokens()) {
					String s = tokenizer.nextToken();
					String d = tokenizer.nextToken();
					Log.d("TAG", s + " : " + d);
					sourcesList.add(s);
					distanceList.add(Double.parseDouble(d));
					if (s.equals("Yamuna Vihar"))
						break;
				}

				spinnerAdapterSources = new ArrayAdapter<String>(
						Activity_Select_Source.this,
						R.layout.listview_spinner_gender_pref, R.id.textView1,
						sourcesList);
				spinnerSource.setAdapter(spinnerAdapterSources);
				spinnerSource
						.setSelection(sourcesList.indexOf(SharedPrefs
								.getString(
										Common_Utilities.UserDetailsVariableDefaultSource,
										sourcesList.get(6))));

				if (SharedPrefs.getString(
						Common_Utilities.UserDetailsVariableCategory, null)
						.equals("Owner")) {
					VehicleInfo v1 = new VehicleInfo();
					VehicleInfo v2 = new VehicleInfo();
					VehicleInfo v3 = new VehicleInfo();

					v1.setVehicleMilage(SharedPrefs
							.getFloat(
									Common_Utilities.VehicleDetailsVariableVehicleMileage1,
									0));
					v1.setVehicleRegNum(SharedPrefs
							.getString(
									Common_Utilities.VehicleDetailsVariableVehicleNumber1,
									null));
					vehicleNumList.add(v1.getVehicleRegNum());
					vehicleDoubleList.add(v1.getVehicleMilage());
					if (!SharedPrefs
							.getString(
									Common_Utilities.VehicleDetailsVariableVehicleNumber2,
									null).equals("NIL")
							|| SharedPrefs
									.getFloat(
											Common_Utilities.VehicleDetailsVariableVehicleMileage2,
											0) != 0) {
						v2.setVehicleMilage(SharedPrefs
								.getFloat(
										Common_Utilities.VehicleDetailsVariableVehicleMileage2,
										0));
						v2.setVehicleRegNum(SharedPrefs
								.getString(
										Common_Utilities.VehicleDetailsVariableVehicleNumber2,
										null));
						vehicleNumList.add(v2.getVehicleRegNum());
						vehicleDoubleList.add(v2.getVehicleMilage());
					}
					if (!SharedPrefs
							.getString(
									Common_Utilities.VehicleDetailsVariableVehicleNumber3,
									null).equals("NIL")
							|| SharedPrefs
									.getFloat(
											Common_Utilities.VehicleDetailsVariableVehicleMileage3,
											0) != 0) {
						v3.setVehicleMilage(SharedPrefs
								.getFloat(
										Common_Utilities.VehicleDetailsVariableVehicleMileage3,
										0));
						v3.setVehicleRegNum(SharedPrefs
								.getString(
										Common_Utilities.VehicleDetailsVariableVehicleNumber3,
										null));
						vehicleNumList.add(v3.getVehicleRegNum());
						vehicleDoubleList.add(v3.getVehicleMilage());
					}
					spinnerAdapterSelectVehicle = new ArrayAdapter<String>(
							Activity_Select_Source.this,
							R.layout.listview_spinner_gender_pref,
							R.id.textView1, vehicleNumList);
					spinnerSelectVehicle
							.setAdapter(spinnerAdapterSelectVehicle);
					spinnerSelectVehicle.setSelection(0);
				}
			}
			dialog.dismiss();

		}
	}

	public class postTravellerDetails extends AsyncTask<String, String, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			Log.d("Down", "On PreExecute : DownloadRestaurant");
			dialog = new ProgressDialog(Activity_Select_Source.this);
			Log.d("Down", "DD");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Loading....\n Please Wait");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

				@Override
				public void onCancel(DialogInterface dialog) {
					Activity_Select_Source.this.finish();
				}});
			dialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {

			Log.d("TAG", "DoInBack");
			String result = HttpPostReq.sendTravellerSourceAndTime(arg0[0],
					arg0[1], arg0[2]);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_ERROR)) {
				Toast.makeText(Activity_Select_Source.this, "Error Occured",
						Toast.LENGTH_SHORT).show();
				Activity_Select_Source.this.finish();
			} else if (result
					.startsWith(Common_Utilities.CODE_USER_VALIDATION_SUCCESS)) {
				
				
				
				SharedPreferences.Editor editor = sharedPrefsTravellerSelectDetails.edit();
				editor.putBoolean(Common_Utilities.SharedPrefsVariableTravelerSelected, true);
				editor.putInt("TimeHour", Calendar.HOUR_OF_DAY);
				editor.putString("Date", (Calendar.DAY_OF_MONTH+"/"+Calendar.MONTH+"/"+Calendar.YEAR));
				editor.commit();

				Intent intentCommuterInfoTraveller = new Intent(
						"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_COMMUTERINFO_TRAVELLER");
				startActivity(intentCommuterInfoTraveller);
				Activity_Select_Source.this.finish();
			}
			dialog.dismiss();

		}
	}
}
