package com.Visdrotech.VehiclePoolingSystem.Activities;

import java.util.Calendar;

import org.w3c.dom.Text;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.textservice.TextInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Visdrotech.VehiclePoolingSystem.Utils.Common_Utilities;

public class Activity_View_Profile extends Activity implements OnClickListener {

	TextView tvName, tvEmail, tvPhone, tvGender, tvGenderPrefs, tvCategory,
			tvUnivRegNum;
	LinearLayout bNext;
	ImageButton ibEdit,ibLogout;
	SharedPreferences SharedPrefs,SharedPrefsTravellerSelectDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_view_profile);

		initialise();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initialise();
	}

	private void initialise() {
		SharedPrefs = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsUserDetails, 0);
		SharedPrefsTravellerSelectDetails = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsTravellerSelectDetails, 0);
		tvEmail = (TextView) findViewById(R.id.tvEmail);
		tvName = (TextView) findViewById(R.id.tvProfileName);
		tvPhone = (TextView) findViewById(R.id.tvPhone);
		tvUnivRegNum = (TextView) findViewById(R.id.tvUniversityRollno);
		tvCategory = (TextView) findViewById(R.id.tvCategory);
		tvGender = (TextView) findViewById(R.id.tvGender);
		tvGenderPrefs = (TextView) findViewById(R.id.tvGenderPrefs);

		ibEdit = (ImageButton) findViewById(R.id.ibEdit);
		bNext = (LinearLayout) findViewById(R.id.bConfirm);
		ibLogout = (ImageButton) findViewById(R.id.ibLogout);
		ibEdit.setOnClickListener(this);
		bNext.setOnClickListener(this);
		ibLogout.setOnClickListener(this);
		
		
		tvEmail.setText(SharedPrefs.getString(
				Common_Utilities.UserDetailsVariableEmail, null));
		tvName.setText(SharedPrefs.getString(
				Common_Utilities.UserDetailsVariableName, null) + "'s Profile");
		tvCategory.setText(SharedPrefs.getString(
				Common_Utilities.UserDetailsVariableCategory, null));
		tvGender.setText(SharedPrefs.getString(
				Common_Utilities.UserDetailsVariableGender, null));
		tvGenderPrefs.setText(SharedPrefs.getString(
				Common_Utilities.UserDetailsVariableGenderPrefs, null));
		tvPhone.setText(SharedPrefs.getString(
				Common_Utilities.UserDetailsVariablePhoneNumber, null));
		tvUnivRegNum.setText(SharedPrefs.getString(
				Common_Utilities.UserDetailsVariableUniversityRegNum, null));

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {
		case R.id.bConfirm:
			if (SharedPrefs.getString(
					Common_Utilities.UserDetailsVariableCategory, null).equalsIgnoreCase(
					"Owner"))
			{if (!SharedPrefsTravellerSelectDetails.getBoolean(
					Common_Utilities.SharedPrefsVariableTravelerSelected, false)  && Calendar.HOUR_OF_DAY>5 &&Calendar.HOUR_OF_DAY<22) {
				Intent intentSelectSource = new Intent(
						"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_SELECT_SOURCE");
				startActivity(intentSelectSource);
			} else  {
				Intent intentSelectTravellers = new Intent(
						"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_COMMUTERINFO_OWNER");
				startActivity(intentSelectTravellers);
			}
			}
			else if (SharedPrefs.getString(
					Common_Utilities.UserDetailsVariableCategory, null).equalsIgnoreCase(
					"Traveller")){
				if (SharedPrefsTravellerSelectDetails.getBoolean(
						Common_Utilities.SharedPrefsVariableTravellerSourceSelected, false)) {	
				Intent intentSelectTravellers = new Intent(
						"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_COMMUTERINFO_TRAVELLER");
				startActivity(intentSelectTravellers);
				}
				else
				{
					Intent intentSelectSource = new Intent(
							"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_SELECT_SOURCE");
					startActivity(intentSelectSource);
				}
			}
		break;

		case R.id.ibEdit:
			final AlertDialog d = new AlertDialog.Builder(
					Activity_View_Profile.this).create();
			d.setTitle("Enter Your Password");
			final EditText e = new EditText(Activity_View_Profile.this);
			e.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
			d.setView(e);
			d.setButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if (e.getText()
							.toString()
							.equals(SharedPrefs
									.getString(
											Common_Utilities.UserDetailsVariablePassword,
											null))) {
						Intent intentEditProfile = new Intent(
								"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_EDIT_PROFILE");
						startActivity(intentEditProfile);
						d.dismiss();
					} else {
						Toast.makeText(Activity_View_Profile.this,
								"Wrong Password", Toast.LENGTH_SHORT).show();
					}
				}
			});
			d.setButton2("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					d.dismiss();
				}
			});
			d.show();

			break;
			
		case R.id.ibLogout:
				SharedPreferences.Editor edit = SharedPrefs.edit();
				edit.clear();
				edit.commit();
				try {
					Class classToHome = Class
							.forName("com.Visdrotech.VehiclePoolingSystem.Activities.Activity_Home");
					Intent intentHome = new Intent(Activity_View_Profile.this,
							classToHome);
					startActivity(intentHome);
					this.finish();
				} catch (ClassNotFoundException e22) {
					e22.printStackTrace();
				}
			break;
		}
	}

}