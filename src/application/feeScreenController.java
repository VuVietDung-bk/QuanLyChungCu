package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import database.DatabaseConnecter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class feeScreenController implements Initializable {
	
	Stage stage;
	Scene scene;
	
	static int type = 0;
	
	@FXML
	private Pane findAptPane;
	@FXML
	private TextField aptID;
	@FXML
	private Pane ComputeFee;
	@FXML
	private Pane feeLivingPane;
	@FXML
	private Pane addOtherFeePane;
	@FXML
	private ChoiceBox<String> choiceBox;
	@FXML
	private TextField currentElecText;
	@FXML
	private TextField currentWaterText;
	@FXML
	private Label aptIDLabel;
	@FXML
	private Label ownerNameLabel;
	@FXML
	private Label phoneLabel;
	@FXML
	private Label curElecLabel;
	@FXML
	private Label curWaterLabel;
	@FXML
	private Label areaLabel;
	@FXML
	private Label countCarLabel;
	@FXML
	private Label countMotorbikeLabel;
	@FXML
	private Label feeElecLabel;
	@FXML
	private Label feeWaterLabel;
	@FXML
	private Label feeServiceLabel;
	@FXML
	private Label feeParkingLabel;
	@FXML 
	private Label totalFeeLabel;
	@FXML
	private TextField otherFeeDetail;
	@FXML
	private TextField otherFeeAmount;
	@FXML
	private Label isForce;
	@FXML
	private CheckBox isForceCheck;
	@FXML
	private Button find;
	
	ObservableList<Apartment> dataApt;
    ObservableList<Relationship> dataRela;
    ObservableList<Resident> dataResident;
    ObservableList<Vehicle> dataVehicle;
    ObservableList<Fee> dataFee;
	
	String[] Choice = {"Thu phi sinh hoat", "Thu phi dong gop"};
	String currentChoice;
	
	static Apartment usingApt;
	static int countMotorbike = 0;
	static int countCar = 0;
	static int amount;
	static String isForced = "Khong bat buoc";
	static String detail;
	
	
	public void select(ActionEvent e) {
		currentChoice = choiceBox.getValue();
		if(currentChoice.equals("Thu phi sinh hoat")){
			feeLivingPane.setVisible(true);
			feeLivingPane.setDisable(false);
			addOtherFeePane.setVisible(false);
			addOtherFeePane.setDisable(true);
			type = 0;
			
		}else if(currentChoice.equals("Thu phi dong gop")) {
			feeLivingPane.setVisible(false);
			feeLivingPane.setDisable(true);
			addOtherFeePane.setVisible(true);
			addOtherFeePane.setDisable(false);
			type = 1;
		}
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataApt = DatabaseConnecter.getApartmentsData();
    	
    	dataRela = DatabaseConnecter.getRelationshipsData();
    	
    	dataResident = DatabaseConnecter.getResidentsData();
    	
    	dataVehicle = DatabaseConnecter.getVehiclesData();
    	
    	dataFee = DatabaseConnecter.getAllFees();
    	
    	find.setOnAction(e -> {
			for(Apartment apt : dataApt) {
				if(aptID.getText().equals(apt.getAptID())) {
					usingApt = apt;
					ComputeFee.setVisible(true);
					ComputeFee.setDisable(false);
					findAptPane.setVisible(false);
					
					for(Vehicle vehicle : dataVehicle) {
						if(apt.getAptID().equals(vehicle.getVehicleAptID())) {
							if(vehicle.getType().equals("O to")) {
								countCar++;
							} else if(vehicle.getType().equals("Xe may")) {
								countMotorbike++;
							}
						}
					}
					
					aptIDLabel.setText("Can ho: "+apt.getAptID());
					ownerNameLabel.setText(findName(apt.getOwnerID()));
					phoneLabel.setText(findPhone(apt.getOwnerID()));
					curElecLabel.setText(String.valueOf(apt.getElec()));
					curWaterLabel.setText(String.valueOf(apt.getWater()));
					areaLabel.setText(String.valueOf(apt.getArea()));
					countCarLabel.setText("So oto: "+countCar);
					countMotorbikeLabel.setText("So xe may: "+countMotorbike);
				}
			}
    	});
    	
		choiceBox.getItems().addAll(Choice);
		choiceBox.setOnAction(this::select);
	}
	
	public void pressEnter(KeyEvent e) {
		if(type == 0) {
				
			if(currentElecText.getText().isEmpty() || currentWaterText.getText().isEmpty()) {
				return;
			}
			int curElec, curWater;
			curElec = Integer.parseInt(currentElecText.getText());
			curWater = Integer.parseInt(currentWaterText.getText());
			
			int lastElec, lastWater;
			lastElec = usingApt.getElec();
			lastWater = usingApt.getWater();
			
			int feeElec, feeWater;
			feeElec = (curElec - lastElec)*3500;
			feeWater = (curElec - lastWater)*25000;
			
			feeElecLabel.setText("Tien dien: "+String.valueOf(feeElec));
			feeElecLabel.setVisible(true);
			feeWaterLabel.setText("Tien nuoc: "+String.valueOf(feeWater));
			feeWaterLabel.setVisible(true);
				
				
			int feeParking;
			
				
			feeParking = countCar*1200000+countMotorbike*200000;
			feeParkingLabel.setText("Phi do xe: "+String.valueOf(feeParking));
			feeParkingLabel.setVisible(true);
			
			int feeService;
			feeService = usingApt.getArea()*100000;
			feeServiceLabel.setText("Phi dich vu: "+String.valueOf(feeService));
			feeServiceLabel.setVisible(true);
			
			amount = feeElec + feeWater + feeParking + feeService;
			totalFeeLabel.setText("Tong tien: "+String.valueOf(amount));
			isForced = "Bat buoc";
		} else if(type == 1) {
			detail = otherFeeDetail.getText();
			amount = Integer.parseInt(otherFeeAmount.getText());
			if(isForceCheck.isSelected()) {
				isForced = "Bat buoc";
			}else {
				isForced = "Khong bat buoc";
			}
		}
	}
	
	public void switchToInfoScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("InformationScreen.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		StyleManager.applyStyle(scene);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public void switchToFeeManagementScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("FeeManagementScreen.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		StyleManager.applyStyle(scene);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public void switchToAccountScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AccountScreen.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		StyleManager.applyStyle(scene);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public void Logout(ActionEvent e) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		StyleManager.applyStyle(scene);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public void sendMessage(ActionEvent e) {
		pressEnter(null);
		String typeFee;
		if(type == 0) {
			typeFee = "Phi sinh hoat";
		}else {
			typeFee = detail;
		}
		Fee newFee = new Fee(usingApt.getAptID(), typeFee, isForced, amount, Date.valueOf(LocalDate.now()), null);
		int forced = isForced.equals("Bat buoc")? 1 : 0;
		if(DatabaseConnecter.insertFee(usingApt.getAptID(), typeFee, forced, 0, amount, Date.valueOf(LocalDate.now()), null)) {
			dataFee.add(newFee);
		}
		
	}
	
	public void back(ActionEvent e) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("FeeScreen.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		StyleManager.applyStyle(scene);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
    }
	
	public String findName(String ID) {
		Resident res = DatabaseConnecter.getResidentByOwnerID(ID);
		return res.getResidentName();
	}
    
    public String findPhone(String ID) {
    	Resident res = DatabaseConnecter.getResidentByOwnerID(ID);
		return res.getResidentPhone();
	}
}
