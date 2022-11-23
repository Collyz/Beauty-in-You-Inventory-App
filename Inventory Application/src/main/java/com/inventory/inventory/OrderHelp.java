package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OrderHelp {

    @FXML private Text editOrderHelp = new Text();
    @FXML
    public void onExit() {
        Stage stage = (Stage) editOrderHelp.getScene().getWindow();
        stage.close();
    }
}
