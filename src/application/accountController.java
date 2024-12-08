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
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class accountController implements Initializable {
	
	Stage stage;
	Scene scene;
	
	@FXML
	private Label accountOwnerName;
	@FXML
	private Label accountOwnerID;
	@FXML
	private Label accountOwnerPhone;
	@FXML
	private Label accountType;
	@FXML
	private Pane resetPasswordPane;
	@FXML
	private TextField currentPassText;
	@FXML
	private TextField newPassText;
	@FXML
	private TextField confirmNewPassText;
	@FXML
	private Button confirmButton;
	@FXML
	private Label note;
	
	Account using = new LoginController().getUsingAccount();
	
	ObservableList<Resident> dataResident;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataResident = DatabaseConnecter.getResidentsData();
		
		accountOwnerName.setText("Ten chu tai khoan: "+findName(using.getAccountOwnerID()));
		accountOwnerID.setText("CCCD: "+using.getAccountOwnerID());
		accountOwnerPhone.setText("So dien thoai: "+findPhone(using.getAccountOwnerID()));
		accountType.setText("Loai tai khoan: "+using.getAccountType());	
		confirmButton.setOnAction(e -> {
			String currentPass = currentPassText.getText();
			String newPass = newPassText.getText();
			String confirmNewPass = confirmNewPassText.getText();
			if(currentPassText.getText().isEmpty() || newPassText.getText().isEmpty() || confirmNewPassText.getText().isEmpty()) {
				note.setText("Vui long nhap day du cac dong!!!");
				note.setVisible(true);
				return;
			}
			if(!currentPass.equals(using.getAccountPassword())) {
				note.setText("Sai mat khau hien tai!!!");
				note.setVisible(true);
				return;
			}
			if(!newPass.equals(confirmNewPass)) {
				note.setText("Mat khau khong trung khop!!!");
				note.setVisible(true);
				return;
			}
			if(newPass.equals(currentPass)) {
				note.setText("Vui long nhap mat khau moi!");
				note.setVisible(true);
				return;
			}
			note.setText("Doi mat khau thanh cong!");
			note.setVisible(true);
			using.setAccountPassword(newPass);
			System.out.println(using.getAccountPassword());
		});
	}
	
	public void resetPassword(ActionEvent e) {
		resetPasswordPane.setVisible(true);
	}
	
	public void switchToInfoScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("InformationScreen.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public void switchToFeeScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("FeeScreen.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public void switchToFeeManagementScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("FeeManagementScreen.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public void Logout(ActionEvent e) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
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
