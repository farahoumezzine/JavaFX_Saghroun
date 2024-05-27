package com.example.demo;

import java.sql.Date;

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
}
