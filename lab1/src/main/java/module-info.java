module com.example.lab1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab1 to javafx.fxml;
    opens controllers to javafx.fxml;
    exports com.example.lab1;
    exports lab1;
    exports exceptions;
    exports controllers;
}