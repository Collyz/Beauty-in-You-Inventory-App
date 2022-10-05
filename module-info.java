module com.example.testscenebuilder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.testscenebuilder to javafx.fxml;
    exports com.example.testscenebuilder;
}