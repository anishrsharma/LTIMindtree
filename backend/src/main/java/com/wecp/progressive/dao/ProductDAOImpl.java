package com.wecp.progressive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
// import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;

import com.wecp.progressive.config.DatabaseConnectionManager;
import com.wecp.progressive.entity.Product;

public class ProductDAOImpl implements ProductDAO {

    // List<Product> productList = new ArrayList<>();

    @Override
    public int addProduct(Product product) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        int generatedID = -1;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO product(warehouse_id,product_name,product_description,quantity,price)VALUES(?,?,?,?,?)";

            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, product.getWarehouseId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getProductDescription());
            ps.setInt(4, product.getQuantity());
            ps.setDouble(5, product.getPrice());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedID = rs.getInt(1);
                product.setProductId(generatedID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return generatedID;
    }

    @Override
    public Product getProductById(int productId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        // int generatedID = -1;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM product WHERE product_id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getInt("warehouse_id"),
                        rs.getString("product_name"),
                        rs.getString("product_description"),
                        rs.getInt("quantity"),
                        rs.getLong("price"));
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE product SET warehouse_id=?,product_name=?,product_description=?,quantity=?,price=? WHERE product_id=?";

            ps = connection.prepareStatement(sql);
            ps.setDouble(1, product.getWarehouseId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getProductDescription());
            ps.setInt(4, product.getQuantity());
            ps.setDouble(5, product.getPrice());
            ps.setInt(6, product.getProductId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    @Override
    public void deleteProduct(int productId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM product WHERE product_id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM product";
            ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("product_id"),
                        rs.getInt("warehouse_id"),
                        rs.getString("product_name"),
                        rs.getString("product_description"),
                        rs.getInt("quantity"),
                        rs.getLong("price")));
            }
            return productList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

}
