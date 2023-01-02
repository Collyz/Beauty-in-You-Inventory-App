package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    //For Profile Tab
    @FXML private Text usernameInfo;
    @FXML private Text passwordInfo;
    @FXML private TableView<LowStockModel> lowStockTable;
    @FXML private TableColumn<LowStockModel, Integer> lowID;
    @FXML private TableColumn<LowStockModel, String> lowName;
    @FXML private TableColumn<LowStockModel, Integer> lowQuantity;
    private final ObservableList<LowStockModel> lowStockObservableList;
    //For Product Tab
    @FXML private TextField productSearchBar;
    @FXML private TableView<ProductTableModel> productTableView;
    @FXML private TableColumn<ProductTableModel, Integer> productColumnID;
    @FXML private TableColumn<ProductTableModel, String> productColumnCategory;
    @FXML private TableColumn<ProductTableModel, String> productColumnName;
    @FXML private TableColumn<ProductTableModel, Integer> productColumnQuantity;
    @FXML private TableColumn<ProductTableModel, Double> productColumnPrice;
    @FXML private TableColumn<ProductTableModel, Integer> productColumnShelfLife;
    private final ObservableList<ProductTableModel> productObservableList;
    @FXML private Text productTextResponse;
    //For Customer Tab
    @FXML private TextField customerSearchBar;
    @FXML private TableView<CustomerTableModel> customerTableView;
    @FXML private TableColumn<CustomerTableModel, Integer> customerColumnID;
    @FXML private TableColumn<CustomerTableModel, String> customerColumnName;
    @FXML private TableColumn<CustomerTableModel, String> customerColumnPhone;
    @FXML private TableColumn<CustomerTableModel, String> customerColumnEmail;
    @FXML private TableColumn<CustomerTableModel, String> customerColumnAddress;
    private ObservableList<CustomerTableModel> customerObservableList;
    @FXML private Text customerTextResponse;
    //For Bulk Order Tab
    @FXML private TextField orderSearchBar;
    @FXML private TableView<BulkOrderModel> orderTableView;
    @FXML private TableColumn<BulkOrderModel, Integer> orderColumnOrderID;
    @FXML private TableColumn<BulkOrderModel, String> orderColumnCustomerName;
    @FXML private TableColumn<BulkOrderModel, String> orderColumnDate;
    @FXML private TableColumn<BulkOrderModel, String> orderColumnProductName;
    @FXML private TableColumn<BulkOrderModel, Integer> orderColumnQuantity;
    @FXML private TableColumn<BulkOrderModel, Double> orderColumnCost;
    @FXML private TableColumn<BulkOrderModel, Double> orderColumnTax;
    @FXML private TableColumn<BulkOrderModel, Double> orderColumnTotal;
    private ObservableList<BulkOrderModel> orderObservableList;
    @FXML private Text orderTextResponse;
    //Email tab
    @FXML private TextField email;
    @FXML private TextField subject;
    @FXML private TextArea message;
    @FXML private Text emailResponse;
    //Resizing
    @FXML private Button clearEmail;
    @FXML private Button sendEmail;
    @FXML private Button helpButton;

    private DatabaseConnection databaseConnection;
    private ProfileModel profileModel;
    private final LoginModel loginModel;
    /*private SendEmail emailer;*/

    public ProfileController(){
        databaseConnection = new DatabaseConnection();

        //Initializing low stock list
        this.lowStockObservableList = FXCollections.observableArrayList();
        //Initializing product list
        this.productObservableList = FXCollections.observableArrayList();
        //Initializing customer list
        this.customerObservableList = FXCollections.observableArrayList();
        //Initializing order list
        this.orderObservableList = FXCollections.observableArrayList();

        //Initializing the profile model
        this.profileModel = new ProfileModel();
        //Shared login model to get username and password if successfully logged in
        this.loginModel = LoginController.loginModel;
        /*//Initializing the email model
        this.emailer = new SendEmail();*/
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        databaseConnection.getConnection();
        //Grabbing username and password data
        usernameInfo.setText(loginModel.getUsername());
        passwordInfo.setText(loginModel.getPassword());
        //Settings column font size to 12
        /*Low stock tableview columns*/
        /*lowID.setStyle("-fx-font-size: 12pt");
        lowName.setStyle("-fx-font-size: 12pt");
        lowQuantity.setStyle("-fx-font-size: 12pt");*/
        /*Product tableview columns*/
        /*productColumnID.setStyle("-fx-font-size: 12pt");
        productColumnCategory.setStyle("-fx-font-size: 12pt");
        productColumnName.setStyle("-fx-font-size: 12pt");
        productColumnQuantity.setStyle("-fx-font-size: 12pt");
        productColumnPrice.setStyle("-fx-font-size: 12pt");
        productColumnShelfLife.setStyle("-fx-font-size: 12pt");*/
        /*Customer tableview columns*/
        /*Bulk Order tableview columns*/
        //Calling model methods to populate tableviews
        profileModel.updateLowQuantityTable(this);
        profileModel.updateProductTable(this);
        profileModel.updateCustomerTable(this);
        profileModel.updateOrderTable(this);
    }

    /**
     * Opens the previous login/register page hence 'logging out'
     * @param event  - Listens for an event on the logout button
     * @throws IOException thrown if there is an issue with the button
     */
    @FXML
    protected void onLogoutClick(javafx.event.ActionEvent event){
        try {
            Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-register.fxml")));
            Scene s = new Scene(p);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(s);
            appStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    //For low stock table
    public TableView<LowStockModel> getLowStockTableView(){
        return this.lowStockTable;
    }
    public TableColumn<LowStockModel, Integer> getLowID(){
        return this.lowID;
    }
    public TableColumn<LowStockModel, String> getLowName(){
        return this.lowName;
    }
    public TableColumn<LowStockModel, Integer> getLowQuantity(){
        return this.lowQuantity;
    }
    public ObservableList<LowStockModel> getObservableLowQuantityList(){
        return this.lowStockObservableList;
    }
    //For product tab table
    public TableView<ProductTableModel> getProductTableView() {
        return productTableView;
    }
    public TableColumn<ProductTableModel, Integer> getProductColumnID() {
        return this.productColumnID;
    }
    public TableColumn<ProductTableModel, String> getProductColumnCategory() {
        return this.productColumnCategory;
    }
    public TableColumn<ProductTableModel, String> getProductColumnName() {
        return this.productColumnName;
    }
    public TableColumn<ProductTableModel, Integer> getProductColumnQuantity() {
        return this.productColumnQuantity;
    }
    public TableColumn<ProductTableModel, Double> getProductColumnPrice() {
        return this.productColumnPrice;
    }
    public TableColumn<ProductTableModel, Integer> getProductColumnShelfLife() {
        return this.productColumnShelfLife;
    }
    public ObservableList<ProductTableModel> getProductObservableList() {
        return this.productObservableList;
    }
    public TextField getProductSearchBar(){
        return this.productSearchBar;
    }
    public Text getProductTextResponse(){
        return this.productTextResponse;
    }

    @FXML
    protected void editProduct(){
        ProductTableModel modifyProduct = productTableView.getSelectionModel().getSelectedItem();
        if(modifyProduct != null){
            profileModel.editProduct(modifyProduct, this);
        }
        else{
            productTextResponse.setText("No row is selected");
        }
    }

    @FXML
    protected void deleteProduct(){
        ProductTableModel deleteProduct = productTableView.getSelectionModel().getSelectedItem();
        if(deleteProduct != null){
            profileModel.deleteProduct(deleteProduct, this);
        }
        else{
            productTextResponse.setText("No row is selected");
        }
    }

    /**
     * Opens a separate window that handles adding a product to the inventory
     */
    @FXML
    protected  void addProduct(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-product.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Add Product");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            AddProduct addProduct = loader.getController();
            addProduct.setProfileController(this);
            addProduct.setProfileModel(profileModel);
            editWindow.show();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    //For customer tab table
    public TableView<CustomerTableModel> getCustomerTableView(){
        return this.customerTableView;
    }
    public TableColumn<CustomerTableModel, Integer> getCustomerColumnID(){
        return this.customerColumnID;
    }
    public TableColumn<CustomerTableModel, String> getCustomerColumnName(){
        return this.customerColumnName;
    }
    public TableColumn<CustomerTableModel, String> getCustomerColumnPhone(){
        return this.customerColumnPhone;
    }
    public TableColumn<CustomerTableModel, String> getCustomerColumnEmail(){
        return this.customerColumnEmail;
    }
    public TableColumn<CustomerTableModel, String> getCustomerColumnAddress(){
        return this.customerColumnAddress;
    }
    public ObservableList<CustomerTableModel> getCustomerObservableList(){
        return this.customerObservableList;
    }
    public TextField getCustomerSearchBar(){
        return this.customerSearchBar;
    }
    public Text getCustomerTextResponse(){
        return this.customerTextResponse;
    }

    @FXML
    protected void editCustomer(){
        CustomerTableModel modifyCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (modifyCustomer != null) {
            profileModel.editCustomer(modifyCustomer, this);
        }
        else{
            customerTextResponse.setText("No row is selected");
        }

    }

    @FXML
    protected void deleteCustomer(){
        CustomerTableModel deleteCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(deleteCustomer != null){
            profileModel.deleteCustomer(deleteCustomer, this);
        }
        else{
            customerTextResponse.setText("No row is selected");
        }
    }

    /**
     * Opens a separate window that handles adding a customer to the inventory
     */
    @FXML
    public void addCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-customer.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Add Customer");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            AddCustomer addCustomer = loader.getController();
            addCustomer.setProfileController(this);
            addCustomer.setProfileModel(profileModel);
            editWindow.show();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    //For order tab table
    public TableView<BulkOrderModel> getOrderTableView(){
        return this.orderTableView;
    }
    public TableColumn<BulkOrderModel, Integer> getOrderColumnOrderID() {
        return this.orderColumnOrderID;
    }
    public TableColumn<BulkOrderModel, String> getOrderColumnCustomerName(){
        return this.orderColumnCustomerName;
    }
    public TableColumn<BulkOrderModel,String> getOrderColumnDate(){
        return this.orderColumnDate;
    }
    public TableColumn<BulkOrderModel, String> getOrderColumnProductName(){
        return this.orderColumnProductName;
    }
    public TableColumn<BulkOrderModel, Integer> getOrderColumnQuantity(){
        return this.orderColumnQuantity;
    }
    public TableColumn<BulkOrderModel, Double> getOrderColumnCost() {
        return this.orderColumnCost;
    }
    public TableColumn<BulkOrderModel, Double> getOrderColumnTax() {
        return this.orderColumnTax;
    }
    public TableColumn<BulkOrderModel, Double> getOrderColumnTotal() {
        return this.orderColumnTotal;
    }
    public ObservableList<BulkOrderModel> getOrderObservableList(){
        return this.orderObservableList;
    }
    public TextField getOrderSearchBar(){
        return this.orderSearchBar;
    }
    public Text getOrderTextResponse(){
        return this.orderTextResponse;
    }

    @FXML
    protected void editOrder(){
        BulkOrderModel bulkOrderModel = orderTableView.getSelectionModel().getSelectedItem();
        if(bulkOrderModel != null) {
            profileModel.editOrder(bulkOrderModel, this);
        }
        else{
            orderTextResponse.setText("No row is selected");
        }
    }

    @FXML
    protected void deleteOrder(){
        BulkOrderModel bulkOrderModel = orderTableView.getSelectionModel().getSelectedItem();
        if(bulkOrderModel != null) {
            profileModel.deleteOrder(bulkOrderModel, this);
        }
        else{
            orderTextResponse.setText("No row is selected");
        }
    }

    /**
     * Opens a separate window that handles adding an order to the inventory
     */
    @FXML
    protected  void addOrder(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-order.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Add Order");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            AddOrder addOrder = loader.getController();
            addOrder.setProfileController(this);
            addOrder.setProfileModel(profileModel);
            editWindow.show();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @FXML
    public void sendEmail(){
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
    public void clearEmail(){
        email.clear();
        subject.clear();
        message.clear();
        emailResponse.setText("");
    }
}
