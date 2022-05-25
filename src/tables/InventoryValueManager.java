/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import beans.InventoryValue;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 *
 * @author KwabenaEpic
 */
public class InventoryValueManager {

    private static Connection conn = ConnectionManager.getInstance().getConnection();

    public static ObservableList<InventoryValue> getInventoryValueList() throws SQLException {
        ObservableList<InventoryValue> inventoryValueList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM inventoryvalue";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

//            System.out.println("Product Table:");
            while (rs.next()) {
                InventoryValue bean = new InventoryValue();
                bean.setProductId(rs.getInt("productId"));
                bean.setProductName(rs.getString("productName"));
                bean.setDescription(rs.getString("description"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setUnitPrice(rs.getDouble("unitPrice"));
                bean.setCostPrice(rs.getDouble("costPrice"));
                bean.setTotalValue(rs.getDouble("totalValue"));
                inventoryValueList.add(bean);
            }
            return inventoryValueList;

        }
    }

    public static Double getStockValue() throws SQLException {
        String sql = "select sum(totalvalue) as stockValue from inventoryvalue ";

        ResultSet rs = null;
        Double stockValue = 0.0;
//        System.out.println(sql);
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
           rs = stmt.executeQuery();

            if (rs.next()) {
                stockValue = rs.getDouble(1);
            }
        }

        return stockValue;
    }

    public static Integer getSumInventory() throws SQLException {
        String sql = "select sum(quantity) as sumInventory from inventoryvalue ";

        ResultSet rs = null;
        int sumInventory = 0;
//        System.out.println(sql);
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();

            if (rs.next()) {
                sumInventory = rs.getInt(1);
            }
        }

        return sumInventory;
    }
}
