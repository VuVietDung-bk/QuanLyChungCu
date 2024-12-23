package application;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class Apartment {
	private String aptID;
	private String ownerID;
	private int vehicle;
	private int elec;
	private int water;
	private int area;
	private CheckBox select;
	private Button detail;
	
	public Apartment(String aptID, String ownerID, int area) {
		this.aptID = aptID;
		this.ownerID = ownerID;
		this.vehicle = 0;
		this.elec = 0;
		this.water = 0;
		this.area = area;
		this.select = new CheckBox();
		this.detail = new Button("Detail");
	}
	
	public Apartment(String aptID, String ownerID, int vehicle, int area) {
		this.aptID = aptID;
		this.ownerID = ownerID;
		this.vehicle = vehicle;
		this.elec = 0;
		this.water = 0;
		this.area = area;
		this.select = new CheckBox();
		this.detail = new Button("Detail");
	}
	
	public Apartment(String aptID, String ownerID, int vehicle, int elec, int water, int area) {
		this.aptID = aptID;
		this.ownerID = ownerID;
		this.vehicle = vehicle;
		this.elec = elec;
		this.water = water;
		this.area = area;
		this.select = new CheckBox();
		this.detail = new Button("Detail");
	}
	
	public String getAptID() {
		return this.aptID;
	}
	
	public String getOwnerID() {
		return this.ownerID;
	}

	public int getVehicle() {
		return this.vehicle;
	}
	
	public int getElec() {
		return this.elec;
	}
	
	public int getWater() {
		return this.water;
	}
	
	public int getArea() {
		return this.area;
	}
	
	public CheckBox getSelect() {
		return this.select;
	}
	
	public void setSelect(CheckBox select) {
		this.select = select;
	}
	
	public Button getDetail() {
		return this.detail;
	}
}
