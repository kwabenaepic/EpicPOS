package tables;

import beans.Business;
import beans.Employee;
import beans.EmployeeLogins;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class BusinessManager {
    private static Connection conn = ConnectionManager.getInstance().getConnection();
    private static ObservableList<Employee> employee;


    public static boolean insert(Business bean) throws Exception {
        String sql
                = "INSERT into business (name, mobile, phone, location, address, created) "
                + "VALUES (?, ?, ?, ?, ?, date(now()))";
        ResultSet keys = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, bean.getName());
            stmt.setString(2, bean.getMobile());
            stmt.setString(3, bean.getPhone());
            stmt.setString(4, bean.getLocation());
            stmt.setString(5, bean.getAddress());

//            stmt.setString(8, bean.getImagePath());

//            if (bean.getImagePath() != null) {
//                InputStream is;
//
//                is = new FileInputStream(bean.getImagePath());
//                stmt.setBlob(9, is);
//            } else {
//                stmt.setBlob(9, (Blob) null);
//            }

            int affected = stmt.executeUpdate();

            if (affected == 1) {
//                keys = stmt.getGeneratedKeys();
//                keys.next();
//
//                int newKey = keys.getInt(1);
//                bean.setEmployeeId(newKey);
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
    public static Business getCompanyInfo() throws SQLException {
        Business bean = new Business();
        String sql = "SELECT * from business";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bean = new Business();
//                bean.setEmployeeId(rs.getString("employeeId"));
                bean.setName(rs.getString("name"));
                bean.setMobile(rs.getString("mobile"));
                bean.setPhone(rs.getString("phone"));
                bean.setAddress(rs.getString("address"));
                bean.setLocation(rs.getString(("location")));

            }

            return bean;
        }
    }

}
