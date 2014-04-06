package com.Visdrotech.VehiclePoolingSystem.Network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.Visdrotech.VehiclePoolingSystem.Core.User;
import com.Visdrotech.VehiclePoolingSystem.Utils.Common_Utilities;

public class HttpPostReq {

	public static String postRegister(User user1) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl
				+ "/register.php");
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableName, user1.getName()));
			nameValuePairs
					.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableEmail, user1
									.getEmail()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableCategory, user1
							.getCategory()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableGender, user1
							.getGender()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableGenderPrefs, user1
							.getGenderPrefs()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariablePhoneNumber, user1
							.getPhoneNumber()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableUniversityRegNum, user1
							.getUniversityRegNum()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariablePassword, user1
							.getPassword()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableSecQues, user1
							.getSecQues()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableSecAns, user1
							.getSecAns()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableDefaultSource, user1
							.getDefaultSource()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableDefaultDestination,
					user1.getDefaultDestination()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.VehicleDetailsVariableVehicleNumber1,
					user1.getVehicle1().getVehicleRegNum()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.VehicleDetailsVariableVehicleMileage1, ""
							+ user1.getVehicle1().getVehicleMilage()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.VehicleDetailsVariableVehicleNumber2,
					user1.getVehicle2().getVehicleRegNum()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.VehicleDetailsVariableVehicleMileage2, ""
							+ user1.getVehicle2().getVehicleMilage()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.VehicleDetailsVariableVehicleNumber3,
					user1.getVehicle3().getVehicleRegNum()));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.VehicleDetailsVariableVehicleMileage3, ""
							+ user1.getVehicle3().getVehicleMilage()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			String responseString = Common_Utilities
					.convertStreamToString(response.getEntity().getContent());
			Log.d("Frrom Net", responseString);

			return responseString;
		} catch (ClientProtocolException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR;
		} catch (IOException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR;
		}
	}

	public static String loginUser(String universityRollNum, String password) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl
				+ "/login.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableUniversityRegNum,
					universityRollNum));
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariablePassword, password));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			String responseString = Common_Utilities
					.convertStreamToString(response.getEntity().getContent());
			Log.d("From Net", responseString);

			return responseString;

		} catch (ClientProtocolException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		} catch (IOException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		}
	}

	public static String getSourcesInfo() {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl
				+ "/listOfSources.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("List", "GetList"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			String responseString = Common_Utilities
					.convertStreamToString(response.getEntity().getContent());
			Log.d("From Net", responseString);
			return responseString;
		} catch (ClientProtocolException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		} catch (IOException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		}

	}

	public static String forgotPasswordPost(String universityRollNum) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl+
				"/forgotPassword.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("UniversityRegNum",
					universityRollNum));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			String responseString = Common_Utilities
					.convertStreamToString(response.getEntity().getContent());
			Log.d("From Net", responseString);

			return responseString;

		} catch (ClientProtocolException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		} catch (IOException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		}
	}

	public static String updateDetails(User user1) {
		// Create a new HttpClient and Post Header
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl
						+ "/modify.php");

				try {
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableName, user1.getName()));
					nameValuePairs
							.add(new BasicNameValuePair(
									Common_Utilities.UserDetailsVariableEmail, user1
											.getEmail()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableCategory, user1
									.getCategory()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableGender, user1
									.getGender()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableGenderPrefs, user1
									.getGenderPrefs()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariablePhoneNumber, user1
									.getPhoneNumber()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableUniversityRegNum, user1
									.getUniversityRegNum()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariablePassword, user1
									.getPassword()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableSecQues, user1
									.getSecQues()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableSecAns, user1
									.getSecAns()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableDefaultSource, user1
									.getDefaultSource()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.UserDetailsVariableDefaultDestination,
							user1.getDefaultDestination()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.VehicleDetailsVariableVehicleNumber1,
							user1.getVehicle1().getVehicleRegNum()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.VehicleDetailsVariableVehicleMileage1, ""
									+ user1.getVehicle1().getVehicleMilage()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.VehicleDetailsVariableVehicleNumber2,
							user1.getVehicle2().getVehicleRegNum()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.VehicleDetailsVariableVehicleMileage2, ""
									+ user1.getVehicle2().getVehicleMilage()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.VehicleDetailsVariableVehicleNumber3,
							user1.getVehicle3().getVehicleRegNum()));
					nameValuePairs.add(new BasicNameValuePair(
							Common_Utilities.VehicleDetailsVariableVehicleMileage3, ""
									+ user1.getVehicle3().getVehicleMilage()));

					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);
					String responseString = Common_Utilities
							.convertStreamToString(response.getEntity().getContent());
					Log.d("Frrom Net", responseString);

					return responseString;
				} catch (ClientProtocolException e) {
					return Common_Utilities.CODE_USER_VALIDATION_ERROR;
				} catch (IOException e) {
					return Common_Utilities.CODE_USER_VALIDATION_ERROR;
				}
	}

	public static String getVehicleOwnerInfoForTraveller(
			String universityRollNum) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl
				+ "/travellerSecond.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableUniversityRegNum,
					universityRollNum));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			String responseString = Common_Utilities
					.convertStreamToString(response.getEntity().getContent());
			Log.d("From Net", responseString);

			return responseString;

		} catch (ClientProtocolException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		} catch (IOException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		}
	}

	public static String sendSelectedTravellers(String OwnerUniRegNum,
			String TravellersUniRegNum, String TravellersFare, String vehicleNum) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl
				+ "/carOwnerReturn.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("carOwner",
					OwnerUniRegNum));
			nameValuePairs.add(new BasicNameValuePair("travellers",
					TravellersUniRegNum));
			nameValuePairs.add(new BasicNameValuePair("fares",
					TravellersFare));
			nameValuePairs.add(new BasicNameValuePair("carno",
					vehicleNum));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			String responseString = Common_Utilities
					.convertStreamToString(response.getEntity().getContent());
			Log.d("From Net", responseString);

			return responseString;

		} catch (ClientProtocolException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		} catch (IOException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		}
	}

	public static String sendTravellerSourceAndTime(String UniRegNum,
			String Time, String Source) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl
				+ "/travellerFirst.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("UniversityRegNum",
					UniRegNum));
			nameValuePairs.add(new BasicNameValuePair("Time", Time));
			nameValuePairs.add(new BasicNameValuePair("Source", Source));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			String responseString = Common_Utilities
					.convertStreamToString(response.getEntity().getContent());
			Log.d("From Net n ", responseString);

			return responseString;

		} catch (ClientProtocolException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		} catch (IOException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		}
	}

	public static String getTravellerListToSelect(String UniRegNum,
			String Source, String Time, String GenderPref) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl
				+ "/carOwnerFirst.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableUniversityRegNum,
					UniRegNum));
			nameValuePairs.add(new BasicNameValuePair("source", Source));
			nameValuePairs.add(new BasicNameValuePair("time", Time));
			nameValuePairs
					.add(new BasicNameValuePair("GenderPrefs", GenderPref));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			String responseString = Common_Utilities
					.convertStreamToString(response.getEntity().getContent());
			Log.d("From Net n ", responseString);

			return responseString;

		} catch (ClientProtocolException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		} catch (IOException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		}
	}
	
	
	
	
	public static String getSelectedTravellerList(String UniRegNum) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Common_Utilities.BaseUrl
				+ "/carOwnerSecond.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(
					Common_Utilities.UserDetailsVariableUniversityRegNum,
					UniRegNum));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			String responseString = Common_Utilities
					.convertStreamToString(response.getEntity().getContent());
			Log.d("From Net n ", responseString);

			return responseString;

		} catch (ClientProtocolException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		} catch (IOException e) {
			return Common_Utilities.CODE_USER_VALIDATION_ERROR + " : "
					+ e.getMessage();
		}
	}

}
