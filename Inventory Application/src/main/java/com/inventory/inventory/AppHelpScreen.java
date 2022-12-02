package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppHelpScreen {

    @FXML private final Text helpLabel = new Text();
    @FXML
    public void onExit() {
        Stage stage = (Stage) helpLabel.getScene().getWindow();
        stage.close();
    }
}
