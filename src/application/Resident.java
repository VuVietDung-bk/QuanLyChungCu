package application;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Resident {
	private String residentID;
	private String residentName;
	private String residentPhone;
	private Button addAccount;
	private CheckBox select;
	
	public Resident(String residentID, String residentName) {
		this.residentID = residentID;
		this.residentName = residentName;
		this.residentPhone = "";
	}
	
	public Resident(String residentID, String residentName, String residentPhone) {
		this.residentID = residentID;
		this.residentName = residentName;
		this.residentPhone = residentPhone;
	}
	
	public String getResidentID() {
		return residentID;
	}
	public void setResidentID(String residentID) {
		this.residentID = residentID;
	}
	public String getResidentPhone() {
		return residentPhone;
	}
	public void setResidentPhone(String residentPhone) {
		this.residentPhone = residentPhone;
	}
	public String getResidentName() {
		return residentName;
	}
	public void setResidentName(String residentName) {
		this.residentName = residentName;
	}

	@Override
	public String toString() {
		return "Resident [residentID=" + residentID + ", residentName=" + residentName + ", residentPhone="
				+ residentPhone + "]";
	}

	public Button getAddAccount() {
		return addAccount;
	}

	public void setAddAccount(Button addAccount) {
		this.addAccount = addAccount;
	}

	public CheckBox getSelect() {
		return select;
	}

	public void setSelect(CheckBox select) {
		this.select = select;
	}
}
