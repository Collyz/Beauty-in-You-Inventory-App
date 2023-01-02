package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteCustomer {

    private int id;
    @FXML private Text name = new Text();
    @FXML private Text phone = new Text();
    @FXML private Text email = new Text();
    @FXML private Text address = new Text();
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
    public void onDelete() {
        String execute = "DELETE FROM Customer WHERE Customer_ID = " + id;
        DatabaseConnection connection = setConnection();
        try{
            Statement statement = connection.databaseLink.createStatement();
            statement.executeUpdate(execute);
            Stage stage = (Stage) name.getScene().getWindow();
            getProfileModel().updateCustomerTable(getProfileController());
            getProfileController().getCustomerTextResponse().setText("Deleted");
            stage.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onCancel(){
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    protected DatabaseConnection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        return connection;
    }
}
