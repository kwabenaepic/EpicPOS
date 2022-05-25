package controller;

import beans.EmployeeLogins;
import beans.Product;
import beans.TopTenSelling;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tables.EmployeeLoginsManager;
import tables.InventoryValueManager;
import tables.ProductManager;
import tables.SaleReportsManager;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author KwabenaEpic
 */
public class HomeController implements Initializable {

    @FXML
    public Label lblMonth;
    @FXML
    public Label lblStockValue;
    @FXML
    public Label lblSumInventoryValue;
    @FXML
    private Label lblTotalSalesToday;
    @FXML
    private Label lblTotalItemsSoldForToday;
    @FXML
    private ComboBox<String> cbSalesMonthly;
    private ObservableList<String> salesMonthly;
    @FXML
    private Label lblTotalMonthlySales;
    @FXML
    private Label lblTotalPreviousMonthSales;
    @FXML
    private Label lblTotalNumberOfSalesForMonth;
    @FXML
    private TableColumn<EmployeeLogins, String> tblClmEmployeeName;
    @FXML
    private TableColumn<EmployeeLogins, String> tblClmOutletId;
    @FXML
    private TableView<EmployeeLogins> tblEmployeesLogedIn;
    private ObservableList<EmployeeLogins> employeesLogedIn = FXCollections.observableArrayList();
    private ObservableList<TopTenSelling> topTenSelling = FXCollections.observableArrayList();
    private ObservableList<Product> reOrderList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<TopTenSelling, String> tblClmProductId;
    @FXML
    private TableColumn<TopTenSelling, String> tblClmName;
    @FXML
    private TableColumn<TopTenSelling, String> tblClmDescription;
    @FXML
    private TableColumn<TopTenSelling, String> tblClmQuantityBought;
    @FXML
    private TableView<TopTenSelling> tblTopTenSelling;
    private SimpleDoubleProperty totalSalesTodayProperty = new SimpleDoubleProperty(0.0);
    private SimpleDoubleProperty totalSalesForMonthProperty = new SimpleDoubleProperty(0.0);
    private SimpleDoubleProperty totalSalesForPreviousMonthProperty = new SimpleDoubleProperty(0.0);
    private SimpleIntegerProperty totalItemsSoldToday = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty totalItemsSoldForMonth = new SimpleIntegerProperty(0);
    @FXML
    private TableView<Product> tblReorderList;
    @FXML
    private TableColumn<Product, String> tblClmReorderProId;
    @FXML
    private TableColumn<Product, String> tblClmReorderProName;
    @FXML
    private TableColumn<Product, String> tblClmReorderProQtyLeft;
    @FXML
    private Label lblMonthlySales;
    @FXML
    private Label lblSumMonthlySales;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        salesMonthly = FXCollections.observableArrayList();
        salesMonthly.add("January");
        salesMonthly.add("February");
        salesMonthly.add("March");
        salesMonthly.add("April");
        salesMonthly.add("May");
        salesMonthly.add("June");
        salesMonthly.add("July");
        salesMonthly.add("August");
        salesMonthly.add("September");
        salesMonthly.add("October");
        salesMonthly.add("November");
        salesMonthly.add("December");
        cbSalesMonthly.setItems(salesMonthly);
        cbSalesMonthly.getSelectionModel().select(getCurrentMonth()-1);
        System.out.println(getCurrentMonth());
        cbSalesMonthly.setVisibleRowCount(8);
//        String value = cbSalesMonthly.getSelectionModel().getSelectedItem();
        try {
            lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(getCurrentMonth()).toString());
            lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(getCurrentMonth()).toString());
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try {
//            lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(getCurrentMonth() - 2) + "");
//            lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(getCurrentMonth() + 1) + "");
//            lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(getCurrentMonth() + 1) + "");
//            lblTotalSalesToday.setText(SaleReportsManager.getTotalSalesToday().toString());
//            lblTotalItemsSoldForToday.setText(SaleReportsManager.getSumItemsSoldToday().toString());
//        } catch (SQLException ex) {
//            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        configureTopTenSellingTable();
//        configureEmployeeLogedInTable();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        try {

                            totalSalesTodayProperty.set(SaleReportsManager.getTotalSalesToday());
                            totalItemsSoldToday.set(SaleReportsManager.getSumItemsSoldToday());
                            totalSalesForMonthProperty.set(SaleReportsManager.getTotalSalesForAMonth((getCurrentMonth())));

                            if (("0"+(getCurrentMonth() + 1)).equals("12")) {
//                                totalSalesForPreviousMonthProperty.set(SaleReportsManager.getTotalSalesForAMonthIfJan(getCurrentMonth() + 12) + "");
                            } else {
                                totalSalesForPreviousMonthProperty.set(SaleReportsManager.getTotalSalesForAMonth(getCurrentMonth()));
                            }
                            totalItemsSoldForMonth.set(SaleReportsManager.getSumItemsSoldForAMonth((getCurrentMonth())));

                            lblTotalSalesToday.textProperty().bind(totalSalesTodayProperty.asString());
                            lblTotalItemsSoldForToday.textProperty().bind(totalItemsSoldToday.asString());
                            lblMonthlySales.textProperty().bind(totalSalesForMonthProperty.asString());
//                            lblTotalPreviousMonthSales.textProperty().bind(totalSalesForPreviousMonthProperty);
                            lblSumMonthlySales.textProperty().bind(totalItemsSoldForMonth.asString());
                            lblStockValue.setText(InventoryValueManager.getStockValue().toString());
                            lblSumInventoryValue.setText(InventoryValueManager.getSumInventory().toString());
                            configureTopTenSellingTable();
                            configureEmployeeLogedInTable();
                            configureReorderListTable();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                    }
                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }
        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();

    }

    private Integer getCurrentMonth() {
        int currentMonth = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
        return currentMonth;
    }

    @FXML
    private void cbSalesMonthlyOnAction(ActionEvent event) {
        String value = cbSalesMonthly.getSelectionModel().getSelectedItem();
        System.out.println(value);
        try {
            switch (value) {
                case "January":

                    lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(1).toString());
//                    lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth("01").toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(1).toString());
//                    break;
                case "February":
                    lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(2).toString());
                    lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(1).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(2).toString());
                    break;
                case "March":
                    lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(3).toString() );
                    lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(2).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(3).toString());
                    break;
                case "April":
                    lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(4).toString() );
                     lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(3).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(4).toString());
                    break;

                case "May":
                     lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(5).toString() );
                     lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(4).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(5).toString());
                    break;
                case "June":
                      lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(6).toString() );
                     lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(5).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(6).toString());
                    break;
                case "July":
                     lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(7).toString() );
                      lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(6).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(7).toString());
                    break;
                case "August":
                    lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(8).toString() );
                      lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(7).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(8).toString());
                    break;
                case "September":
                   lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(9).toString() );
                      lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(8).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(9).toString());
                    break;
                case "October":
                   lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(10).toString() );
                     lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(9).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(10).toString());
                    break;
                case "November":
                     lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(11).toString() );
                      lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(10).toString());;
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(11).toString());
                    break;
                case "December":
                    lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(12).toString() );
                      lblTotalPreviousMonthSales.setText(SaleReportsManager.getTotalSalesForAMonth(11).toString());
                    lblTotalNumberOfSalesForMonth.setText(SaleReportsManager.getSumItemsSoldForAMonth(12).toString());
                    break;
