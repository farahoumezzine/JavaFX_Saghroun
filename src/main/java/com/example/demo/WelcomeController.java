package com.example.demo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {
    @FXML
    private Button btnadmin;
    @FXML
    private Button btnparent;
    @FXML
    private Button btnchef;
    @FXML
    private Button btnteacher;
    @FXML
    private void handleParentButton() {
        try {
            // Load the Register.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
            //Scene scene = new Scene(fxmlLoader.load());
            Scene scene = new Scene(fxmlLoader.load(), 600, 750);
            // Get the current stage
            Stage stage = (Stage) btnparent.getScene().getWindow();

            // Set the new scene
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
