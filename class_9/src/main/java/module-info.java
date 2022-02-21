module com.example.morozov {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.morozov to javafx.fxml;
    exports com.example.morozov;
    exports com.example.morozov.controllers;
    opens com.example.morozov.controllers to javafx.fxml;
    exports com.example.morozov.models;
    opens com.example.morozov.models to javafx.fxml;
}