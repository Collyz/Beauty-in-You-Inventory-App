package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ModifyCustomer {

    private int id;
    @FXML private TextField name = new TextField();
    @FXML private TextField phone = new TextField();
    @FXML private TextField address = new TextField();
    @FXML private TextField email = new TextField();
    private ProfileController controller;
    private ProfileModel profileModel;

    public void setID(int id){
        this.id = id;
    }

    @FXML
    protected void setName(String n){
        name.setText(n);
    }

    @FXML
    protected  void setPhone(String p) {
        phone.setText(p);
    }

    @FXML
    protected  void setEmail(String e) {
        email.setText(e);
    }

    @FXML
    protected  void setAddress(String a) {
        address.setText(a);
    }

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
    public void onEdit() {
        String execute = "UPDATE Customer " +
                "SET Customer_Name = '"  + name.getText() + "' ," +
                "Customer_Phone = '"  + phone.getText() + "' ," +
                "Customer_Email = '" + email.getText() + "' ," +
                "Customer_Address = '"  + address.getText() + "'" +
                " WHERE Customer_ID = " + id;
        Connection connection = setConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(execute);
            Stage stage = (Stage) name.getScene().getWindow();
            getProfileModel().updateCustomerTable(getProfileController());
            getProfileController().getCustomerTextResponse().setText("Updated");
            stage.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onCancel() {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    protected Connection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getConnection();
    }
}
