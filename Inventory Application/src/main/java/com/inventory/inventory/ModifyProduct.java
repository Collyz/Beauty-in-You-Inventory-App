package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ModifyProduct {

    private int id;
    @FXML private TextField name = new TextField();
    @FXML private TextField category = new TextField();
    @FXML private TextField price = new TextField();
    @FXML private TextField quantity = new TextField();
    @FXML private TextField shelfLife = new TextField();

    public void initialize(){}

    public void setID(int ID){
        this.id = ID;
    }

    @FXML
    protected void setName(String n){
        name.setText(n);
    }

    @FXML
    protected void setCategory(String c){
        category.setText(c);
    }

    @FXML
    protected void setPrice(String p){
        price.setText(p);
    }

    @FXML
    protected void setQuantity(String q){
        quantity.setText(q);
    }

    @FXML
    protected void setShelfLife(String s){
        shelfLife.setText(s);
    }

    @FXML
    protected void onEdit(){
        String execute = "UPDATE PRODUCT " +
                "SET PRODUCT_NAME = '"  + name.getText() + "' ," +
                "CATEGORY = '"  + category.getText() + "' ," +
                "PRICE = "  + price.getText() + " ," +
                "QUANTITY = "  + quantity.getText() + " ," +
                "SHELF_LIFE = "  + shelfLife.getText() +
                " WHERE PRODUCT_ID = " + id;
        Connection connection = setConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(execute);
            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void onCancel(){
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    protected Connection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getConnection();
    }
}
