package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
   // private Label welcomeText;
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button gotoRegisterButton;

    public void loginButtonOnAction(ActionEvent event) {
        //loginMessageLabel.setText("Welcome to Login Page! You try to Login");

        if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
            //loginMessageLabel.setText("Welcome to Login Page! You try to Login");
    validateLogin();
        }else{
            loginMessageLabel.setText("Please enter your username and password");
        }
    }
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String hashedPassword = hashPassword(passwordTextField.getText());

        String verifyLogin = "SELECT count(1) FROM children WHERE (username = ? OR email = ?) AND password = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, usernameTextField.getText());
            preparedStatement.setString(3, hashedPassword);

            ResultSet queryResult = preparedStatement.executeQuery();
            if (queryResult.next() && queryResult.getInt(1) == 1) {
                loginMessageLabel.setText("Welcome");
            } else {
                loginMessageLabel.setText("Login Failed! Please try again");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createAccountForm(){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Stage registerStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 650, 450);
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(scene);
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }

    public void view2() {
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