package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import database.DatabaseConnecter;

public class LoginController implements Initializable {
	
	@FXML
	private TextField Name;
	@FXML
	private PasswordField Password;
	@FXML
	ImageView Image;
	@FXML
	private TextField userNameText;
	@FXML
	private TextField phoneRecoveryText;
	@FXML
	private PasswordField newPassText;
	@FXML
	private PasswordField confirmNewPassText;
	@FXML
	private Label resetPassNote;
	@FXML
	private Pane loginPane;
	@FXML
	private Pane forgetPassPane;
	@FXML
	private Button confirmButton;
	
	private Stage stage;
	private Scene scene;
	private static Account usingAcc;
	
	ObservableList<Account> dataAcc;
	ObservableList<Resident> dataResident;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataAcc = DatabaseConnecter.getAccountsData();
		
		dataResident = DatabaseConnecter.getResidentsData();
		
		confirmButton.setOnAction(e -> {
			String userName = userNameText.getText();
			String phone = phoneRecoveryText.getText();
			for(Account acc : dataAcc) {
				if(acc.getAccountUsername().equals(userName)) {
					if(findPhone(acc.getAccountOwnerID()).equals(phone)) {
						String newPass = newPassText.getText();
						String confirmNewPass = confirmNewPassText.getText();
						if(newPass.equals(confirmNewPass)) {
							acc.setAccountPassword(newPass);
							forgetPassPane.setVisible(false);
							loginPane.setVisible(true);
						} else {
							resetPassNote.setText("Mat khau khong trung khop!!");
							return;
						}
					} else {
						resetPassNote.setText("Sai so dien thoai khoi phuc!!");
						return;
					}
				}
			}
			resetPassNote.setText("Khong tim thay ten dang nhap!!");
			return;
		});
		
	}
	
	public void Login(ActionEvent e) throws IOException {
		String user = Name.getText();
		System.out.println(user);
		String pass = Password.getText();
		System.out.println(pass);
		Account acc = DatabaseConnecter.getUserAccount(user, pass);
		if(acc == null) return;
		usingAcc = acc;
		if (acc.getAccountType().equals("admin")) {
			System.out.println("Dang nhap thanh cong.");
			Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			StyleManager.applyStyle(scene);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.centerOnScreen();
			stage.show();
		} else {
			System.out.println("Dang nhap thanh cong.");
			Parent root = FXMLLoader.load(getClass().getResource("UserScreen.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			StyleManager.applyStyle(scene);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.centerOnScreen();
			stage.show();
		}
	}
	
	public void forgetPass() {
		loginPane.setVisible(false);
		forgetPassPane.setVisible(true);
	}
	
	public Account getUsingAccount() {
		return usingAcc;
	}

	public String findPhone(String ID) {
		for(Resident res : dataResident) {
			if(ID.equals(res.getResidentID())) {
				return res.getResidentPhone();
			}
		}
		return "";
	}
}
