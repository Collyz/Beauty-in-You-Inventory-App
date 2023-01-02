package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddProduct {

    @FXML private TextField name = new TextField();
    @FXML private TextField category = new TextField();
    @FXML private TextField price = new TextField();
    @FXML private TextField quantity = new TextField();
    @FXML private TextField shelfLife = new TextField();
    @FXML private Text addProductResponse = new Text();
    @FXML private Text asterisk1 = new Text();
    @FXML private Text asterisk2 = new Text();
    @FXML private Text asterisk3 = new Text();
    @FXML private Text asterisk4 = new Text();
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
    public void onAdd(){
        //Creates the database connection for queries
        Connection connection = setConnection();
        //Gets the ID of the last item to increment the ID number when adding a new number
        String getIDS = "SELECT PRODUCT_ID FROM Product ORDER BY PRODUCT_ID DESC LIMIT 1";
        //The empty query
        String insertQuery;
        //Use to determine if a shelf life is input to determine what type of query is used.
        boolean addLife;
        //The check that determines which type of query is used
        if (!shelfLife.getText().isBlank() || shelfLife.getText().equals("0")) {
            insertQuery = "INSERT INTO Product VALUES (";
            addLife = true;
        } else {
            insertQuery = "Insert INTO Product(Product_ID, PRODUCT_NAME, CATEGORY, QUANTITY, PRICE) VALUES (";
            addLife = false;
        }
        //Determines if the required fields 'Product Name, Category, Price, and Quantity' are filled
        boolean sendQueryOrNot = !name.getText().isBlank() &&
                !category.getText().isBlank() &&
                !price.getText().isBlank() &&
                !quantity.getText().isBlank();

        //If the required fields are filled
        if (sendQueryOrNot) {
            try {
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(getIDS);
                while (result.next()) {
                    //Creates a query that INCLUDES the shelf life of a product
                    if (addLife) {
                        //Gets the last item in product table id
                        int prodID = result.getInt(1) + 1;
                        insertQuery = insertQuery + (prodID)
                                + ", '" + category.getText().toUpperCase() + "'," +
                                "'" + name.getText() + "',"
                                + price.getText() + ","
                                + quantity.getText() + ","
                                + shelfLife.getText() + ")";
                        addProductResponse.setText("Added Successfully ID: " + prodID);
                    } else {
                        //Creates a query that DOES NOT INCLUDE the shelf life of a product
                        int prodID = result.getInt(1) + 1;
                        insertQuery = insertQuery + prodID
                                + ", '" + name.getText() + "' ," +
                                " '" + category.getText().toUpperCase() + "' ,"
                                + price.getText() + ","
                                + quantity.getText() + ")";
                        addProductResponse.setText("Added Successfully ID: " + prodID);
                    }
                }
                //Clears the text fields after the query is sent
                name.clear();
                category.clear();
                price.clear();
                quantity.clear();
                shelfLife.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //Inserts the item into the database;
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(insertQuery);
                getProfileModel().updateProductTable(getProfileController());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            //Response given in the addProductResponse text field if all the required text fields are not filled
            addProductResponse.setText("Fill in Required Fields");
            asterisk1.setText("*");
            asterisk2.setText("*");
            asterisk3.setText("*");
            asterisk4.setText("*");
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
