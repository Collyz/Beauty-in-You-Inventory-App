module com.inventory.inventory {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.mail;

    opens com.inventory.inventory to javafx.fxml;
    exports com.inventory.inventory;
}