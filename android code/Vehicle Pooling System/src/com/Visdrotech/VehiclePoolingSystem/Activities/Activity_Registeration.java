package com.Visdrotech.VehiclePoolingSystem.Activities;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.Visdrotech.VehiclePoolingSystem.Core.User;
import com.Visdrotech.VehiclePoolingSystem.Core.VehicleInfo;
import com.Visdrotech.VehiclePoolingSystem.Network.HttpPostReq;
import com.Visdrotech.VehiclePoolingSystem.Utils.Common_Utilities;

public class Activity_Registeration extends Activity implements
		OnClickListener, OnCheckedChangeListener {

	RadioGroup rgGender, rgCategory;
	EditText etName, etUsername, etEmail, etPassword, etPhone,
			etConfirmPassword, etUnivRegNum, etVehicleNum1, etVehicleNum2,
			etVehicleNum3, etVehicleMilage1, etVehicleMilage2,
			etVehicleMilage3, etSecQues, etSecAns;
	LinearLayout lVehicleInfoAdd, bSubmit, bReset;
	Spinner spinnerGenderPref, spinnerSource;
	SpinnerAdapter spinnerAdapter, spinnerAdapterSources;
	ArrayList<String> StringObjectSample;
	ArrayList<String> sourceList;
	User user;
	SharedPreferences SharedPrefs;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_registeration);

		initialise();

