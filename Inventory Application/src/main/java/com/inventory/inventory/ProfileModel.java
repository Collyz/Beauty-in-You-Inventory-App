package com.inventory.inventory;

import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileModel {

    private final DatabaseConnection databaseConnection;

    public ProfileModel(){
        databaseConnection = new DatabaseConnection();
        databaseConnection.getConnection();
    }

    public void updateLowQuantityTable(ProfileController controller){
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

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            // Query the database to check for changes
            try {
                Statement statement = databaseConnection.databaseLink.createStatement();
                ResultSet resultSet = statement.executeQuery(lowQuantityQuery);

                // Clear the observable list and refill it with the latest data from the database
                controller.getObservableLowQuantityList().clear();
                while (resultSet.next()) {
                    Integer queryID = resultSet.getInt("PRODUCT_ID");
                    String queryName = resultSet.getString("PRODUCT_NAME");
                    Integer queryQuantity = resultSet.getInt("QUANTITY");

                    controller.getObservableLowQuantityList().add(new LowStockModel(queryID, queryName, queryQuantity));
                }
            } catch (SQLException e) {
                Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        };
        executor.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }

    public void updateProductTable(ProfileController controller){
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
    }

    public void updateCustomerTable(ProfileController controller){
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
    }

    public void updateOrderTable(ProfileController controller){
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
    }


}
