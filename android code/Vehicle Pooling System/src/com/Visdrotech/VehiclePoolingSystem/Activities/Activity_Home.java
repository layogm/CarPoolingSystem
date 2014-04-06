package com.Visdrotech.VehiclePoolingSystem.Activities;

import java.util.Calendar;
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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.Visdrotech.VehiclePoolingSystem.Core.User;
import com.Visdrotech.VehiclePoolingSystem.Core.VehicleInfo;
import com.Visdrotech.VehiclePoolingSystem.Network.HttpPostReq;
import com.Visdrotech.VehiclePoolingSystem.Utils.Common_Utilities;

public class Activity_Home extends Activity implements OnClickListener {

	LinearLayout bSignUp, bSignIn;
	SharedPreferences sharedPrefs, sharedPrefsTravellerSelect;
	User user;
	String universityRegNum, password;
	EditText etUsername, etPassword;
	CheckBox cbRemebered;
	LinearLayout bForgotPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);

		Initialise();

		if (sharedPrefsTravellerSelect
				.getString("Date", "date")
				.equals((Calendar.DAY_OF_MONTH + "/" + Calendar.MONTH + "/" + Calendar.YEAR))) {
			int dif = (sharedPrefsTravellerSelect.getInt("TimeHour", 25) - Calendar.HOUR_OF_DAY);
			if (dif < 0) {
				dif = -dif;
			}
			if (dif > 15) {
				SharedPreferences.Editor editor = sharedPrefsTravellerSelect
						.edit();
				editor.clear();
				editor.commit();
			}
		} else {
			if (Calendar.HOUR_OF_DAY > 18) {
				SharedPreferences.Editor editor = sharedPrefsTravellerSelect
						.edit();
				editor.clear();
				editor.commit();
			}
		}

		IfLoggedIn();
	}

	private void Initialise() {
		sharedPrefs = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsUserDetails, 0);
		sharedPrefsTravellerSelect = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsTravellerSelectDetails, 0);
		user = new User();

		etUsername = (EditText) findViewById(R.id.etUsername_Login);
		etPassword = (EditText) findViewById(R.id.etPassword_Login);
		cbRemebered = (CheckBox) findViewById(R.id.cbRememberd);
		bSignIn = (LinearLayout) findViewById(R.id.bLogin_Login);
		bSignUp = (LinearLayout) findViewById(R.id.bRegister_Login);
		bSignIn.setOnClickListener(this);
		bSignUp.setOnClickListener(this);
		bForgotPass = (LinearLayout) findViewById(R.id.bForgotPass);
		bForgotPass.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__home, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	private void IfLoggedIn() {
		if (sharedPrefs.getBoolean(
				Common_Utilities.UserDetailsSharedPrefsIsRemembered, false)) {

			setValues();
			if (user.getCategory().equals("Both")) {
				AlertDialog dialog = new AlertDialog.Builder(Activity_Home.this)
						.create();
				dialog.setTitle("Will you be bringing your own Vehicle?");
				dialog.setButton("Yes", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						user.setCategory("Owner");
						dialog.dismiss();
						Intent intentViewProfile = new Intent(
								"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_VIEW_PROFILE");
						startActivity(intentViewProfile);
						Activity_Home.this.finish();
					}
				});
				dialog.setButton2("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						user.setCategory("Traveller");

						dialog.dismiss();
						Intent intentViewProfile = new Intent(
								"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_VIEW_PROFILE");
						startActivity(intentViewProfile);
						Activity_Home.this.finish();
					}
				});
			} else {
				Intent intentViewProfile = new Intent(
						"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_VIEW_PROFILE");
				startActivity(intentViewProfile);
				Activity_Home.this.finish();
			}
		}

	}

	private void setValues() {
		user.setEmail(sharedPrefs.getString(
				Common_Utilities.UserDetailsVariableEmail, null));
		user.setName(sharedPrefs.getString(
				Common_Utilities.UserDetailsVariableName, null));
		user.setPhoneNumber(sharedPrefs.getString(
				Common_Utilities.UserDetailsVariablePhoneNumber, null));
		user.setUniversityRegNum(sharedPrefs.getString(
				Common_Utilities.UserDetailsVariableUniversityRegNum, null));
		user.setPassword(sharedPrefs.getString(
				Common_Utilities.UserDetailsVariablePassword, null));

		user.setCategory(sharedPrefs.getString(
				Common_Utilities.UserDetailsVariableCategory, null));
		if (sharedPrefs.getString(Common_Utilities.UserDetailsVariableCategory,
				null).equals("Owner")) {
			VehicleInfo vehicle1 = new VehicleInfo();
			vehicle1.setVehicleMilage(sharedPrefs.getFloat(
					Common_Utilities.VehicleDetailsVariableVehicleMileage1, 0));
			vehicle1.setVehicleRegNum(sharedPrefs
					.getString(
							Common_Utilities.VehicleDetailsVariableVehicleNumber1,
							null));
			user.setVehicle1(vehicle1);

			if (!sharedPrefs
					.getString(
							Common_Utilities.VehicleDetailsVariableVehicleNumber2,
							null).equals("NIL")
					|| sharedPrefs
							.getFloat(
									Common_Utilities.VehicleDetailsVariableVehicleMileage2,
									0) != 0) {
				VehicleInfo vehicle2 = new VehicleInfo();
				vehicle2.setVehicleMilage(sharedPrefs.getFloat(
						Common_Utilities.VehicleDetailsVariableVehicleMileage2,
						0));
				vehicle2.setVehicleRegNum(sharedPrefs.getString(
						Common_Utilities.VehicleDetailsVariableVehicleNumber2,
						null));
				user.setVehicle2(vehicle2);
			}

			if (!sharedPrefs
					.getString(
							Common_Utilities.VehicleDetailsVariableVehicleNumber3,
							null).equals("NIL")
					|| sharedPrefs
							.getFloat(
									Common_Utilities.VehicleDetailsVariableVehicleMileage3,
									0) != 0) {
				VehicleInfo vehicle3 = new VehicleInfo();
				vehicle3.setVehicleMilage(sharedPrefs.getFloat(
						Common_Utilities.VehicleDetailsVariableVehicleMileage3,
						0));
				vehicle3.setVehicleRegNum(sharedPrefs.getString(
						Common_Utilities.VehicleDetailsVariableVehicleNumber3,
						null));
				user.setVehicle3(vehicle3);
			}
		}

		user.setGender(sharedPrefs.getString(
				Common_Utilities.UserDetailsVariableGender, null));
		user.setSecQues(sharedPrefs.getString(
				Common_Utilities.UserDetailsVariableSecQues, null));
		user.setSecAns(sharedPrefs.getString(
				Common_Utilities.UserDetailsVariableSecAns, null));

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bLogin_Login:

			universityRegNum = etUsername.getText().toString();
			password = etPassword.getText().toString();
			new postLogin().execute(universityRegNum, password);

			break;

		case R.id.bRegister_Login:
			Intent intentRegister = new Intent(
					"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_REGISTER");
			startActivity(intentRegister);
			break;

		case R.id.bForgotPass:
			final AlertDialog d = new AlertDialog.Builder(Activity_Home.this)
					.create();
			d.setTitle("Enter Your University Reg. Num.");
			final EditText e = new EditText(Activity_Home.this);
			d.setView(e);
			d.setButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intentForgotPass = new Intent(
							"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_FORGOT_PASSWORD");
					Bundle b = new Bundle();
					b.putString(
							Common_Utilities.UserDetailsVariableUniversityRegNum,
							e.getText().toString());
					intentForgotPass.putExtras(b);
					startActivity(intentForgotPass);

				}
			});
			d.setButton2("Cancel", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					d.dismiss();
				}
			});
			d.show();
			break;
		}

	}

	public class postLogin extends AsyncTask<String, String, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			Log.d("Down", "On PreExecute : DownloadRestaurant");

			dialog = new ProgressDialog(Activity_Home.this);
			Log.d("Down", "DD");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Logging In");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					Activity_Home.this.finish();
				}
			});
			dialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {

			return HttpPostReq.loginUser(arg0[0], arg0[1]);

		}

		@SuppressWarnings({ "deprecation", "unused" })
		@Override
		protected void onPostExecute(String result) {

			dialog.dismiss();
			if (result
					.startsWith(Common_Utilities.CODE_USER_VALIDATION_SUCCESS)) {
				Toast.makeText(Activity_Home.this, "Login Successful!! ",
						Toast.LENGTH_SHORT).show();
				StringTokenizer tokenizer = new StringTokenizer(result, "|");
				tokenizer.nextToken();
				user.setUniversityRegNum(tokenizer.nextToken());
				user.setName(tokenizer.nextToken());
				user.setGender(tokenizer.nextToken());
				user.setCategory(tokenizer.nextToken());
				user.setEmail(tokenizer.nextToken());
				user.setGenderPrefs(tokenizer.nextToken());
				user.setPhoneNumber(tokenizer.nextToken());
				user.setPassword(tokenizer.nextToken());
				user.setDefaultSource(tokenizer.nextToken());
				tokenizer.nextToken();
				user.setSecQues(tokenizer.nextToken());
				user.setSecAns(tokenizer.nextToken());

				VehicleInfo v1 = new VehicleInfo();
				v1.setVehicleRegNum(tokenizer.nextToken());
				VehicleInfo v2 = new VehicleInfo();
				v2.setVehicleRegNum(tokenizer.nextToken());
				VehicleInfo v3 = new VehicleInfo();
				v3.setVehicleRegNum(tokenizer.nextToken());
				v1.setVehicleMilage(Double.parseDouble(tokenizer.nextToken()));
				v2.setVehicleMilage(Double.parseDouble(tokenizer.nextToken()));
				v3.setVehicleMilage(Double.parseDouble(tokenizer.nextToken()));
				user.setVehicle1(v1);
				user.setVehicle2(v2);
				user.setVehicle3(v3);

				if (user.getCategory().equals("Both")) {
					AlertDialog dialog = new AlertDialog.Builder(
							Activity_Home.this).create();
					dialog.setTitle("Will you be bringing your own Vehicle");
					dialog.setButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									user.setCategory("Owner");
									Common_Utilities.saveDataToSharedPrefs(
											user, cbRemebered.isChecked(),
											sharedPrefs);
									dialog.dismiss();
									Intent intentViewProfile = new Intent(
											"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_VIEW_PROFILE");
									startActivity(intentViewProfile);
									Activity_Home.this.finish();
								}
							});
					dialog.setButton2("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									user.setCategory("Traveller");
									Common_Utilities.saveDataToSharedPrefs(
											user, cbRemebered.isChecked(),
											sharedPrefs);
									dialog.dismiss();
									Intent intentViewProfile = new Intent(
											"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_VIEW_PROFILE");
									startActivity(intentViewProfile);
									Activity_Home.this.finish();
								}
							});
					dialog.show();
				} else {
					Common_Utilities.saveDataToSharedPrefs(user,
							cbRemebered.isChecked(), sharedPrefs);
					Intent intentViewProfile = new Intent(
							"com.Visdrotech.VehiclePoolingSystem.Activities.ACTIVITY_VIEW_PROFILE");
					startActivity(intentViewProfile);
					Activity_Home.this.finish();
				}

			} else if (result
					.startsWith(Common_Utilities.CODE_USER_VALIDATION_ERROR)) {
				Toast.makeText(Activity_Home.this,
						"Login Unsuccessful!! Error Occured!!",
						Toast.LENGTH_SHORT).show();
			} else if (result
					.startsWith(Common_Utilities.CODE_USER_VALIDATION_WRONG_DETAILS)) {
				Toast.makeText(Activity_Home.this, "Wrong UserName/Password!!",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}
