package com.example.morozov;

import java.sql.*;

public class DB {
    private final String HOST="localhost";
    private final String PORT="8889";
    private final String DB_NAME="morozov-java";
    private final String LOGIN="root";
    private final String PASS="root";

    private Connection dbConn=null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String Connstr="jdbc:mysql://"+ HOST+":"+PORT+"/"+DB_NAME;//информация о подключении
        Class.forName("com.mysql.cj.jdbc.Driver");//какую технологию используем для подключения
        dbConn= DriverManager.getConnection(Connstr,LOGIN,PASS);//подключение к базе данных
        return dbConn;
    }
    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn=getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public boolean isExistsUser(String login){
        String sql="SELECT `id` FROM `users` WHERE `login`=?";
        try {
            PreparedStatement prSt=getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            ResultSet res=prSt.executeQuery();
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void regUser(String login, String email, String pass){
        String sql="INSERT INTO `users` (`login`, `email`, `password`) VALUES (?,?,?)";
        try {
            PreparedStatement prSt=getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, email);
            prSt.setString(3, pass);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean authUser(String login, String pass) {
        String sql="SELECT `id` FROM `users` WHERE `login`=? AND `password`=?";
        try {
            PreparedStatement prSt=getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, pass);
            ResultSet res=prSt.executeQuery();
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getArticles() {
        String sql = "SELECT `title`, `intro` FROM `ar`";
        Statement statement = null;
        try {
            statement = getDbConnection().createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addArticles(String title, String intro, String text) {
        String sql="INSERT INTO `ar` (`title`, `intro`, `text`) VALUES (?,?,?)";
        try {
            PreparedStatement prSt=getDbConnection().prepareStatement(sql);
            prSt.setString(1, title);
            prSt.setString(2, intro);
            prSt.setString(3, text);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
