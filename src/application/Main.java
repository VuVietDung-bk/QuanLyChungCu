package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;



public class Main extends Application {
	private Stage loginScreen;
	
	@Override
	public void start(Stage loginScreen) throws IOException {
		this.loginScreen = loginScreen;
		this.loginScreen.setTitle("BlueMoon Apartment Manager");

		showLoginScreen();
	}
	
	public void showLoginScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Main.fxml"));
		Parent root = loader.load();
		Scene loginScene = new Scene(root);
		
		loginScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		loginScreen.setScene(loginScene);
		Image icon = new Image("images//applogo.png");
		loginScreen.getIcons().add(icon);
		loginScreen.setResizable(false);
		loginScreen.centerOnScreen();
		loginScreen.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
