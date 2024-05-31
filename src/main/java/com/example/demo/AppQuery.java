package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppQuery {
    private DatabaseConnection c =new DatabaseConnection();

    public void addChildren(com.example.demo.Child children){
        try{
            c.getConnection();
            java.sql.PreparedStatement ps = c.getConnection().prepareStatement("INSERT INTO children (username, password, firstname, lastname, dateofbirth," +
                    " phone, email,conditions,allergies, activities, gender,password_confirmation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ? , ? , ? , ? , ? , ?)");

            ps.setString(1, children.getUsername());
            ps.setString(2, children.getPassword());
            ps.setString(3, children.getFirstname());
            ps.setString(4,children.getLastname());
            ps.setDate(5, Date.valueOf(children.getDateOfBirth()));
            ps.setString(6,  children.getPhone());
            ps.setString(7, children.getEmail());
            ps.setString(8, children.getConditions());
            ps.setString(9, children.getAllergies());
            ps.setString(10, children.getActivities());
            ps.setString(11, children.getGender());
            ps.setString(12, children.getPasswordConfirmation());
            ps.execute();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<Child> getChildrenList() {
        ObservableList<Child> childList = FXCollections.observableArrayList();
        try {
            String query = "SELECT id, firstname, lastname, dateofbirth, activities, gender, username, phone, email, allergies,conditions,message FROM children";
             c.getConnection();
            Statement st = c.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            com.example.demo.Child s;
            while (rs.next()) {
                s = new Child(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        null,  // Assuming password is not fetched
                        null,  // Assuming password confirmation is not fetched
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("dateofbirth").toLocalDate(),
                        rs.getString("activities"),
                        null,  // Assuming filename is not fetched
                        rs.getString("gender"),
                        rs.getString("allergies"),
                        rs.getString("conditions"),
                        rs.getString("message")
                );
                childList.add(s);
            }

            rs.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return childList;
    }

    public void updateChild(Child child) {
        try {
            java.sql.Connection connection = c.getConnection();
            java.sql.PreparedStatement ps = connection.prepareStatement(
                    "UPDATE children SET firstname = ?, lastname = ?, dateofbirth = ?, activities = ?, gender = ?, username = ?, phone = ?, email = ?, allergies = ?, conditions = ?, message = ? WHERE id = ?");

            ps.setString(1, child.getFirstname());
            ps.setString(2, child.getLastname());
            ps.setDate(3, Date.valueOf(child.getDateOfBirth()));
            ps.setString(4, child.getActivities());
            ps.setString(5, child.getGender());
            ps.setString(6, child.getUsername());
            ps.setString(7, child.getPhone());
            ps.setString(8, child.getEmail());
            ps.setString(9, child.getAllergies());
            ps.setString(10, child.getConditions());
            ps.setString(11, child.getMessage());
            ps.setLong(12, child.getId()); // Set the ID parameter

            int rowsUpdated = ps.executeUpdate(); // Use executeUpdate to get the number of updated rows
            ps.close();
            connection.close();

            if (rowsUpdated > 0) {
                System.out.println("An existing child was updated successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteChild(Child child) {
        try {
            java.sql.Connection connection = c.getConnection();
            java.sql.PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM children WHERE id = ?");

            ps.setLong(1, child.getId());
            ps.executeUpdate(); // Use executeUpdate to delete the child
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}




