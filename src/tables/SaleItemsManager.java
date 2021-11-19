package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.ConnectionManager;
import beans.SaleItems;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaleItemsManager {

    private static ObservableList<SaleItems> salesTransaction;

    private static Connection conn = ConnectionManager.getConnection();

    public static ObservableList<SaleItems> getSaleItemsList() throws SQLException {
        ObservableList<SaleItems> saleitemsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM saleitems";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            System.out.println("Product SaleItems:");
            while (rs.next()) {
                SaleItems bean = new SaleItems();
                bean.setSaleId(rs.getInt("saleId"));
                bean.setTicketNumber(rs.getString("ticketNumber"));
                bean.setProductId(rs.getInt("productId"));
                bean.setQuantity(rs.getInt("quantity"));
                saleitemsList.add(bean);

//                System.out.println(bf.toString());
            }
            return saleitemsList;

        }
    }

    public static boolean insert(SaleItems bean) throws Exception {

        String sql = "INSERT into saleitems (ticketNumber, productId, quantity)" + "VALUES (?, ?, ?)";
        ResultSet keys = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, bean.getTicketNumber());
            stmt.setInt(2, bean.getProductId());
            stmt.setDouble(3, bean.getQuantity());

            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();
                int newKey = keys.getInt(1);
                bean.setSaleId(newKey);
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
