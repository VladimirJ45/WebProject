package com.DAO;

import com.DBConnector.DBConnectionManager;
import com.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public UserDAO() throws SQLException, ClassNotFoundException {
    }
    private final DBConnectionManager dbConnectionManager = new DBConnectionManager();
    private final Connection connection = dbConnectionManager.getConnection();

    private static final String GET_USER_BY_USERNAME = "SELECT * FROM Users WHERE Username = ?";

    private final RoleDAO roleDAO = new RoleDAO();
    private final AccountDAO accountDAO = new AccountDAO();

    public User findByUsername(String username){
        User user = new User();
        try(PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME);){
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                user.setId(rs.getInt("Id"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setRole(roleDAO.findById(rs.getInt("RoleId")));
                user.setAccount(accountDAO.findById(rs.getInt("AccountId")));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return user;
    }
}
