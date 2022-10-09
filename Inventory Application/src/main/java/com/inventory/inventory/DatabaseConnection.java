package com.inventory.inventory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection databaseLink;
    public Connection getConnection(){
        String databaseName = "test";
        String databaseUser = "root";
        String databasePassword = "Dolphin1!";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;
        try{
            Class.forName("com.inventory.inventory.LoginRegisterController");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch(Exception e){
            e.printStackTrace();
        }

        return databaseLink;
    }

}
