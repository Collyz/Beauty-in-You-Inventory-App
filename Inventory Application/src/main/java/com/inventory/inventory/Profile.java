package com.inventory.inventory;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Profile {

    //For Profile Tab
    @FXML private Text usernameInfo = new Text();
    @FXML private Text passwordInfo = new Text();

    //For Search Tab
    @FXML private TextField productSearchBar = new TextField();
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
    @FXML private Text productQueryResponse = new Text();

    //For Customer Search
    @FXML private TextField customerSearchBar = new TextField();
    @FXML private TextArea custSearchRes = new TextArea();
    @FXML private Text customerQueryResponse = new Text();

    @FXML private Button addCustomerButton = new Button();
    @FXML private Text addCustomerResponse = new Text();
    @FXML private Text asterisk5 = new Text();
    @FXML private Text asterisk6 = new Text();
    @FXML private Text asterisk7 = new Text();
    @FXML private Text asterisk8 = new Text();


    /**
     * Opens the previous login/register page essentially logging out
     * @param event  - Listens for an event on the logout button
     * @throws IOException thrown if there is an issue with the button
     */
    @FXML
    protected void onLogoutClick(javafx.event.ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-register.fxml")));
        Scene s = new Scene(p);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(s);
        appStage.show();
    }

    /**
     * Formats the
     * @param results - The result set to use to get information from the database
     * @return String that is displayed as a search result
     * @throws SQLException If the result set or database do not exist
     */
    private String productFormat(ResultSet results){
        String toReturn = String.format("%s %s%33s  %s  %s  %s", "ID", "Category", "Name", "Quantity", "Price", "Shelf Life") + "\n";
        try{
            if(results.next()) {
                do{
                    int ID = results.getInt(1);
                    String category = results.getString(2);
                    String name = results.getString(3);
                    int quantity = results.getInt(4);
                    double price = results.getDouble(5);
                    String shelf = results.getString(6);
                    if (shelf == null) {
                        shelf = "N/A";
                    }
                    int padding = 40 - category.length();
                    String formatting = "%2d %s %" + padding + "s  %6d  %7.2f  %7s";
                    String formattedString = String.format(formatting, ID, category, name, quantity, price, shelf + "\n");
                    toReturn = toReturn + formattedString;
                } while(results.next());
            }else{
                productQueryResponse.setText("No such product exits, try again");
                return "";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return toReturn;
    }
    /**
     * The search bar method. After typing in the name of a product and pressing enter,
     * a query is sent and the results of it are displayed. It closes all filters and clears
     * the result text areas.
     */
    @FXML
    protected void onProductSearch(){
        productSearchBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                searchRes.clear();
                Connection connection = setConnection();
                String search = productSearchBar.getText();
                String searchQuery = "SELECT * FROM PRODUCT WHERE PRODUCT_NAME = '" + search + "'";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(searchQuery);
                    deselect("categories");
                    deselect("quantities");
                    deselect("shelf life");
                    searchRes.setText(productFormat(result));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void displayAllProducts(){
        if(searchRes.getText().isBlank() && productSearchBar.getText().isBlank()){
            Connection connection = setConnection();
            String search = "SELECT * FROM PRODUCT";
            try{
                Statement stmt = connection.createStatement();
                ResultSet results = stmt.executeQuery(search);
                searchRes.setText(productFormat(results));
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void refreshProducts(){
        productSearchBar.clear();
        searchRes.clear();
        productQueryResponse.setText("");
        displayAllProducts();
    }

    //Helper Method to multiple methods that need to deselect filters (Both product and customer)
    private void deselect(String filter){
        CheckMenuItem[] deselect = {catBrushes,catMakeUp,catSkin,catFace,catLips,catEyes,quantLeast,quantMiddle,
                quantGreatest,shelfNA,shelf6M,shelf12M,shelf18M,shelf24M};
        if(filter.equals("categories")){
            for(int i = 0; i < 6; i++){
                deselect[i].setSelected(false);
            }
        }
        if(filter.equals("quantities")){
            for(int i = 6; i < 9; i++){
                deselect[i].setSelected(false);
            }
        }
        if(filter.equals("shelf life")){
            for(int i = 9; i < deselect.length; i++){
                deselect[i].setSelected(false);
            }
        }
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
        deselect("quantities");
        //Shelf Life filter deselection
        deselect("shelf life");
        //Clears the searchRes TextField
        searchRes.clear();
        Connection connection = setConnection();
        String query = "SELECT * FROM Product WHERE CATEGORY = ";
        String[] categories = {"'BRUSHES' ORDER BY PRODUCT_Name", "'MAKEUP REMOVERS'  ORDER BY PRODUCT_NAME",
                "'SKINCARE' ORDER BY PRODUCT_Name", "'FACE' ORDER BY PRODUCT_Name", "'LIPS' ORDER BY PRODUCT_Name",
                "'EYES' ORDER BY PRODUCT_Name"};
        String[] queryResults = new String[categories.length];
        for(int i = 0; i < categories.length; i++){
            try{
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query + categories[i]);
                String delimiter = "----------------------------\n";
                String setText = categories[i] + "\n";
                setText = setText + productFormat(result);
                queryResults[i] = setText + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        if(catBrushes.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[0]);
        } else{removeProductFilterText("'BRUSHES'");}
        if(catMakeUp.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[1]);
        } else{removeProductFilterText("'MAKEUP REMOVER'");}
        if(catSkin.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[2]);
        } else{removeProductFilterText("'SKINCARE'");}
        if(catFace.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[3]);
        } else{removeProductFilterText("'FACE'");}
        if(catLips.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[4]);
        } else{removeProductFilterText("'LIPS'");}
        if(catEyes.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[5]);
        } else{removeProductFilterText("'EYES'");}

    }

    /**
     * Quantity Filters - 
     * When displayed it shows the category name followed immediately below it by the name.
     * On the right of the name is the quantity in ascending order from top to bottom and 
     * shelf life if applicable.
     */
    @FXML protected void quantFilters(){
        //Category filters deselection
        deselect("categories");
        //Shelf Life filter deselection
        deselect("shelf life");
        //Clears the searchRes TextField
        searchRes.clear();
        Connection connection = setConnection();
        String query = "SELECT * FROM PRODUCT WHERE QUANTITY ";
        String[] quantities = {"<= 3 ORDER BY QUANTITY", "> 3 AND QUANTITY < 10 ORDER BY QUANTITY", "> 10 ORDER BY QUANTITY"};
        String[] queryResults = new String[quantities.length];
        for(int i = 0; i < quantities.length; i++){
            try{
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query + quantities[i]);
                String delimiter = "----------------------------\n";
                String setText = quantities[i] + "\n";
                setText = setText + productFormat(result);
                queryResults[i] = setText + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        if(quantLeast.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[0]);
        } else{removeProductFilterText(quantities[0]);}
        if(quantMiddle.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[1]);
        } else{removeProductFilterText(quantities[1]);}
        if(quantGreatest.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[2]);
        }else{removeProductFilterText(quantities[2]);}
    }

    /**
     * Shelf Life Filters - 
     * When displayed it shows the category name followed immediately below it by the name.
     * On the right of the name is the shelf life in ascending order from top to bottom 
     * and shelf life if applicable.
     */
    @FXML protected void shelfFilters(){
        //Category filters deselection
        deselect("categories");
        //Quantity filters deselection
        deselect("quantities");
        //Clears the searchRes TextField
        searchRes.clear();
        Connection connection = setConnection();
        String query = "SELECT * FROM PRODUCT WHERE SHELF_LIFE ";
        String[] shelfLife = {"IS NULL", " = 6 ORDER BY SHELF_LIFE", " = 12 ORDER BY SHELF_LIFE", " = 18 ORDER BY SHELF_LIFE", " = 24 ORDER BY SHELF_LIFE"};
        String[] queryResults = new String[shelfLife.length];
        for(int i = 0; i < shelfLife.length; i++){
            try{
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query + shelfLife[i]);
                String delimiter = "----------------------------\n";
                String setText = shelfLife[i] + "\n";
                setText = setText + productFormat(result);
                queryResults[i] = setText + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        if(shelfNA.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[0]);
        }else{removeProductFilterText("N/A");}
        if(shelf6M.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[1]);
        } else{removeProductFilterText(shelfLife[1]);}
        if(shelf12M.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[2]);
        } else{removeProductFilterText(shelfLife[2]);}
        if(shelf18M.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[3]);
        } else{removeProductFilterText(shelfLife[3]);}
        if(shelf24M.isSelected()){
            searchRes.setText(searchRes.getText() + queryResults[4]);
        } else{removeProductFilterText(shelfLife[4]);}
    }

    //Helper method for the filter methods. Removes the text of the filter that is unselected
    private void removeProductFilterText(String filter){
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

    //Opens a separate window that handles adding a product to the inventory
    @FXML
    protected  void addProduct(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-product.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Add Product");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            AddProduct controller = loader.getController();
            editWindow.show();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void editProduct(){
        if(!productSearchBar.getText().equals("")) {
            Connection connection = setConnection();
            String query = "SELECT PRODUCT_NAME FROM PRODUCT WHERE PRODUCT_NAME = '" + productSearchBar.getText() + "'";
            try {
                String compare = "";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query);
                while (result.next()) {
                    compare = result.getString(1);
                }
                if (compare.equals(productSearchBar.getText())) {
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
                    productQueryResponse.setText("SUCCESS!");
                }
                else{
                    productQueryResponse.setText("No matches found, try again");
                }

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            productQueryResponse.setText("Input the name of the product you want to edit");
        }
    }

    @FXML
    protected void deleteProduct(){
        if(!productSearchBar.getText().isBlank()) {
            Connection connection = setConnection();
            String query = "SELECT PRODUCT_NAME FROM PRODUCT WHERE PRODUCT_NAME = '" + productSearchBar.getText() + "'";
            try {
                String compare = "";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query);
                while (result.next()) {
                    compare = result.getString(1);
                }
                if (compare.equals(productSearchBar.getText())) {
                    String query2 = "SELECT PRODUCT_ID, PRODUCT_NAME, CATEGORY, PRICE, QUANTITY, SHELF_LIFE from Product WHERE PRODUCT_NAME = '" + compare + "'";
                    ResultSet result2 = statement.executeQuery(query2);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("delete-product.fxml"));
                    Parent p = loader.load();
                    Stage editWindow = new Stage();
                    editWindow.setTitle("Delete");
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
                    productQueryResponse.setText("SUCCESS!");
                }
                else{
                    productQueryResponse.setText("No matches found, try again");
                }

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            productQueryResponse.setText("Input the name of the product you want to edit");
        }

    }
/**********************************************************************************************************************/
    private String customerFormat(ResultSet results){
        String toReturn = String.format("%s  %s %20s  %18s  %22s", "ID", "Name", "Phone #", "Email", "Address") + "\n";
        try{

            if(results.next()){
                do{
                    int ID = results.getInt(1);
                    String name = results.getString(2);
                    String phone = results.getString(3);
                    String address = results.getString(4);
                    String email = results.getString(5);
                    if(phone == null){
                        phone = "N/A";
                    }
                    if(email == null){
                        email = "N/A";
                    }
                    int phonePad = 32 - name.length();
                    int emailPad = 37 - phone.length();
                    int addressPad = 39 - phone.length();
                    String formatting = "%s   %s%" + phonePad + "s%"+ emailPad +"s%"+ addressPad +"s";
                    String formattedString = String.format(formatting, ID, name, phone , email, address + "\n");
                    toReturn = toReturn + formattedString;
                } while(results.next());
            }else{
                customerQueryResponse.setText("No matches found, try again");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return toReturn;
    }

    //WIP
    private void removeCustomerFilterText(String filter){
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

    @FXML protected void refreshCustomers(){
        customerSearchBar.clear();
        custSearchRes.clear();
        customerQueryResponse.setText("");
        displayAllCustomers();
    }
    @FXML
    protected void onCustomerSearch(){
        customerSearchBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                custSearchRes.clear();
                Connection connection = setConnection();
                String search = customerSearchBar.getText();
                String searchQuery = "SELECT * FROM CUSTOMER WHERE CUSTOMER_NAME = \'" + search + "\'";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(searchQuery);
                    String setText = customerFormat(result);
                    deselect("");
                    custSearchRes.setText(setText);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML protected void displayAllCustomers(){
        if(custSearchRes.getText().isBlank() && customerSearchBar.getText().isBlank()){
            Connection connection = setConnection();
            String search = "SELECT * FROM CUSTOMER";
            try{
                Statement stmt = connection.createStatement();
                ResultSet results = stmt.executeQuery(search);
                custSearchRes.setText(customerFormat(results));
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void editCustomer() {
        if(!customerSearchBar.getText().isBlank()) {
            Connection connection = setConnection();
            String query = "SELECT CUSTOMER_NAME FROM CUSTOMER WHERE CUSTOMER_NAME = '" + customerSearchBar.getText() + "'";
            try {
                String compare = "";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query);
                while (result.next()) {
                    compare = result.getString(1);
                }
                if (compare.equals(customerSearchBar.getText())) {
                    String query2 = "SELECT CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_PHONE, CUSTOMER_EMAIL, CUSTOMER_ADDRESS FROM Customer WHERE CUSTOMER_NAME = '" + compare + "'";
                    ResultSet result2 = statement.executeQuery(query2);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-customer.fxml"));
                    Parent p = loader.load();
                    Stage editWindow = new Stage();
                    editWindow.setTitle("Edit");
                    editWindow.setScene(new Scene(p));
                    editWindow.initModality(Modality.APPLICATION_MODAL);
                    ModifyCustomer controller = loader.getController();
                    while(result2.next()){
                        controller.setID(result2.getInt(1));
                        controller.setName(result2.getString(2));
                        controller.setPhone(result2.getString(3));
                        controller.setEmail(result2.getString(4));
                        controller.setAddress(result2.getString(5));
                    }
                    editWindow.show();
                    customerQueryResponse.setText("SUCCESS!");
                }
                else{
                    customerQueryResponse.setText("No matches found, try again");
                }

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            customerQueryResponse.setText("Input the name of the product you want to edit");
        }
    }

    public void deleteCustomer() {
        if(!customerSearchBar.getText().isBlank()) {
            Connection connection = setConnection();
            String query = "SELECT CUSTOMER_NAME FROM CUSTOMER WHERE CUSTOMER_NAME = '" + customerSearchBar.getText() + "'";
            try {
                String compare = "";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query);
                while (result.next()) {
                    compare = result.getString(1);
                }
                if (compare.equals(customerSearchBar.getText())) {
                    String query2 = "SELECT CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_PHONE, CUSTOMER_EMAIL, CUSTOMER_ADDRESS FROM Customer WHERE CUSTOMER_NAME = '" + compare + "'";
                    ResultSet result2 = statement.executeQuery(query2);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("delete-customer.fxml"));
                    Parent p = loader.load();
                    Stage editWindow = new Stage();
                    editWindow.setTitle("Delete");
                    editWindow.setScene(new Scene(p));
                    editWindow.initModality(Modality.APPLICATION_MODAL);
                    DeleteCustomer controller = loader.getController();
                    while(result2.next()){
                        controller.setID(result2.getInt(1));
                        controller.setName(result2.getString(2));
                        controller.setPhone(result2.getString(3));
                        controller.setEmail(result2.getString(4));
                        controller.setAddress(result2.getString(5));
                    }
                    editWindow.show();
                    customerQueryResponse.setText("SUCCESS!");
                }
                else{
                    customerQueryResponse.setText("No matches found, try again");
                }

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            customerQueryResponse.setText("No matches found, try again");
        }
    }

    //WIP
    public void addCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-customer.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Add Customer");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            AddCustomer controller = loader.getController();
            editWindow.show();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onOpen()throws SQLException {
        Connection connection = setConnection();

        String usernameQuery = "SELECT Username, Password FROM Admin_Accounts";
        Statement userStatement = connection.createStatement();
        ResultSet userResultSet = userStatement.executeQuery(usernameQuery);
        if(userResultSet.next()) {
            usernameInfo.setText(userResultSet.getString(1));
            passwordInfo.setText(userResultSet.getString(2));}
    }
    
    protected Connection setConnection(){
        DatabaseConnection connection = new DatabaseConnection();
        return connection.getConnection();
    }
}
