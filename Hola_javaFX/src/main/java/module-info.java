module com.example.hola_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hola_javafx to javafx.fxml;
    exports com.example.hola_javafx;
}