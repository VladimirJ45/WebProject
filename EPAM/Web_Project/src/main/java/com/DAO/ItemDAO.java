package com.DAO;

import com.DBConnector.DBConnectionManager;
import com.Model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public ItemDAO() throws SQLException, ClassNotFoundException {
    }

    private final DBConnectionManager dbConnectionManager = new DBConnectionManager();
    private final Connection connection = dbConnectionManager.getConnection();

    private static final String ADD_NEW_ITEM = "INSERT INTO Items (Name, Description, Price) VALUES (?, ?, ?)";
    private static final String GET_ALL_ITEMS = "SELECT * FROM Items";
    private static final String DELETE_BY_ID = "DELETE FROM Items WHERE Id=?";
    private static final String GET_ITEM_BY_ID = "SELECT * FROM Items WHERE Id=?";
    private static final String GET_ITEM_BY_NAME = "SELECT * FROM Items WHERE Name=?";
    private static final String UPDATE_ITEM = "UPDATE Items SET Name=?, Description=?, Price=? WHERE Id=?";

    public void updateItem(Item item){
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ITEM);) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setInt(3, item.getPrice());
            statement.setInt(4, item.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Item getItemByName(String name){
        Item item = new Item();
        try (PreparedStatement statement = connection.prepareStatement(GET_ITEM_BY_NAME);) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                item.setId(rs.getInt("Id"));
                item.setName(rs.getString("Name"));
                item.setDescription(rs.getString("Description"));
                item.setPrice(rs.getInt("Price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return item;
    }

    public Item getItemById(int id) {
        Item item = new Item();
        try (PreparedStatement statement = connection.prepareStatement(GET_ITEM_BY_ID);) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                item.setId(rs.getInt("Id"));
                item.setName(rs.getString("Name"));
                item.setDescription(rs.getString("Description"));
                item.setPrice(rs.getInt("Price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return item;
    }

    public void addItems(Item item) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_NEW_ITEM);) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setInt(3, item.getPrice());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_ITEMS);) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("Id"));
                item.setName(rs.getString("Name"));
                item.setDescription(rs.getString("Description"));
                item.setPrice(rs.getInt("Price"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return items;
    }

    public void deleteItemById(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_BY_ID);) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
