package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class termsandconditionsController {
    @FXML
    private Button gotoRegisterButton;

    public void viewTerms() {
        try {
            Stage stage = (Stage) gotoRegisterButton.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Scene scene = new Scene(root, 600, 750);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
