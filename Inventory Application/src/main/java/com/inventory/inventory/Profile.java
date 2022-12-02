package com.inventory.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Profile implements Initializable{

    //For Profile Tab
    @FXML private Text usernameInfo;
    @FXML private Text passwordInfo;
    //For Product Tab
    @FXML private TextField productSearchBar;
    @FXML private TextField orderSearchBar;
    @FXML private CheckMenuItem catBrushes;
    @FXML private CheckMenuItem catMakeUp;
    @FXML private CheckMenuItem catSkin;
    @FXML private CheckMenuItem catFace;
    @FXML private CheckMenuItem catLips;
    @FXML private CheckMenuItem catEyes;
    @FXML private CheckMenuItem quantLeast;
    @FXML private CheckMenuItem quantMiddle;
    @FXML private CheckMenuItem quantGreatest;
    @FXML private CheckMenuItem shelfNA;
    @FXML private CheckMenuItem shelf6M;
    @FXML private CheckMenuItem shelf12M;
    @FXML private CheckMenuItem shelf18M;
    @FXML private CheckMenuItem shelf24M;
    @FXML private TextArea prodSearchRes;
    @FXML private TextArea orderSearchRes;
    @FXML private Text productQueryResponse;
    @FXML private Text orderQueryResponse;

    //For Customer Tab
    @FXML private TextField customerSearchBar;
    @FXML private TextArea custSearchRes;
    @FXML private Text customerQueryResponse;
    @FXML private CheckMenuItem nameForward;
    @FXML private CheckMenuItem nameBackward;
    @FXML private CheckMenuItem addressForward;
    @FXML private CheckMenuItem addressBackward;
    //Email tab
    @FXML private TextField email;
    @FXML private TextField subject;
    @FXML private TextArea message;
    @FXML private Text emailResponse;
    //Resizing
    @FXML private VBox vbox;
    @FXML private TabPane tabPane;
    @FXML private ToolBar toolBar;
    @FXML private Button pRefresh;
    @FXML private Button cRefresh;
    @FXML private Button oRefresh;
    @FXML private Button clearEmail;
    @FXML private Button sendEmail;

    @FXML private TableView<LowStockModel> lowQuantTable;
    @FXML private TableColumn<LowStockModel, Integer> columnID;
    @FXML private TableColumn<LowStockModel, String> columnProd;
    @FXML private TableColumn<LowStockModel, Integer> columnQuant;
    ObservableList<LowStockModel> lowStockModelList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resource){
        Connection connection = setConnection();
        String lowQuantityQuery = "SELECT PRODUCT_ID, PRODUCT_NAME, QUANTITY FROM Product WHERE QUANTITY <= 3";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(lowQuantityQuery);

            while(resultSet.next()){

                Integer queryID = resultSet.getInt("PRODUCT_ID");
                String queryName = resultSet.getString("PRODUCT_NAME");
                Integer queryQuantity = resultSet.getInt("QUANTITY");

                //Fills in the observable list
                lowStockModelList.add(new LowStockModel(queryID, queryName, queryQuantity));
            }

            columnID.setCellValueFactory(new PropertyValueFactory<>("prodID"));
            columnProd.setCellValueFactory(new PropertyValueFactory<>("prodName"));
            columnQuant.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            lowQuantTable.setItems(lowStockModelList);
        }catch(SQLException e){
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }
    @FXML
    protected void resize(){
        //Tab Pane for all tabs resize
        tabPane.setMinHeight(vbox.getHeight());

        /*Profile tab*/
        toolBar.setLayoutY(vbox.getHeight() - (toolBar.getHeight()*2) - 14);
        toolBar.setMinWidth(vbox.getWidth());
        /*Product tab*/
        //Product Searchbar resize
        productSearchBar.setMinWidth(vbox.getWidth() - 100);
        //Moving refresh button with searchbar
        pRefresh.setLayoutX(productSearchBar.getWidth() + 15);
        //Search Results resize
        prodSearchRes.setMinHeight(vbox.getHeight() - 200);
        prodSearchRes.setMinWidth(vbox.getWidth() - 40);

        /*Customer tab*/
        //Product Searchbar resize
        customerSearchBar.setMinWidth(vbox.getWidth() - 100);
        //Moving refresh button with searchbar
        cRefresh.setLayoutX(customerSearchBar.getWidth() + 15);
        //Search Results resize
        custSearchRes.setMinHeight(vbox.getHeight() - 200);
        custSearchRes.setMinWidth(vbox.getWidth() - 40);

        /*Order tab*/
        //Product Searchbar resize
        orderSearchBar.setMinWidth(vbox.getWidth() - 100);
        //Moving refresh button with searchbar
        oRefresh.setLayoutX(customerSearchBar.getWidth() + 15);
        //Search Results resize
        orderSearchRes.setMinHeight(vbox.getHeight() - 200);
        orderSearchRes.setMinWidth(vbox.getWidth() - 40);

        //Changes the fonts of all search results based on the size of the vbox
        if(vbox.getWidth() > 640 && vbox.getWidth() < 1000) {
            Font font = Font.font("Monospaced", 16);
            prodSearchRes.setFont(font);
            custSearchRes.setFont(font);
            orderSearchRes.setFont(font);
            /*Email tab*/
            email.setMinWidth(350);
            subject.setMinWidth(350);
            message.setMinWidth(350);
            message.setMinHeight(450);
            clearEmail.setLayoutX(email.getLayoutX() + email.getWidth() + 36);
            sendEmail.setLayoutX(email.getLayoutX() + email.getWidth() + 36);
            lowQuantTable.setMinWidth(409);
        }
        else if(vbox.getWidth() >= 1000){
            Font font = Font.font("Monospaced", 18);
            prodSearchRes.setFont(font);
            custSearchRes.setFont(font);
            orderSearchRes.setFont(font);
            /*Email tab*/
            email.setMinWidth(450);
            subject.setMinWidth(450);
            message.setMinWidth(450);
            message.setMinHeight(550);
            clearEmail.setLayoutX(email.getLayoutX() + email.getWidth() + 36);
            sendEmail.setLayoutX(email.getLayoutX() + email.getWidth() + 36);
            lowQuantTable.setMinWidth(509);

        }
        else{
            Font font = Font.font("Monospaced", 14);
            prodSearchRes.setFont(font);
            custSearchRes.setFont(font);
            orderSearchRes.setFont(font);
            email.setMinWidth(258);
            subject.setMinWidth(258);
            message.setMinWidth(258);
            message.setMinHeight(162);
            clearEmail.setLayoutX(email.getLayoutX() + email.getWidth() + 36);
            sendEmail.setLayoutX(email.getLayoutX() + email.getWidth() + 36);
        }

    }

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
                productQueryResponse.setText("No such product exists, try again.");
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
                prodSearchRes.clear();
                Connection connection = setConnection();
                String search = productSearchBar.getText();
                String searchQuery = "SELECT * FROM PRODUCT WHERE PRODUCT_NAME = '" + search + "'";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(searchQuery);
                    deselect("categories");
                    deselect("quantities");
                    deselect("shelf life");
                    prodSearchRes.setText(productFormat(result));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void displayAllProducts(){
        if(prodSearchRes.getText().isBlank() && productSearchBar.getText().isBlank()){
            Connection connection = setConnection();
            String search = "SELECT * FROM PRODUCT";
            try{
                Statement stmt = connection.createStatement();
                ResultSet results = stmt.executeQuery(search);
                prodSearchRes.setText(productFormat(results));
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void refreshProducts(){
        productSearchBar.clear();
        prodSearchRes.clear();
        productQueryResponse.setText("");
        displayAllProducts();
    }

    //Helper Method to multiple methods that need to deselect filters (Both product and customer)
    private void deselect(String filter){
        CheckMenuItem[] deselect = {catBrushes,catMakeUp,catSkin,catFace,catLips,catEyes,quantLeast,quantMiddle,
                quantGreatest,shelfNA,shelf6M,shelf12M,shelf18M,shelf24M,nameForward,nameBackward,addressForward,addressBackward};
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
            for(int i = 9; i < 14; i++){
                deselect[i].setSelected(false);
            }
        }
        if(filter.equals("name")){
            for(int i = 14; i <16; i++){
                deselect[i].setSelected(false);
            }
        }
        if(filter.equals("address")){
            for(int i = 16; i <18; i++){
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
        //Clears the prodSearchResTextField
        prodSearchRes.clear();
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
                queryResults[i] = productFormat(result) + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        queryResults[0] = "Category: Brushes\n" + queryResults[0];
        queryResults[1] = "Category: Makeup Removers\n" + queryResults[1];
        queryResults[2] = "Category: Skincare\n" + queryResults[2];
        queryResults[3] = "Category: Face\n" + queryResults[3];
        queryResults[4] = "Category: Lips\n" + queryResults[4];
        queryResults[5] = "Category: Eyes\n" + queryResults[5];
        if(catBrushes.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[0]);
        } else{removeProductFilterText("'BRUSHES'");}
        if(catMakeUp.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[1]);
        } else{removeProductFilterText("'MAKEUP REMOVER'");}
        if(catSkin.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[2]);
        } else{removeProductFilterText("'SKINCARE'");}
        if(catFace.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[3]);
        } else{removeProductFilterText("'FACE'");}
        if(catLips.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[4]);
        } else{removeProductFilterText("'LIPS'");}
        if(catEyes.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[5]);
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
        //Clears the prodSearchResTextField
        prodSearchRes.clear();
        Connection connection = setConnection();
        String query = "SELECT * FROM PRODUCT WHERE QUANTITY ";
        String[] quantities = {"<= 3 ORDER BY QUANTITY", "> 3 AND QUANTITY < 10 ORDER BY QUANTITY", "> 10 ORDER BY QUANTITY"};
        String[] queryResults = new String[quantities.length];
        for(int i = 0; i < quantities.length; i++){
            try{
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query + quantities[i]);
                String delimiter = "----------------------------\n";
                queryResults[i] = productFormat(result) + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        queryResults[0] = "Quantity Less than or Equal to Three\n" + queryResults[0];
        queryResults[1] = "Quantity Less than Ten\n" + queryResults[1];
        queryResults[2] = "Quantity Greater Than 10\n" + queryResults[2];
        if(quantLeast.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[0]);
        } else{removeProductFilterText(quantities[0]);}
        if(quantMiddle.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[1]);
        } else{removeProductFilterText(quantities[1]);}
        if(quantGreatest.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[2]);
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
        //Clears the prodSearchRes TextField
        prodSearchRes.clear();
        Connection connection = setConnection();
        String query = "SELECT * FROM PRODUCT WHERE SHELF_LIFE ";
        String[] shelfLife = {"IS NULL", " = 6 ORDER BY SHELF_LIFE", " = 12 ORDER BY SHELF_LIFE", " = 18 ORDER BY SHELF_LIFE", " = 24 ORDER BY SHELF_LIFE"};
        String[] queryResults = new String[shelfLife.length];
        for(int i = 0; i < shelfLife.length; i++){
            try{
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query + shelfLife[i]);
                String delimiter = "----------------------------\n";
                queryResults[i] = productFormat(result) + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        queryResults[0] = "Shelf Life: No Expiration\n" + queryResults[0];
        queryResults[1] = "Shelf Life: 6 Months\n" + queryResults[1];
        queryResults[2] = "Shelf Life: 12 Months\n" + queryResults[2];
        queryResults[3] = "Shelf Life: 18 Months\n" + queryResults[3];
        queryResults[4] = "Shelf Life: 24 Months\n" + queryResults[4];
        if(shelfNA.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[0]);
        }else{removeProductFilterText("N/A");}
        if(shelf6M.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[1]);
        } else{removeProductFilterText(shelfLife[1]);}
        if(shelf12M.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[2]);
        } else{removeProductFilterText(shelfLife[2]);}
        if(shelf18M.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[3]);
        } else{removeProductFilterText(shelfLife[3]);}
        if(shelf24M.isSelected()){
            prodSearchRes.setText(prodSearchRes.getText() + queryResults[4]);
        } else{removeProductFilterText(shelfLife[4]);}
    }

    //Helper method for the filter methods. Removes the text of the filter that is unselected
    private void removeProductFilterText(String filter){
        StringTokenizer st = new StringTokenizer(prodSearchRes.getText(),"\n");
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
        prodSearchRes.setText(temp);
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
            editWindow.show();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    //NEW
    @FXML
    protected void help(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("help-screen.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Help");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            editWindow.show();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    
    
    @FXML
    protected void appHelpScreen(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("app-help-screen.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Help - About BIY Inventory App");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
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
        StringTokenizer st = new StringTokenizer(prodSearchRes.getText(),"\n");
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
        prodSearchRes.setText(temp);
    }

    @FXML protected void customerNameFilter(){
        custSearchRes.clear();
        deselect("address");
        String query = "SELECT * FROM Customer ORDER BY Customer_Name";
        Connection connection = setConnection();
        String[] names = {"", " DESC"};
        String[] queryResults = new String[names.length];
        for(int i = 0; i < names.length; i++){
            try{
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query + names[i]);
                String delimiter = "----------------------------\n";
                String setText = customerFormat(result);
                queryResults[i] = setText + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        queryResults[0] = "Alphanumeric Customer Names\n" + queryResults[0];
        queryResults[1] = "Reverse Alphanumeric Customer Names\n" + queryResults[1];
        if(nameForward.isSelected()){
            custSearchRes.setText(custSearchRes.getText() + queryResults[0]);
        } else{removeCustomerFilterText(names[0]);}
        if(nameBackward.isSelected()){
            custSearchRes.setText(custSearchRes.getText() + queryResults[1]);
        } else{removeCustomerFilterText(names[1]);}
    }

    @FXML protected void customerAddressFilter(){
        custSearchRes.clear();
        deselect("name");
        String query = "SELECT * FROM Customer ORDER BY Customer_Address";
        Connection connection = setConnection();
        String[] names = {"", " DESC"};
        String[] queryResults = new String[names.length];
        for(int i = 0; i < names.length; i++){
            try{
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query + names[i]);
                String delimiter = "----------------------------\n";
                String setText = customerFormat(result);
                queryResults[i] = setText + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        queryResults[0] = "Alphanumeric Customer Addresses\n" + queryResults[0];
        queryResults[1] = "Reverse Alphanumeric Customer Addresses\n" + queryResults[1];
        if(addressForward.isSelected()){
            custSearchRes.setText(custSearchRes.getText() + queryResults[0]);
        } else{removeCustomerFilterText(names[0]);}
        if(addressBackward.isSelected()){
            custSearchRes.setText(custSearchRes.getText() + queryResults[1]);
        } else{removeCustomerFilterText(names[1]);}
    }

    @FXML
    protected void refreshCustomers(){
        deselect("name");
        deselect("address");
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
                String searchQuery = "SELECT * FROM CUSTOMER WHERE CUSTOMER_NAME = '" + search + "'";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(searchQuery);
                    String setText = customerFormat(result);
                    deselect("name");
                    deselect("address");
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
            editWindow.show();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void sendEmail(){
        SendEmail emailer = new SendEmail();
        try{
            emailer.sendMail(email.getText(), subject.getText(), message.getText());
            emailResponse.setText("Sent Successfully");
        } catch(Exception e){
            emailResponse.setText("Error!");
            e.printStackTrace();
        }
    }

    @FXML
    protected void clearEmail(){
        email.clear();
        subject.clear();
        message.clear();
        emailResponse.setText("");
    }


    @FXML
    protected void deleteOrder(){
        if(!orderSearchBar.getText().isBlank()) {
            Connection connection = setConnection();
            String query = "SELECT Order_ID FROM `projectprototype`.`Order` WHERE Order_ID = " + orderSearchBar.getText();
            try {
                String compare = "";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query);
                while (result.next()) {
                    compare = result.getString(1);
                }
                if (compare.equals(orderSearchBar.getText())) {
                    String query2 = "SELECT Order_ID, Customer_ID, Order_Date from `projectprototype`.`Order` WHERE Order_ID = '" + compare + "'";
                    ResultSet result2 = statement.executeQuery(query2);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("delete-order.fxml"));
                    Parent p = loader.load();
                    Stage editWindow = new Stage();
                    editWindow.setTitle("Delete");
                    editWindow.setScene(new Scene(p));
                    editWindow.initModality(Modality.APPLICATION_MODAL);
                    DeleteOrder controller = loader.getController();
                    while(result2.next()){
                        controller.setOrderID(result2.getString(1));
                        controller.setCustomerID(result2.getString(2));
                        controller.setDate(result2.getString(3));
                    }
                    editWindow.show();
                    orderQueryResponse.setText("SUCCESS!");
                }
                else{
                    orderQueryResponse.setText("No matches found, try again");
                }

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            orderQueryResponse.setText("Input the name of the order you want to edit.");
        }

    }


    @FXML
    protected  void addOrder(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-order.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Add Order");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            editWindow.show();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }



    /**
     * Formats the orders in the TextField below the search bar
     * @param results - The result set to use to get information from the database
     * @return String that is displayed as a search result
     */
    private String orderFormat(ResultSet results, ResultSet results2){
        String returnOrder = String.format("%s %12s %5s %19s %12s %10s %10s %10s" , "Order_ID", "Customer_ID", "Date", "Product_ID", "Quantity", "Cost" , "Tax", "Total") + "\n";
        ArrayList<String> orderList = new ArrayList<>();
        ArrayList<String> orderLineList = new ArrayList<>();
        ArrayList<String> productCost = new ArrayList<>();
        int productID = 0;
        ArrayList<Integer> productIDs = new ArrayList<>();
        ArrayList<Integer> productQuantity = new ArrayList<>();
        String searchProductPrice = "SELECT PRICE FROM PRODUCT where PRODUCT_ID = ";
        //Order
        try{
            if(results.next()) {
                do{
                    int order_ID = results.getInt(1);
                    String date = results.getString(2);
                    int customer_ID = results.getInt(3);

                    //%2d means integer ID, %s is string category, %padding is the string format of name, %7.2f for price(float)
                    String formatting = "%s %9s %20s ";
                    String formattedString = String.format(formatting, order_ID, customer_ID, date);
                    orderList.add(formattedString);
                } while(results.next());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        //Order Line
        try{
            if(results2.next()) {
                do{
                    int product_ID = results2.getInt(1);
                    int quantity = results2.getInt(2);
                    productIDs.add(product_ID);
                    productQuantity.add((quantity));

                    String formatting = "%5s %15s";
                    String formattedString = String.format(formatting, product_ID, quantity);
                    orderLineList.add(formattedString);
                } while(results2.next());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        //Product cost evaluation
        try{
            Connection connection = setConnection();
            Statement statement = connection.createStatement();
            for(int i = 0; i < productIDs.size(); i++) {
                ResultSet results3 = statement.executeQuery(searchProductPrice + productIDs.get(i));
                if (results3.next()) {
                    do {
                        double productPrice = results3.getInt(1) * productQuantity.get(i);
                        double tax = productPrice * .0625;
                        double cost = productPrice + tax;
                        String formatting = "%17s %10s %10s";
                        String formattedString = String.format(formatting, productPrice, tax, cost + "\n");
                        productCost.add(formattedString);
                    } while (results3.next());
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }


        for(int i = 0; i < orderList.size(); i++){
            returnOrder = returnOrder + orderList.get(i) + orderLineList.get(i) + productCost.get(i);
        }
        return returnOrder;
    }


    @FXML
    public void displayAllOrders(){
        if(orderSearchRes.getText().isBlank() && orderSearchBar.getText().isBlank()){
            Connection connection = setConnection();
            String searchOrder = "SELECT * FROM projectprototype.ORDER";
            String searchOrderLine = "SELECT Product_ID, Quantity FROM projectprototype.ORDERLINE";
            ResultSet[] results = new ResultSet[3];
            try{
                Statement stmt = connection.createStatement();
                ResultSet resultSet1 = stmt.executeQuery(searchOrder);
                results[0] = resultSet1;
            }catch(SQLException e){
                e.printStackTrace();
            }
            try{
                Statement stmt = connection.createStatement();
                ResultSet resultSet2 = stmt.executeQuery(searchOrderLine);
                results[1] = resultSet2;
            }catch(SQLException e){
                e.printStackTrace();
            }
            orderSearchRes.setText(orderFormat(results[0], results[1]));
        }
    }


    //refreshes all orders on the screen
    public void refreshOrders(){
        orderSearchBar.clear();
        orderSearchRes.clear();
        orderQueryResponse.setText("");
        displayAllOrders();
    }
    
    
    
    @FXML
    protected void editOrder() {
        String orderID = "";
        String date = "";
        String customerID = "";
        String productID = "";
        String quantity = "";
        if (!orderSearchBar.getText().equals("") || !orderSearchBar.getText().isBlank() || !orderSearchBar.getText().isEmpty()) {
            Connection connection = setConnection();
            String queryOrder = "SELECT Order_ID FROM projectprototype.order WHERE Order_ID = '" + orderSearchBar.getText() + "'";
            try {
                String compare = "";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(queryOrder);
                while (result.next()) {
                    compare = result.getString(1);
                }
                if (compare.equals(orderSearchBar.getText())) {
                    String query2 = "SELECT Order_ID, Order_Date, Customer_ID from projectprototype.order WHERE Order_ID = '" + compare + "'";
                    ResultSet result2 = statement.executeQuery(query2);

                    while (result2.next()) {
                        orderID = result2.getString(1);
                        date = result2.getString(2);
                        customerID = result2.getString(3);
                    }
                } else {
                    orderQueryResponse.setText("No matches found, try again");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                Statement statement = connection.createStatement();
                String query2 = "SELECT Order_ID, Product_ID, Quantity from projectprototype.orderline WHERE Order_ID = '" + orderSearchBar.getText() + "'";
                ResultSet result2 = statement.executeQuery(query2);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-order.fxml"));
                Parent p = loader.load();
                Stage editWindow = new Stage();
                editWindow.setTitle("Edit");
                editWindow.setScene(new Scene(p));
                editWindow.initModality(Modality.APPLICATION_MODAL);
                ModifyOrder controller = loader.getController();
                while (result2.next()) {
                    productID = result2.getString(2);
                    quantity = result2.getString(3);
                }
                controller.setOrderID(orderID);
                controller.setDate(date);
                controller.setCustomerID(customerID);
                controller.setProductID(productID);
                controller.setQuantity(quantity);

                editWindow.show();
                orderQueryResponse.setText("SUCCESS!");

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            orderQueryResponse.setText("Input the ID of the order you want to edit.");
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
