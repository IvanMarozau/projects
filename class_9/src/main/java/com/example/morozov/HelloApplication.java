package com.example.morozov;

import com.example.morozov.models.Users;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        File file = new File("user.settings");
        DB db = new DB();

        if(file.exists()) {
            FileInputStream fis = new FileInputStream("user.settings");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Users users = (Users) ois.readObject();
            ois.close();
            if(db.isExistsUser(users.getLogin()))
                setScene("articles-panel.fxml", stage);
            else
                setScene("main.fxml", stage);
        }else {
            setScene("main.fxml", stage);
        }

    }

    public static void main(String[] args) {
        launch();
    }
    public static void setScene(String sceneName, Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Program");
        stage.setScene(scene);
        stage.show();

    }
}