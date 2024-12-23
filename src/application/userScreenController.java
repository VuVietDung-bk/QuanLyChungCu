package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import database.DatabaseConnecter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class userScreenController implements Initializable {

	Stage stage;
	Scene scene;

	Account userAcc = new LoginController().getUsingAccount();
	@FXML
	private Pane userAptInfoPane;
	@FXML
	private Pane userAccountPane;
	@FXML
	private Label userAptID;
	@FXML
	private Label userOwnerName;
	@FXML
	private Label userOwnerID;
	@FXML
	private Label userOwnerPhone;
	@FXML
	private Label userArea;
	@FXML
	private Label userElec;
	@FXML
	private Label userWater;
	@FXML
	private TableView userVehicleTableView;
	@FXML
	private TableView userRelaTableView;
	@FXML
	private TableView userFeeTableView;
	@FXML
	private Label userAccName;
	@FXML
	private Label userAccID;
	@FXML
	private Label userAccPhone;
	@FXML
	private PasswordField userCurPassText;
	@FXML
	private PasswordField userNewPassText;
	@FXML
	private PasswordField userConfirmNewPassText;
	@FXML
	private Button payButton;
	@FXML
	private Button confirmButton;
	@FXML
	private Pane resetPassPane;
	
	
	ObservableList<Apartment> dataApt;
    ObservableList<Relationship> dataRela;
    ObservableList<Resident> dataResident;
    ObservableList<Vehicle> dataVehicle;
    ObservableList<Fee> dataFee;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataApt = DatabaseConnecter.getApartmentsData();
    	
    	dataRela = DatabaseConnecter.getRelationshipsData();
    	
    	dataResident = DatabaseConnecter.getResidentsData();
    	
    	dataVehicle = DatabaseConnecter.getVehiclesData();
    	
    	dataFee = DatabaseConnecter.getAllFees();
    	
    	TableColumn<Vehicle, String> vehicleAptIDCol = new TableColumn<>("Căn hộ");
        vehicleAptIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleAptID"));
        
        TableColumn<Vehicle, String> vehicleIDCol = new TableColumn<>("Biển số");
        vehicleIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleID"));
        
        TableColumn<Vehicle, String> vehicleTypeCol = new TableColumn<>("Loại");
        vehicleTypeCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("type"));
        
        userVehicleTableView.getColumns().addAll(vehicleAptIDCol, vehicleIDCol, vehicleTypeCol);
    	
        TableColumn<Relationship, String> nameCol = new TableColumn<>("Tên");
        nameCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("name"));
        
        TableColumn<Relationship, String> IDCol = new TableColumn<>("ID");
        IDCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("ID"));
        
        TableColumn<Relationship, String> relationshipCol = new TableColumn<>("Quan hệ với chủ hộ");
        relationshipCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("relationship"));
        
        userRelaTableView.getColumns().addAll(nameCol, IDCol, relationshipCol);
        
        TableColumn<Fee, String> aptIDCol = new TableColumn<>("Căn hộ");
		aptIDCol.setCellValueFactory(new PropertyValueFactory<Fee, String>("aptID"));
		
		TableColumn<Fee, String> typeFeeCol = new TableColumn<>("Loại phí");
		typeFeeCol.setCellValueFactory(new PropertyValueFactory<Fee, String>("typeFee"));
		
		TableColumn<Fee, String> isForcedCol = new TableColumn<>("Bắt buộc");
		isForcedCol.setCellValueFactory(new PropertyValueFactory<Fee, String>("isForced"));
		
		TableColumn<Fee, Integer> amountCol = new TableColumn<>("Số tiền");
		amountCol.setCellValueFactory(new PropertyValueFactory<Fee, Integer>("amount"));
		
		TableColumn<Fee, String> statusCol = new TableColumn<>("Tình trạng");
		statusCol.setCellValueFactory(new PropertyValueFactory<Fee, String>("status"));
		
		TableColumn<Fee, CheckBox> checkCol = new TableColumn<>("Thanh toán");
		checkCol.setCellValueFactory(new PropertyValueFactory<Fee, CheckBox>("check"));
		
		userFeeTableView.getColumns().addAll(aptIDCol, typeFeeCol, isForcedCol, amountCol, statusCol, checkCol);
    	
		userAptID.setText("Căn hộ: "+findApt(userAcc.getAccountOwnerID()).getAptID());
		userOwnerName.setText("Tên chủ hộ: "+findName(findApt(userAcc.getAccountOwnerID()).getOwnerID()));
		userOwnerID.setText("CCCD chủ hộ: "+findApt(userAcc.getAccountOwnerID()).getOwnerID());
		userOwnerPhone.setText("Số điện thoại: "+ findPhone(findApt(userAcc.getAccountOwnerID()).getOwnerID()));
		userArea.setText("Diện tích: "+ findApt(userAcc.getAccountOwnerID()).getArea());
		userElec.setText("Số điện: "+findApt(userAcc.getAccountOwnerID()).getElec());
		userWater.setText("Số nước: "+findApt(userAcc.getAccountOwnerID()).getWater());
		userAccName.setText("Tên chủ tài khoản: "+findName(userAcc.getAccountOwnerID()));
		userAccID.setText("CCCD: "+userAcc.getAccountOwnerID());
		userAccPhone.setText("Số điện thoại: "+ findPhone(userAcc.getAccountOwnerID()));
		
		ObservableList<Vehicle> userAptVehicleData = FXCollections.observableArrayList();
        for (Vehicle vehicle : dataVehicle) {
            if (findApt(userAcc.getAccountOwnerID()).getAptID().equals(vehicle.getVehicleAptID())) {
            	userAptVehicleData.add(vehicle);
            }
        }
        
        userVehicleTableView.setItems(userAptVehicleData);
        userVehicleTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        ObservableList<Relationship> userAptRelaData = FXCollections.observableArrayList();
        for (Relationship rel : dataRela) {
            if (findApt(userAcc.getAccountOwnerID()).getOwnerID().equals(rel.getOwnerID())) {
            	rel.setName(findName(rel.getID()));
            	userAptRelaData.add(rel);
            }
        }
        
        userRelaTableView.setItems(userAptRelaData);
        userRelaTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        ObservableList<Fee> userAptFeeData = FXCollections.observableArrayList();
        for (Fee fee : dataFee) {
            if (findApt(userAcc.getAccountOwnerID()).getAptID().equals(fee.getAptID())) {
            	userAptFeeData.add(fee);
            }
        }
        
        userFeeTableView.setItems(userAptFeeData);
        userFeeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
        payButton.setOnAction(e -> {

            for (Fee fee : userAptFeeData) {
            	int isForced = fee.getIsForced().equals("Bắt buộc")? 1 : 0;
            	if(fee.getStatus().equals("Đã thanh toán")) {
            		showAlert(Alert.AlertType.WARNING, "Lưu ý", "Khoản phí đã được thanh toán!");
            		fee.getCheck().setSelected(false);
            		return;
            	} else if(fee.getStatus().equals("Quá hạn")) {
            		showAlert(Alert.AlertType.WARNING, "Lưu ý", "Khoản phí đã hết hạn.Vui lòng gặp quản lý chung cư để biết thêm chi tiết.");
            		fee.getCheck().setSelected(false);
            		return;
            	}
                if (fee.getCheck().isSelected() && DatabaseConnecter.updateFeeStatusToPaid(fee.getAptID(), fee.getTypeFee(),
                		isForced, fee.getAmount())) {
                    fee.setStatus("Đã thanh toán");
                    showAlert(Alert.AlertType.WARNING, "Thành công", "Thanh toán thành công!");
                    fee.getCheck().setSelected(false);
                }
            }
            userFeeTableView.refresh();
        });
        
        confirmButton.setOnAction(e -> {
            String currentPass = userCurPassText.getText();
            String newPass = userNewPassText.getText();
            String confirmNewPass = userConfirmNewPassText.getText();

            if (userCurPassText.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập mật khẩu hiện tại!");
                return;
            }
            if (userNewPassText.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập mật khẩu mới!");
                return;
            }
            if (userConfirmNewPassText.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng xác nhận mật khẩu mới!");
                return;
            }

            if (!currentPass.equals(userAcc.getAccountPassword())) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Sai mật khẩu hiện tại!");
                return;
            }

            if (!newPass.equals(confirmNewPass)) {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Mật khẩu không trùng khớp!");
                return;
            }

            if (newPass.length() < 6) {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Mật khẩu mới phải có ít nhất 6 ký tự!");
                return;
            }
            userAcc.setAccountPassword(newPass);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đổi mật khẩu thành công!");
        });

		
	}
	
	private void showAlert(Alert.AlertType alertType, String title, String message) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}


	public void infoScreen(ActionEvent e) {
		userAptInfoPane.setVisible(true);
		userAccountPane.setVisible(false);
	}
	
	public void accountScreen(ActionEvent e) {
		userAccountPane.setVisible(true);
		userAptInfoPane.setVisible(false);
	}
	
	public void resetPass(ActionEvent e) {
		resetPassPane.setVisible(true);
	}
	
	public Apartment findApt(String ID) {
		for(Apartment apt : dataApt) {
			if(userAcc.getAccountOwnerID().equals(apt.getOwnerID())) {
				return apt;
			}
		}
		for(Relationship rel : dataRela) {
			if(ID.equals(rel.getID())) {
				String ownerID = rel.getOwnerID();
				for(Apartment apt : dataApt) {
					if(ownerID.equals(apt.getOwnerID())) {
						return apt;
					}
				}
			}
		}
		return new Apartment("", "", 0);
	}
	
	public String findName(String ID) {
		for(Resident res : dataResident) {
			if(ID.equals(res.getResidentID())) {
				return res.getResidentName();
			}
		}
		return "";
	}
    
    public String findPhone(String ID) {
		for(Resident res : dataResident) {
			if(ID.equals(res.getResidentID())) {
				return res.getResidentPhone();
			}
		}
		return "";
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
	
}
