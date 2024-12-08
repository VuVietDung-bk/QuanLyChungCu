package application;

import javafx.scene.control.CheckBox;

public class Fee {
	String aptID;
	String typeFee;
	String isForced;
	String status;
	int amount;
	CheckBox check;
	
	public Fee(String aptID, String typeFee, String isForced, int amount) {
		this.aptID = aptID;
		this.typeFee = typeFee;
		this.isForced = isForced;
		this.amount = amount;
		this.status = "Chua thanh toan";
		this.check = new CheckBox();
	}
	
	public Fee(String aptID, String typeFee, String isForced, String status, int amount) {
		this.aptID = aptID;
		this.typeFee = typeFee;
		this.isForced = isForced;
		this.amount = amount;
		this.status = status;
		this.check = new CheckBox();
	}
	
	public String getAptID() {
		return this.aptID;
	}
	
	public String getTypeFee() {
		return this.typeFee;
	}
	
	public String getIsForced() {
		return this.isForced;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public CheckBox getCheck() {
		return this.check;
	}
	
	public void setCheck(CheckBox check) {
		this.check = check;
	}
}
