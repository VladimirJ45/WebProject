package com.DAO;

import com.DBConnector.DBConnectionManager;
import com.Model.Account;
import com.Model.Item;
import com.Model.UserOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserOrderDAO {
    public UserOrderDAO()  throws SQLException, ClassNotFoundException{
    }
    private final DBConnectionManager dbConnectionManager = new DBConnectionManager();
    private final Connection connection = dbConnectionManager.getConnection();
    private final ItemDAO itemDAO = new ItemDAO();

    private static final String CREATE_NEW_ORDER = "INSERT INTO UserOrders (ItemId, AccountId) VALUES (?, ?)";
    private static final String GET_USER_ORDERS = "SELECT ItemId FROM UserOrders WHERE AccountId=?";

    public void createNewOrder(UserOrder userOrder){
        try(PreparedStatement statement = connection.prepareStatement(CREATE_NEW_ORDER);){
            statement.setInt(1, userOrder.getItem().getId());
            statement.setInt(2, userOrder.getAccount().getId());
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Item> getUserItems(Account account){
        List<Item> items = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(GET_USER_ORDERS);){
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                items.add(itemDAO.getItemById(rs.getInt("ItemId")));
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return items;
    }
}
