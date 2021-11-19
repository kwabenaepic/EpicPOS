package tables;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.EmployeeLogins;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import beans.Employee;

import database.ConnectionManager;

public class EmployeeManager {

    private static Connection conn = ConnectionManager.getConnection();
    private static ObservableList<Employee> employee;

    public static boolean insert(beans.Employee bean) throws Exception {
        String sql
             = "INSERT into employee (firstName, lastName, phone, mobile, email, username, password, imagePath, image,"
             + "address, city, securityGroup, enabled) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet keys = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, bean.getFirstName());
            stmt.setString(2, bean.getLastName());
            stmt.setString(3, bean.getPhone());
            stmt.setString(4, bean.getMobile());
            stmt.setString(5, bean.getEmail());

//          stmt.setString(6, bean.getEmployeeId());
            stmt.setString(6, bean.getUsername());
            stmt.setString(7, bean.getPassword());
            stmt.setString(8, bean.getImagePath());

            if (bean.getImagePath() != null) {
                InputStream is;

                is = new FileInputStream(bean.getImagePath());
                stmt.setBlob(9, is);
            } else {
                stmt.setBlob(9, (Blob) null);
            }

            stmt.setString(11, bean.getAddress());
            stmt.setString(12, bean.getCity());
            stmt.setString(13, bean.getSecurityGroup());
            stmt.setBoolean(14, bean.getEnabled());

            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();

                int newKey = keys.getInt(1);

                bean.setEmployeeNumber(newKey);
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

//  public static Employee getRow(int id) throws SQLException {
//
//      String sql = "SELECT * FROM Employee WHERE id = ?";
//      ResultSet rs = null;
//
//      try (
//              PreparedStatement stmt = conn.prepareStatement(sql);) {
//          stmt.setInt(1, id);
//          rs = stmt.executeQuery();
//
//          if (rs.next()) {
//              Employee bean = new Employee();
//              bean.setId(id);
//              bean.setUserName(rs.getString("userName"));
//              bean.setPassword(rs.getString("password"));
//
//              return bean;
//
//          } else {
//              return null;
//          }
//
//      } catch (SQLException e) {
//          System.err.println(e);
//          return null;
//      } finally {
//          if (rs != null) {
//              rs.close();
//          }
//      }
//
//  }
    public static Employee validate(String username, String password) {
        Employee bean = new Employee();
        String sql = "SELECT * FROM employee WHERE username = ? AND password = ?;";
//        ResultSet rs = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                 bean = new Employee();
                bean.setUsername(rs.getString("username"));
                bean.setPassword(rs.getString("password"));
                bean.setFirstName(rs.getString("firstName"));
                bean.setLastName(rs.getString("lastName"));
                bean.setEmail(rs.getString("email"));
                bean.setMobile(rs.getString("mobile"));
                bean.setPhone(rs.getString("phone"));
                bean.setEmployeeId(rs.getInt("id"));
                bean.setImage(rs.getBlob("image"));
            }
        } catch (SQLException e) {
//            System.err.println(e.getMessage());
            System.out.println(e.getMessage());
        }
        return bean;
    }

    public static ObservableList<Employee> getEmployeesList() throws SQLException {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Employee Table:");

            while (rs.next()) {
                Employee bean = new Employee();

                bean.setFirstName(rs.getString("firstName"));
                bean.setLastName(rs.getString("lastName"));
                bean.setPhone(rs.getString("phone"));
                bean.setMobile(rs.getString("mobile"));
                bean.setEmail(rs.getString("email"));

                bean.setEmployeeId(rs.getInt("id"));
                bean.setUsername(rs.getString("username"));
                bean.setPassword(rs.getString("password"));

//                if (rs.getBlob("image") != null) {
//                    ByteArrayInputStream byteArrayInputStream
//                         = new ByteArrayInputStream(rs.getBlob("image").getBytes(1, (int) rs.getBlob("image").length()));
//
//                    bean.setImage(rs.getBlob("image"));
//                }

                bean.setAddress(rs.getString("address"));
                bean.setCity(rs.getString("city"));
                bean.setSecurityGroup(rs.getString("securityGroup"));
                bean.setEnabled(rs.getBoolean("enabled"));
                employeeList.add(bean);
            }

            return employeeList;
        }
    }

//  public static boolean update(Employee bean) throws Exception {
//
//      String sql
//              = "UPDATE Employee SET "
//              + "userName = ?, password = ? "
//              + "WHERE employeesId = ?";
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
}


//~ Formatted by Jindent --- http://www.jindent.com
