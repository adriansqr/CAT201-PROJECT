module com.example.catproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.catproject to javafx.fxml;
    exports com.example.catproject;
}