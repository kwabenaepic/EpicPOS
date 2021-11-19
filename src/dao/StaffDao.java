/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.sql.DataSource;
import model.Staff;

/**
 *
 * @author KwabenaEpic
 */
public interface StaffDao {

    public void setDataSource(DataSource ds);

    public void create(String staffId, String userName, String firstName, String lastName, String password, String imagePath, String email);

//    public Staff getStaff(String staffId);
//    List<Staff> select(String staffId);
//    List<Staff> selectAll();
    public void delete(String staffId);

    public void deleteAll();

    public void update(String userName, String firstName, String lastName, String password, String imagePath, String email);

}
