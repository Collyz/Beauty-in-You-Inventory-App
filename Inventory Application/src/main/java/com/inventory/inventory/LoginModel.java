package com.inventory.inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginModel {


    private final DatabaseConnection databaseConnection;
    private String username;
    private String password;

    public LoginModel(){
        this.databaseConnection = new DatabaseConnection();
        databaseConnection.getConnection();

        username = "";
        password = "";
    }

    public boolean verifyLogin(String username, String password){
        boolean checkUsername = false;
        boolean checkPassword = false;
        String selectUsername = " Select Username from ADMIN_ACCOUNTS WHERE Username = '" + username  +"'";
        String selectPassword = " Select Password from ADMIN_ACCOUNTS WHERE Password = '" + password +"'";
        //Executes the query
        try{
            Statement statement = databaseConnection.databaseLink.createStatement();
            ResultSet result = statement.executeQuery(selectUsername);

            //Gets the username stored in database
            while(result.next()){
                if(username.equals(result.getString("Username"))){
                    checkUsername = true;
                }
            }
            //Gets the password stored in database
            result = statement.executeQuery(selectPassword);
            while(result.next()){
                if(password.equals(result.getString("Password"))){
                    checkPassword = true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return (checkUsername && checkPassword);
    }

    public void insertAccount(String username, String password, String passwordRepeat, String email, LoginController loginController){
        String query = "";
        boolean nameSize = false;
        boolean passwordCheck = false;
        if(username.length() >= 8 ){
            loginController.setRegisterWrong("");
            nameSize = true;
            if(password.equals(passwordRepeat)) {
                passwordCheck = true;
                query = "INSERT INTO Admin_Accounts VALUES(" +
                        "'" + username+ "', " +
                        "'" + password + "'," +
                        "'" + email + "')";
                loginController.setRegisterWrong("");
            } else{loginController.setRegisterWrong("Passwords do not match! ");}
        }
        else{loginController.setRegisterWrong("Your username is too short");}
        //Executes the query
        if(nameSize && passwordCheck && checkAccountExists()) {
            try {
                Statement statement = databaseConnection.databaseLink.createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{loginController.setRegisterWrong("An account already exists");}
    }

    private boolean checkAccountExists(){
        int numRows = 0;
        try{
            Statement statement = databaseConnection.databaseLink.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ADMIN_ACCOUNTS");
            while(resultSet.next()){
                numRows++;
            }
            System.out.println(numRows);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return numRows > 1;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }


}