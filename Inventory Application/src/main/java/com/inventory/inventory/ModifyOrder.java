package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ModifyOrder {

    @FXML private TextField orderID = new TextField();
    @FXML private TextField quantity = new TextField();
    @FXML private TextField product_ID = new TextField();
    @FXML private TextField date = new TextField();
    @FXML private TextField customer_ID = new TextField();


    @FXML
    protected void setOrderID(String o){
        orderID.setText(o);
    }
    @FXML
    protected void setDate(String d){
        date.setText(d);
    }

    @FXML
    protected  void setQuantity(String q) {
        quantity.setText(q);
    }

    @FXML
    protected  void setCustomerID(String c) {
        customer_ID.setText(c);
    }

    @FXML
    protected  void setProductID(String p) {
        product_ID.setText(p);
    }

    @FXML
    public void onEdit() {
        String execute1 = "UPDATE projectprototype.Order " +
                "SET Customer_ID = '"  + customer_ID.getText() + "' ," +
                "Order_Date = '"  + date.getText() + "' " +
                " WHERE Order_ID = " + orderID.getText();
        String execute2 = "UPDATE projectprototype.Orderline " +
                "SET Product_ID = '"  + product_ID.getText() + "' ," +
                "Quantity = '"  + quantity.getText() + "' " +
                " WHERE Order_ID = " + orderID.getText();
        Connection connection = setConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(execute1);
            statement.executeUpdate(execute2);
            Stage stage = (Stage) date.getScene().getWindow();
            stage.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onCancel() {
        Stage stage = (Stage) date.getScene().getWindow();
        stage.close();
    }

    protected Connection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getConnection();
    }
}
