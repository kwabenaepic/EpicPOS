package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import beans.Receipts;

import database.ConnectionManager;

public class ReceiptsManager {
    private static Connection conn = ConnectionManager.getInstance().getConnection();
    private static ObservableList<Receipts> product;

//  }
    public static boolean insert(Receipts bean) throws Exception {
        String sql =
            "INSERT into receipts (customerId, employeeId, salesOutletId , modeOfPayment, ticketNumber, amountPaid, balance, receiptDate)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
        ResultSet keys = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, bean.getCustomerId());
            stmt.setInt(2, bean.getEmployeeId());
            stmt.setString(3, bean.getSalesOutletId());
            stmt.setString(4, bean.getModeOfPayment());
            stmt.setString(5, bean.getTicketNumber());
            stmt.setDouble(6, bean.getAmountPaid());
            stmt.setDouble(7, bean.getBalance());

            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();

                int newKey = keys.getInt(1);
                System.out.println("A new  receipt!" + newKey);
                bean.setReceiptId(newKey);
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

    public static ObservableList<Receipts> getProductsInTransactionList() throws SQLException {
        ObservableList<Receipts> productList = FXCollections.observableArrayList();
        String                   sql         = "SELECT * FROM receipts";

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Product Receipts:");

            while (rs.next()) {
                Receipts bean = new Receipts();

                bean.setReceiptId(rs.getInt("receiptId"));
                bean.setCustomerId(rs.getString("customerId"));
                bean.setEmployeeId(rs.getInt("employeeId"));
                bean.setSalesOutletId(rs.getString("salesOutletId"));
                bean.setModeOfPayment(rs.getString("modeOfPayMent"));
                bean.setTicketNumber(rs.getString("ticketNumber"));
                bean.setAmountPaid(rs.getDouble("amountPaid"));
                bean.setBalance(rs.getDouble("balance"));
                bean.setReceiptDate(rs.getString("receiptDate"));
                productList.add(bean);

//              System.out.println(bf.toString());
            }

            return productList;
        }
    }

//  public static boolean update(Product bean) throws Exception {
//
//      String sql
//              = "UPDATE Product SET "
//              + "userName = ?, password = ? "
//              + "WHERE productsId = ?";
//      try (
//              PreparedStatement stmt = conn.prepareStatement(sql);) {
//
//          stmt.setString(1, bean.getUserName());
//          stmt.setString(2, bean.getPassword());
//          stmt.setInt(3, bean.getId());
//
//          int affected = stmt.executeUpdate();
//          if (affected == 1) {
//              return true;
//          } else {
//              return false;
//          }
//
//      } catch (SQLException e) {
//          System.err.println(e);
//          return false;
//      }
//
//  }
}


//~ Formatted by Jindent --- http://www.jindent.com
