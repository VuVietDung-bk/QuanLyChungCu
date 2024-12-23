package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import database.DatabaseConnecter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
	ImageView Image;
	@FXML
	private Pane resetPasswordPane;
	@FXML
	private PasswordField currentPassText;
	@FXML
	private PasswordField newPassText;
	@FXML
	private PasswordField confirmNewPassText;
	@FXML
	private Button confirmButton;
	@FXML
	private Pane infoCurrentAcc;
	@FXML
	private TextField searchBar;
	@FXML
	private TableView usernameManagementTableView;
	@FXML
	private TextField currentUsernameText;
	@FXML
	private TextField newUsernameText;
	@FXML
	private Button confirmResetUsername;
	@FXML
	private Pane resetUsernamePane;
	
	Account using = new LoginController().getUsingAccount();
	
	ObservableList<Resident> dataResident;
	ObservableList<Account> dataAcc;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataResident = DatabaseConnecter.getResidentsData();
		dataAcc = DatabaseConnecter.getAccountsData();
		
		accountOwnerName.setText("Tên chủ tài khoản: "+findName(using.getAccountOwnerID()));
		accountOwnerID.setText("CCCD: "+using.getAccountOwnerID());
		accountOwnerPhone.setText("Số điện thoại: "+findPhone(using.getAccountOwnerID()));
		accountType.setText("Loại tài khoản: "+using.getAccountType());	
		confirmButton.setOnAction(e -> {
		    try {
		        String currentPass = currentPassText.getText();
		        String newPass = newPassText.getText();
		        String confirmNewPass = confirmNewPassText.getText();

		        if (currentPass.isEmpty() || newPass.isEmpty() || confirmNewPass.isEmpty()) {
		            showAlert("Lỗi", "Vui lòng nhập đầy đủ các trường!");
		            return;
		        }
		        if (!currentPass.equals(using.getAccountPassword())) {
		            showAlert("Lỗi", "Mật khẩu hiện tại không đúng!");
		            return;
		        }
		        if (!newPass.equals(confirmNewPass)) {
		            showAlert("Lỗi", "Mật khẩu mới không khớp!");
		            return;
		        }
		        if (newPass.equals(currentPass)) {
		            showAlert("Lỗi", "Vui lòng nhập mật khẩu mới!");
		            return;
		        }

		        using.setAccountPassword(newPass);
		        showAlert("Thành công", "Đổi mật khẩu thành công!");
		    } catch (Exception ex) {
		        showAlert("Lỗi", "Đã xảy ra lỗi: " + ex.getMessage());
		        ex.printStackTrace();
		    }
		});

		
		TableColumn<Account, String> accOwnerIDCol = new TableColumn<>("CCCD chủ tài khoản");
		accOwnerIDCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountOwnerID"));
		
		TableColumn<Account, String> accTypeCol = new TableColumn<>("Loại tài khoản");
		accTypeCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountType"));
		
		TableColumn<Account, String> accUsernameCol = new TableColumn<>("Tên đăng nhập");
		accUsernameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountUsername"));
		
		TableColumn<Account, Button> resetUsernameCol = new TableColumn<>("Đổi tên đăng nhập");
		resetUsernameCol.setCellValueFactory(new PropertyValueFactory<Account, Button>("resetUsername"));
		
		usernameManagementTableView.getColumns().addAll(accOwnerIDCol, accTypeCol, accUsernameCol, resetUsernameCol);
		usernameManagementTableView.setItems(dataAcc);
		usernameManagementTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		
		FilteredList<Account> filterData = new FilteredList<Account>(dataAcc, b -> true);
		searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filterData.setPredicate(Account -> {
				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}
				String searchKeyword = newValue.toLowerCase();
				
				if (Account.getAccountOwnerID().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
				} else if (Account.getAccountUsername().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
				}else if (Account.getAccountType().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<Account> sortedData = new SortedList<>(filterData);
		sortedData.comparatorProperty().bind(usernameManagementTableView.comparatorProperty());

		usernameManagementTableView.setItems(sortedData);
		usernameManagementTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		for(Account acc : dataAcc) {
			acc.getResetUsername().setOnAction(e -> {
				searchBar.setVisible(false);
				usernameManagementTableView.setVisible(false);
				resetUsernamePane.setVisible(true);
				
				confirmResetUsername.setOnAction(event -> {
				    String currentUsername = currentUsernameText.getText();
				    String newUsername = newUsernameText.getText();

				    if (!currentUsername.isEmpty() && currentUsername.equals(acc.getAccountUsername())) {
				        if (!newUsername.isEmpty()) {
				            acc.setAccountUsername(newUsername);
				            DatabaseConnecter.setUsername(acc.getAccountOwnerID(), newUsername);
				            showAlert("Thành công", "Đổi tên đăng nhập thành công!");
				        } else {
				            showAlert("Lỗi", "Tên đăng nhập mới không được để trống!");
				        }
				    } else {
				        showAlert("Lỗi", "Tên đăng nhập hiện tại không đúng!");
				    }
				});

			});
		}
	}
	
	public void resetPassword(ActionEvent e) {
		resetPasswordPane.setVisible(true);
	}
	
	private void showAlert(String title, String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}

	
	public void resetUsername(ActionEvent e) {
		infoCurrentAcc.setVisible(false);
		searchBar.setVisible(true);
		usernameManagementTableView.setVisible(true);
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
	
	public void switchToFeeScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("FeeScreen.fxml"));
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
	
	public void switchToAccountScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AccountScreen.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
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
