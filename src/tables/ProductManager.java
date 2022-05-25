package tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.ConnectionManager;
import beans.Product;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductManager {

//    private static ObservableList<Product> product;

    private static Connection conn = ConnectionManager.getInstance().getConnection();

    public static ObservableList<Product> getProductsList() throws SQLException {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";
        try (
                 Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product bean = new Product();
                bean.setCategory(rs.getString("category"));
                bean.setCostPrice(rs.getDouble("costPrice"));
                bean.setDescription(rs.getString("description"));
                bean.setProductId(rs.getInt("productId"));
//                bean.setProductNumber(rs.getString("productNumber"));
                bean.setName(rs.getString("name"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setReorderLevel(rs.getInt("reorderLevel"));
//                bean.setSuppleirId(rs.getString("suppleirId"));
                bean.setUnitPrice(rs.getDouble("unitPrice"));
//                bean.setAttribute(rs.getString("attribute"));
                bean.setSize(rs.getString("size"));
                bean.setALU(rs.getString("ALU"));
                bean.setUPC(rs.getString("UPC"));
                bean.setVendorCode(rs.getString("vendorCode"));

                productList.add(bean);

            }
            return productList;

        }
    }

    public static boolean insert(beans.Product bean) throws Exception {

        String sql = "INSERT into product (name, category, ALU, UPC, vendorCode, description, "
                + "costPrice, unitPrice, quantity, reorderLevel, size) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet keys = null;
        try (
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, bean.getName());
            stmt.setString(2, bean.getCategory());
//            stmt.setString(3, bean.getSuppleirId());
//            stmt.setString(3, bean.getAttribute());
//            stmt.setString(4, bean.getSize());
            stmt.setString(3, bean.getALU());
            stmt.setString(4, bean.getUPC());
            stmt.setString(5, bean.getVendorCode());
            stmt.setString(6, bean.getDescription());
            stmt.setDouble(7, bean.getCostPrice());
            stmt.setDouble(8, bean.getUnitPrice());
            stmt.setInt(9, bean.getQuantity());
            stmt.setInt(10, bean.getReorderLevel());
            stmt.setString(11, bean.getSize());

            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();
                int newKey = keys.getInt(1);
                bean.setProductId(newKey);
            } else {
                System.err.println("No rows affected");
                return false;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            if (keys != null) {
                keys.close();
            }
        }
        return true;
    }

    public static boolean updateItemQuntityOnSale(beans.Product bean) throws Exception {

        String sql = "UPDATE product SET quantity = (quantity - ?) WHERE productId = ? ";

        ResultSet keys = null;
        //Execute UPDATE operation
        try (
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bean.getQuantity());
            stmt.setInt(2, bean.getProductId());

            int affected = stmt.executeUpdate();
            return affected == 1;

        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    public static ObservableList<Product> getReorderList() throws SQLException {
        ObservableList<Product> reOrderList = FXCollections.observableArrayList();
        String sql = "Select * from product where quantity <= reorderLevel";
        try (
                Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                Product bean = new Product();
                bean.setProductId(rs.getInt("productId"));
                bean.setName(rs.getString("name"));
                bean.setDescription(rs.getString("description"));
                bean.setQuantity(rs.getInt("quantity"));
                reOrderList.add(bean);
            }
            rs.close();

            return reOrderList;

        }
    }
    public static boolean update(Product bean) throws Exception {
        String sql
                = "UPDATE product SET name = ?, ALU = ?, UPC = ?, vendorCode = ?, "
                + "description = ?, costPrice = ?, unitPrice = ?, quantity = ?, reorderLevel = ?, category = ?, size = ? WHERE productId = ?";
        ResultSet keys = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, bean.getName());
            stmt.setString(2, bean.getALU());
            stmt.setString(3, bean.getUPC());
            stmt.setString(4, bean.getVendorCode());
            stmt.setString(5, bean.getDescription());
            stmt.setDouble(6, bean.getCostPrice());
            stmt.setDouble(7, bean.getUnitPrice());
            stmt.setInt(8, bean.getQuantity());
            stmt.setInt(9, bean.getReorderLevel());
            stmt.setString(10, bean.getCategory());
            stmt.setString(11, bean.getSize());
            stmt.setInt(12, bean.getProductId());


            int affected = stmt.executeUpdate();

            if (affected == 1) {
                return true;
            } else {
                System.err.println("No rows affected");
                return false;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public static Product getRow(int productId) throws SQLException {

        String sql = "SELECT * FROM product WHERE productId = ?";
        ResultSet rs = null;

        try (
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, productId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Product bean = new Product();
                bean.setCategory(rs.getString("category"));
                bean.setCostPrice(rs.getDouble("costPrice"));
                bean.setDescription(rs.getString("description"));
                bean.setProductId(rs.getInt("productId"));
                bean.setName(rs.getString("name"));
                bean.setSize(rs.getString("size"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setReorderLevel(rs.getInt("reorderLevel"));
                bean.setUnitPrice(rs.getDouble("unitPrice"));
                bean.setALU(rs.getString("ALU"));
                bean.setUPC(rs.getString("UPC"));
                bean.setVendorCode(rs.getString("vendorCode"));

                return bean;

            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

    }

    public static boolean deleteProduct(int id) throws SQLException {

        String sql = "delete FROM product WHERE productId = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                return true;
            } else {
                System.err.println("No rows affected");
                return false;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }

    }


//    public static boolean update(Product bean) throws Exception {
//
//        String sql
//                = "UPDATE Product SET "
//                + "userName = ?, password = ? "
//                + "WHERE productsId = ?";
//        try (
//                PreparedStatement stmt = conn.prepareStatement(sql);) {
//
//            stmt.setString(1, bean.getUserName());
//            stmt.setString(2, bean.getPassword());
//            stmt.setInt(3, bean.getId());
//
//            int affected = stmt.executeUpdate();
//            if (affected == 1) {
//                return true;
//            } else {
//                return false;
//            }
//
//        } catch (SQLException e) {
//            System.err.println(e);
//            return false;
//        }
//
//    }
}
