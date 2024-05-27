package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChildController implements Initializable{

    Connection databaseLink= null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnSave;
    @FXML
    private Button  btnUpdate;
    @FXML
    private Button  btnDelete;
    @FXML
    private Button  btnClear;
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
    private TableView<Child> table;
    @FXML
    private TableColumn<String,String> colid;
    @FXML
    private TableColumn<String,String> colfirstname;
    @FXML
    private TableColumn<String,String> collastname;
    @FXML
    private TableColumn<String,String> colbirthday;
    @FXML
    private TableColumn<String,String> colactivities;
    @FXML
    private TableColumn<String,String> colgender;
    @FXML
    private TableColumn<String,String> colusername;
    @FXML
    private TableColumn<String,String> colphone;
    @FXML
    private TableColumn<String,String> colemail;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    @FXML
    private void addChildren() {
        String activities = getSelectedActivities();
        String gender = malegender.isSelected() ? "Male" : "Female";

        Child children = new Child(
                usernameTextField.getText(),
                emailTextField.getText(),
                phoneTextField.getText(),
                setPasswordField.getText(),
                confirmPasswordField.getText(),
                firstnameTextField.getText(),
                lastnameTextField.getText(),
                dateofbirthDatePicker.getValue(),
                activities,
                null,  // Assuming filename is not being set here
                gender,
                allergiesTextField.getText(),
                medicalTextField.getText(),
                messageTextField.getText()
        );

        AppQuery query = new AppQuery();
        query.addChildren(children);
    }

    private String getSelectedActivities() {
        StringBuilder activities = new StringBuilder();
        if (Act1.isSelected()) activities.append("Outdoor Exploration, ");
        if (Act2.isSelected()) activities.append("Arts and Crafts, ");
        if (Act3.isSelected()) activities.append("Music and Movement, ");
        if (Act4.isSelected()) activities.append("Swimming Pool, ");
        if (Act5.isSelected()) activities.append("Sensory Play, ");
        if (Act6.isSelected()) activities.append("Yoga, ");
        if (activities.length() > 0) {
            activities.setLength(activities.length() - 2);  // Remove the last comma and space
        }
        return activities.toString();
    }
}
