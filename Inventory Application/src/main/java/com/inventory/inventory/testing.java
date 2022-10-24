package com.inventory.inventory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testing {
    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        String query = "SELECT PRODUCT_NAME, QUANTITY, SHELF_LIFE from Product WHERE CATEGORY = ";
        String[] categories = {"\'BRUSHES\'", "\'MAKEUP REMOVER\'", "\'SKINCARE\'", "\'FACE\'", "\'LIPS\'", "\'EYES\'"};
        String[] queryResults = new String[6];
        for(int i = 0; i < categories.length; i++){
            try{
                Statement statement = connection.databaseLink.createStatement();
                ResultSet result = statement.executeQuery(query + categories[i]);
                String delimiter = "-------------------------\n";
                String setText = categories[i] + "\n";
                while(result.next()){
                    String name = result.getString(1);
                    String quantity = result.getString(2);
                    String shelf = result.getString(3);
                    if(shelf == null){
                        shelf = "N/A";
                    }
                    int padding= 35 - name.length();
                    String formatting = "%s%" + padding + "s%10s";
                    setText = setText + String.format(formatting, name, quantity, shelf + "\n");
                }
                queryResults[i] = setText + delimiter;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        for(int i = 0; i < categories.length; i++){
            System.out.println(queryResults[i]);
        }
    }
}
