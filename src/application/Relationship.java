package application;

import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class Relationship {
	private String ownerID;
	private String ID;
	private String relationship;
	private String name;
	
	public Relationship(String ownerID, String ID, String relationship) {
		this.ownerID = ownerID;
		this.ID = ID;
		this.relationship = relationship;
	}
	
	public String getOwnerID() {
		return this.ownerID;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public String getRelationship() {
		return this.relationship;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Relationship [ownerID=" + ownerID + ", ID=" + ID + ", relationship=" + relationship + ", name=" + name
				+ "]";
	}
	
	
}
