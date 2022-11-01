package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteProduct {

    private int id;
    @FXML
    private Text name = new Text();
    @FXML private Text category = new Text();
    @FXML private Text price = new Text();
    @FXML private Text quantity = new Text();
    @FXML private Text shelfLife = new Text();

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
    public void onDelete(){
        String execute = "DELETE FROM PRODUCT WHERE PRODUCT_ID = " + id;
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

    @FXML public void onCancel(){
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    protected DatabaseConnection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        return connection;
    }



}
