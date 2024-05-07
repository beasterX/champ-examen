module com.example.champ_examen {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.champ_examen to javafx.fxml;
    exports com.example.champ_examen;
}