package com.DBConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionManager {

    private final String USERNAME = "root";
    private final String PASSWORD = "74VgMm-735";
    private final String HOST = "localhost";
    private final String DATABASENAME = "OnlineStore";
    private final String url;
    private final Properties properties;

    public DBConnectionManager(){
        this.url = "jdbc:mysql://"+HOST+"/"+DATABASENAME+"?characterEncoding=utf8";
        this.properties = new Properties();
        this.properties.setProperty("user", USERNAME);
        this.properties.setProperty("password", PASSWORD);
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection(this.url, this.properties);
    }
}
