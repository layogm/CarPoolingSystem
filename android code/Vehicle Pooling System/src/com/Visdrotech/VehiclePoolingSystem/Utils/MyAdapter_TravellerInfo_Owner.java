package com.Visdrotech.VehiclePoolingSystem.Utils;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.Visdrotech.VehiclePoolingSystem.Activities.R;
import com.Visdrotech.VehiclePoolingSystem.Core.User;

public class MyAdapter_TravellerInfo_Owner extends ArrayAdapter<User> {

	private static final String tag = "Custom Adapter";
	ImageButton ibCall,ibMsg,ibEmail;
	
	Context context;
	TextView tvEmail, tvName, tvPhone, tvMeetingPoint, tvTime, tvHead, tvFare;
	ArrayList<User> TravellerList;
	ArrayList<String> TimeList,FareList;
	
	public MyAdapter_TravellerInfo_Owner(Context context, int resource, int textViewResourceId,
			List<User> TravellerList, List<String> TimeList , List<String> fareList) {
		super(context, resource, textViewResourceId, TravellerList);
		this.context = context;
		this.TravellerList = (ArrayList<User>) TravellerList;
		this.TimeList = (ArrayList<String>) TimeList;
		this.FareList = (ArrayList<String>) fareList;
		Log.d("TAG 4",TravellerList.size()+" : "+TimeList.size() + " : "+ FareList.size());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.listview_travel_info_owner_view, parent,
					false);
		}

		Log.d(tag, "In GetView");
		tvHead =(TextView) view.findViewById(R.id.tvHead);
		tvHead.setText("Traveller "+(position+1));
		tvMeetingPoint = (TextView) view.findViewById(R.id.tvMeetingPointOwnerInfo);
		tvName = (TextView) view.findViewById(R.id.tvNameOwnerInfo);
		tvPhone = (TextView) view.findViewById(R.id.tvPhoneOwnerInfo);
		tvEmail = (TextView) view.findViewById(R.id.tvEmailOwnerInfo);
		tvTime = (TextView) view.findViewById(R.id.tvTimeOwnerInfo);
		tvFare = (TextView) view.findViewById(R.id.tvFareOwnerInfo);
		
		tvName.setText(TravellerList.get(position).getName());
		tvMeetingPoint.setText(TravellerList.get(position).getDefaultSource());
		tvPhone.setText(TravellerList.get(position).getPhoneNumber());
		tvEmail.setText(TravellerList.get(position).getEmail());
		tvTime.setText(TimeList.get(position));
		tvFare.setText("Rs."+FareList.get(position));
		
		ibCall = (ImageButton) view.findViewById(R.id.bCall);
		ibMsg = (ImageButton) view.findViewById(R.id.bMsg);
		ibEmail = (ImageButton) view.findViewById(R.id.bEmail);
		
		ibCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + tvPhone.getText().toString())));
			}
		});
		ibMsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms", tvPhone.getText().toString(), null)));
			}
		});
		ibEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EmailPopup(tvEmail.getText().toString());
			}
		});
		
		return view;
		
	}

	private void EmailPopup(final String emailid) {
		AlertDialog alertEditbox;
		alertEditbox = new AlertDialog.Builder(context).create();

		alertEditbox.setTitle("Write Your Email!!");
		final EditText etv = new EditText(context);
		etv.setHeight(250);
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
				    context.startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		alertEditbox.show();
	}

}
