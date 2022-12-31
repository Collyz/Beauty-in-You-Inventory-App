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
    @FXML private AnchorPane prodPane;
    @FXML private TextField productSearchBar;
    @FXML private TableView<ProductTableModel> productTableView;
    @FXML private TableColumn<ProductTableModel, Integer> productColumnID;
    @FXML private TableColumn<ProductTableModel, String> productColumnCategory;
    @FXML private TableColumn<ProductTableModel, String> productColumnName;
    @FXML private TableColumn<ProductTableModel, Integer> productColumnQuantity;
    @FXML private TableColumn<ProductTableModel, Double> productColumnPrice;
    @FXML private TableColumn<ProductTableModel, Integer> productColumnShelfLife;
    private final ObservableList<ProductTableModel> productObservableList;
    //For Customer Tab
    @FXML private TextField customerSearchBar;
    @FXML private TableView<CustomerTableModel> customerTableView;
    @FXML private TableColumn<CustomerTableModel, Integer> customerColumnID;
    @FXML private TableColumn<CustomerTableModel, String> customerColumnName;
    @FXML private TableColumn<CustomerTableModel, String> customerColumnPhone;
    @FXML private TableColumn<CustomerTableModel, String> customerColumnEmail;
    @FXML private TableColumn<CustomerTableModel, String> customerColumnAddress;
    @FXML private ObservableList<CustomerTableModel> customerObservableList;
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
    @FXML private Button helpButton;

    private DatabaseConnection databaseConnection;
    private ProfileModel profileModel;
    private final LoginModel loginModel;


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
        return lowID;
    }
    public TableColumn<LowStockModel, String> getLowName(){
        return lowName;
    }
    public TableColumn<LowStockModel, Integer> getLowQuantity(){
        return lowQuantity;
    }
    public ObservableList<LowStockModel> getObservableLowQuantityList(){
        return this.lowStockObservableList;
    }
    //For product tab table
    public TableView<ProductTableModel> getProductTableView() {
        return productTableView;
    }
    public TableColumn<ProductTableModel, Integer> getProductColumnID() {
        return productColumnID;
    }
    public TableColumn<ProductTableModel, String> getProductColumnCategory() {
        return productColumnCategory;
    }
    public TableColumn<ProductTableModel, String> getProductColumnName() {
        return productColumnName;
    }
    public TableColumn<ProductTableModel, Integer> getProductColumnQuantity() {
        return productColumnQuantity;
    }
    public TableColumn<ProductTableModel, Double> getProductColumnPrice() {
        return productColumnPrice;
    }
    public TableColumn<ProductTableModel, Integer> getProductColumnShelfLife() {
        return productColumnShelfLife;
    }
    public ObservableList<ProductTableModel> getProductObservableList() {
        return productObservableList;
    }
    //For customer tab table
    public TableView<CustomerTableModel> getCustomerTableView(){
        return customerTableView;
    }
    public TableColumn<CustomerTableModel, Integer> getCustomerColumnID(){
        return customerColumnID;
    }
    public TableColumn<CustomerTableModel, String> getCustomerColumnName(){
        return customerColumnName;
    }
    public TableColumn<CustomerTableModel, String> getCustomerColumnPhone(){
        return customerColumnPhone;
    }
    public TableColumn<CustomerTableModel, String> getCustomerColumnEmail(){
        return customerColumnEmail;
    }
    public TableColumn<CustomerTableModel, String> getCustomerColumnAddress(){
        return customerColumnAddress;
    }
    public ObservableList<CustomerTableModel> getCustomerObservableList(){
        return customerObservableList;
    }
    //For order tab table
    public TableView<BulkOrderModel> getOrderTableView(){
        return orderTableView;
    }
    public TableColumn<BulkOrderModel, Integer> getOrderColumnOrderID() {
        return orderColumnOrderID;
    }
    public TableColumn<BulkOrderModel, String> getOrderColumnCustomerName(){
        return orderColumnCustomerName;
    }
    public TableColumn<BulkOrderModel,String> getOrderColumnDate(){
        return orderColumnDate;
    }
    public TableColumn<BulkOrderModel, String> getOrderColumnProductName(){
        return orderColumnProductName;
    }
    public TableColumn<BulkOrderModel, Integer> getOrderColumnQuantity(){
        return orderColumnQuantity;
    }
    public TableColumn<BulkOrderModel, Double> getOrderColumnCost() {
        return orderColumnCost;
    }
    public TableColumn<BulkOrderModel, Double> getOrderColumnTax() {
        return orderColumnTax;
    }
    public TableColumn<BulkOrderModel, Double> getOrderColumnTotal() {
        return orderColumnTotal;
    }
    public ObservableList<BulkOrderModel> getOrderObservableList(){
        return orderObservableList;
    }
}
