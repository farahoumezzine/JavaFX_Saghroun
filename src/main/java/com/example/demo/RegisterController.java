package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {
    @FXML
    private Button CancelButtonRegister;
    @FXML
    private Button  gotoLoginButton;
    @FXML
    private Button  RegisterButton;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private DatePicker dateofbirthDatePicker;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField setPasswordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private TextField allergiesTextField;
    @FXML
    private TextField medicalTextField;
    @FXML
    private TextArea messageTextField;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private Label SuccessregisterMessageLabel;

    @FXML
    private RadioButton malegender;
    @FXML
    private RadioButton femalegender;
    @FXML
    private CheckBox Act1;
    @FXML
    private CheckBox Act2;
    @FXML
    private CheckBox Act3;
    @FXML
    private CheckBox Act4;
    @FXML
    private CheckBox Act5;
    @FXML
    private CheckBox Act6;
    @FXML
    private CheckBox termsCheckBox;

    public void RegisterButtonOnAction(ActionEvent event) {
        if (firstnameTextField.getText().isBlank() || lastnameTextField.getText().isBlank() ||
                 phoneTextField.getText().isBlank() ||
                usernameTextField.getText().isBlank() || emailTextField.getText().isBlank() ||
                setPasswordField.getText().isBlank() || confirmPasswordField.getText().isBlank()
        && allergiesTextField.getText().isBlank() && medicalTextField.getText().isBlank() && messageTextField.getText().isBlank() ) {
            registerMessageLabel.setText("Please fill in all fields");
            return;
        }
        LocalDate dateOfBirth = dateofbirthDatePicker.getValue();
        if (dateOfBirth == null) {
            registerMessageLabel.setText("Please select a date of birth");
            return; // Exit method if no date is selected
        }
        if (!malegender.isSelected() && !femalegender.isSelected()) {
            registerMessageLabel.setText("Please select gender");
            return;
        }

        if (!Act1.isSelected() && !Act2.isSelected() && !Act3.isSelected() &&
                !Act4.isSelected() && !Act5.isSelected() && !Act6.isSelected()) {
            registerMessageLabel.setText("Please select at least one activity");
            return;
        }

        // Check if password matches confirm password
        String password = setPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (!password.equals(confirmPassword)) {
            registerMessageLabel.setText("Password and Confirm Password do not match");
            return;
        }
        String phoneNumber = phoneTextField.getText();
        if (!isNumeric(phoneNumber) || phoneNumber.length() != 8) {
            registerMessageLabel.setText("Phone number must be numeric and have 8 digits");
            return;
        }
        String email = emailTextField.getText();
        if (!isValidEmail(email)) {
            registerMessageLabel.setText("Please enter a valid email address");
            return;
        }
        if (!termsCheckBox.isSelected()) {
            registerMessageLabel.setText("Please accept terms and conditions");
            return; // Exit the method if checkbox is not selected
        }
        String hashedPassword = hashPassword(password);

        validateRegistration(hashedPassword, dateOfBirth);
        //registerMessgeLabel.setText("Welcome to Login Page! You try to Login");
        //validateLogin();
    }
    private boolean isValidEmail(String email) {
        // Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private String hashPassword(String password) {

        return password;
    }
    public void cancelButtonOnAction (ActionEvent event) {
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

    public void validateRegistration(String hashedPassword , LocalDate dateOfBirth) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // Check if username already exists
        String verifyUsername = "SELECT COUNT(1) FROM children WHERE username='" + usernameTextField.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyUsername);
            if (queryResult.next()) {
                int count = queryResult.getInt(1);
                if (count > 0) {
                    registerMessageLabel.setText("Username already exists. Please choose another username.");
                    return; // Exit method if username already exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If username is unique, proceed with registration
        String insertQuery = "INSERT INTO children (username, password, firstname, lastname, dateofbirth, phone, email,message,conditions,allergies, activities, gender,password_confirmation) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ? , ? , ? , ? , ? , ?)";
        try (PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, firstnameTextField.getText());
            preparedStatement.setString(4, lastnameTextField.getText());
            preparedStatement.setDate(5, Date.valueOf(dateOfBirth));
            preparedStatement.setString(6, phoneTextField.getText());
            preparedStatement.setString(7, emailTextField.getText());
            preparedStatement.setString(8, messageTextField.getText());
            preparedStatement.setString(9, medicalTextField.getText());
            preparedStatement.setString(10, allergiesTextField.getText());
            preparedStatement.setString(11, getSelectedActivities());
            preparedStatement.setString(12, getSelectedGender());
            preparedStatement.setString(13, confirmPasswordField.getText());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                registerMessageLabel.setTextFill(Color.web("#0f8a2c"));

                registerMessageLabel.setText("Registration successful. Welcome!");
            } else {
                registerMessageLabel.setText("Registration failed. Please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            registerMessageLabel.setText("Registration failed. Please try again.");
        }
    }

    // Method to get selected activities
    private String getSelectedActivities() {
        List<String> selectedActivities = new ArrayList<>();
        if (Act1.isSelected()) selectedActivities.add(Act1.getText());
        if (Act2.isSelected()) selectedActivities.add(Act2.getText());
        if (Act3.isSelected()) selectedActivities.add(Act3.getText());
        if (Act4.isSelected()) selectedActivities.add(Act4.getText());
        if (Act5.isSelected()) selectedActivities.add(Act5.getText());
        if (Act6.isSelected()) selectedActivities.add(Act6.getText());
        return String.join(", ", selectedActivities);
    }

    // Method to get selected gender
    private String getSelectedGender() {
        if (malegender.isSelected()) {
            return "Male";
        } else if (femalegender.isSelected()) {
            return "Female";
        } else {
            return "Other";
        }


}
}
