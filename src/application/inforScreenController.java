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

		TableColumn<Apartment, Integer> areaCol = new TableColumn<>("Dien tich");
		areaCol.setCellValueFactory(new PropertyValueFactory<Apartment, Integer>("area"));

		TableColumn<Apartment, Button> detailCol = new TableColumn<>("Chi tiet");
		detailCol.setCellValueFactory(new PropertyValueFactory<Apartment, Button>("detail"));

		TableColumn<Apartment, CheckBox> selectCol = new TableColumn<>("Chọn");
		selectCol.setCellValueFactory(new PropertyValueFactory<Apartment, CheckBox>("select"));

		AptTableView.getColumns().addAll(aptIDCol, ownerIDCol, vehicleCol, elecCol, waterCol, areaCol, detailCol,
				selectCol);

		TableColumn<Relationship, String> nameCol = new TableColumn<>("Ten");
		nameCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("name"));

		TableColumn<Relationship, String> IDCol = new TableColumn<>("ID");
		IDCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("ID"));

		TableColumn<Relationship, String> relationshipCol = new TableColumn<>("Quan he voi chu ho");
		relationshipCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("relationship"));

		relationshipTableView.getColumns().addAll(nameCol, IDCol, relationshipCol);

		TableColumn<Vehicle, String> vehicleAptIDCol = new TableColumn<>("Can ho");
		vehicleAptIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleAptID"));

		TableColumn<Vehicle, String> vehicleIDCol = new TableColumn<>("Bien so");
		vehicleIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleID"));

		TableColumn<Vehicle, String> vehicleTypeCol = new TableColumn<>("Loai");
		vehicleTypeCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("type"));

		vehicleTableView.getColumns().addAll(vehicleAptIDCol, vehicleIDCol, vehicleTypeCol);

		TableColumn<Relationship, String> newAptNameCol = new TableColumn<>("Ten");
		newAptNameCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("name"));

		TableColumn<Relationship, String> newAptIDCol = new TableColumn<>("ID");
		newAptIDCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("ID"));

		TableColumn<Relationship, String> newAptRelationshipCol = new TableColumn<>("Quan he voi chu ho");
		newAptRelationshipCol.setCellValueFactory(new PropertyValueFactory<Relationship, String>("relationship"));

		addRelaTableView.getColumns().addAll(newAptNameCol, newAptIDCol, newAptRelationshipCol);

		TableColumn<Vehicle, String> newAptVehicleAptIDCol = new TableColumn<>("Can ho");
		newAptVehicleAptIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleAptID"));

		TableColumn<Vehicle, String> newAptVehicleIDCol = new TableColumn<>("Bien so");
		newAptVehicleIDCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleID"));

		TableColumn<Vehicle, String> newAptVehicleTypeCol = new TableColumn<>("Loai");
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

				aptIDLabel.setText(apt.getAptID());
				ownerNameLabel.setText(findName(apt.getOwnerID()));
				ownerIDLabel.setText(apt.getOwnerID());
				phoneLabel.setText(findPhone(apt.getOwnerID()));
				elecLabel.setText(String.valueOf(apt.getElec()));
				waterLabel.setText(String.valueOf(apt.getWater()));
				areaLabel.setText(String.valueOf(apt.getArea()));

				ObservableList<Relationship> aptDetailData = FXCollections.observableArrayList();

				Resident res = DatabaseConnecter.getResidentByOwnerID(apt.getOwnerID());
				for (Relationship rel : dataRela) {
					if (res.getResidentID().equals(rel.getOwnerID())) {
						rel.setName(DatabaseConnecter.getResidentNameById(rel.getID()));
						aptDetailData.add(rel);
					}
				}

				relationshipTableView.setItems(aptDetailData);
				relationshipTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

				ObservableList<Vehicle> vehicleDetailData = FXCollections.observableArrayList();
				for (Vehicle vehicle : dataVehicle) {
					if (apt.getAptID().equals(vehicle.getVehicleAptID())) {
						vehicleDetailData.add(vehicle);
					}
				}

				vehicleTableView.setItems(vehicleDetailData);
				vehicleTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			});

		}

		FilteredList<Apartment> filterData = new FilteredList<Apartment>(dataApt, b -> true);
		searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filterData.setPredicate(Apartment -> {
				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}
				String searchKeyword = newValue.toLowerCase();

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

		typeVehicle.getItems().addAll("Xe may", "O to");
		typeVehicle.setOnAction(this::select);
		
		DatabaseConnecter.updateVehicleCountForApartments();
	}

	public void deleteSelectedRow(ActionEvent e) {
		for (Apartment apt : dataApt) {
			if (apt.getSelect().isSelected()) {
				if (DatabaseConnecter.deleteApartmentById(apt.getAptID())) {
					dataApt.remove(apt);
				}
			}
		}
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
	}

	public void refreshAddRela(ActionEvent e) {
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
		ObservableList<Vehicle> vehicleData = FXCollections.observableArrayList();
		for (Vehicle vehicle : dataVehicle) {
			if (usingApt.getAptID().equals(vehicle.getVehicleAptID())) {
				vehicleData.add(vehicle);
			}
		}

		addVehicleTableView.setItems(vehicleData);
		addVehicleTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void confirmInDetailScreen(ActionEvent e) {
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
		
		Relationship newRela = new Relationship(usingApt.getOwnerID(), ID, rela);
		
		if(DatabaseConnecter.insertRelationship(usingApt.getOwnerID(), ID, rela)) {
			dataRela.add(newRela);
		}

		AptTableView.refresh();
		relationshipTableView.refresh();

		addName.clear();
		addID.clear();
		addRela.clear();
	}

	public void refresh(ActionEvent e) {
		aptIDLabel.setText(usingApt.getAptID());
		ownerNameLabel.setText(findName(usingApt.getOwnerID()));
		ownerIDLabel.setText(usingApt.getOwnerID());
		phoneLabel.setText(findPhone(usingApt.getOwnerID()));

		ObservableList<Relationship> aptDetailData = FXCollections.observableArrayList();

		Resident res = DatabaseConnecter.getResidentByOwnerID(usingApt.getOwnerID());
		for (Relationship rel : dataRela) {
			if (res.getResidentID().equals(rel.getOwnerID())) {
				rel.setName(DatabaseConnecter.getResidentNameById(rel.getID()));
				aptDetailData.add(rel);
			}
		}

		relationshipTableView.setItems(aptDetailData);
		relationshipTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void back(ActionEvent e) throws IOException {
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