package com.inventory.inventory;

import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

            controller.getColumnID().setCellValueFactory(new PropertyValueFactory<>("productID"));
            controller.getColumnProd().setCellValueFactory(new PropertyValueFactory<>("productName"));
            controller.getColumnQuantity().setCellValueFactory(new PropertyValueFactory<>("quantity"));

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
        String productQuery = "SELECT * FROM Product";
        try{
            Statement statement = databaseConnection.databaseLink.createStatement();
            ResultSet resultSet = statement.executeQuery(productQuery);
            while(resultSet.next()){
                Integer queryID = resultSet.getInt(1);
                String queryCategory = resultSet.getString(2);
                String queryName = resultSet.getString(3);
                Integer queryQuantity = resultSet.getInt(4);
                Double queryPrice = resultSet.getDouble(5);
                Integer queryShelfLife = resultSet.getInt(6);

                //Fills in the observable list
                controller.getObservableProductList().add(new ProductTableModel(queryID, queryCategory, queryName, queryQuantity, queryPrice, queryShelfLife));
            }

            controller.getColumnProductID().setCellValueFactory(new PropertyValueFactory<>("ID"));
            controller.getColumnProductCategory().setCellValueFactory(new PropertyValueFactory<>("category"));
            controller.getColumnProductName().setCellValueFactory(new PropertyValueFactory<>("name"));
            controller.getColumnProductQuantity().setCellValueFactory(new PropertyValueFactory<>("quantity"));
            controller.getColumnProductPrice().setCellValueFactory(new PropertyValueFactory<>("price"));
            controller.getColumnProductShelfLife().setCellValueFactory(new PropertyValueFactory<>("life"));

            controller.getProductTableView().setItems(controller.getObservableProductList());

        }catch(SQLException e){
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }


}
