package com.inventory.inventory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testing {
    public static void main(String[] args) {
        /*SendEmail test = new SendEmail();
        try{
            test.sendMail("m.mowla03151@gmail.com", "Hello", "Testing the email system\nAin't it lovely");
            System.out.println("It sent");
        } catch(Exception e){
            e.printStackTrace();
        }*/

        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        String search = "SELECT * FROM CUSTOMER";
        String toReturn = String.format("%s  %s %20s  %18s  %22s", "ID", "Name", "Phone #", "Email", "Address") + "\n";
        try{
            Statement stmt = connectDB.createStatement();
            ResultSet results = stmt.executeQuery(search);
            if(results.next()){
                do{
                    int ID = results.getInt(1);
                    String name = results.getString(2);
                    String phone = results.getString(3);
                    String address = results.getString(4);
                    String email = results.getString(5);
                    if(phone == null){
                        phone = "N/A";
                    }
                    if(email == null){
                        email = "N/A";
                    }
                    int phonePad = 32 - name.length();
                    int emailPad = 37 - phone.length();
                    int addressPad = 39 - phone.length();
                    String formatting = "%s   %s%" + phonePad + "s%"+ emailPad +"s%"+ addressPad +"s";
                    String formattedString = String.format(formatting, ID, name, phone , email, address + "\n");
                    toReturn = toReturn + formattedString;
                } while(results.next());
            }else{

            }
            System.out.println(toReturn);
        }catch(SQLException e){
            e.printStackTrace();
        }
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
