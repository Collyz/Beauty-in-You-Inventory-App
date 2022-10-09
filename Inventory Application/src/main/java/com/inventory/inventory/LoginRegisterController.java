package com.inventory.inventory;

//JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//MySQL imports
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginRegisterController {

    @FXML
    private TabPane loginPane = new TabPane();
    @FXML
    private TabPane registerPane = new TabPane();
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

    private String username = "";
    private String password = "";

    @FXML
    protected void onLoginClick(javafx.event.ActionEvent event) throws IOException {
        DatabaseConnection connection = setConnection();
        String selectUsername = " Select Username from ADMIN_ACCOUNTS WHERE Username = \'" + loginUsername.getText()  +"\'";
        String selectPassword = " Select Password from ADMIN_ACCOUNTS WHERE Password = \'" + loginPassword.getText()  +"\'";
        //Executes the query
        try{
            Statement statement = connection.databaseLink.createStatement();
            ResultSet connect = statement.executeQuery(selectUsername);

            //Gets the username stored in database

            while(connect.next()){
                setUsername(connect.getString("Username"));
            }
            //Gets the password stored in database
            connect = statement.executeQuery(selectPassword);
            while(connect.next()){
                setPassword(connect.getString("Password"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        if(loginUsername.getText().equals(this.username) && loginPassword.getText().equals(this.password)) {
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

    @FXML
    protected void onRegisterClick(javafx.event.ActionEvent events) throws IOException{
        DatabaseConnection connection = setConnection();
        String query = "";
        if(registerUsername.getText().length() >= 8 ){
            registerWrong.setText("");
            if(registerPassword.getText().equals(registerPasswordRe.getText())) {
                query = "INSERT INTO Admin_Accounts VALUES( 0 , " +
                        "\'" + registerUsername.getText() + "\', " +
                        "\'" + registerPassword.getText() + "\'," +
                        "\'" + registerEmail.getText() + "\')";
                registerWrong.setText("");
            }
            else{
                registerWrong.setText("Passwords do not match! ");
            }
        }
        else{
            registerWrong.setText("Your username is too short");
        }
        //Executes the query
        try{
            Statement statement = connection.databaseLink.createStatement();
            statement.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected DatabaseConnection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        return connection;
    }

    private void setUsername(String username){
        this.username = username;
    }
    private void setPassword(String password){
        this.password = password;
    }
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

}
