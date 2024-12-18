package application;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Vehicle {
	private String vehicleID;
	private String vehicleAptID;
	private String type;
	private CheckBox select;
	
	public Vehicle(String vehicleAptID, String vehicleID, String type) {
		this.vehicleAptID = vehicleAptID;
		this.vehicleID = vehicleID;
		this.type = type;
		this.select = new CheckBox();
	}
	
	public String getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}
	public String getVehicleAptID() {
		return vehicleAptID;
	}
	public void setVehicleAptID(String aptID) {
		this.vehicleAptID = aptID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public CheckBox getSelect() {
		return select;
	}

	public void setSelect(CheckBox select) {
		this.select = select;
	}
	
}
