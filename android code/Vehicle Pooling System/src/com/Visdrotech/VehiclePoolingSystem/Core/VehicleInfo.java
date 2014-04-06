package com.Visdrotech.VehiclePoolingSystem.Core;

public class VehicleInfo {
private String VehicleRegNum;
private double VehicleMilage;

public VehicleInfo()
{
	VehicleMilage = 0;
	VehicleRegNum = "NIL";
}
public String getVehicleRegNum() {
	return VehicleRegNum;
}
public void setVehicleRegNum(String vehicleRegNum) {
	VehicleRegNum = vehicleRegNum;
}
public double getVehicleMilage() {
	return VehicleMilage;
}
public void setVehicleMilage(double vehicleMilage) {
	VehicleMilage = vehicleMilage;
}
}
