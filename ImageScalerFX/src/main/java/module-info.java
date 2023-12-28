module imagescalerfx.imagescalerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens imagescalerfx.imagescalerfx to javafx.fxml;
    exports imagescalerfx.imagescalerfx;
    opens imagescalerfx.model to javafx.base;
    exports imagescalerfx.model;
}