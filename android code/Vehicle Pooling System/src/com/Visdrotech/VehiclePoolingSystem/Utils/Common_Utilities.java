package com.Visdrotech.VehiclePoolingSystem.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.Visdrotech.VehiclePoolingSystem.Core.User;

public class Common_Utilities {
	
	public static String UserDetailsVariableName = "Name";
	public static String UserDetailsVariableGender = "Gender";
	public static String UserDetailsVariableCategory = "Category";
	public static String UserDetailsVariableEmail = "Email";
	public static String UserDetailsVariableGenderPrefs = "GenderPrefs";
	public static String UserDetailsVariablePhoneNumber = "PhoneNumber";
	public static String UserDetailsVariableUniversityRegNum = "UniversityRegNum";
	public static String UserDetailsVariablePassword = "Password";
	public static String UserDetailsVariableDefaultSource = "DefaultSource";
	public static String UserDetailsVariableDefaultDestination = "DefaultDestination";
	public static String UserDetailsVariableSecQues = "SecQues";
	public static String UserDetailsVariableSecAns = "SecAns";
	
	public static String VehicleDetailsVariableVehicleNumber1 = "VehicleNumber1";
	public static String VehicleDetailsVariableVehicleMileage1 = "VehicleMileage1";	
	public static String VehicleDetailsVariableVehicleNumber2 = "VehicleNumber2";
	public static String VehicleDetailsVariableVehicleMileage2 = "VehicleMileage2";	
	public static String VehicleDetailsVariableVehicleNumber3 = "VehicleNumber3";
	public static String VehicleDetailsVariableVehicleMileage3 = "VehicleMileage3";
	
	
	public static String BaseUrl = "http://192.168.163.1/vps/php";
	
	
	public static String UserDetailsSharedPrefsIsRemembered = "IsRemebered";
	
	public static final String fileNameSharedPrefsUserDetails = "UserDetailsPref";
	
	
	public static final String fileNameSharedPrefsTravellerSelectDetails = "TravellerSelectDetails";
	
	public static String  SharedPrefsVariableTravelerSelected= "TravellerSelected";
	public static String  SharedPrefsVariableTravellerSourceSelected= "TravellerSelected";
	
	
	public static final String CODE_USER_VALIDATION_SUCCESS = "1";
	public static final String CODE_USER_VALIDATION_ERROR = "0";
	public static final String CODE_USER_VALIDATION_WRONG_DETAILS = "2";
	public static final String CODE_USER_VALIDATION_DEFAULT= "3";
	
	public static String byteArrayToString(byte[] from) {
		String result = "";
		try {
			if(from != null)
				result = new String(from, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static byte[] stringToByteArray(String arg) {
		if(arg == null || arg.length() == 0)
			return new byte[0];
	    String hex = String.format("%040x", new BigInteger(arg.getBytes()));
	    BigInteger bi = new BigInteger(hex, 16);
     	byte[] bytes = bi.toByteArray();
     	return bytes;
	}
	

	// Convert the response into a string from response.
	public static String convertStreamToString(InputStream inputStream)
			throws IOException {
		if (inputStream != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				inputStream.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}
	
	public static String getAllUserDetailsInAString(User user)
	{
		return user.getName()+" "+user.getCategory()+" "+user.getEmail()+" "+user.getGender()+" "+user.getGenderPrefs()+" "+user.getPhoneNumber()+" "+user.getUniversityRegNum()+" "+user.getPassword()+" "+user.getSecQues()+" "+user.getSecAns()+" "+user.getDefaultDestination()+" "+user.getDefaultSource()+" "+user.getVehicle1().getVehicleRegNum()+" "+user.getVehicle1().getVehicleMilage()+" "+user.getVehicle2().getVehicleRegNum()+" "+user.getVehicle2().getVehicleMilage()+" "+user.getVehicle3().getVehicleRegNum()+" "+user.getVehicle3().getVehicleMilage();
	}
	
	public static void saveDataToSharedPrefs(User user, Boolean isRemembered, SharedPreferences sharedPrefs) {
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString(Common_Utilities.UserDetailsVariableName, user.getName());
		editor.putString(Common_Utilities.UserDetailsVariableEmail, user.getEmail());
		editor.putString(Common_Utilities.UserDetailsVariableUniversityRegNum, user.getUniversityRegNum());
		editor.putString(Common_Utilities.UserDetailsVariableCategory, user.getCategory());
		editor.putString(Common_Utilities.UserDetailsVariableGender, user.getGender());
		editor.putString(Common_Utilities.UserDetailsVariableGenderPrefs, user.getGenderPrefs());
		editor.putString(Common_Utilities.UserDetailsVariablePhoneNumber, user.getPhoneNumber());
		editor.putString(Common_Utilities.UserDetailsVariablePassword, user.getPassword());
		editor.putBoolean(Common_Utilities.UserDetailsSharedPrefsIsRemembered, isRemembered);
		editor.putString(Common_Utilities.UserDetailsVariableSecQues, user.getSecQues());
		editor.putString(Common_Utilities.UserDetailsVariableSecAns, user.getSecAns());
		editor.putString(Common_Utilities.UserDetailsVariableDefaultSource, user.getDefaultSource());
		editor.putString(Common_Utilities.UserDetailsVariableDefaultDestination, user.getDefaultDestination());
		editor.putString(Common_Utilities.VehicleDetailsVariableVehicleNumber1, user.getVehicle1().getVehicleRegNum());
		editor.putFloat(Common_Utilities.VehicleDetailsVariableVehicleMileage1, (float) user.getVehicle1().getVehicleMilage());
		editor.putString(Common_Utilities.VehicleDetailsVariableVehicleNumber2, user.getVehicle2().getVehicleRegNum());
		editor.putFloat(Common_Utilities.VehicleDetailsVariableVehicleMileage2, (float) user.getVehicle2().getVehicleMilage());
		editor.putString(Common_Utilities.VehicleDetailsVariableVehicleNumber3, user.getVehicle3().getVehicleRegNum());
		editor.putFloat(Common_Utilities.VehicleDetailsVariableVehicleMileage3, (float) user.getVehicle3().getVehicleMilage());
		editor.commit();
	}
	
	
	
	public static boolean haveInternet(Context ctx) {

		ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		
		
//	    NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
//	            .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

	    if (!mWifi.isConnected() ){
//	    	|| info == null || !info.isConnected()) {
	        return false;
	    }
	    
	    return true;
	}
}
