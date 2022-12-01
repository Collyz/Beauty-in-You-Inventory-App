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
    @FXML private Text response = new Text();

    @FXML
    protected void initialize(){}

    @FXML
    public void onAdd() {
        //Creates the database connection for queries
        Connection connection = setConnection();
        //Query to get the ID of the last item to increment the ID number when adding a new number
        String getIDS = "SELECT Order_ID FROM inventorydatabase.Order ORDER BY Order_ID DESC LIMIT 1";
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
        try{
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            statement1.executeQuery("SELECT Customer_ID FROM Customer WHERE Customer_Name='" + customerName.getText() +"';");
            ResultSet result1 = statement1.getResultSet();
            statement2.executeQuery("SELECT Product_ID FROM Product WHERE Product_Name='" + product.getText() +"';");
            ResultSet result2 = statement2.getResultSet();

            String insertQueryOrder = "INSERT INTO inventorydatabase.Order VALUES("+ id + ", '" + date.getText() + "', " + result1;
            String insertQueryOrderLine = "INSERT INTO inventorydatabase.Order_Line VALUES("+ id + ", '" + result2 + ", " + quantity.getText();
            Statement statement3 = connection.createStatement();
            statement3.executeUpdate(insertQueryOrder);
            statement3.executeUpdate(insertQueryOrderLine);
            response.setText("Success!");
        }catch(SQLException e){
            e.printStackTrace();
        }
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
