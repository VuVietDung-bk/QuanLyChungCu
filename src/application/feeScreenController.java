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
import javafx.scene.control.Alert;
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
	static int all = 0;
	
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
	private Label feeManagementLabel;
	@FXML 
	private Label totalFeeLabel;
	@FXML
	private TextField otherFeeDetail;
	@FXML
	private TextField otherFeeAmount;
	@FXML
	private TextField validDayText;
	@FXML
	private Label isForce;
	@FXML
	private CheckBox isForceCheck;
	@FXML
	private Button find;
	@FXML
	private Pane addForAllApt;
	@FXML
	private TextField addAllDetail;
	@FXML
	private TextField addAllAmount;
	@FXML
	private TextField addAllValidDay;
	@FXML
	private CheckBox addAllCheck;
	
	ObservableList<Apartment> dataApt;
    ObservableList<Relationship> dataRela;
    ObservableList<Resident> dataResident;
    ObservableList<Vehicle> dataVehicle;
    ObservableList<Fee> dataFee;
	
	String[] Choice = {"Thu phí sinh hoạt", "Thu phí đóng góp"};
	String currentChoice;
	
	static Apartment usingApt;
	static int countMotorbike = 0;
	static int countCar = 0;
	static int amount;
	static String isForced = "Không bắt buộc";
	static String detail;
	Date today = Date.valueOf(LocalDate.now());
	
	public void select(ActionEvent e) {
		currentChoice = choiceBox.getValue();
		if(currentChoice.equals("Thu phí sinh hoạt")){
			feeLivingPane.setVisible(true);
			feeLivingPane.setDisable(false);
			addOtherFeePane.setVisible(false);
			addOtherFeePane.setDisable(true);
			type = 0;
			
		}else if(currentChoice.equals("Thu phí đóng góp")) {
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
				if(aptID.getText().equalsIgnoreCase(apt.getAptID())) {
					usingApt = apt;
					ComputeFee.setVisible(true);
					ComputeFee.setDisable(false);
					findAptPane.setVisible(false);
					all = 0;
					
					for(Vehicle vehicle : dataVehicle) {
						if(apt.getAptID().equals(vehicle.getVehicleAptID())) {
							if(vehicle.getType().equals("Car")) {
								countCar++;
							} else if(vehicle.getType().equals("Bike")) {
								countMotorbike++;
							}
						}
					}
					
					aptIDLabel.setText("Căn hộ: "+apt.getAptID());
					ownerNameLabel.setText(findName(apt.getOwnerID()));
					phoneLabel.setText(findPhone(apt.getOwnerID()));
					curElecLabel.setText(String.valueOf(apt.getElec()));
					curWaterLabel.setText(String.valueOf(apt.getWater()));
					areaLabel.setText(String.valueOf(apt.getArea()));
					countCarLabel.setText("Số ô tô: "+countCar);
					countMotorbikeLabel.setText("Số xe máy: "+countMotorbike);
				}
			}
    	});
    	
		choiceBox.getItems().addAll(Choice);
		choiceBox.setOnAction(this::select);
	}
	
	int calculateElectricFee(int consumption) {
	    int fee = 0;
	    if (consumption <= 50) {
	        fee = consumption * 1678;
	    } else if (consumption <= 100) {
	        fee = 50 * 1678 + (consumption - 50) * 1734;
	    } else if (consumption <= 200) {
	        fee = 50 * 1678 + 50 * 1734 + (consumption - 100) * 2014;
	    } else if (consumption <= 300) {
	        fee = 50 * 1678 + 50 * 1734 + 100 * 2014 + (consumption - 200) * 2536;
	    } else if (consumption <= 400) {
	        fee = 50 * 1678 + 50 * 1734 + 100 * 2014 + 100 * 2536 + (consumption - 300) * 2834;
	    } else {
	        fee = 50 * 1678 + 50 * 1734 + 100 * 2014 + 100 * 2536 + 100 * 2834 + (consumption - 400) * 2927;
	    }
	    return fee;
	}

	int calculateWaterFee(int consumption) {
	    int fee = 0;
	    if (consumption <= 10) {
	        fee = consumption * 6700;
	    } else if (consumption <= 20) {
	        fee = 10 * 6700 + (consumption - 10) * 7900;
	    } else {
	        fee = 10 * 6700 + 10 * 7900 + (consumption - 20) * 9400;
	    }
	    return fee;
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

			int feeElec = calculateElectricFee(curElec - lastElec);
			int feeWater = calculateWaterFee(curWater - lastWater);

			feeElecLabel.setText("Tiền điện: " + String.format("%,d VNĐ", feeElec));
			feeElecLabel.setVisible(true);

			feeWaterLabel.setText("Tiền nước: " + String.format("%,d VNĐ", feeWater));
			feeWaterLabel.setVisible(true);
				
			int feeParking;
			feeParking = countCar*1200000+countMotorbike*70000;
			feeParkingLabel.setText("Phí đỗ xe: "+String.format("%,d VNĐ", feeParking));
			feeParkingLabel.setVisible(true);
			
			int feeService;
			int feeServicePerM2;
			if(usingApt.getArea()>100) {
				feeServicePerM2 = 16500;
			} else if(usingApt.getArea()>80) {
				feeServicePerM2 = 13000;
			} else if(usingApt.getArea()>60) {
				feeServicePerM2 = 10000;
			} else if(usingApt.getArea()>40) {
				feeServicePerM2 = 7500;
			} else if(usingApt.getArea()>20) {
				feeServicePerM2 = 5000;
			} else {
				feeServicePerM2 = 2500;
			}
			feeService = usingApt.getArea()*feeServicePerM2;
			feeServiceLabel.setText("Phí dịch vụ: "+String.format("%,d VNĐ", feeService));
			feeServiceLabel.setVisible(true);
			
			int feeManagement;
			feeManagement = usingApt.getArea()*7000;
			feeManagementLabel.setText("Tiền phí quản lý: "+String.format("%,d VNĐ", feeManagement));
			
			amount = feeElec + feeWater + feeParking + feeService;
			totalFeeLabel.setText("Tổng tiền: "+String.format("%,d VNĐ", amount));
			isForced = "Bắt buộc";
		} 
	}
	
	private void showAlert(String message) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Thông báo");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	public void sendMessage(ActionEvent e) {
		pressEnter(null);
		String typeFee;
		Date dueDate;
		if(type == 0) {
			if(currentElecText.getText().isEmpty() || currentWaterText.getText().isEmpty()) {
				//hien thong bao
				showAlert("Vui lòng nhập đủ thông tin cho phí sinh hoạt.");
				return;
			}
			typeFee = "Phí sinh hoạt";
			dueDate = Date.valueOf(LocalDate.now().plusDays(15));
			Fee newFee = new Fee(usingApt.getAptID(), typeFee, isForced, amount, today, dueDate);
			int forced = isForced.equals("Bắt buộc")? 1 : 0;
			if(DatabaseConnecter.insertFee(usingApt.getAptID(), typeFee, forced, 0, amount, Date.valueOf(LocalDate.now()), dueDate)) {
				dataFee.add(newFee);
				DatabaseConnecter.updateElecWater(usingApt.getAptID(), Integer.valueOf(currentElecText.getText()), Integer.valueOf(currentWaterText.getText()));
				curElecLabel.setText(currentElecText.getText());
				curWaterLabel.setText(currentWaterText.getText());
			}
			feeElecLabel.setText("Tiền điện: ");
			feeWaterLabel.setText("Tiền nước: ");
			feeParkingLabel.setText("Phí đỗ xe: ");
			feeServiceLabel.setText("Phí dịch vụ: ");
			feeManagementLabel.setText("Tiền phí quản lý: ");
			totalFeeLabel.setText("Tổng tiền: ");
			currentElecText.clear();
			currentWaterText.clear();
		} else if(type == 1 && all == 0){
			if(otherFeeDetail.getText().isEmpty() || otherFeeAmount.getText().isEmpty() || validDayText.getText().isEmpty()) {
				//hien thong bao
				showAlert("Vui lòng nhập đủ thông tin cho phí khác.");
				return;
			}
			
				amount = Integer.parseInt(otherFeeAmount.getText());
				if(isForceCheck.isSelected()) {
					isForced = "Bắt buộc";
				}else {
					isForced = "Không bắt buộc";
				}
			typeFee = otherFeeDetail.getText();
			try {
	            int validDay = Integer.parseInt(validDayText.getText());
	            dueDate = Date.valueOf(LocalDate.now().plusDays(validDay));
	        } catch (NumberFormatException ex) {
	            showAlert("Số ngày hợp lệ phải là số nguyên.");
	            return;
	        }
			Fee newFee = new Fee(usingApt.getAptID(), typeFee, isForced, amount, today, dueDate);
			int forced = isForced.equals("Bắt buộc")? 1 : 0;
			if(DatabaseConnecter.insertFee(usingApt.getAptID(), typeFee, forced, 0, amount, Date.valueOf(LocalDate.now()), dueDate)) {
				dataFee.add(newFee);
			}
			otherFeeDetail.clear();
			otherFeeAmount.clear();
			validDayText.clear();
			isForceCheck.setSelected(false);
		} else if(type == 1 && all == 1) {
			if(addAllDetail.getText().isEmpty() || addAllAmount.getText().isEmpty() || addAllValidDay.getText().isEmpty()) {
				//hien thong bao
				showAlert("Vui lòng nhập đủ thông tin cho phí khác.");
				return;
			}
			
				amount = Integer.parseInt(addAllAmount.getText());
				if(addAllCheck.isSelected()) {
					isForced = "Bắt buộc";
				}else {
					isForced = "Không bắt buộc";
				}
			typeFee = addAllDetail.getText();
			try {
	            int validDay = Integer.parseInt(addAllValidDay.getText());
	            dueDate = Date.valueOf(LocalDate.now().plusDays(validDay));
	        } catch (NumberFormatException ex) {
	            showAlert("Số ngày hợp lệ phải là số nguyên.");
	            return;
	        }
			int forced = isForced.equals("Bắt buộc")? 1 : 0;
			for(Apartment apt : dataApt) {
				DatabaseConnecter.insertFee(apt.getAptID(), typeFee, forced, 0, amount, Date.valueOf(LocalDate.now()), dueDate);
			}
			addAllDetail.clear();
			addAllAmount.clear();
			addAllValidDay.clear();
			addAllCheck.setSelected(false);
		}
	}
	
	public void addNewFeeForAllApt(ActionEvent e) {
		type = 1;
		all = 1;
		addForAllApt.setVisible(true);
		findAptPane.setVisible(false);
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