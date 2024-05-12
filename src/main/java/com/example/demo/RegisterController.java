package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    private Button CancelButtonRegister;
    @FXML
    private Button  gotoLoginButton;

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) CancelButtonRegister.getScene().getWindow();
        stage.close();
    }

    public void view() {
        try {
            Stage stage = (Stage) gotoLoginButton.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
