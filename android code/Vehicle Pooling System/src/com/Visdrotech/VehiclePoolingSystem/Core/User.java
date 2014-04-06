package com.Visdrotech.VehiclePoolingSystem.Core;

public class User {
private String Email;
private String Password;
private String Name;
private String PhoneNumber;
private String UniversityRegNum;
private String Gender;
private String GenderPrefs;
private String Category;
private String DefaultSource;
private String DefaultDestination = "Delhi Technological University";
private String SecQues;
private String SecAns;
private VehicleInfo vehicle1;
private VehicleInfo vehicle2;
private VehicleInfo vehicle3;

public User(){
	vehicle1 = new VehicleInfo();
	vehicle2 = new VehicleInfo();
	vehicle3 = new VehicleInfo();
	vehicle1.setVehicleMilage(0);
	vehicle1.setVehicleRegNum("NIL");
	vehicle2.setVehicleMilage(0);
	vehicle2.setVehicleRegNum("NIL");
	vehicle3.setVehicleMilage(0);
	vehicle3.setVehicleRegNum("NIL");
}


public String getSecQues() {
	return SecQues;
}


public void setSecQues(String secQues) {
	SecQues = secQues;
}


public String getSecAns() {
	return SecAns;
}


public void setSecAns(String secAns) {
	SecAns = secAns;
}


public VehicleInfo getVehicle1() {
	return vehicle1;
}


public void setVehicle1(VehicleInfo vehicle1) {
	this.vehicle1 = vehicle1;
}


public VehicleInfo getVehicle2() {
	return vehicle2;
}


public void setVehicle2(VehicleInfo vehicle2) {
	this.vehicle2 = vehicle2;
}


public VehicleInfo getVehicle3() {
	return vehicle3;
}


public void setVehicle3(VehicleInfo vehicle3) {
	this.vehicle3 = vehicle3;
}


public String getDefaultSource() {
	return DefaultSource;
}

public String getDefaultDestination() {
	return DefaultDestination;
}
public void setDefaultSource(String defaultSource) {
	DefaultSource = defaultSource;
}
public String getGender() {
	return Gender;
}
public void setGender(String gender) {
	Gender = gender;
}
public String getGenderPrefs() {
	return GenderPrefs;
}
public void setGenderPrefs(String genderPrefs) {
	GenderPrefs = genderPrefs;
}
public String getCategory() {
	return Category;
}
public void setCategory(String category) {
	Category = category;
}
public String getEmail() {
	return Email;
}
public void setEmail(String email) {
	Email = email;
}
public String getPassword() {
	return Password;
}
public void setPassword(String password) {
	Password = password;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public String getPhoneNumber() {
	return PhoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
	PhoneNumber = phoneNumber;
}
public String getUniversityRegNum() {
	return UniversityRegNum;
}
public void setUniversityRegNum(String universityRegNum) {
	UniversityRegNum = universityRegNum;
}

}