//		if (!Common_Utilities.haveInternet(Activity_Registeration.this)) {
//			Toast.makeText(Activity_Registeration.this,
//					"Intenet Connection is necessary!!", Toast.LENGTH_SHORT)
//					.show();
//			Activity_Registeration.this.finish();
//		}

		setSpinner();

		setSourceList();
	}

	private void setSourceList() {
		new postSelectSource().execute();

	}

	private void initialise() {
		SharedPrefs = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsUserDetails, 0);
		user = new User();
		etEmail = (EditText) findViewById(R.id.etEmail);
		etName = (EditText) findViewById(R.id.etName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
		etPhone = (EditText) findViewById(R.id.etPhone);
		etUnivRegNum = (EditText) findViewById(R.id.etUnivRegNum);
		etVehicleNum1 = (EditText) findViewById(R.id.etVehicleNum1);
		etVehicleNum2 = (EditText) findViewById(R.id.etVehicleNum2);
		etVehicleNum3 = (EditText) findViewById(R.id.etVehicleNum3);
		etVehicleMilage1 = (EditText) findViewById(R.id.etVehicleMilage1);
		etVehicleMilage2 = (EditText) findViewById(R.id.etVehicleMilage2);
		etVehicleMilage3 = (EditText) findViewById(R.id.etVehicleMilage3);
		etSecQues = (EditText) findViewById(R.id.etSecQues);
		etSecAns = (EditText) findViewById(R.id.etSecAns);

		lVehicleInfoAdd = (LinearLayout) findViewById(R.id.lVehicleinfoAdd);
		bSubmit = (LinearLayout) findViewById(R.id.bSubmit);
		bReset = (LinearLayout) findViewById(R.id.bReset);

		rgCategory = (RadioGroup) findViewById(R.id.rgCategory);
		rgGender = (RadioGroup) findViewById(R.id.rgGender);

		rgCategory.check(R.id.rbTraveller);
		rgGender.check(R.id.rbMale);

		spinnerGenderPref = (Spinner) findViewById(R.id.spinner_selectGenderPrefs);
		StringObjectSample = new ArrayList<String>();
		sourceList = new ArrayList<String>();
		spinnerSource = (Spinner) findViewById(R.id.spinner_selectSource);

		rgCategory.setOnCheckedChangeListener(this);
		rgGender.setOnCheckedChangeListener(this);

		bReset.setOnClickListener(this);
		bSubmit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bReset:
			clearAll();
			break;
		case R.id.bSubmit:
			if (validateData()) {
				getDataFromViews();
				new postSubmit().execute("user");
			}
		}
	}

	private boolean validateData() {
		if (etName.getText().toString().equals(null)
				|| etName.getText().toString().equals("")) {
			Toast.makeText(Activity_Registeration.this, "Enter your name!!",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (etEmail.getText().toString().equals(null)
				|| etEmail.getText().toString().equals("")) {
			Toast.makeText(Activity_Registeration.this,
					"Enter your email-id!!", Toast.LENGTH_SHORT).show();
			return false;
		} else if (etPassword.getText().toString().equals(null)
				|| etPassword.getText().toString().equals("")) {
			Toast.makeText(Activity_Registeration.this, "Enter password!!",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (etPhone.getText().toString().equals(null)
				|| etPhone.getText().toString().equals("")) {
			Toast.makeText(Activity_Registeration.this,
					"Enter your phone number!!", Toast.LENGTH_SHORT).show();
			return false;
		} else if (etSecQues.getText().toString().equals(null)
				|| etSecQues.getText().toString().equals("")) {
			Toast.makeText(Activity_Registeration.this,
					"Enter your Security Question!!", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (etSecAns.getText().toString().equals(null)
				|| etSecAns.getText().toString().equals("")) {
			Toast.makeText(Activity_Registeration.this,
					"Enter your Security Answer!!", Toast.LENGTH_SHORT).show();
			return false;
		} else if (etUnivRegNum.getText().toString().equals(null)
				|| etUnivRegNum.getText().toString().equals("")) {
			Toast.makeText(Activity_Registeration.this,
					"Enter your university registration number!!",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (!etPassword.getText().toString()
				.equals(etConfirmPassword.getText().toString())) {
			Toast.makeText(Activity_Registeration.this,
					"Password do not match!!", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (rgCategory.getCheckedRadioButtonId() == R.id.rbOwner
				|| rgCategory.getCheckedRadioButtonId() == R.id.rbBoth) {
			if (etVehicleNum1.getText().toString().equals(null)
					|| etVehicleMilage1.getText().toString().equals(null)
					|| etVehicleNum1.getText().toString().equals("")
					|| etVehicleMilage1.getText().toString().equals("")) {
				Toast.makeText(Activity_Registeration.this,
						"At least 1 vehicle should be present",
						Toast.LENGTH_SHORT).show();
			}
		}
		
		
		if(etPhone.getText().toString().length()!=10)
		{
			Toast.makeText(Activity_Registeration.this,
					"Phone number invalid!!", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!etEmail.getText().toString().contains("@")||!etEmail.getText().toString().contains(".")||etEmail.getText().toString().length()<5)
		{
			Toast.makeText(Activity_Registeration.this,
					"Email-Id invalid!!", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(etPassword.getText().toString().length()<6||!checkContainCaps(etPassword.getText().toString())||!checkContainNum(etPassword.getText().toString()))
		{
			Toast.makeText(Activity_Registeration.this,
					"Password invalid!!", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (etUnivRegNum.getText().toString().length() < 8
				|| !etUnivRegNum.getText().toString().startsWith("DTU/")
				|| etUnivRegNum.getText().toString().lastIndexOf("/")<6) {
			Toast.makeText(Activity_Registeration.this, "University Registration Number invalid!!",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;

	}

	private boolean checkContainNum(String string) {
		for(int i = 0 ; i < string.length() ; i++)
		{
			if(Character.isDigit(string.charAt(i)))
			{
				return true;
			}
		}
		return false;
	}

	private boolean checkContainCaps(String string) {
		for(int i = 0 ; i < string.length() ; i++)
		{
			if(Character.isUpperCase(string.charAt(i)))
			{
				return true;
			}
		}
		return false;
	}

	private void getDataFromViews() {
		user.setEmail(etEmail.getText().toString());
		user.setName(etName.getText().toString());
		user.setPassword(etPassword.getText().toString());
		user.setPhoneNumber(etPhone.getText().toString());
		user.setUniversityRegNum(etUnivRegNum.getText().toString());
		user.setSecQues(etSecQues.getText().toString());
		user.setSecAns(etSecAns.getText().toString());
		user.setDefaultSource(sourceList.get(spinnerSource.getSelectedItemPosition()));

		if (rgGender.getCheckedRadioButtonId() == R.id.rbMale) {
			user.setGender("Male");
		} else {
			user.setGender("Female");
		}
		if (rgCategory.getCheckedRadioButtonId() == R.id.rbOwner) {
			user.setCategory("Owner");
		} else if (rgCategory.getCheckedRadioButtonId() == R.id.rbBoth) {
			user.setCategory("Both");
		} else {
			user.setCategory("Traveller");
		}
		user.setGenderPrefs(""
				+ StringObjectSample.get(spinnerGenderPref
						.getSelectedItemPosition()));

		if (user.getCategory().equalsIgnoreCase("Owner")
				|| user.getCategory().equalsIgnoreCase("Both")) {
			VehicleInfo vehicle1 = new VehicleInfo();
			vehicle1.setVehicleRegNum(etVehicleNum1.getText().toString());
			vehicle1.setVehicleMilage(Double.parseDouble(etVehicleMilage1
					.getText().toString()));
			user.setVehicle1(vehicle1);

			if (!etVehicleNum2.getText().toString().equals(null)
					&& !etVehicleMilage2.getText().toString().equals(null)
					&& !etVehicleNum2.getText().toString().equals("")
					&& !etVehicleMilage2.getText().toString().equals("")) {
				VehicleInfo vehicle2 = new VehicleInfo();
				vehicle2.setVehicleRegNum(etVehicleNum2.getText().toString());
				vehicle2.setVehicleMilage(Double.parseDouble(etVehicleMilage2
						.getText().toString()));
				user.setVehicle2(vehicle2);
			}
			if (!etVehicleNum3.getText().toString().equals(null)
					&& !etVehicleMilage3.getText().toString().equals(null)
					&& !etVehicleNum3.getText().toString().equals("")
					&& !etVehicleMilage3.getText().toString().equals("")) {
				VehicleInfo vehicle3 = new VehicleInfo();
				vehicle3.setVehicleRegNum(etVehicleNum3.getText().toString());
				vehicle3.setVehicleMilage(Double.parseDouble(etVehicleMilage3
						.getText().toString()));
				user.setVehicle3(vehicle3);
			}
		} else {
			VehicleInfo vehicle1 = new VehicleInfo();
			vehicle1.setVehicleRegNum("NIL");
			vehicle1.setVehicleMilage(0);
			user.setVehicle1(vehicle1);
			VehicleInfo vehicle2 = new VehicleInfo();
			vehicle2.setVehicleRegNum("NIL");
			vehicle2.setVehicleMilage(0);
			user.setVehicle2(vehicle2);
			VehicleInfo vehicle3 = new VehicleInfo();
			vehicle3.setVehicleRegNum("NIL");
			vehicle3.setVehicleMilage(0);
			user.setVehicle3(vehicle3);

		}
	}

	private void clearAll() {
		etEmail.setText("");
		etName.setText("");
		etPassword.setText("");
		etPhone.setText("");
		etConfirmPassword.setText("");
		etUnivRegNum.setText("");
		spinnerGenderPref.setSelection(0);
		etVehicleMilage1.setText("");
		etVehicleMilage2.setText("");
		etVehicleMilage3.setText("");
		etSecQues.setText("");
		etSecAns.setText("");
		etVehicleNum1.setText("");
		etVehicleNum2.setText("");
		etVehicleNum3.setText("");
		rgCategory.setOnCheckedChangeListener(this);
		rgGender.setOnCheckedChangeListener(this);
	}

	private void setSpinner() {
		StringObjectSample.add("Gender not an Issue");
		StringObjectSample.add("At least 1 from same gender");
		StringObjectSample.add("All from same gender");
		spinnerAdapter = new ArrayAdapter<String>(Activity_Registeration.this,
				R.layout.listview_spinner_gender_pref, R.id.textView1,
				StringObjectSample);
		spinnerGenderPref.setAdapter(spinnerAdapter);
		spinnerGenderPref.setSelection(spinnerGenderPref
				.getSelectedItemPosition());
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (group.getId()) {
		case R.id.rgCategory:
			if (checkedId == R.id.rbOwner || checkedId == R.id.rbBoth) {
				lVehicleInfoAdd.setVisibility(View.VISIBLE);
			} else {
				lVehicleInfoAdd.setVisibility(View.GONE);
			}
			break;
		}
	}

	public class postSubmit extends AsyncTask<String, String, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			Log.d("Down", "On PreExecute : DownloadRestaurant");
			dialog = new ProgressDialog(Activity_Registeration.this);
			Log.d("Down", "DD");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Registering....\n Please Wait");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

				@Override
				public void onCancel(DialogInterface dialog) {
					Activity_Registeration.this.finish();
				}});
			dialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {

			String result = HttpPostReq.postRegister(user);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("TAGS", result);
			if (result.contains(Common_Utilities.CODE_USER_VALIDATION_ERROR)) {
				Toast.makeText(Activity_Registeration.this, "Error Occured",
						Toast.LENGTH_SHORT).show();
			} else if (result
					.contains(Common_Utilities.CODE_USER_VALIDATION_SUCCESS)) {
				Common_Utilities.saveDataToSharedPrefs(user, false, SharedPrefs);
				Toast.makeText(Activity_Registeration.this,
						"Registration Successful!!", Toast.LENGTH_SHORT).show();
				Activity_Registeration.this.finish();
			} else if (result
					.contains(Common_Utilities.CODE_USER_VALIDATION_WRONG_DETAILS)) {
				Toast.makeText(Activity_Registeration.this,
						"Registration Number / Phone Number already taken.",
						Toast.LENGTH_SHORT).show();
			}

			dialog.dismiss();

		}
	}

	public class postSelectSource extends AsyncTask<String, String, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			Log.d("Down", "On PreExecute : DownloadRestaurant");
			dialog = new ProgressDialog(Activity_Registeration.this);
			Log.d("Down", "DD");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Loading....\n Please Wait");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

				@Override
				public void onCancel(DialogInterface dialog) {
					Activity_Registeration.this.finish();
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
			if (result.equals(Common_Utilities.CODE_USER_VALIDATION_ERROR)) {
				Toast.makeText(Activity_Registeration.this, "Error Occured",
						Toast.LENGTH_SHORT).show();
				Activity_Registeration.this.finish();
			} else {

				StringTokenizer tokenizer = new StringTokenizer(result, "|");
				Log.d("TAG", "" + tokenizer.countTokens());
				while (tokenizer.hasMoreTokens()) {
					String s = tokenizer.nextToken();
					Log.d("TAG", s);
					sourceList.add(s);
					if (s.equals("Yamuna Vihar"))
						break;
					tokenizer.nextToken();
				}

				spinnerAdapterSources = new ArrayAdapter<String>(
						Activity_Registeration.this,
						R.layout.listview_spinner_gender_pref, R.id.textView1,
						sourceList);
				spinnerSource.setAdapter(spinnerAdapterSources);
				spinnerSource.setSelection(spinnerSource
						.getSelectedItemPosition());
			}

			dialog.dismiss();

		}
	}
}
