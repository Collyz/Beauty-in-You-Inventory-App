package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    @FXML private TableColumn<ProductTableModel, Integer> idProductColumn;
    @FXML private TableColumn<ProductTableModel, String> categoryProductColumn;
    @FXML private TableColumn<ProductTableModel, String> nameProductColumn;
    @FXML private TableColumn<ProductTableModel, Integer> quantityProductColumn;
    @FXML private TableColumn<ProductTableModel, Double> priceProductColumn;
    @FXML private TableColumn<ProductTableModel, Integer> shelfLifeProductColumn;
    private final ObservableList<ProductTableModel> productObservableList;

    @FXML private TextArea orderSearchRes;
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
    @FXML private Button helpButton;

    private DatabaseConnection databaseConnection;
    private ProfileModel profileModel;
    private final LoginModel loginModel;


    public ProfileController(){
        databaseConnection = new DatabaseConnection();

        //Initializing low stock list
        this.lowStockObservableList = FXCollections.observableArrayList();
        //Initializing product list
        productObservableList = FXCollections.observableArrayList();
        //Initializing low stock table
        this.lowStockTable = new TableView<>();
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
        //Calling model methods to populate tableviews
        profileModel.updateLowQuantityTable(this);
        profileModel.updateProductTable(this);

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

    public TableView<ProductTableModel> getProductTableView() {
        return productTableView;
    }
    public TableColumn<ProductTableModel, Integer> getIdProductColumn() {
        return idProductColumn;
    }
    public TableColumn<ProductTableModel, String> getCategoryProductColumn() {
        return categoryProductColumn;
    }
    public TableColumn<ProductTableModel, String> getNameProductColumn() {
        return nameProductColumn;
    }
    public TableColumn<ProductTableModel, Integer> getQuantityProductColumn() {
        return quantityProductColumn;
    }
    public TableColumn<ProductTableModel, Double> getPriceProductColumn() {
        return priceProductColumn;
    }
    public TableColumn<ProductTableModel, Integer> getShelfLifeProductColumn() {
        return shelfLifeProductColumn;
    }
    public ObservableList<ProductTableModel> getProductObservableList() {
        return productObservableList;
    }


}
