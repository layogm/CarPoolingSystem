package com.Visdrotech.VehiclePoolingSystem.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Visdrotech.VehiclePoolingSystem.Activities.R;
import com.Visdrotech.VehiclePoolingSystem.Core.User;

public class MyAdapter extends ArrayAdapter<User> {

	private static final String tag = "Custom Adapter";
	HashMap<String, Boolean> checked;
	Context context;
	CheckBox check;
	TextView tvMeetingPoint, tvName, tvTime ;
	ArrayList<TextView> tvWarning;
	LinearLayout bAddNote;
	String TableName;
	ArrayList<User> TravellerList;
	HashMap<String,String> TimeList;
	int checkedNum;
	int minSameGender = 0;
	
	public MyAdapter(Context context, int resource, int textViewResourceId,
			List<User> TravellerList,  HashMap<String,String> TimeList) {
		super(context, resource, textViewResourceId, TravellerList);
		this.context = context;
		this.TravellerList = (ArrayList<User>) TravellerList;
		this.TimeList = TimeList;
		tvWarning = new ArrayList<TextView>();
		checkedNum=0;
		checked = new HashMap<String, Boolean>();
		
		for(int i = 0 ; i < TravellerList.size() ; i++)
		{
			checked.put(TravellerList.get(i).getUniversityRegNum(), false);
		}

	}
	
	public ArrayList<User> getSelectedTravellers(){
		ArrayList<User> selectedTarvellers = new ArrayList<User>();
		for (int i = 0; i < TravellerList.size(); i++) {
			if(checked.get(TravellerList.get(i).getUniversityRegNum()))
			{
				selectedTarvellers.add(TravellerList.get(i));
			}
		}
		return selectedTarvellers;
	}
	
	public ArrayList<String> getSelectedTravellerTime(){
		ArrayList<String> selectedTarvellersTime = new ArrayList<String>();
		for (int i = 0; i < TravellerList.size(); i++) {
			if(checked.get(i))
			{
				selectedTarvellersTime.add(TimeList.get(TravellerList.get(i).getDefaultSource()));
			}
		}
		return selectedTarvellersTime;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.listview_select_traveller, parent,
					false);
		}

		tvMeetingPoint = (TextView) view.findViewById(R.id.tvMeetingPoint);
		tvName = (TextView) view.findViewById(R.id.tvtravellerName);
		tvTime = (TextView) view.findViewById(R.id.tvTime);
		tvWarning.add((TextView) view.findViewById(R.id.tvWarning));
		
		
		tvName.setText(TravellerList.get(position).getName());
		tvMeetingPoint.setText("Meeting Point : "+TravellerList.get(position).getDefaultSource());
		tvTime.setText("Time : "+TimeList.get(TravellerList.get(position).getDefaultSource()));
		
		
		
		
		
		
		
		
		
		
		
		check = (CheckBox) view.findViewById(R.id.cbSelectTraveller);
		
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					if(TravellerList.get(position).getGenderPrefs().equals("At least 1 from same gender"))
					{
						tvWarning.get(position).setVisibility(View.VISIBLE);
						tvWarning.get(position).setText("(At least 2 commuters must be "+TravellerList.get(position).getGender()+")");
						minSameGender++;
					}
					else if(TravellerList.get(position).getGenderPrefs().equals("All from same gender"))
					{
						tvWarning.get(position).setVisibility(View.VISIBLE);
						tvWarning.get(position).setText("(All the commuters must be "+TravellerList.get(position).getGender()+")");
					}				
					
					checkedNum++;
					checked.put(TravellerList.get(position).getUniversityRegNum(),  true);
				} else {
					tvWarning.get(position).setVisibility(View.GONE);
					if(TravellerList.get(position).getGenderPrefs().equals("At least 1 from same gender"))
					{
						minSameGender--;
					}
					checkedNum--;
					checked.put(TravellerList.get(position).getUniversityRegNum(),false);
				}
			}
		});
		check.setChecked(checked.get(TravellerList.get(position).getUniversityRegNum()));
		
		return view;
		
	}

}
