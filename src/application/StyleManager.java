package application;

import javafx.scene.Scene;

public class StyleManager {
    private static final String STYLESHEET = "/style.css";

    public static void applyStyle(Scene scene) {
        scene.getStylesheets().clear(); 
        scene.getStylesheets().add(Main.class.getResource(STYLESHEET).toExternalForm());
    }
}
