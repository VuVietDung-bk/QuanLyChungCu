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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class inforScreenController implements Initializable {

	Stage stage;
	Scene scene;

	@FXML
	private TableView<Apartment> AptTableView;
	@FXML
	private Pane detailPane;
	@FXML
	private TableView relationshipTableView;
	@FXML
	private Label aptIDLabel;
	@FXML
	private Label ownerNameLabel;
	@FXML
	private Label ownerIDLabel;
	@FXML
	private Label phoneLabel;
	@FXML
	private Label elecLabel;
	@FXML
	private Label waterLabel;
	@FXML
	private Label areaLabel;
	@FXML
	private TableView vehicleTableView;
	@FXML
	private Button deleteButton;
	@FXML
	private Button addButton;
	@FXML
	private Pane addAptPane;
	@FXML
	private TextField searchBar;
	@FXML
	private Button selectAll;
	@FXML
	private Pane addRelaPane;
	@FXML
	private TextField addName;
	@FXML
	private TextField addID;
	@FXML
	private TextField addRela;
	@FXML
	private TableView addRelaTableView;
	@FXML
	private TableView addVehicleTableView;
	@FXML
	private Pane addAptChildPane;
	@FXML
	private TextField aptIDText;
	@FXML
	private TextField ownerNameText;
	@FXML
	private TextField ownerIDText;
	@FXML
	private TextField phoneText;
	@FXML
	private TextField areaText;
	@FXML
	private Pane addInfoForNewAptPane;
	@FXML
	private Pane addNewAptRelaPane;
	@FXML
	private Pane addNewAptVehiclePane;
	@FXML
	private TextField addNameText;
	@FXML
	private TextField addIDText;
	@FXML
	private TextField addRelaText;
	@FXML
	private TextField addVehicleIDText;
	@FXML
	private ChoiceBox<String> typeVehicle;
	@FXML
	private Pane searchBarPane;
	@FXML
	private Pane addVehiclePane;
	@FXML
	private ChoiceBox<String> typeVehicleInDetailScreen;
	@FXML
	private TextField addVehicleText;
	@FXML
	private Button deleteVehicle;
	

	static String currentChoice;

	static Apartment usingApt;

	ObservableList<Apartment> dataApt;
	ObservableList<Relationship> dataRela;
	ObservableList<Resident> dataResident;
	ObservableList<Vehicle> dataVehicle;

	ObservableList<Relationship> newAptRela;
	ObservableList<Vehicle> newAptVehicle;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		dataApt = DatabaseConnecter.getApartmentsData();

		dataRela = DatabaseConnecter.getRelationshipsData();

		dataResident = DatabaseConnecter.getResidentsData();

		dataVehicle = DatabaseConnecter.getVehiclesData();

		TableColumn<Apartment, String> aptIDCol = new TableColumn<>("Căn hộ");
		aptIDCol.setCellValueFactory(new PropertyValueFactory<Apartment, String>("aptID"));

		TableColumn<Apartment, String> ownerIDCol = new TableColumn<>("CCCD");
		ownerIDCol.setCellValueFactory(new PropertyValueFactory<Apartment, String>("ownerID"));

		TableColumn<Apartment, Integer> vehicleCol = new TableColumn<>("Số xe");
		vehicleCol.setCellValueFactory(new PropertyValueFactory<Apartment, Integer>("vehicle"));

		TableColumn<Apartment, Integer> elecCol = new TableColumn<>("Điện");
		elecCol.setCellValueFactory(new PropertyValueFactory<Apartment, Integer>("elec"));

		TableColumn<Apartment, Integer> waterCol = new TableColumn<>("Nước");
		waterCol.setCellValueFactory(new PropertyValueFactory<Apartment, Integer>("water"));

		TableColumn<Apartment, Integer> areaCol = new TableColumn<>("Diện tích");
		areaCol.setCellValueFactory(new PropertyValueFactory<Apartment, Integer>("area"));

		TableColumn<Apartment, Button> detailCol = new TableColumn<>("Chi tiết căn hộ");
		detailCol.setCellValueFactory(new PropertyValueFactory<Apartment, Button>("detail"));

		TableColumn<Apartment, CheckBox> selectCol = new TableColumn<>("Chọn");
		selectCol.setCellValueFactory(new PropertyValueFactory<Apartment, CheckBox>("select"));

		AptTableView.getColumns().addAll(aptIDCol, ownerIDCol, vehicleCol, elecCol, waterCol, areaCol, detailCol,
				selectCol);

		TableColumn<Relationship, String> nameCol = new TableColumn<>("Tên");
		nameCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("name"));

		TableColumn<Relationship, String> IDCol = new TableColumn<>("ID");
		IDCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("ID"));

		TableColumn<Relationship, String> relationshipCol = new TableColumn<>("Quan hệ với chủ hộ");
		relationshipCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("relationship"));

		relationshipTableView.getColumns().addAll(nameCol, IDCol, relationshipCol);

		TableColumn<Vehicle, String> vehicleAptIDCol = new TableColumn<>("Căn hộ");
		vehicleAptIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleAptID"));

		TableColumn<Vehicle, String> vehicleIDCol = new TableColumn<>("Biển số");
		vehicleIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleID"));

		TableColumn<Vehicle, String> vehicleTypeCol = new TableColumn<>("Loại");
		vehicleTypeCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("type"));
		
		TableColumn<Vehicle, CheckBox> vehicleDeleteCol = new TableColumn<>("Xóa");
		vehicleDeleteCol.setCellValueFactory(new PropertyValueFactory<Vehicle, CheckBox>("select"));

		vehicleTableView.getColumns().addAll(vehicleAptIDCol, vehicleIDCol, vehicleTypeCol, vehicleDeleteCol);

		TableColumn<Relationship, String> newAptNameCol = new TableColumn<>("Tên");
		newAptNameCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("name"));

		TableColumn<Relationship, String> newAptIDCol = new TableColumn<>("ID");
		newAptIDCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("ID"));

		TableColumn<Relationship, String> newAptRelationshipCol = new TableColumn<>("Quan hệ với chủ hộ");
		newAptRelationshipCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("relationship"));

		addRelaTableView.getColumns().addAll(newAptNameCol, newAptIDCol, newAptRelationshipCol);

		TableColumn<Vehicle, String> newAptVehicleAptIDCol = new TableColumn<>("Căn hộ");
		newAptVehicleAptIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleAptID"));

		TableColumn<Vehicle, String> newAptVehicleIDCol = new TableColumn<>("Biển số");
		newAptVehicleIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleID"));

		TableColumn<Vehicle, String> newAptVehicleTypeCol = new TableColumn<>("Loại");
		newAptVehicleTypeCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("type"));

		addVehicleTableView.getColumns().addAll(newAptVehicleAptIDCol, newAptVehicleIDCol, newAptVehicleTypeCol);

		for (Apartment apt : dataApt) {
			apt.getDetail().setOnAction(e -> {
				usingApt = apt;
				AptTableView.setVisible(false);
				AptTableView.setDisable(true);
				detailPane.setVisible(true);
				detailPane.setDisable(false);
				deleteButton.setVisible(false);
				addButton.setVisible(false);
				searchBarPane.setVisible(false);

				aptIDLabel.setText("Căn hộ: "+apt.getAptID());
				ownerNameLabel.setText("Tên chủ hộ: "+findName(apt.getOwnerID()));
				ownerIDLabel.setText("CCCD: "+apt.getOwnerID());
				phoneLabel.setText("Số điện thoại"+findPhone(apt.getOwnerID()));
				elecLabel.setText("Số điện: "+String.valueOf(apt.getElec()));
				waterLabel.setText("Số nước: "+String.valueOf(apt.getWater()));
				areaLabel.setText("Diện tích: "+String.valueOf(apt.getArea()));
		        
				populateRelationshipTable(usingApt.getOwnerID());
				populateVehicleTable(usingApt.getAptID());
				
			});
		}

		FilteredList<Apartment> filterData = new FilteredList<Apartment>(dataApt, b -> true);
		searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filterData.setPredicate(Apartment -> {
				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}
				String searchKeyword = newValue.toLowerCase();
			    
			    for(Relationship rela : dataRela) {
			    	if(Apartment.getOwnerID().toLowerCase().indexOf(rela.getOwnerID()) > -1 && rela.getID().toLowerCase().indexOf(searchKeyword) > -1) {
			    		return true;
			    	}
			    }
				
				if (Apartment.getAptID().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
				} else if (Apartment.getOwnerID().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<Apartment> sortedData = new SortedList<>(filterData);
		sortedData.comparatorProperty().bind(AptTableView.comparatorProperty());

		selectAll.setOnAction(e -> {
			for (Apartment apt : sortedData) {
				apt.getSelect().setSelected(true);
			}
		});

		AptTableView.setItems(sortedData);
		AptTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		typeVehicle.getItems().addAll("Bike", "Car");
		typeVehicleInDetailScreen.getItems().addAll("Bike", "Car");
		typeVehicle.setOnAction(this::select);
		typeVehicleInDetailScreen.setOnAction(this::selectInDetailScreen);
		
		DatabaseConnecter.updateVehicleCountForApartments();
	}

	public void deleteAptSelectedRow(ActionEvent e) {
		ObservableList<Apartment> aptRemove = FXCollections.observableArrayList();
		for (Apartment apt : dataApt) {
			if (apt.getSelect().isSelected()) {
				DatabaseConnecter.deleteApartmentById(apt.getAptID());
				aptRemove.add(apt);
				System.out.println("xoa can ho "+apt.getAptID());
			}
		}
		dataApt.removeAll(aptRemove);
		AptTableView.setItems(dataApt);
		dataApt = DatabaseConnecter.getApartmentsData();
	}
	
	private void populateRelationshipTable(String ownerId) {
	    ObservableList<Relationship> aptDetailData = FXCollections.observableArrayList();
	    Resident res = DatabaseConnecter.getResidentByOwnerID(ownerId);
	    if (res != null) {
	        for (Relationship rel : dataRela) {
	            if (res.getResidentID().equals(rel.getOwnerID())) {
	                rel.setName(DatabaseConnecter.getResidentNameById(rel.getID()));
	                aptDetailData.add(rel);
	            }
	        }
	    }
	    relationshipTableView.setItems(aptDetailData);
	    relationshipTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private void populateVehicleTable(String aptId) {
	    ObservableList<Vehicle> vehicleDetailData = FXCollections.observableArrayList();
	    for (Vehicle vehicle : dataVehicle) {
	        if (aptId.equals(vehicle.getVehicleAptID())) {
	            vehicleDetailData.add(vehicle);
	        }
	    }
	    vehicleTableView.setItems(vehicleDetailData);
	    vehicleTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void deleteVehicle(ActionEvent e) {
		ObservableList<Vehicle> vehicleDetailData = FXCollections.observableArrayList();
		vehicleDetailData = vehicleTableView.getItems();
		ObservableList<Vehicle> vehicleRemove = FXCollections.observableArrayList();
	    for(Vehicle vehicle : vehicleDetailData) {
	    	if(vehicle.getSelect().isSelected()) {
	    		vehicleRemove.add(vehicle);
	    		DatabaseConnecter.deleteVehicle(vehicle.getVehicleID());
	    	}
	    }
	    vehicleDetailData.removeAll(vehicleRemove);
	    vehicleTableView.setItems(vehicleDetailData);
	    vehicleTableView.refresh();
	    dataVehicle = DatabaseConnecter.getVehiclesData();
	}


	public void Logout(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		StyleManager.applyStyle(scene);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}

	public void addApartment(ActionEvent e) {
		AptTableView.setVisible(false);
		AptTableView.setDisable(true);
		deleteButton.setVisible(false);
		searchBarPane.setVisible(false);
		addButton.setVisible(false);
		addAptPane.setVisible(true);
		addAptPane.setDisable(false);
	}

	public void confirmAddApt() {
		if (aptIDText.getText().isEmpty() || ownerNameText.getText().isEmpty() || ownerIDText.getText().isEmpty()
				|| areaText.getText().isEmpty()) {
			return;
		}
		String newAptId = aptIDText.getText();
		String newAptOwnerName = ownerNameText.getText();
		String newAptOwnerID = ownerIDText.getText();
		String newAptOwnerPhone = phoneText.getText();
		int newAptArea = Integer.parseInt(areaText.getText());
		Resident newRes = new Resident(newAptOwnerID, newAptOwnerName, newAptOwnerPhone);
		
		if (DatabaseConnecter.insertResident(newAptOwnerID, newAptOwnerName, newAptOwnerPhone)) {
			dataResident.add(newRes);
		}
		
		Apartment newApt = new Apartment(newAptId, newAptOwnerID, newAptArea);
		int newAccountId = DatabaseConnecter.insertAccount(newAptOwnerID, "user" + newAptOwnerPhone,
				"pass" + newAptOwnerPhone, "user");
		
		if (newAccountId > -1 && DatabaseConnecter.insertApartment(newAptId, newAptOwnerID, 0, 0, 0, newAptArea)) {
			dataApt.add(newApt);
		}
		
		usingApt = newApt;
		addAptChildPane.setVisible(false);
		addInfoForNewAptPane.setVisible(true);
	}

	public void addRelaForNewApt() {
		addNewAptRelaPane.setVisible(true);
	}

	public void addVehicleForNewApt() {
		addNewAptVehiclePane.setVisible(true);
	}

	public void select(ActionEvent e) {
		currentChoice = typeVehicle.getValue();
	}
	
	public void selectInDetailScreen(ActionEvent e) {
		currentChoice = typeVehicleInDetailScreen.getValue();
	}

	public void confirmAddRelaForNewApt() {
		if (addNameText.getText().isEmpty() || addIDText.getText().isEmpty() || addRelaText.getText().isEmpty()) {
			return;
		}
		String newResName = addNameText.getText();
		String newResID = addIDText.getText();
		String newRela = addRelaText.getText();

		Resident newRes = new Resident(newResID, newResName);

		if (DatabaseConnecter.insertResident(newResID, newResName, "")) {
			dataResident.add(newRes);
		}

		Relationship newRelationship = new Relationship(usingApt.getOwnerID(), newResID, newRela);
		
		if(DatabaseConnecter.insertRelationship(newResID, usingApt.getOwnerID(), newRela)) {
			dataRela.add(newRelationship);
		}
		

		addNameText.clear();
		addIDText.clear();
		addRelaText.clear();
	}

	public void confirmAddVehicleForNewApt() {
		if (addVehicleIDText.getText().isEmpty()) {
			return;
		}
		String newVehicleID = addVehicleIDText.getText();
		Vehicle newVehicle = new Vehicle(usingApt.getAptID(), newVehicleID, currentChoice);
		
		if(DatabaseConnecter.insertVehicle(newVehicleID, usingApt.getAptID(), currentChoice)) {
			dataVehicle.add(newVehicle);
		}
		

		addVehicleIDText.clear();
		DatabaseConnecter.updateVehicleCountForApartments();
	}

	public void addRelaInDetailScreen(ActionEvent e) {
		addRelaPane.setVisible(true);
		addRelaPane.setDisable(false);
		addVehiclePane.setVisible(false);
	}
	
	public void addVehicleInDetailScreen(ActionEvent e) {
		addVehiclePane.setVisible(true);
		addRelaPane.setVisible(false);
	}

	public void refreshAddRela(ActionEvent e) {
		dataRela = DatabaseConnecter.getRelationshipsData();
		ObservableList<Relationship> newRela = FXCollections.observableArrayList();
		Resident res = DatabaseConnecter.getResidentByOwnerID(usingApt.getOwnerID());
		for (Relationship rel : dataRela) {
			if (res.getResidentID().equals(rel.getOwnerID())) {
				rel.setName(DatabaseConnecter.getResidentNameById(rel.getID()));
				newRela.add(rel);
			}
		}

		addRelaTableView.setItems(newRela);
		addRelaTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void refreshAddVehicle(ActionEvent e) {
		dataVehicle = DatabaseConnecter.getVehiclesData();
		ObservableList<Vehicle> vehicleData = FXCollections.observableArrayList();
		for (Vehicle vehicle : dataVehicle) {
			if (usingApt.getAptID().equals(vehicle.getVehicleAptID())) {
				vehicleData.add(vehicle);
			}
		}

		addVehicleTableView.setItems(vehicleData);
		addVehicleTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void confirmAddRelaInDetailScreen(ActionEvent e) {
		if (addName.getText().isEmpty() || addID.getText().isEmpty() || addRela.getText().isEmpty()) {
			return;
		}
		String name = addName.getText();
		String ID = addID.getText();
		String rela = addRela.getText();

		Resident newRes = new Resident(ID, name);
		System.out.println(name);
		if(DatabaseConnecter.insertResident(ID, name, "")) {
			dataResident.add(newRes);
		}
		
		Relationship newRela = new Relationship(ID, usingApt.getOwnerID(), rela);
		
		if(DatabaseConnecter.insertRelationship(ID, usingApt.getOwnerID(), rela)) {
			dataRela.add(newRela);
		}

		AptTableView.refresh();
		relationshipTableView.refresh();

		addName.clear();
		addID.clear();
		addRela.clear();
	}
	
	public void confirmAddVehicleInDetailScreen(ActionEvent e) {
		if (addVehicleText.getText().isEmpty()) {
			return;
		}
		String newVehicleID = addVehicleText.getText();
		Vehicle newVehicle = new Vehicle(usingApt.getAptID(), newVehicleID, currentChoice);
		
		if(DatabaseConnecter.insertVehicle(newVehicleID, usingApt.getAptID(), currentChoice)) {
			dataVehicle.add(newVehicle);
		}
		
		addVehicleText.clear();
		DatabaseConnecter.updateVehicleCountForApartments();
		
	}

	public void refresh(ActionEvent e) {
		
		dataRela = DatabaseConnecter.getRelationshipsData();
		ObservableList<Relationship> aptDetailData = FXCollections.observableArrayList();

		Resident res = DatabaseConnecter.getResidentByOwnerID(usingApt.getOwnerID());
		for (Relationship rel : dataRela) {
			if (res.getResidentID().equals(rel.getOwnerID())) {
				rel.setName(DatabaseConnecter.getResidentNameById(rel.getID()));
				aptDetailData.add(rel);
			}
		}

		relationshipTableView.setItems(aptDetailData);
		relationshipTableView.refresh();
		relationshipTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	public void refreshVehicle(ActionEvent e) {
		dataVehicle = DatabaseConnecter.getVehiclesData();
		ObservableList<Vehicle> vehicleData = FXCollections.observableArrayList();
		for (Vehicle vehicle : dataVehicle) {
			if (usingApt.getAptID().equals(vehicle.getVehicleAptID())) {
				vehicleData.add(vehicle);
			}
		}

		vehicleTableView.setItems(vehicleData);
		vehicleTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void back(ActionEvent e) throws IOException {
		currentChoice = null;
		Parent root = FXMLLoader.load(getClass().getResource("InformationScreen.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		StyleManager.applyStyle(scene);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}

	public void switchToFeeScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("FeeScreen.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		StyleManager.applyStyle(scene);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}

	public void switchToFeeManagementScreen(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("FeeManagementScreen.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
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