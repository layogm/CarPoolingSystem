package com.Visdrotech.VehiclePoolingSystem.Activities;

import java.util.StringTokenizer;

import com.Visdrotech.VehiclePoolingSystem.Core.VehicleInfo;
import com.Visdrotech.VehiclePoolingSystem.Network.HttpPostReq;
import com.Visdrotech.VehiclePoolingSystem.Utils.Common_Utilities;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView.Tokenizer;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_ForgotPassword extends Activity{

	TextView tvQues,tvPassword;
	EditText etAns;
	LinearLayout bOk;
	String Answer,question,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_forgot_password);
		new postLogin().execute(getIntent().getExtras().getString(Common_Utilities.UserDetailsVariableUniversityRegNum));
		
	}
	private void initialise() {
		tvQues = (TextView) findViewById(R.id.tvSecQues);
		tvQues.setText(question);
		tvPassword = (TextView) findViewById(R.id.tvPassword);
		tvPassword.setVisibility(View.GONE);
		etAns = (EditText)findViewById(R.id.etSecAns);
		bOk = (LinearLayout) findViewById(R.id.bConfirm);
		bOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tvPassword.setVisibility(View.VISIBLE);
				if(etAns.getText().toString().equals(""))
				{
					tvPassword.setText("No Answer Entered");
				}
				if(etAns.getText().toString().equals(Answer))
				
				{
					tvPassword.setText("Your Password is \""+password+"\"");
				}
				else
				{
					tvPassword.setText("Wrong Answer.");
				}
			}
		});
	}
	
	
	
	
	public class postLogin extends AsyncTask<String, String, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			Log.d("Down", "On PreExecute : DownloadRestaurant");

			dialog = new ProgressDialog(Activity_ForgotPassword.this);
			Log.d("Down", "DD");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Loading...\nPlease Wait....");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

				@Override
				public void onCancel(DialogInterface dialog) {
					Activity_ForgotPassword.this.finish();
				}});
			dialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {

			 return HttpPostReq.forgotPasswordPost(arg0[0]);

			 
		}

		@SuppressWarnings({ "deprecation", "unused" })
		@Override
		protected void onPostExecute(String result) {
			
			dialog.dismiss();

			if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_SUCCESS)) {
							StringTokenizer toeknizer = new StringTokenizer(result,
									"|");
							toeknizer.nextToken();
							
							question = toeknizer.nextToken();
							Answer = toeknizer.nextToken();
							password = toeknizer.nextToken();
							
							initialise();
							
			} else if (result.startsWith(Common_Utilities.CODE_USER_VALIDATION_ERROR)){
				Toast.makeText(Activity_ForgotPassword.this,
						"Unsuccessful!! Error Occured!!",
						Toast.LENGTH_SHORT).show();
			} 

		}
	}

}
