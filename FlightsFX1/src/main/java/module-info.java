module flightsfx.flightsfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens flightsfx.flightsfx to javafx.fxml;
    opens flightsfx.model to javafx.base;
    exports flightsfx.flightsfx;
    exports flightsfx.model;
}