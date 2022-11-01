package com.inventory.inventory;

import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.events.Event;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Profile {

    //For Profile Tab
    @FXML private Text usernameInfo = new Text();
    @FXML private Text passwordInfo = new Text();
    //For Search Tab
    @FXML private TextField searchBar = new TextField();
    @FXML private CheckMenuItem catBrushes = new CheckMenuItem();
    @FXML private CheckMenuItem catMakeUp = new CheckMenuItem();
    @FXML private CheckMenuItem catSkin = new CheckMenuItem();
    @FXML private CheckMenuItem catFace = new CheckMenuItem();
    @FXML private CheckMenuItem catLips = new CheckMenuItem();
    @FXML private CheckMenuItem catEyes = new CheckMenuItem();
    @FXML private CheckMenuItem quantLeast = new CheckMenuItem();
    @FXML private CheckMenuItem quantMiddle = new CheckMenuItem();
    @FXML private CheckMenuItem quantGreatest = new CheckMenuItem();
    @FXML private CheckMenuItem shelfNA = new CheckMenuItem();
    @FXML private CheckMenuItem shelf6M = new CheckMenuItem();
    @FXML private CheckMenuItem shelf12M = new CheckMenuItem();
    @FXML private CheckMenuItem shelf18M = new CheckMenuItem();
    @FXML private CheckMenuItem shelf24M = new CheckMenuItem();
    @FXML private TextArea searchRes = new TextArea();

    //For Add/Modify Tab
    @FXML private TextField addProductName = new TextField();
    @FXML private TextField addProductCat = new TextField();
    @FXML private TextField addProductPrice = new TextField();
    @FXML private TextField addProductQuan = new TextField();
    @FXML private TextField addProductLife = new TextField();
    @FXML private Text addProductResponse = new Text();
    @FXML private Text asterisk1 = new Text();
    @FXML private Text asterisk2 = new Text();
    @FXML private Text asterisk3 = new Text();
    @FXML private Text asterisk4 = new Text();
    @FXML private TextField modSearch = new TextField();
    @FXML private Text findResponse = new Text();


    //Opens the previous login/register page essentially logging out
    @FXML
    protected void onLogoutClick(javafx.event.ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-register.fxml")));
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
                        int padding = 37 - name.length();
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

    @FXML
    public void displayAll(){
        if(searchRes.getText().equals("") && searchBar.getText().equals("")){
            DatabaseConnection connection = setConnection();
            String search = "SELECT * FROM PRODUCT";
            try{

                String setText = String.format("%s %s%33s %s %s %s", "ID", "Category", "Name", "Quantity", "Price", "Shelf Life") + "\n";
                Statement stmt = connection.databaseLink.createStatement();
                ResultSet results = stmt.executeQuery(search);
                while(results.next()){
                    int ID = results.getInt(1);
                    String category = results.getString(2);
                    String name = results.getString(3);
                    int quantity = results.getInt(4);
                    double price = results.getDouble(5);
                    String shelf = results.getString(6);
                    if(shelf == null){
                        shelf = "N/A";
                    }
                    int padding = 40 - category.length();
                    String formatting = "%2d %s %"+ padding + "s %6d %7.2f %7s";
                    String formattedString = String.format(formatting, ID, category, name, quantity, price, shelf + "\n");
                    setText = setText + formattedString;
                }
                searchRes.setText(searchBar.getText() + setText);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    //Helper Method to onSearch. It clears filters and empties the results text area
    private void deselectFilters(){
        //Category filters deselection
        catBrushes.setSelected(false);
        catMakeUp.setSelected(false);
        catSkin.setSelected(false);
        catFace.setSelected(false);
        catLips.setSelected(false);
        catEyes.setSelected(false);
        //Quantity filters deselection
        quantLeast.setSelected(false);
        quantMiddle.setSelected(false);
        quantGreatest.setSelected(false);
        //Shelf Life filter deselection
        shelfNA.setSelected(false);
        shelf6M.setSelected(false);
        shelf12M.setSelected(false);
        shelf18M.setSelected(false);
        shelf24M.setSelected(false);
    }

    //Filters: Category, Quantity, Shelf Life
    /**
     * Category Filters -
     * When displayed it shows the category name followed immediately below it by the name,
     * On the right of the name is the quantity and shel life if applicable.
     */
    @FXML
    protected void catFilters(){
        //Quantity filters deselection
        quantLeast.setSelected(false);
        quantMiddle.setSelected(false);
        quantGreatest.setSelected(false);
        //Shelf Life filter deselection
        shelfNA.setSelected(false);
        shelf6M.setSelected(false);
        shelf12M.setSelected(false);
        shelf12M.setSelected(false);
        shelf24M.setSelected(false);

        searchRes.clear();
        DatabaseConnection connection = setConnection();
        String query = "SELECT PRODUCT_NAME, QUANTITY, SHELF_LIFE from Product WHERE CATEGORY = ";
        String[] categories = {"\'BRUSHES\' ORDER BY PRODUCT_Name", "\'MAKEUP REMOVERS\'  ORDER BY PRODUCT_Name",
                "\'SKINCARE\' ORDER BY PRODUCT_Name", "\'FACE\' ORDER BY PRODUCT_Name", "\'LIPS\' ORDER BY PRODUCT_Name",
                "\'EYES\' ORDER BY PRODUCT_Name"};
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
        if(catBrushes.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[0]);
        } else{removeCatText("\'BRUSHES\'");}
        if(catMakeUp.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[1]);
        } else{removeCatText("\'MAKEUP REMOVER\'");}
        if(catSkin.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[2]);
        } else{removeCatText("\'SKINCARE\'");}
        if(catFace.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[3]);
        } else{removeCatText("\'FACE\'");}
        if(catLips.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[4]);
        } else{removeCatText("\'LIPS\'");}
        if(catEyes.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[5]);
        } else{removeCatText("\'EYES\'");}

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
     * Quantity Filters - 
     * When displayed it shows the category name followed immediately below it by the name.
     * On the right of the name is the quantity in ascending order from top to bottom and 
     * shelf life if applicable.
     */
    @FXML protected void quantFilters(){
        //Category filters deselection
        catBrushes.setSelected(false);
        catMakeUp.setSelected(false);
        catSkin.setSelected(false);
        catFace.setSelected(false);
        catLips.setSelected(false);
        catEyes.setSelected(false);
        //Shelf Life filter deselection
        shelfNA.setSelected(false);
        shelf6M.setSelected(false);
        shelf12M.setSelected(false);
        shelf18M.setSelected(false);
        shelf24M.setSelected(false);

        searchRes.clear();
        DatabaseConnection connection = setConnection();
        String query = "SELECT CATEGORY, PRODUCT_NAME, QUANTITY, SHELF_LIFE FROM Product WHERE QUANTITY ";
        String[] quantities = {"<= 3 ORDER BY QUANTITY", "> 3 AND QUANTITY < 10 ORDER BY QUANTITY", "> 10 ORDER BY QUANTITY"};
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
     * Shelf Life Filters - 
     * When displayed it shows the category name followed immediately below it by the name.
     * On the right of the name is the shelf life in ascending order from top to bottom 
     * and shelf life if applicable.
     */
    @FXML protected void shelfFilters(){
        //Category filters deselection
        catBrushes.setSelected(false);
        catMakeUp.setSelected(false);
        catSkin.setSelected(false);
        catFace.setSelected(false);
        catLips.setSelected(false);
        catEyes.setSelected(false);
        //Quantity filters deselection
        quantLeast.setSelected(false);
        quantMiddle.setSelected(false);
        quantGreatest.setSelected(false);

        searchRes.clear();
        DatabaseConnection connection = setConnection();
        String query = "SELECT CATEGORY, PRODUCT_NAME, QUANTITY, SHELF_LIFE FROM Product WHERE SHELF_LIFE ";
        String[] shelfLife = {"IS NULL", " = 6 ORDER BY SHELF_LIFE", " = 12 ORDER BY SHELF_LIFE", " = 18 ORDER BY SHELF_LIFE", " = 24 ORDER BY SHELF_LIFE"};
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
        if(shelf6M.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[1]);
        } else{removeShelfText(shelfLife[1]);}
        if(shelf12M.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[2]);
        } else{removeShelfText(shelfLife[2]);}
        if(shelf18M.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[3]);
        } else{removeShelfText(shelfLife[3]);}
        if(shelf24M.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[4]);
        } else{removeShelfText(shelfLife[4]);}
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
    protected void onEdit(){
        if(!modSearch.getText().equals("")) {
            DatabaseConnection connection = setConnection();
            String query = "SELECT PRODUCT_NAME FROM PRODUCT WHERE PRODUCT_NAME = '" + modSearch.getText() + "'";
            try {
                String compare = "";
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(query);
                while (result.next()) {
                    compare = result.getString(1);
                }
                if (compare.equals(modSearch.getText())) {
                    String query2 = "SELECT PRODUCT_ID, PRODUCT_NAME, CATEGORY, PRICE, QUANTITY, SHELF_LIFE from Product WHERE PRODUCT_NAME = '" + compare + "'";
                    ResultSet result2 = statement.executeQuery(query2);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-product.fxml"));
                    Parent p = loader.load();
                    Stage editWindow = new Stage();
                    editWindow.setTitle("Edit");
                    editWindow.setScene(new Scene(p));
                    editWindow.initModality(Modality.APPLICATION_MODAL);
                    ModifyProduct controller = loader.getController();
                    while(result2.next()){
                        controller.setID(result2.getInt(1));
                        controller.setName(result2.getString(2));
                        controller.setCategory(result2.getString(3));
                        controller.setPrice(result2.getString(4));
                        controller.setQuantity(result2.getString(5));
                        controller.setShelfLife(result2.getString(6));
                    }
                    editWindow.show();
                    findResponse.setText("SUCCESS!");
                }
                else{
                    findResponse.setText("No matches found, try again");
                }

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            findResponse.setText("Input the name of the product you want to edit");
        }
    }

    @FXML
    protected void onDelete(){
        if(!modSearch.getText().equals("")) {
            DatabaseConnection connection = setConnection();
            String query = "SELECT PRODUCT_NAME FROM PRODUCT WHERE PRODUCT_NAME = '" + modSearch.getText() + "'";
            try {
                String compare = "";
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(query);
                while (result.next()) {
                    compare = result.getString(1);
                }
                if (compare.equals(modSearch.getText())) {
                    String query2 = "SELECT PRODUCT_ID, PRODUCT_NAME, CATEGORY, PRICE, QUANTITY, SHELF_LIFE from Product WHERE PRODUCT_NAME = '" + compare + "'";
                    ResultSet result2 = statement.executeQuery(query2);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("delete-product.fxml"));
                    Parent p = loader.load();
                    Stage editWindow = new Stage();
                    editWindow.setTitle("Edit");
                    editWindow.setScene(new Scene(p));
                    editWindow.initModality(Modality.APPLICATION_MODAL);
                    DeleteProduct controller = loader.getController();
                    while(result2.next()){
                        controller.setID(result2.getInt(1));
                        controller.setName(result2.getString(2));
                        controller.setCategory(result2.getString(3));
                        controller.setPrice(result2.getString(4));
                        controller.setQuantity(result2.getString(5));
                        controller.setShelfLife(result2.getString(6));
                    }
                    editWindow.show();
                    findResponse.setText("SUCCESS!");
                }
                else{
                    findResponse.setText("No matches found, try again");
                }

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            findResponse.setText("Input the name of the product you want to edit");
        }

    }

    @FXML
    protected void onOpen()throws SQLException {
        DatabaseConnection connection = setConnection();

        String usernameQuery = "SELECT Username, Password FROM Admin_Accounts";
        Statement userStatement = connection.databaseLink.createStatement();
        ResultSet userResultSet = userStatement.executeQuery(usernameQuery);
        if(userResultSet.next()) {
            usernameInfo.setText(userResultSet.getString(1));
            passwordInfo.setText(userResultSet.getString(2));}
    }

    protected DatabaseConnection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        return connection;
    }


}
