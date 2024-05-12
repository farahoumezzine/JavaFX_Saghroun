package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Stage registerStage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 750);
        registerStage.initStyle(StageStyle.UNDECORATED);
        registerStage.setScene(scene);
        registerStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}