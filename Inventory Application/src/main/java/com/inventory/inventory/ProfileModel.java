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
            controller.getIdProductColumn().setCellValueFactory(new PropertyValueFactory<>("id"));
            controller.getCategoryProductColumn().setCellValueFactory(new PropertyValueFactory<>("category"));
            controller.getNameProductColumn().setCellValueFactory(new PropertyValueFactory<>("name"));
            controller.getQuantityProductColumn().setCellValueFactory(new PropertyValueFactory<>("quantity"));
            controller.getPriceProductColumn().setCellValueFactory(new PropertyValueFactory<>("price"));
            controller.getShelfLifeProductColumn().setCellValueFactory(new PropertyValueFactory<>("shelfLife"));

            controller.getProductTableView().setItems(controller.getProductObservableList());

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
