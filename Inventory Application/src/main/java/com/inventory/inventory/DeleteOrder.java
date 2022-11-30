package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteOrder {

    private int id;
    @FXML private Text order_ID = new Text();
    @FXML private Text customer_ID = new Text();
    @FXML private Text date = new Text();
    @FXML private Text product_ID = new Text();
    @FXML private Text completed = new Text();

    @FXML
    protected void initialize(){}

    @FXML
    protected void setID(int ID){
        this.id = ID;
    }

    @FXML
    protected void setOrderID(String o){
        order_ID.setText(o);
    }

    @FXML
    protected void setCustomerID(String c){
        customer_ID.setText(c);
    }

    @FXML
    protected void setDate(String d){
        date.setText(d);
    }

    @FXML
    protected void setProductID(String p){
        product_ID.setText(p);
    }

    @FXML
    protected void setCompleted(String co){
        completed.setText(co);
    }

    @FXML
    protected void onDelete(){
        String execute1 = "DELETE FROM `inventorydatabase`.`Order` WHERE Order_ID = " + id;
        String execute2 = "DELETE FROM `inventorydatabase`.`Order_Line` WHERE Order_ID = " + id;
        String execute3 = "DELETE FROM `inventorydatabase`.`Order_Completed` WHERE Order_ID = " + id;
        DatabaseConnection connection = setConnection();
        try{
            Statement statement = connection.databaseLink.createStatement();
            statement.executeUpdate(execute1);
            statement.executeUpdate(execute2);
            statement.executeUpdate(execute3);
            Stage stage = (Stage) order_ID.getScene().getWindow();
            stage.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void onCancel(){
        Stage stage = (Stage) order_ID.getScene().getWindow();
        stage.close();
    }

    protected DatabaseConnection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        return connection;
    }
}