package com.DAO;

import com.DBConnector.DBConnectionManager;
import com.Model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {
    public RoleDAO() throws SQLException, ClassNotFoundException {
    }
    private final DBConnectionManager dbConnectionManager = new DBConnectionManager();
    private final Connection connection = dbConnectionManager.getConnection();

    private static final String GET_ROLE_BY_ID = "SELECT * FROM Roles WHERE Id = ?";

    public Role findById(int id){
        Role role = new Role();
        try(PreparedStatement statement = connection.prepareStatement(GET_ROLE_BY_ID);){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                role.setId(rs.getInt("Id"));
                role.setName(rs.getString("Name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return role;
    }
}
