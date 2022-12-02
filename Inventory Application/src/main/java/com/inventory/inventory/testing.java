package com.inventory.inventory;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testing {
    public static void main(String[] args) {

        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        String orderQuery = "SELECT * FROM projectprototype.ORDER";
        String orderQueryLine = "SELECT Product_ID, Quantity FROM projectprototype.ORDERLINE";
        String returnOrder = String.format("%s %12s %5s %19s %12s", "Order_ID", "Customer_ID", "Date", "Product_ID", "Quantity") + "\n";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet results = statement.executeQuery(orderQuery);
            if(results.next()) {
                do{
                    int order_ID = results.getInt(1);
                    String date = results.getString(2);
                    int customer_ID = results.getInt(3);

                    //%2d means integer ID, %s is string category, %padding is the string format of name, %7.2f for price(float)
                    String formatting = "%s %9s %20s ";
                    String formattedString = String.format(formatting, order_ID, customer_ID, date);
                    returnOrder = returnOrder + formattedString;
                } while(results.next());
            }else{
                System.out.println("Empty");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            Statement statement = connectDB.createStatement();
            ResultSet results = statement.executeQuery(orderQueryLine);
            if(results.next()) {
                do{
                    int product_ID = results.getInt(1);
                    System.out.println(product_ID);
                    int quantity = results.getInt(2);
                    System.out.println(quantity);

                    String formatting = "%5s %15s";
                    String formattedString = String.format(formatting, product_ID, quantity + "\n");
                    returnOrder = returnOrder + formattedString;
                } while(results.next());
            }else{
                System.out.println("None");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println(returnOrder);
        /*String search2 = "eheh";
        String searchQuery = "SELECT * FROM PRODUCT WHERE PRODUCT_NAME = '" + search2 + "'";
        String search = "SELECT * FROM PRODUCT";
        try {
            String setText = String.format("%s %s%33s  %s  %s  %s", "ID", "Category", "Name", "Quantity", "Price", "Shelf Life") + "\n";
            Statement stmt = connection.databaseLink.createStatement();
            ResultSet results = stmt.executeQuery(searchQuery);
            if (results.next()){
                do {
                    int ID = results.getInt(1);
                    String category = results.getString(2);
                    String name = results.getString(3);
                    int quantity = results.getInt(4);
                    double price = results.getDouble(5);
                    String shelf = results.getString(6);
                    if (shelf == null) {
                        shelf = "N/A";
                    }
                    int padding = 40 - category.length();
                    String formatting = "%2d %s %" + padding + "s  %6d  %7.2f  %7s";
                    String formattedString = String.format(formatting, ID, category, name, quantity, price, shelf + "\n");
                    setText = setText + formattedString;
                } while (results.next());
        }else{
                System.out.println("The result set is empty");
            }
            System.out.println(setText);
            //searchRes.setText(searchBar.getText() + setText);
        }catch(SQLException e){
            e.printStackTrace();
        }*/
    }
}
