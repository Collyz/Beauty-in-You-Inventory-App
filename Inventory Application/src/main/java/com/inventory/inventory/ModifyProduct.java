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
    public void setName(String n){
        name.setText(n);
    }
    public void setCategory(String c){
        category.setText(c);
    }

    public void setPrice(String p){
        price.setText(p);
    }

    public void setQuantity(String q){
        quantity.setText(q);
    }

    public void setShelfLife(String s){
        shelfLife.setText(s);
    }

    @FXML
    public void onEdit(){
        String execute = "UPDATE PRODUCT " +
                "SET PRODUCT_NAME = '"  + name.getText() + "' ," +
                "CATEGORY = '"  + category.getText() + "' ," +
                "PRICE = "  + price.getText() + " ," +
                "QUANTITY = "  + quantity.getText() + " ," +
                "SHELF_LIFE = "  + shelfLife.getText() +
                " WHERE PRODUCT_ID = " + id;
        DatabaseConnection connection = setConnection();
        try{
            Statement statement = connection.databaseLink.createStatement();
            statement.executeUpdate(execute);
            Stage stage = (Stage) name.getScene().getWindow();
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
