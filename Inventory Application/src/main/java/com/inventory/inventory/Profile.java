package com.inventory.inventory;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Profile {

    //For Profile Tab
    @FXML private Text usernameInfo = new Text();
    @FXML private Text passwordInfo = new Text();
    //For Search Tab
    @FXML TextField searchBar = new TextField();
    @FXML CheckMenuItem brushesFilter = new CheckMenuItem();
    @FXML CheckMenuItem makeupFilter = new CheckMenuItem();
    @FXML CheckMenuItem skinFilter = new CheckMenuItem();
    @FXML CheckMenuItem faceFilter = new CheckMenuItem();
    @FXML CheckMenuItem lipFilter = new CheckMenuItem();
    @FXML CheckMenuItem eyeFilter = new CheckMenuItem();
    @FXML TextArea searchResults = new TextArea();

    //For Add/Modify Tab
    @FXML TextField addProductName = new TextField();
    @FXML TextField addProductCat = new TextField();
    @FXML TextField addProductPrice = new TextField();
    @FXML TextField addProductQuan = new TextField();
    @FXML TextField addProductLife = new TextField();
    @FXML Text addProductResponse = new Text();
    @FXML Text asterisk1 = new Text();
    @FXML Text asterisk2 = new Text();
    @FXML Text asterisk3 = new Text();
    @FXML Text asterisk4 = new Text();
    @FXML Button addProductButton = new Button();


    @FXML
    protected void onLogoutClick(javafx.event.ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("login-register.fxml"));
        Scene s = new Scene(p);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(s);
        appStage.show();
    }

