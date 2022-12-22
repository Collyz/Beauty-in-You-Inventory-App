package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;
    @FXML private TextField registerUsername;
    @FXML private TextField registerPassword;
    @FXML private TextField registerPasswordRedo;
    @FXML private TextField registerEmail;
    @FXML private Text loginWrong;
    @FXML private Text registerWrong;

    private final LoginModel model;

    public LoginController() {

        this.model = new LoginModel();
    }

    public void setRegisterWrong(String error){
        this.registerWrong.setText(error);
    }
    @FXML
    protected void loginButtonClick(javafx.event.ActionEvent event){
        try {
            if (model.verifyLogin(loginUsername.getText(), loginPassword.getText())) {
                Parent p = FXMLLoader.load((getClass().getResource("profile.fxml")));
                Scene s = new Scene(p);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(s);
                appStage.show();
            } else {
                loginWrong.setText("Wrong Username/Password! Try again.");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void registerButtonClick(){
        model.insertAccount(registerUsername.getText(), registerPassword.getText(), registerPasswordRedo.getText(),registerEmail.getText(), this);
    }

}