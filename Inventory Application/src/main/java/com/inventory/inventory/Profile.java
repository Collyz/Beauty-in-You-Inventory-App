package com.inventory.inventory;

import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    @FXML CheckMenuItem quantLeast = new CheckMenuItem();
    @FXML CheckMenuItem quantMiddle = new CheckMenuItem();
    @FXML CheckMenuItem quantGreatest = new CheckMenuItem();
    @FXML CheckMenuItem shelfNA = new CheckMenuItem();
    @FXML CheckMenuItem shelfSixM = new CheckMenuItem();
    @FXML CheckMenuItem shelfTwelveM = new CheckMenuItem();
    @FXML TextArea searchRes = new TextArea();

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


    //Opens the previous login/register page essentially logging out
    @FXML
    protected void onLogoutClick(javafx.event.ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("login-register.fxml"));
        Scene s = new Scene(p);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(s);
        appStage.show();
    }

    /**
     * The search bar method. After typing in the name of a product and pressing enter,
     * a query is sent and the results of it are displayed. It closes all filters and clears
     * the result text areas.
     */
    @FXML
    protected void onSearch(){
        searchBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                searchRes.clear();
                DatabaseConnection connection = setConnection();
                String search = searchBar.getText();
                String searchQuery = "SELECT CATEGORY, PRODUCT_NAME, QUANTITY, SHELF_LIFE from Product WHERE PRODUCT_NAME = \'" + search + "\'";
                try {
                    Statement statement = connection.databaseLink.createStatement();
                    ResultSet result = statement.executeQuery(searchQuery);
                    String setText = "";
                    while (result.next()) {
                        String category = "\'" + result.getString(1) + "\'\n";
                        String name = result.getString(2);
                        String quantity = result.getString(3);
                        String shelf = result.getString(4);
                        if(shelf == null){
                            shelf = "N/A";
                        }
                        int padding= 37 - name.length();
                        String formatting = "%s%" + padding + "s%25s";
                        String formattedString = String.format(formatting, name, quantity, shelf + "\n");
                        setText = category + formattedString;
                    }
                    deselectFilters();
                    searchRes.setText(setText);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    //Helper Method to onSearch. It clears menu check items and empties the result text areas.
    private void deselectFilters(){
        brushesFilter.setSelected(false);
        makeupFilter.setSelected(false);
        skinFilter.setSelected(false);
        faceFilter.setSelected(false);
        lipFilter.setSelected(false);
        eyeFilter.setSelected(false);
    }

    //Filters

    /**
     * Filters the products by categories.
     * When displayed it shows the category name followed immediately bellow it by the name,
     * on the right of the name is the quantity and shel life if applicable
     * @return String[] - used by helper method removeCatFilters to remove
     * category filters that are not selected.
     */
    @FXML
    protected String[] catFilters(){
        searchRes.clear();
        DatabaseConnection connection = setConnection();
        String query = "SELECT PRODUCT_NAME, QUANTITY, SHELF_LIFE from Product WHERE CATEGORY = ";
        String[] categories = {"\'BRUSHES\'", "\'MAKEUP REMOVERS\'", "\'SKINCARE\'", "\'FACE\'", "\'LIPS\'", "\'EYES\'"};
        String[] queryResults = new String[6];
        for(int i = 0; i < categories.length; i++){
            try{
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(query + categories[i]);
                String delimiter = "----------------------------\n";
                String setText = categories[i] + "\n";
                while(result.next()){
                    String name = result.getString(1);
                    String quantity = result.getString(2);
                    String shelf = result.getString(3);
                    if(shelf == null){
                        shelf = "N/A";
                    }
                    int padding= 37 - name.length();
                    String formatting = "%s%" + padding + "s%25s";
                    String formattedString = String.format(formatting, name, quantity, shelf + "\n");
                    setText = setText + formattedString;
                }
                queryResults[i] = setText + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        if(brushesFilter.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[0]);
        } else{removeCatText("\'BRUSHES\'");}
        if(makeupFilter.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[1]);
        } else{removeCatText("\'MAKEUP REMOVER\'");}
        if(skinFilter.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[2]);
        } else{removeCatText("\'SKINCARE\'");}
        if(faceFilter.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[3]);
        } else{removeCatText("\'FACE\'");}
        if(lipFilter.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[4]);
        } else{removeCatText("\'LIPS\'");}
        if(eyeFilter.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[5]);
        } else{removeCatText("\'EYES\'");}
        return queryResults;
    }
    //Helper method for catFilters. Removes the text of the filter that is unselected
    private void removeCatText(String filter){
        StringTokenizer st = new StringTokenizer(searchRes.getText(),"\n");
        ArrayList<String> words = new ArrayList<>();
        while(st.hasMoreTokens()){
            words.add(st.nextToken());
        }
        for(int i = 0; i < words.size(); i++){
            if(words.get(i).equals(filter)){
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
        searchRes.setText(temp);
    }

    /**
     *
     */
    @FXML protected void quantFilters(){
        searchRes.clear();
        DatabaseConnection connection = setConnection();
        String query = "SELECT CATEGORY, PRODUCT_NAME, QUANTITY, SHELF_LIFE FROM Product WHERE QUANTITY ";
        String[] quantities = {"<= 3", "> 3 AND QUANTITY < 10", "> 10"};
        String[] queryResults = new String[quantities.length];
        for(int i = 0; i < quantities.length; i++){
            try{
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(query + quantities[i]);
                String delimiter = "----------------------------\n";
                String setText = "";
                while(result.next()){
                    String category = "\'" + result.getString(1) + "\'\n";
                    String name = result.getString(2);
                    String quantity = result.getString(3);
                    String shelf = result.getString(4);
                    if(shelf == null){
                        shelf = "N/A";
                    }
                    int padding= 37 - name.length();
                    String formatting = "%s%" + padding + "s%25s";
                    String formattedString = String.format(formatting, name, quantity, shelf + "\n");
                    setText = setText + category + formattedString;
                }
                queryResults[i] = setText + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        if(quantLeast.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[0]);
        } else{removeQuantText(quantities[0]);}
        if(quantMiddle.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[1]);
        } else{removeCatText(quantities[1]);}
        if(quantGreatest.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[2]);
        }else{removeQuantText(quantities[2]);}
    }
    //Helper method to quantFilters. Removes the text of the filter that is unselected
    private void removeQuantText(String filter){
        StringTokenizer st = new StringTokenizer(searchRes.getText(),"\n");
        ArrayList<String> words = new ArrayList<>();
        while(st.hasMoreTokens()){
            words.add(st.nextToken());
        }
        for(int i = 0; i < words.size(); i++){
            if(words.get(i).equals(filter)){
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
        searchRes.setText(temp);
    }

    /**
     *
     */
    @FXML protected void shelfFilters(){
        searchRes.clear();
        DatabaseConnection connection = setConnection();
        String query = "SELECT CATEGORY, PRODUCT_NAME, QUANTITY, SHELF_LIFE FROM Product WHERE SHELF_LIFE ";
        String[] shelfLife = {"IS NULL", " = 6", " = 12"};
        String[] queryResults = new String[shelfLife.length];
        for(int i = 0; i < shelfLife.length; i++){
            try{
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(query + shelfLife[i]);
                String delimiter = "----------------------------\n";
                String setText = "";
                while(result.next()){
                    String category = "\'" + result.getString(1) + "\'\n";
                    String name = result.getString(2);
                    String quantity = result.getString(3);
                    String shelf = result.getString(4);
                    if(shelf == null){
                        shelf = "N/A";
                    }
                    int padding= 37 - name.length();
                    String formatting = "%s%" + padding + "s%25s";
                    String formattedString = String.format(formatting, name, quantity, shelf + "\n");
                    setText = setText + category + formattedString;
                }
                queryResults[i] = setText + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        if(shelfNA.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[0]);
        }else{removeShelfText("N/A");}
        if(shelfSixM.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[1]);
        } else{removeShelfText(shelfLife[1]);}
        if(shelfTwelveM.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[2]);
        } else{removeShelfText(shelfLife[2]);}
    }
    //Helper method to quantFilters
    private void removeShelfText(String filter){
        StringTokenizer st = new StringTokenizer(searchRes.getText(),"\n");
        ArrayList<String> words = new ArrayList<>();
        while(st.hasMoreTokens()){
            words.add(st.nextToken());
        }
        for(int i = 0; i < words.size(); i++){
            if(words.get(i).equals(filter)){
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
        searchRes.setText(temp);
    }
    //Adds a product to the database in the product table
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