/*    @FXML
    protected void onSearch(javafx.event.ActionEvent event) throws IOException{
        DatabaseConnection connection = setConnection();
        String search = searchBar.getText();
        String searchQuery =
        try{
            Statement statement = connection.databaseLink.createStatement();
            ResultSet connect = statement.executeQuery()
        }catch(Exception e){
            e.printStackTrace();
        }

    }*/

    //Filters
    @FXML protected void onBrushesClick(){
        if(brushesFilter.isSelected()) {
            DatabaseConnection connection = setConnection();
            String selectBrushes = "SELECT PRODUCT_NAME from Product WHERE CATEGORY = \'BRUSHES\'";
            try {
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(selectBrushes);
                String delimiter = "-------------------------\n";
                String setText = "BRUSHES: \n";
                while (result.next()) {
                    setText = setText + result.getString(1) + "\n";
                }
                searchResults.setText(searchResults.getText() + setText + delimiter);
                System.out.println(searchResults.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else{
            StringTokenizer st = new StringTokenizer(searchResults.getText(),"\n");
            ArrayList<String> words = new ArrayList<>();
            while(st.hasMoreTokens()){
                words.add(st.nextToken());
            }
            for(int i = 0; i < words.size(); i++){
                if(words.get(i).equals("BRUSHES: ")){
                    while(!words.get(i).equals("-------------------------")){
                        words.remove(i);
                    }
                    words.remove(i);
                }
            }
            String temp = "";
            while(!words.isEmpty()){
                temp = temp + words.remove(0) + "\n";
            }
            searchResults.setText(temp);
        }
    }
    @FXML protected void onMakeupRemoverClick() {
        String setText = null;
        if (makeupFilter.isSelected()) {
            DatabaseConnection connection = setConnection();
            String selectBrushes = "SELECT PRODUCT_NAME from Product WHERE CATEGORY = \'MAKEUP REMOVERS\'";
            try {
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(selectBrushes);
                String delimiter = "-------------------------\n";
                setText = "MAKEUP REMOVER: \n";
                while (result.next()) {
                    setText = setText + result.getString(1) + "\n";
                }
                searchResults.setText(searchResults.getText() + setText + delimiter);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            StringTokenizer st = new StringTokenizer(searchResults.getText(),"\n");
            ArrayList<String> words = new ArrayList<>();
            while(st.hasMoreTokens()){
                words.add(st.nextToken());
            }
            for(int i = 0; i < words.size(); i++){
                if(words.get(i).equals("MAKEUP REMOVER: ")){
                    while(!words.get(i).equals("-------------------------")){
                        words.remove(i);
                    }
                    words.remove(i);
                }
            }
            String temp = "";
            while(!words.isEmpty()){
                temp = temp + words.remove(0) + "\n";
            }
            searchResults.setText(temp);
        }
    }
    @FXML protected void onSkincareClick(){
        if(skinFilter.isSelected()) {
            DatabaseConnection connection = setConnection();
            String selectBrushes = "SELECT PRODUCT_NAME from Product WHERE CATEGORY = \'SKINCARE\'";
            try {
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(selectBrushes);
                String delimiter = "-------------------------\n";
                String setText = "SKIN: \n";
                while (result.next()) {
                    setText = setText + result.getString(1) + "\n";
                }
                searchResults.setText(searchResults.getText() + setText + delimiter);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            StringTokenizer st = new StringTokenizer(searchResults.getText(),"\n");
            ArrayList<String> words = new ArrayList<>();
            while(st.hasMoreTokens()){
                words.add(st.nextToken());
            }
            for(int i = 0; i < words.size(); i++){
                if(words.get(i).equals("SKIN: ")){
                    while(!words.get(i).equals("-------------------------")){
                        words.remove(i);
                    }
                    words.remove(i);
                }
            }
            String temp = "";
            while(!words.isEmpty()){
                temp = temp + words.remove(0) + "\n";
            }
            searchResults.setText(temp);
        }
    }
    @FXML protected void onFaceClip(){
        if(faceFilter.isSelected()) {
            DatabaseConnection connection = setConnection();
            String selectBrushes = "SELECT PRODUCT_NAME from Product WHERE CATEGORY = \'FACE\'";
            try {
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(selectBrushes);
                String delimiter = "-------------------------\n";
                String setText = "FACE: \n";
                while (result.next()) {
                    setText = setText + result.getString(1) + "\n";
                }
                searchResults.setText(searchResults.getText() + setText + delimiter);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            StringTokenizer st = new StringTokenizer(searchResults.getText(),"\n");
            ArrayList<String> words = new ArrayList<>();
            while(st.hasMoreTokens()){
                words.add(st.nextToken());
            }
            for(int i = 0; i < words.size(); i++){
                if(words.get(i).equals("FACE: ")){
                    while(!words.get(i).equals("-------------------------")){
                        words.remove(i);
                    }
                    words.remove(i);
                }
            }
            String temp = "";
            while(!words.isEmpty()){
                temp = temp + words.remove(0) + "\n";
            }
            searchResults.setText(temp);
        }
    }
    @FXML protected void onLipsClick(){
        if(lipFilter.isSelected()) {
            DatabaseConnection connection = setConnection();
            String selectBrushes = "SELECT PRODUCT_NAME from Product WHERE CATEGORY = \'LIPS\'";
            try {
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(selectBrushes);
                String delimiter = "-------------------------";
                String setText = "LIPS: \n";
                while (result.next()) {
                    setText = setText + result.getString(1) + "\n";
                }
                searchResults.setText(searchResults.getText() + setText + delimiter);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            StringTokenizer st = new StringTokenizer(searchResults.getText(),"\n");
            ArrayList<String> words = new ArrayList<>();
            while(st.hasMoreTokens()){
                words.add(st.nextToken());
            }
            for(int i = 0; i < words.size(); i++){
                if(words.get(i).equals("LIPS: ")){
                    while(!words.get(i).equals("-------------------------")){
                        words.remove(i);
                    }
                    words.remove(i);
                }
            }
            String temp = "";
            while(!words.isEmpty()){
                temp = temp + words.remove(0) + "\n";
            }
            searchResults.setText(temp);
        }
    }
    @FXML protected void onEyesCLick() {
        if(eyeFilter.isSelected()) {
            DatabaseConnection connection = setConnection();
            String selectBrushes = "SELECT PRODUCT_NAME from Product WHERE CATEGORY = \'EYES\'";
            try {
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(selectBrushes);
                String delimiter = "-------------------------\n";
                String setText = "EYES: \n";
                while (result.next()) {
                    setText = setText + result.getString(1) + "\n";
                }
                searchResults.setText(searchResults.getText() + setText + delimiter);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            StringTokenizer st = new StringTokenizer(searchResults.getText(),"\n");
            ArrayList<String> words = new ArrayList<>();
            while(st.hasMoreTokens()){
                words.add(st.nextToken());
            }
            for(int i = 0; i < words.size(); i++){
                if(words.get(i).equals("EYES: ")){
                    while(!words.get(i).equals("-------------------------")){
                        words.remove(i);
                    }
                    words.remove(i);
                }
            }
            String temp = "";
            while(!words.isEmpty()){
                temp = temp + words.remove(0) + "\n";
            }
            searchResults.setText(temp);
        }
    }

    @FXML
    protected  void onAdd(){
        //Creates the database connection for queries
        DatabaseConnection connection = setConnection();
        //Gets the ID of the last item to increment the ID number when adding a new number
        String getIDS = "select PRODUCT_ID from Product ORDER BY PRODUCT_ID DESC LIMIT 1;";
        //The empty query
        String insertQuery = "";
        //Use to determine if a shelf life is input to determine what type of query is used.
        Boolean addLife = null;
        //The check that determines which type of query is used
        if (!addProductLife.getText().equals("")) {
            insertQuery = "INSERT INTO Product VALUES (";
            addLife = true;
        } else {
            insertQuery = "Insert INTO Product(Product_ID, CATEGORY, PRODUCT_NAME, QUANTITY, PRICE) VALUES (";
            addLife = false;
        }
        //Determines if the required fields 'Product Name, Category, Price, and Quantity' are filled
        Boolean sendQueryOrNot = !addProductName.getText().equals("") &&
                !addProductCat.getText().equals("") &&
                !addProductPrice.getText().equals("") &&
                !addProductQuan.getText().equals("");

        //If the required fields are filled
        if (sendQueryOrNot) {
            //Makes sure that the filled in text fields are the proper type input i.e. String vs int vs double
            /**
             * to be added
             */
            try {
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(getIDS);
                while (result.next()) {
                    //Creates a query that INCLUDES the shelf life of a product
                    if (addLife) {
                        //Gets the last item in product table id
                        int prodID = result.getInt(1) + 1;
                        insertQuery = insertQuery + (prodID)
                                + ",\'" + addProductName.getText() + "\'," +
                                "\'" + addProductCat.getText().toUpperCase() + "\',"
                                + addProductPrice.getText() + ","
                                + addProductQuan.getText() + ","
                                + addProductLife.getText() + ")";
                        addProductResponse.setText("");
                        addProductResponse.setText("Added Successfully ID: " + prodID);
                    } else {
                        //Creates a query that DOES NOT INCLUDE the shelf life of a product
                        insertQuery = insertQuery + (result.getInt(1) + 1)
                                + ",\'" + addProductName.getText() + "\'," +
                                "\'" + addProductCat.getText() + "\',"
                                + addProductPrice.getText() + ","
                                + addProductQuan.getText() + ")";
                    }
                }
                //Clears the text fields after the query is sent
                addProductName.clear();
                addProductCat.clear();
                addProductPrice.clear();
                addProductQuan.clear();
                addProductLife.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //Inserts the item into the database;
            try {
                Statement statement = connection.databaseLink.createStatement();
                statement.executeUpdate(insertQuery);
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
    protected void onOpen()throws SQLException {
        DatabaseConnection connection = setConnection();
        Statement stmt  = connection.databaseLink.createStatement();

        String usernameQuery = "SELECT Username FROM Admin_Accounts";
        PreparedStatement userStatement=connection.databaseLink.prepareStatement(usernameQuery);
        ResultSet userResultSet=userStatement.executeQuery();
        if(userResultSet.next()) {
            usernameInfo.setText(userResultSet.getString(1)); }
        //usernameInfo.setText(String.valueOf(stmt.execute("SELECT Username FROM Admin_Accounts")));
        //String resultUser = String.valueOf(stmt.execute("SELECT Username FROM Admin_Accounts"));
        //usernameInfo.setText(resultUser);

        String passwordQuery = "SELECT Password FROM Admin_Accounts";
        PreparedStatement passStatement=connection.databaseLink.prepareStatement(passwordQuery);
        ResultSet passResultSet=passStatement.executeQuery();
        if(passResultSet.next()) {
            passwordInfo.setText(passResultSet.getString(1)); }
        //passwordInfo.setText(String.valueOf(stmt.execute("SELECT Password FROM Admin_Accounts")));
        //String resultPass = String.valueOf(stmt.execute("SELECT Password FROM Admin_Accounts"));
        //passwordInfo.setText(resultPass);

    }

    protected DatabaseConnection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        return connection;
    }


}
