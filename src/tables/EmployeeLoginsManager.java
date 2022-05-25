
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.EmployeeLogins;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author KwabenaEpic
 */
public class EmployeeLoginsManager {

    private static Connection conn = ConnectionManager.getInstance().getConnection();

    public static boolean insertLogin(EmployeeLogins bean) throws Exception {
        String sql = "INSERT INTO employeelogins (date, username, sessionId, status, salesOutletId, loginTime) VALUES (date(now()), ?, ?, ?, ?, CURRENT_TIME)";
//                + "ON CONFLICT(username) DO UPDATE set status = status";
        ResultSet keys = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, bean.getDate());
            stmt.setString(1, bean.getUsername());
            stmt.setString(2, bean.getSessionId());
            stmt.setInt(3, bean.getStatus());
            stmt.setString(4, bean.getSalesOutletId());
//            ResultSet keys = null;
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();

                int newKey = keys.getInt(1);
                bean.setLoginId(newKey);
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

    public static ObservableList<EmployeeLogins> getLogedinEmployees() throws SQLException {
        ObservableList<EmployeeLogins> logedinEmployeesList = FXCollections.observableArrayList();
        String sql = "SELECT username, salesOutletId FROM employeelogins where status = 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EmployeeLogins bean = new EmployeeLogins();

//                bean.setEmployeeId(rs.getString("employeeId"));
                bean.setUserName(rs.getString("username"));
                bean.setSalesOutletId(rs.getString("salesOutletId"));
                logedinEmployeesList.add(bean);
            }

            return logedinEmployeesList;
        }
    }

    public static boolean updateLogout(String sessionId) {
        String sql = "UPDATE employeelogins SET status = 0, logoutTime = CURRENT_TIME WHERE sessionId = ? ";
        ResultSet keys = null;

        // Execute UPDATE operation
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

//          stmt.setString(1, bean.getLogoutTime());
            stmt.setString(1, sessionId);

            int affected = stmt.executeUpdate();

            return affected == 1;
        } catch (SQLException e) {
            System.err.println(e);

            return false;
        }

    }
}
