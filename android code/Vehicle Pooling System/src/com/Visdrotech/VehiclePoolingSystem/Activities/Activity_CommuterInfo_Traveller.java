package com.Visdrotech.VehiclePoolingSystem.Activities;

import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Visdrotech.VehiclePoolingSystem.Core.User;
import com.Visdrotech.VehiclePoolingSystem.Network.HttpPostReq;
import com.Visdrotech.VehiclePoolingSystem.Utils.Common_Utilities;

public class Activity_CommuterInfo_Traveller extends Activity implements OnClickListener{


	ImageButton ibCall,ibMsg,ibMail;
	TextView tvPhone,tvName,tvEmail,tvHead,tvVNum,tvFare;
	SharedPreferences SharedPrefs;
	User user;
	LinearLayout lName,lPhone,lEmail,lFare,lVNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_view_commuter_info_traveller);

		SharedPrefs = getSharedPreferences(
				Common_Utilities.fileNameSharedPrefsUserDetails, 0);
		initialise();

		
		new postReqGetOwnerData().execute(SharedPrefs.getString(Common_Utilities.UserDetailsVariableUniversityRegNum, null));
		
	}


	private void initialise() {
		user = new User();

		tvHead = (TextView) findViewById(R.id.tvHead);
		tvName = (TextView) findViewById(R.id.tvName);
		tvEmail = (TextView) findViewById(R.id.tvEmail);
		tvPhone = (TextView) findViewById(R.id.tvPhone);
		tvVNum = (TextView) findViewById(R.id.tvVehicleNum);
		tvFare = (TextView) findViewById(R.id.tvFare);
		
		lName = (LinearLayout) findViewById(R.id.lName);
		lEmail = (LinearLayout) findViewById(R.id.lEmail);
		lPhone = (LinearLayout) findViewById(R.id.lPhone);
		lVNum = (LinearLayout) findViewById(R.id.lVehicleNum);
		lFare = (LinearLayout) findViewById(R.id.lFare);
		
		ibCall = (ImageButton) findViewById(R.id.bCall);
		ibMsg = (ImageButton) findViewById(R.id.bMsg);
		ibMail = (ImageButton) findViewById(R.id.bEmail);
		
		ibCall.setOnClickListener(this);
		ibMsg.setOnClickListener(this);
		ibMail.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bCall:
			startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + tvPhone.getText().toString())));
			break;
		case R.id.bMsg:
			startActivity(new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms", tvPhone.getText().toString(), null)));
			break;
		case R.id.bEmail:
			EmailPopup(tvEmail.getText().toString());
			break;
		}
	}
	
	private void EmailPopup(final String emailid) {
		AlertDialog alertEditbox;
		alertEditbox = new AlertDialog.Builder(this).create();

		alertEditbox.setTitle("Write Your Email!!");
		final EditText etv = new EditText(this);
		alertEditbox.setView(etv);
		alertEditbox.setButton("Send", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String mailFromUser = etv.getText().toString();
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailid});
				i.putExtra(Intent.EXTRA_SUBJECT, "VPS Issue");
				i.putExtra(Intent.EXTRA_TEXT   , mailFromUser);
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(Activity_CommuterInfo_Traveller.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
			
			}
		});
		alertEditbox.show();
	}
	
	public class postReqGetOwnerData extends AsyncTask<String, String, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			Log.d("Down", "On PreExecute : DownloadRestaurant");
			dialog = new ProgressDialog(Activity_CommuterInfo_Traveller.this);
			Log.d("Down", "DD");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Loading...\nPlease Wait...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

				@Override
				public void onCancel(DialogInterface dialog) {
					Activity_CommuterInfo_Traveller.this.finish();
				}});
			dialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {

			return HttpPostReq.getVehicleOwnerInfoForTraveller(arg0[0]);
			
		}

		@Override
		protected void onPostExecute(String result) {

			dialog.dismiss();
			
			if (result.startsWith("0")||result.startsWith("NIL")) 
			{
			Toast.makeText(Activity_CommuterInfo_Traveller.this, "Error Occurred!! Please try again!!", Toast.LENGTH_SHORT)
			.show();
		}else if(result.startsWith("1"))
			{
				StringTokenizer tokenizer = new StringTokenizer(result,"|");
				
				lName.setVisibility(View.VISIBLE);
				lPhone.setVisibility(View.VISIBLE);
				lEmail.setVisibility(View.VISIBLE);

				tokenizer.nextToken();
				tokenizer.nextToken();
				tvName.setText(tokenizer.nextToken());
				tvPhone.setText(tokenizer.nextToken());
				tvEmail.setText(tokenizer.nextToken());
				tvVNum.setText(tokenizer.nextToken());
				tvFare.setText("Rs."+tokenizer.nextToken());
				
			}
			else
			{
				lName.setVisibility(View.GONE);
				lPhone.setVisibility(View.GONE);
				lEmail.setVisibility(View.GONE);
				lVNum.setVisibility(View.GONE);
				lFare.setVisibility(View.GONE);
				tvHead.setText("Sorry no vehicle owner has been found for your location yet ;(.\nPlease check after sometime");
			}
		}
	}

}
