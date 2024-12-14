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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class feeManagementController implements Initializable {
	
	Stage stage;
	Scene scene;
	
	@FXML
	private TableView feeManageTableView;
	
	ObservableList<Fee> feeData;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		feeData = DatabaseConnecter.getAllFees();
		
		TableColumn<Fee, String> aptIDCol = new TableColumn<>("Can ho");
		aptIDCol.setCellValueFactory(new PropertyValueFactory<Fee, String>("aptID"));
		
		TableColumn<Fee, String> typeFeeCol = new TableColumn<>("Loai phi");
		typeFeeCol.setCellValueFactory(new PropertyValueFactory<Fee, String>("typeFee"));
		
		TableColumn<Fee, String> isForcedCol = new TableColumn<>("Bat buoc");
		isForcedCol.setCellValueFactory(new PropertyValueFactory<Fee, String>("isForced"));
		
		TableColumn<Fee, Integer> amountCol = new TableColumn<>("So tien");
		amountCol.setCellValueFactory(new PropertyValueFactory<Fee, Integer>("amount"));
		
		TableColumn<Fee, String> statusCol = new TableColumn<>("Tinh trang");
		statusCol.setCellValueFactory(new PropertyValueFactory<Fee, String>("status"));
		
		TableColumn<Fee, CheckBox> checkCol = new TableColumn<>("Xoa");
		checkCol.setCellValueFactory(new PropertyValueFactory<Fee, CheckBox>("check"));
		
		feeManageTableView.getColumns().addAll(aptIDCol, typeFeeCol, isForcedCol, amountCol, statusCol, checkCol);
		
		
		
		feeManageTableView.setItems(feeData);
		feeManageTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	@FXML
	private void deleteSelectedRow(ActionEvent e) {
    	ObservableList<Fee> deleteData = FXCollections.observableArrayList();
    	for(Fee fee : feeData) {
    		if(fee.getCheck().isSelected()) {
    			int isForced = fee.getIsForced().equals("Bat buoc")? 1 : 0;
    			if(DatabaseConnecter.deleteFee(fee.getAptID(), fee.getTypeFee(), isForced, 1, fee.getAmount()));
    				deleteData.add(fee);
    		}
    	}
    	feeData.removeAll(deleteData);
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
	
}