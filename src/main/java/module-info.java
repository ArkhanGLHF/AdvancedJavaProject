module com.example.projetv0 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projetv0 to javafx.fxml;
    exports com.example.projetv0;
}