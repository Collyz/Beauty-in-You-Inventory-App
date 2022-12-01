package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddOrder {

    @FXML private TextField date = new TextField();
    @FXML private TextField customerName = new TextField();
    @FXML private TextField product = new TextField();
    @FXML private TextField quantity = new TextField();
    @FXML private Text addProductResponse = new Text();

    @FXML
    protected void initialize(){}

    @FXML
    public void onAdd() {
        //Creates the database connection for queries
        Connection connection = setConnection();
        //Query to get the ID of the last item to increment the ID number when adding a new number
        String getIDS = "SELECT Order_ID FROM projectprototype.Order ORDER BY Order_ID DESC LIMIT 1";
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
        int customerID = -1;
        //The insert Query
        try{
            Statement statement1 = connection.createStatement();
            statement1.executeQuery("SELECT Customer_ID FROM Customer WHERE Customer_Name = '" + customerName.getText() + "' ");

            ResultSet result1 = statement1.getResultSet();
            System.out.println(customerName.getText());
            while(result1.next()) {
                customerID = result1.getInt(1);
                System.out.println(customerID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        int prodID = -1;
        try{
            Statement statement2 = connection.createStatement();
            statement2.executeQuery("SELECT Product_ID FROM Product WHERE Product_Name = '" + product.getText()+"'");
            ResultSet result2 = statement2.getResultSet();
            while(result2.next()){
                prodID = result2.getInt(1);
                System.out.println(prodID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            String insertQueryOrder = "INSERT INTO projectprototype.Order VALUES("+ id + ", '" + date.getText() + "', " + customerID + ", " + 1 + ")";
            String insertQueryOrderLine = "INSERT INTO projetprototype.orderline VALUES(" + id + ", " + prodID+ ", " + quantity.getText() +")";
            Statement statement3 = connection.createStatement();
            statement3.executeUpdate(insertQueryOrder);
            statement3.executeUpdate(insertQueryOrderLine);
        }catch(SQLException e){
            e.printStackTrace();
        }
        addProductResponse.setText("Success!");
    }

    @FXML
    public void onCancel(){
        Stage stage = (Stage) date.getScene().getWindow();
        stage.close();
    }

    protected Connection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getConnection();
    }
}