//                default:
////                    tblSales.getItems().clear();
//                         lblTotalMonthlySales.setText(SaleReportsManager.getTotalSalesForAMonth(3) + "");

            }

        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getSalesMonthly() {
    }

    private void configureEmployeeLogedInTable() {
//        tblClmEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        tblClmEmployeeName.setCellValueFactory(new PropertyValueFactory<>("username"));
        tblClmOutletId.setCellValueFactory(new PropertyValueFactory<>("salesOutletId"));

        tblEmployeesLogedIn.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblEmployeesLogedIn.setItems(null);
//        actionCol.setSortable(false);
        employeesLogedIn = FXCollections.observableArrayList();
        try {
            employeesLogedIn.addAll(EmployeeLoginsManager.getLogedinEmployees());
            tblEmployeesLogedIn.setItems(employeesLogedIn);
//            System.out.println(emplo);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void configureTopTenSellingTable() {
        tblClmProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblClmName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblClmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblClmQuantityBought.setCellValueFactory(new PropertyValueFactory<>("quantityBought"));

        tblTopTenSelling.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblTopTenSelling.setItems(null);
//        tblClmProductId.setPrefWidth(50);
//        tblClmProductId.setMinWidth(50);
//        tblClmProductId.setMaxWidth(50);
//        actionCol.setSortable(false);
        topTenSelling = FXCollections.observableArrayList();
        try {
            topTenSelling.addAll(SaleReportsManager.getTopTenSelling());
            tblTopTenSelling.setItems(topTenSelling);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void configureReorderListTable() {
        tblClmReorderProId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblClmReorderProName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmReorderProQtyLeft.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tblReorderList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblReorderList.setItems(null);
//        actionCol.setSortable(false);
        reOrderList = FXCollections.observableArrayList();
        try {
            reOrderList.addAll(ProductManager.getReorderList());
            tblReorderList.setItems(reOrderList);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
