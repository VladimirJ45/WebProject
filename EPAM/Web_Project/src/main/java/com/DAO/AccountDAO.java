package com.DAO;

import com.DBConnector.DBConnectionManager;
import com.Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public AccountDAO() throws SQLException, ClassNotFoundException {
    }

    private final DBConnectionManager dbConnectionManager = new DBConnectionManager();
    private final Connection connection = dbConnectionManager.getConnection();
    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM Account WHERE Id = ?";
    private static final String GET_ALL_ACCOUNTS = "SELECT * FROM Account";

    public Account findById(int id){
        Account account = new Account();
        try(PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_ID);){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                account.setId(rs.getInt("Id"));
                account.setAddress(rs.getString("Address"));
                account.setPhoneNumber(rs.getString("PhoneNumber"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return account;
    }

    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_ACCOUNTS);){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Account account = new Account();
                account.setId(rs.getInt("Id"));
                account.setAddress(rs.getString("Address"));
                account.setPhoneNumber(rs.getString("PhoneNumber"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                accounts.add(account);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return accounts;
    }
}
