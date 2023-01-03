package com.inventory.inventory;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileModel {

    private final DatabaseConnection databaseConnection;

    public ProfileModel(){
        databaseConnection = new DatabaseConnection();
        databaseConnection.getConnection();
    }

    public void updateLowQuantityTable(ProfileController controller){
        controller.getObservableLowQuantityList().clear();
        String lowQuantityQuery = "SELECT PRODUCT_ID, PRODUCT_NAME, QUANTITY FROM Product WHERE QUANTITY <= 3";
        try{
            Statement statement = databaseConnection.databaseLink.createStatement();
            ResultSet resultSet = statement.executeQuery(lowQuantityQuery);

            while(resultSet.next()){

                Integer queryID = resultSet.getInt("PRODUCT_ID");
                String queryName = resultSet.getString("PRODUCT_NAME");
                Integer queryQuantity = resultSet.getInt("QUANTITY");

                //Fills in the observable list
                controller.getObservableLowQuantityList().add(new LowStockModel(queryID, queryName, queryQuantity));
            }

            controller.getLowID().setCellValueFactory(new PropertyValueFactory<>("productID"));
            controller.getLowName().setCellValueFactory(new PropertyValueFactory<>("productName"));
            controller.getLowQuantity().setCellValueFactory(new PropertyValueFactory<>("quantity"));

            controller.getLowStockTableView().setItems(controller.getObservableLowQuantityList());

        }catch(SQLException e){
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public void updateProductTable(ProfileController controller){
        controller.getProductObservableList().clear();
        String getProducts = "SELECT * FROM PRODUCT";
        try{
            Statement statement = databaseConnection.databaseLink.createStatement();
            ResultSet resultSet = statement.executeQuery(getProducts);
            while(resultSet.next()) {
                Integer queryID = resultSet.getInt("PRODUCT_ID");
                String queryCategory = resultSet.getString("CATEGORY");
                String queryName = resultSet.getString("PRODUCT_NAME");
                Integer queryQuantity = resultSet.getInt("QUANTITY");
                Double queryPrice = resultSet.getDouble("PRICE");
                Integer queryShelfLife = resultSet.getInt("SHELF_LIFE");
                controller.getProductObservableList().add(new ProductTableModel(queryID, queryCategory, queryName,
                        queryQuantity, queryPrice, queryShelfLife));
            }
            controller.getProductColumnID().setCellValueFactory(new PropertyValueFactory<>("id"));
            controller.getProductColumnCategory().setCellValueFactory(new PropertyValueFactory<>("category"));
            controller.getProductColumnName().setCellValueFactory(new PropertyValueFactory<>("name"));
            controller.getProductColumnQuantity().setCellValueFactory(new PropertyValueFactory<>("quantity"));
            controller.getProductColumnPrice().setCellValueFactory(new PropertyValueFactory<>("price"));
            controller.getProductColumnShelfLife().setCellValueFactory(new PropertyValueFactory<>("shelfLife"));

            controller.getProductTableView().setItems(controller.getProductObservableList());

        }catch(SQLException e){
            e.printStackTrace();
        }

        FilteredList<ProductTableModel> filteredItems = new FilteredList<>(controller.getProductObservableList(), p -> true);
        controller.getProductSearchBar().textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredItems.setPredicate(productTableModel -> {
                // If the filter field is empty, show all items
                if (newValue.isBlank() || newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(productTableModel.getId().toString().indexOf(searchKeyword) > -1){
                    return true;
                }else if (productTableModel.getCategory().toLowerCase().indexOf(searchKeyword) > -1.){
                    return true;
                }else if(productTableModel.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(productTableModel.getQuantity().toString().indexOf(searchKeyword) > -1){
                    return true;
                } else if(productTableModel.getPrice().toString().indexOf(searchKeyword) > -1){
                    return true;
                } else if(productTableModel.getShelfLife().toString().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });

        SortedList<ProductTableModel> sortedData = new SortedList<>(filteredItems);
        sortedData.comparatorProperty().bind(controller.getProductTableView().comparatorProperty());
        controller.getProductTableView().setItems(sortedData);

    }

    public void updateCustomerTable(ProfileController controller){
        controller.getCustomerObservableList().clear();
        String getCustomers = "SELECT * FROM Customer ORDER BY Customer_Name";
        try{
            Statement statement = databaseConnection.databaseLink.createStatement();
            ResultSet resultSet = statement.executeQuery(getCustomers);
            while(resultSet.next()){
                Integer queryID = resultSet.getInt("Customer_ID");
                String queryName = resultSet.getString("Customer_Name");
                String queryPhone = resultSet.getString("Customer_Phone");
                String queryEmail = resultSet.getString("Customer_Email");
                String queryAddress = resultSet.getString("Customer_Address");

                controller.getCustomerObservableList().add(new CustomerTableModel(queryID, queryName,
                        queryPhone, queryEmail, queryAddress));
            }
            controller.getCustomerColumnID().setCellValueFactory(new PropertyValueFactory<>("id"));
            controller.getCustomerColumnName().setCellValueFactory(new PropertyValueFactory<>("name"));
            controller.getCustomerColumnPhone().setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            controller.getCustomerColumnEmail().setCellValueFactory(new PropertyValueFactory<>("email"));
            controller.getCustomerColumnAddress().setCellValueFactory(new PropertyValueFactory<>("address"));

            controller.getCustomerTableView().setItems(controller.getCustomerObservableList());
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        FilteredList<CustomerTableModel> filteredItems = new FilteredList<>(controller.getCustomerObservableList(), p -> true);
        controller.getCustomerSearchBar().textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredItems.setPredicate(customerTableModel -> {
                // If the filter field is empty, show all items
                if (newValue.isBlank() || newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(customerTableModel.getId().toString().indexOf(searchKeyword) > -1){
                    return true;
                }else if (customerTableModel.getName().toLowerCase().indexOf(searchKeyword) > -1.){
                    return true;
                }else if(customerTableModel.getPhoneNumber().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(customerTableModel.getEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(customerTableModel.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });

        SortedList<CustomerTableModel> sortedData = new SortedList<>(filteredItems);
        sortedData.comparatorProperty().bind(controller.getCustomerTableView().comparatorProperty());
        controller.getCustomerTableView().setItems(sortedData);
    }

    public void updateOrderTable(ProfileController controller){
        controller.getOrderObservableList().clear();
        String order = "SELECT * FROM projectprototype.ORDER";
        String orderLine = "SELECT Product_ID, Quantity FROM projectprototype.ORDERLINE";
        String customerSearch = "SELECT CUSTOMER_NAME FROM Customer WHERE CUSTOMER_ID = ";
        String productSearch = "SELECT PRODUCT_NAME, Price FROM PRODUCT WHERE PRODUCT_ID = ";
        ArrayList<Integer> orderID = new ArrayList<>();
        ArrayList<Integer> customerID = new ArrayList<>();
        ArrayList<String> orderDate = new ArrayList<>();
        ArrayList<Integer> productID = new ArrayList<>();
        ArrayList<Integer> productQuantity = new ArrayList<>();
        ArrayList<String> productName = new ArrayList<>();
        ArrayList<String> customerNames = new ArrayList<>();
        ArrayList<Double> productCost = new ArrayList<>();
        //Data from order table
        try{
            Statement statement = databaseConnection.databaseLink.createStatement();
            ResultSet resultSet = statement.executeQuery(order);
            while(resultSet.next()){
                Integer queryOrderId = resultSet.getInt("Order_ID");
                Integer queryCustomerId = resultSet.getInt("Customer_ID");
                String queryDate = resultSet.getString("Order_Date");
                orderID.add(queryOrderId);
                customerID.add(queryCustomerId);
                orderDate.add(queryDate);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        //Data from orderline table
        try{
            Statement statement = databaseConnection.databaseLink.createStatement();
            ResultSet resultSet = statement.executeQuery(orderLine);
            while(resultSet.next()){
                Integer queryProduct_ID = resultSet.getInt("PRODUCT_ID");
                Integer queryQuantity = resultSet.getInt("QUANTITY");
                productID.add(queryProduct_ID);
                productQuantity.add(queryQuantity);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        //Customer name from customer table
        for(int i = 0; i< customerID.size(); i++) {
            try {
                Statement statement = databaseConnection.databaseLink.createStatement();
                ResultSet resultSet = statement.executeQuery(customerSearch + customerID.get(i));
                while(resultSet.next()){
                    customerNames.add(resultSet.getString("CUSTOMER_NAME"));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        //Data from product table
        for(int i = 0; i < productID.size(); i++) {
            try {
                Statement statement = databaseConnection.databaseLink.createStatement();
                ResultSet resultSet = statement.executeQuery(productSearch + productID.get(i));
                while (resultSet.next()) {
                    String queryProductName = resultSet.getString("PRODUCT_NAME");
                    Double queryCost = resultSet.getDouble("PRICE");
                    productName.add(queryProductName);
                    productCost.add(queryCost);
                }
                //Math for cost, tax and total
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < orderID.size(); i++){
            Integer idInput = orderID.get(i);
            String nameInput = customerNames.get(i);
            String dateInput = orderDate.get(i);
            String prodNameInput = productName.get(i);
            Integer prodQuantityInput = productQuantity.get(i);
            Double costInput = productCost.get(i) * productQuantity.get(i);
            Double taxInput = costInput * .06625;
            Double totalInput = costInput + taxInput;
            controller.getOrderObservableList().add(new BulkOrderModel(idInput, nameInput,dateInput,prodNameInput,
                    prodQuantityInput,costInput ,taxInput,totalInput));
        }

        controller.getOrderColumnOrderID().setCellValueFactory(new PropertyValueFactory<>("orderID"));
        controller.getOrderColumnCustomerName().setCellValueFactory(new PropertyValueFactory<>("customerName"));
        controller.getOrderColumnDate().setCellValueFactory(new PropertyValueFactory<>("date"));
        controller.getOrderColumnProductName().setCellValueFactory(new PropertyValueFactory<>("productName"));
        controller.getOrderColumnQuantity().setCellValueFactory(new PropertyValueFactory<>("quantity"));
        controller.getOrderColumnCost().setCellValueFactory(new PropertyValueFactory<>("cost"));
        controller.getOrderColumnTax().setCellValueFactory(new PropertyValueFactory<>("tax"));
        controller.getOrderColumnTotal().setCellValueFactory(new PropertyValueFactory<>("total"));

        controller.getOrderTableView().setItems(controller.getOrderObservableList());


        FilteredList<BulkOrderModel> filteredItems = new FilteredList<>(controller.getOrderObservableList(), p -> true);
        controller.getOrderSearchBar().textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredItems.setPredicate(bulkOrderModel -> {
                // If the filter field is empty, show all items
                if (newValue.isBlank() || newValue.isEmpty() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(bulkOrderModel.getOrderID().toString().indexOf(searchKeyword) > -1){
                    return true;
                }else if (bulkOrderModel.getCustomerName().toLowerCase().indexOf(searchKeyword) > -1.){
                    return true;
                }else if(bulkOrderModel.getDate().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(bulkOrderModel.getProductName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(bulkOrderModel.getQuantity().toString().indexOf(searchKeyword) > -1){
                    return true;
                } else if(bulkOrderModel.getCost().toString().indexOf(searchKeyword) > -1){
                    return true;
                }else if(bulkOrderModel.getTax().toString().indexOf(searchKeyword) > -1){
                    return true;
                }else if(bulkOrderModel.getTotal().toString().indexOf(searchKeyword) > -1){
                    return true;
                }
                else{
                    return false;
                }
            });
        });

        SortedList<BulkOrderModel> sortedData = new SortedList<>(filteredItems);
        sortedData.comparatorProperty().bind(controller.getOrderTableView().comparatorProperty());
        controller.getOrderTableView().setItems(sortedData);
    }

    public void editProduct(ProductTableModel tableData, ProfileController controller){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-product.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Edit");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            ModifyProduct modifyProduct = loader.getController();
            modifyProduct.setID(tableData.getId());
            modifyProduct.setName(tableData.getName());
            modifyProduct.setCategory(tableData.getCategory());
            modifyProduct.setPrice(tableData.getPrice().toString());
            modifyProduct.setQuantity(tableData.getQuantity().toString());
            modifyProduct.setShelfLife(tableData.getShelfLife().toString());

            modifyProduct.setProfileController(controller);
            modifyProduct.setProfileModel(this);


            editWindow.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void deleteProduct(ProductTableModel tableData, ProfileController controller){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("delete-product.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Delete");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            DeleteProduct deleteProduct = loader.getController();
            deleteProduct.setID(tableData.getId());
            deleteProduct.setName(tableData.getName());
            deleteProduct.setCategory(tableData.getCategory());
            deleteProduct.setQuantity(tableData.getQuantity().toString());
            deleteProduct.setPrice(tableData.getPrice().toString());
            deleteProduct.setShelfLife(tableData.getShelfLife().toString());

            deleteProduct.setProfileController(controller);
            deleteProduct.setProfileModel(this);

            editWindow.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void editCustomer(CustomerTableModel tableData, ProfileController controller){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-customer.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Edit");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            ModifyCustomer modifyCustomer = loader.getController();
            modifyCustomer.setID(tableData.getId());
            modifyCustomer.setName(tableData.getName());
            modifyCustomer.setPhone(tableData.getPhoneNumber());
            modifyCustomer.setEmail(tableData.getEmail());
            modifyCustomer.setAddress(tableData.getAddress());

            modifyCustomer.setProfileController(controller);
            modifyCustomer.setProfileModel(this);

            editWindow.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void deleteCustomer(CustomerTableModel tableData, ProfileController controller){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("delete-customer.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Delete");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            DeleteCustomer deleteCustomer = loader.getController();
            deleteCustomer.setID(tableData.getId());
            deleteCustomer.setName(tableData.getName());
            deleteCustomer.setPhone(tableData.getPhoneNumber());
            deleteCustomer.setEmail(tableData.getEmail());
            deleteCustomer.setAddress(tableData.getAddress());

            deleteCustomer.setProfileController(controller);
            deleteCustomer.setProfileModel(this);
            editWindow.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void editOrder(BulkOrderModel tableData, ProfileController controller){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-order.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Edit");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            ModifyOrder modifyOrder = loader.getController();

            modifyOrder.setOrderID(tableData.getOrderID().toString());
            modifyOrder.setDate(tableData.getDate());
            modifyOrder.setCustomerID(tableData.getCustomerName());
            modifyOrder.setProductID(tableData.getProductName());
            modifyOrder.setQuantity(tableData.getQuantity().toString());

            modifyOrder.setProfileController(controller);
            modifyOrder.setProfileModel(this);

            editWindow.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void deleteOrder(BulkOrderModel tableData, ProfileController controller){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("delete-order.fxml"));
            Parent p = loader.load();
            Stage editWindow = new Stage();
            editWindow.setTitle("Delete");
            editWindow.setScene(new Scene(p));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            DeleteOrder deleteOrder = loader.getController();

            deleteOrder.setOrderID(tableData.getOrderID().toString());
            deleteOrder.setCustomerID(tableData.getCustomerName());
            deleteOrder.setDate(tableData.getDate());

            deleteOrder.setProfileController(controller);
            deleteOrder.setProfileModel(this);

            editWindow.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
