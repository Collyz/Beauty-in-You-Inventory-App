package com.inventory.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProfileController {

    //For Profile Tab
    @FXML
    private Text usernameInfo;
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
    @FXML private Button helpButton;

    @FXML private TableView<LowStockModel> lowQuantTable;
    @FXML private TableColumn<LowStockModel, Integer> columnID;
    @FXML private TableColumn<LowStockModel, String> columnProd;
    @FXML private TableColumn<LowStockModel, Integer> columnQuant;
    ObservableList<LowStockModel> lowStockModelList = FXCollections.observableArrayList();
}
