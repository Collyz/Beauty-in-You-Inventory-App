package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddCustomer {

    @FXML private TextField name = new TextField();
    @FXML private TextField phone = new TextField();
    @FXML private TextField address = new TextField();
    @FXML private TextField email = new TextField();
    @FXML private Text response = new Text();
    private ProfileController controller;
    private ProfileModel profileModel;


    public void setProfileController(ProfileController controller){
        this.controller = controller;
    }

    public ProfileController getProfileController(){
        return this.controller;
    }

    public void setProfileModel(ProfileModel profileModel){
        this.profileModel = profileModel;
    }

    public ProfileModel getProfileModel(){
        return profileModel;
    }

    @FXML
    public void onAdd() {
        //Creates the database connection for queries
        Connection connection = setConnection();
        //Query to get the ID of the last item to increment the ID number when adding a new number
        String getIDS = "SELECT Customer_ID FROM Customer ORDER BY Customer_ID DESC LIMIT 1";
        int id = 0;
        //Getting the id
        try{
            Statement statement = connection.createStatement();
            ResultSet idResult = statement.executeQuery(getIDS);
            while(idResult.next()){
                id = idResult.getInt(1) + 1;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        //The insert Query
        String tempPhone = "'" + phone.getText() + "'";
        String tempEmail = "'" + email.getText() + "'";
        if(tempPhone.equals("")){
            tempPhone = "null";
        }
        if(tempEmail.equals("")){
            tempEmail = "null";
        }
        String insertQuery = "INSERT INTO CUSTOMER VALUES("+ id + ", '" + name.getText() + "', " + tempPhone + " ,'" + address.getText() +  "', " + tempEmail + ")";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);
            getProfileModel().updateCustomerTable(getProfileController());
            response.setText("Success!");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onCancel(){
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    protected Connection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getConnection();
    }
}
