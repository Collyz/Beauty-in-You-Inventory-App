package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class ProfileController {

    @FXML
    private Text usernameInfo = new Text();
    @FXML
    private Text passwordInfo = new Text();
    @FXML
    private AnchorPane profilePane = new AnchorPane();

    @FXML
    protected void onLogoutClick(javafx.event.ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("login-register.fxml"));
        Scene s = new Scene(p);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(s);
        appStage.show();
    }

    @FXML
    protected void onOpen(javafx.event.Event event)throws IOException{
        DatabaseConnection connection = setConnection();
        connection.getConnection();
        usernameInfo.setText("work");
        passwordInfo.setText("test");
    }

    protected DatabaseConnection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        return connection;
    }


}
