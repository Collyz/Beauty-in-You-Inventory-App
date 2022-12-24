package com.inventory.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML private TableColumn<LowStockModel, Integer> columnID;
    @FXML private TableColumn<LowStockModel, String> columnProd;
    @FXML private TableColumn<LowStockModel, Integer> columnQuantity;
    private final ObservableList<LowStockModel> lowStockObservableList;
    //For Product Tab
    @FXML private TextField productSearchBar;
    @FXML private TableView<ProductTableModel> productTable;
    @FXML private TableColumn<ProductTableModel, Integer> columnProductID;
    @FXML private TableColumn<ProductTableModel, String> columnProductCategory;
    @FXML private TableColumn<ProductTableModel, String> columnProductName;
    @FXML private TableColumn<ProductTableModel, Integer> columnProductQuantity;
    @FXML private TableColumn<ProductTableModel, Double> columnProductPrice;
    @FXML private TableColumn<ProductTableModel, Integer> columnProductShelfLife;
    private final ObservableList<ProductTableModel> productTableObservableList;

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

    private ProfileModel profileModel;
    private final LoginModel loginModel;


    public ProfileController(){
        this.productTableObservableList = FXCollections.observableArrayList();
        this.lowStockObservableList = FXCollections.observableArrayList();
        this.lowStockTable = new TableView<>();
        this.productTable = new TableView<>();
        this.profileModel = new ProfileModel();
        this.loginModel = LoginController.loginModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        //usernameInfo.setText(loginModel.getUsername());
        //passwordInfo.setText(loginModel.getPassword());
        //profileModel.updateLowQuantityTable(this);
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
    public TableColumn<LowStockModel, Integer> getColumnID(){
        return columnID;
    }
    public TableColumn<LowStockModel, String> getColumnProd(){
        return columnProd;
    }
    public TableColumn<LowStockModel, Integer> getColumnQuantity(){
        return columnQuantity;
    }
    public ObservableList<LowStockModel> getObservableLowQuantityList(){
        return this.lowStockObservableList;
    }

    public TableView<ProductTableModel> getProductTableView(){
        return this.productTable;
    }
    public TableColumn<ProductTableModel, Integer> getColumnProductID(){
        return this.columnProductID;
    }
    public TableColumn<ProductTableModel, String> getColumnProductCategory(){
        return this.columnProductCategory;
    }
    public TableColumn<ProductTableModel, String> getColumnProductName(){
        return this.columnProductName;
    }
    public TableColumn<ProductTableModel, Integer> getColumnProductQuantity(){
        return this.columnProductQuantity;
    }
    public TableColumn<ProductTableModel, Double> getColumnProductPrice(){
        return this.columnProductPrice;
    }
    public TableColumn<ProductTableModel, Integer> getColumnProductShelfLife(){
        return this.columnProductShelfLife;
    }
    public ObservableList<ProductTableModel> getObservableProductList(){
        return this.productTableObservableList;
    }

    @FXML
    public void plzWork(){
        profileModel.updateProductTable(this);
    }
}
