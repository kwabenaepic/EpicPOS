///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package dao;
//
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import daoMapper.StaffMapper;
//import model.Staff;
//
///**
// *
// * @author KwabenaEpic
// */
//public class StaffJDBCTemplate implements StaffDao {
//
//    private DataSource dataSource;
//    private JdbcTemplate jdbcTemplateObject;
//
//    @Override
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
////        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
//    }
//
//    @Override
//    public void create(String staffId, String userName, String firstName, String lastName, String password, String imagePath, String email) {
//        JdbcTemplate insert = new JdbcTemplate(dataSource);
//        insert.update("insert into Staff (staffId, userName, firstName, lastName, password, imagePath, email) values (?,?)",
//                new Object[]{staffId, userName, firstName, lastName, password, imagePath, email});
////        String SQL = "insert into Staff (staffId, userName, firstName, lastName, password, imagePath, email) values (?, ?)",
////        jdbcTemplateObject.update(SQL, staffId, userName, firstName, lastName, password, imagePath, email);
//        System.out.println("Created Record Name = " + firstName);
//
//    }
//
////    @Override
////    public Staff getStaff(String staffId) {
////          JdbcTemplate select = new JdbcTemplate(dataSource);
////          return select.query("select * from Staff where staffId = ?",
////                new StaffMapper());
////          Staff staff = query.query("select * from Staff where staffId = ?",
////                  new Object[]{staffId}, new StaffMapper());
//////        String SQL = "select * from Staff where staffId = ?";
//////        Staff staff = jdbcTemplateObject.queryForObject(SQL,
//////                new Object[]{staffId}, new StaffMapper());
//////
//////        return query;
////    }
////    @Override
////    public List<Staff> select(String staffId) {
////        JdbcTemplate select = new JdbcTemplate(dataSource);
////        return select.query("select * from Staff where staffId = ?",
////                new Object[]{staffId},
////                new StaffMapper());
////    }
////    @Override
////    public List<Staff> selectAll() {
////        JdbcTemplate select = new JdbcTemplate(dataSource);
////        return select.query("select * from Staff",
////                new StaffMapper());
////    }
//    @Override
//    public void delete(String staffId) {
//        JdbcTemplate delete = new JdbcTemplate(dataSource);
//        delete.update("DELETE from Staff where staffId = ?",
//                new Object[]{staffId});
//
//        System.out.println("Deleted Record with ID = " + staffId);
//
//    }
//
//    @Override
//    public void update(String userName, String firstName, String lastName, String password, String imagePath, String email) {
//        JdbcTemplate update = new JdbcTemplate(dataSource);
//        update.update("update Staff set userName = ?, firstName = ?, lastName = ?, password = ?, imagePath = ?, email = ? where staffId = ?",
//                new Object[]{userName, firstName, lastName, password, imagePath, email});
//
//        System.out.println("Updated Record with Name = ");
//
//    }
//
//    @Override
//    public void deleteAll() {
//        JdbcTemplate delete = new JdbcTemplate(dataSource);
//        delete.update("DELETE from STAFF");
//    }
//
//}
