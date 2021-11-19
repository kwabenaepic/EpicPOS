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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author KwabenaEpic
 */
public class InventoryValueManager {

    private static Connection conn = ConnectionManager.getConnection();

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
                bean.setQuantity(rs.getInt("quantity"));
                bean.setUnitPrice(rs.getDouble("unitPrice"));
                bean.setTotalValue(rs.getDouble("totalValue"));
                inventoryValueList.add(bean);
            }
            return inventoryValueList;

        }
    }
}
