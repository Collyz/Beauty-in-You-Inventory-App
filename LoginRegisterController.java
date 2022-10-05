package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class LoginRegisterController {

    @FXML
    private TextField loginUsername = new TextField();
    @FXML
    private TextField loginPassword = new TextField();
    @FXML
    private TextField registerUsername = new TextField();
    @FXML
    private TextField registerPassword = new TextField();
    @FXML
    private TextField registerPasswordRe = new TextField();
    @FXML
    private TextField registerEmail = new TextField();
    @FXML
    private Text loginWrong = new Text();
    @FXML
    private Text registerWrong = new Text();

    @FXML
    private Button regButton = new Button();
    private int ID = 0;


    @FXML
    protected void onRegisterClick(javafx.event.ActionEvent events) throws IOException{
        if(registerPassword.getText().equals(registerPasswordRe.getText())) {
            String query = "INSERT INTO Admin_Accounts VALUES(" + registerUsername.getText() + ", "
                    + registerPassword.getText() + ", " + registerEmail.getText() + ", 1)";
            registerWrong.setText("");
        }
        else{
            registerWrong.setText("Passwords do not match! ");
        }
    }
    @FXML
    protected void onLoginClick(javafx.event.ActionEvent event) throws IOException {
        if(loginUsername.getCharacters().length() >= 8) {
            Parent p = FXMLLoader.load(getClass().getResource("profile.fxml"));
            Scene s = new Scene(p);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(s);
            appStage.show();
        }
        else{
            loginWrong.setText("Wrong Username/Password! Try again.");
        }
    }

}
