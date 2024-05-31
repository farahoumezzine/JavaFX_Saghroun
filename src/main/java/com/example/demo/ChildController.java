package com.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChildController implements Initializable {

    Connection databaseLink = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;
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
    private TableColumn<Child, String> colid;
    @FXML
    private TableColumn<Child, String> colfirstname;
    @FXML
    private TableColumn<Child, String> collastname;
    //@FXML
   // private TableColumn<Child, String> colbirthday;
    @FXML
    private TableColumn<Child, String> colactivities;
    @FXML
    private TableColumn<Child, String> colgender;
    @FXML
    private TableColumn<Child, String> colusername;
    @FXML
    private TableColumn<Child, String> colphone;
    @FXML
    private TableColumn<Child, String> colemail;
    private Child child;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showChildren();
    }

    @FXML
    private void addChildren() {
        if (firstnameTextField.getText().isBlank() || lastnameTextField.getText().isBlank() ||
                phoneTextField.getText().isBlank() || usernameTextField.getText().isBlank() ||
                emailTextField.getText().isBlank() || setPasswordField.getText().isBlank() ||
                confirmPasswordField.getText().isBlank()) {
            registerMessageLabel.setTextFill(Color.web("#ff0000"));
            registerMessageLabel.setText("Please fill in all fields");
            return;
        }

        LocalDate dateOfBirth = dateofbirthDatePicker.getValue();
        if (dateOfBirth == null) {
            registerMessageLabel.setTextFill(Color.web("#ff0000"));
            registerMessageLabel.setText("Please select a date of birth");
            return;
        }

        if (!malegender.isSelected() && !femalegender.isSelected()) {
            registerMessageLabel.setTextFill(Color.web("#ff0000"));
            registerMessageLabel.setText("Please select gender");
            return;
        }

        if (!Act1.isSelected() && !Act2.isSelected() && !Act3.isSelected() &&
                !Act4.isSelected() && !Act5.isSelected() && !Act6.isSelected()) {
            registerMessageLabel.setTextFill(Color.web("#ff0000"));
            registerMessageLabel.setText("Please select at least one activity");
            return;
        }

        String password = setPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (!password.equals(confirmPassword)) {
            registerMessageLabel.setTextFill(Color.web("#ff0000"));
            registerMessageLabel.setText("Password and Confirm Password do not match");
            return;
        }

        String phoneNumber = phoneTextField.getText();
        if (!isNumeric(phoneNumber) || phoneNumber.length() != 8) {
            registerMessageLabel.setTextFill(Color.web("#ff0000"));
            registerMessageLabel.setText("Phone number must be numeric and have 8 digits");
            return;
        }

        String email = emailTextField.getText();
        if (!isValidEmail(email)) {
            registerMessageLabel.setTextFill(Color.web("#ff0000"));
            registerMessageLabel.setText("Please enter a valid email address");
            return;
        }

        String hashedPassword = hashPassword(password);

        String activities = getSelectedActivities();
        String gender = malegender.isSelected() ? "Male" : "Female";

        Child children = new Child(
                usernameTextField.getText(),
                emailTextField.getText(),
                phoneTextField.getText(),
                hashedPassword,
                hashedPassword, // Assuming you also want to hash the password confirmation
                firstnameTextField.getText(),
                lastnameTextField.getText(),
                dateOfBirth,
                activities,
                null,  // Assuming filename is not being set here
                gender,
                allergiesTextField.getText(),
                medicalTextField.getText(),
                messageTextField.getText()
        );

        AppQuery query = new AppQuery();

        query.addChildren(children);
        registerMessageLabel.setTextFill(Color.web("#0f8a2c"));
        registerMessageLabel.setText("Registration successful. Welcome!");
    }

    private String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
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

    @FXML
    private void showChildren() {
        com.example.demo.AppQuery query = new com.example.demo.AppQuery();
        ObservableList<Child> list = query.getChildrenList();
        colid.setCellValueFactory(new PropertyValueFactory<Child, String>("id"));
        colfirstname.setCellValueFactory(new PropertyValueFactory<Child, String>("firstname"));
        collastname.setCellValueFactory(new PropertyValueFactory<Child, String>("lastname"));
        colactivities.setCellValueFactory(new PropertyValueFactory<Child, String>("activities"));
        colgender.setCellValueFactory(new PropertyValueFactory<Child, String>("gender"));
        colusername.setCellValueFactory(new PropertyValueFactory<Child, String>("username"));
        colphone.setCellValueFactory(new PropertyValueFactory<Child, String>("phone"));
        colemail.setCellValueFactory(new PropertyValueFactory<Child, String>("email"));
        table.setItems(list);
    }
   /* @FXML
    private void mouseClicked(MouseEvent event) {
        try {
            Child child = table.getSelectionModel().getSelectedItem();

            // Assuming the Child class has appropriate constructors and getters
            this.child = new Child(
                    child.getId(),
                    child.getFirstname(),
                    child.getLastname(),
                    child.getDateofbirth(),
                    child.getActivities(),
                    child.getGender(),
                    child.getUsername(),
                    child.getPhone(),
                    child.getEmail(),
                    child.getAllergies(),
                    child.getConditions(),
                    child.getMessage()
            );

            firstnameTextField.setText(child.getFirstname());
            lastnameTextField.setText(child.getLastname());
            //dateofbirthDatePicker.setValue(child.getDateofbirth());
            phoneTextField.setText(child.getPhone());
            usernameTextField.setText(child.getUsername());
            emailTextField.setText(child.getEmail());
           // allergiesTextField.setText(child.getAllergies());
           // medicalTextField.setText(child.getMedical());
          //  messageTextField.setText(child.getMessage());

            // Set gender RadioButton
            if ("Male".equalsIgnoreCase(child.getGender())) {
                malegender.setSelected(true);
            } else if ("Female".equalsIgnoreCase(child.getGender())) {
                femalegender.setSelected(true);
            }

            // Reset activities CheckBoxes
            Act1.setSelected(false);
            Act2.setSelected(false);
            Act3.setSelected(false);
            Act4.setSelected(false);
            Act5.setSelected(false);
            Act6.setSelected(false);

            // Set activities CheckBoxes
            String activities = child.getActivities();
            if (activities.contains("Outdoor Exploration")) Act1.setSelected(true);
            if (activities.contains("Arts and Crafts")) Act2.setSelected(true);
            if (activities.contains("Music and Movement")) Act3.setSelected(true);
            if (activities.contains("Swimming Pool")) Act4.setSelected(true);
            if (activities.contains("Sensory Play")) Act5.setSelected(true);
            if (activities.contains("Yoga")) Act6.setSelected(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
   private void handleChildSelection(MouseEvent event) {
       Child selectedChild = table.getSelectionModel().getSelectedItem();
       if (selectedChild != null) {
           this.child = selectedChild;

           // Update the UI with the selected child's information
           firstnameTextField.setText(child.getFirstname());
           lastnameTextField.setText(child.getLastname());
           dateofbirthDatePicker.setValue(child.getDateOfBirth());
           phoneTextField.setText(child.getPhone());
           usernameTextField.setText(child.getUsername());
           emailTextField.setText(child.getEmail());
           allergiesTextField.setText(child.getAllergies());
           medicalTextField.setText(child.getConditions());
           messageTextField.setText(child.getMessage());

           // Set gender RadioButton
           if ("Male".equalsIgnoreCase(child.getGender())) {
               malegender.setSelected(true);
           } else if ("Female".equalsIgnoreCase(child.getGender())) {
               femalegender.setSelected(true);
           }

           // Reset activities CheckBoxes
           Act1.setSelected(false);
           Act2.setSelected(false);
           Act3.setSelected(false);
           Act4.setSelected(false);
           Act5.setSelected(false);
           Act6.setSelected(false);

           // Set activities CheckBoxes
           String activities = child.getActivities();
           if (activities.contains("Outdoor Exploration")) Act1.setSelected(true);
           if (activities.contains("Arts and Crafts")) Act2.setSelected(true);
           if (activities.contains("Music and Movement")) Act3.setSelected(true);
           if (activities.contains("Swimming Pool")) Act4.setSelected(true);
           if (activities.contains("Sensory Play")) Act5.setSelected(true);
           if (activities.contains("Yoga")) Act6.setSelected(true);

           // Enable update and delete buttons
           btnUpdate.setDisable(false);
           btnDelete.setDisable(false);
       }
   }
   
    @FXML
    private void updateChild(){
        try{

            com.example.demo.AppQuery query = new com.example.demo.AppQuery();
            Child child = new Child(
                    this.child.getId(),
                    usernameTextField.getText(),
                    emailTextField.getText(),
                    phoneTextField.getText(),
                    null,
                    null, // Assuming you also want to hash the password confirmation
                    firstnameTextField.getText(),
                    lastnameTextField.getText(),
                    null,
                    null,
                    null,  // Assuming filename is not being set here
                    null,
                    allergiesTextField.getText(),
                    medicalTextField.getText(),
                    messageTextField.getText()
            );
            query.updateChild(child);
            showChildren();
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void deleteChild(){
        try{
            com.example.demo.AppQuery query = new com.example.demo.AppQuery();
            Child child = new Child(
                    this.child.getId(),
                    usernameTextField.getText(),
                    emailTextField.getText(),
                    phoneTextField.getText(),
                    null,
                    null, // Assuming you also want to hash the password confirmation
                    firstnameTextField.getText(),
                    lastnameTextField.getText(),
                    null,
                    null,
                    null,  // Assuming filename is not being set here
                    null,
                    allergiesTextField.getText(),
                    medicalTextField.getText(),
                    messageTextField.getText()
            );
            query.deleteChild(child);
            showChildren();
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
