/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Business;
import beans.ProductsInTransaction;
import beans.ReceiptsReprint;
import beans.SaleReports;
import com.sun.prism.impl.Disposer.Record;
import database.ConnectionManager;
import epicpos.Main;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import tables.BusinessManager;
import tables.ProductsInTransactionManager;
import tables.SaleReportsManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author KwabenaEpic
 */
public class SalesReportsController implements Initializable {

    @FXML
    private AnchorPane acContent;
    @FXML
    private BorderPane bpRoot;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnRefresh;
    @FXML
    private SplitPane splitPane;

    @FXML
    private TableColumn<Record, Boolean> tblClmAction;
    @FXML
    private AnchorPane bpRightAnchor;
    @FXML
    private TableView<SaleReports> tblSales;
    @FXML
    private TableColumn<SaleReports, String> tblClmDate;
    @FXML
    private TableColumn<SaleReports, String> tblClmSaleOutlet;
    @FXML
    private TableColumn<SaleReports, String> tblClmRecieptId;
    @FXML
    private TableColumn<SaleReports, String> tblClmEmployee;
    @FXML
    private TableColumn<SaleReports, Integer> tblClmNoOfItems;
    @FXML
    private TableColumn<SaleReports, Double> tblClmTotal;
    @FXML
    private TableColumn<SaleReports, String> tblClmModeOfPayment;
    @FXML
    private TableColumn<SaleReports, String> tblClmTicketNumber;
    private ObservableList<String> dateRange;
    @FXML
    private ComboBox<String> cbDateRange;
    private ObservableList<SaleReports> tableData = FXCollections.observableArrayList();
    private ObservableList<ProductsInTransaction> tableDataProduct = FXCollections.observableArrayList();
    private ObservableList<ProductsInTransaction> dataList = FXCollections.observableArrayList();
    private static Connection conn = ConnectionManager.getInstance().getConnection();

    @FXML
    private Button btnHide;
    @FXML
    private TableView<ProductsInTransaction> tblInventory;
    @FXML
    private TableColumn<ProductsInTransaction, String> tblClmItemId;
    @FXML
    private TableColumn<ProductsInTransaction, String> tblClmItemName;
    @FXML
    private TableColumn<ProductsInTransaction, String> tblClmDescription;
    @FXML
    private TableColumn<ProductsInTransaction, Integer> tblClmQuantity;
    @FXML
    private TableColumn<ProductsInTransaction, Double> tblClmUnitPrice;
    @FXML
    private Label lblSaleItemDate;
    @FXML
    private Button btnShowRange;
    @FXML
    private Label subTotalLbl;
    @FXML
    private Label taxLbl;
    @FXML
    private Label lblTotal;
    @FXML
    private DatePicker dpDateFrom;
    @FXML
    private DatePicker dpDateTo;
    String selectedReceipt;
    @FXML
    private Button btnReprintReceipt;
    @FXML
    private Label lblAmountPaid;
    @FXML
    private Label lblBalance;
    @FXML
    private TableColumn<SaleReports, Double> tblClmAmountPaid;
    @FXML
    private TableColumn<SaleReports, Double> tblClmBalance;
    HashMap<Integer, Double> saleItems = new HashMap();
    private Business businessData ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        final ProgressIndicator progressIndicator = new ProgressIndicator(0);
        dateRange = FXCollections.observableArrayList();

        dateRange.add("Today");
        dateRange.add("Yesterday");
        dateRange.add("This Week");
//        dateRange.add("Last Week");
        dateRange.add("This Month");
        dateRange.add("Last Month");
        dateRange.add("All Sales");
        cbDateRange.setItems(dateRange);
        cbDateRange.setVisibleRowCount(5);

        splitPane.getItems().remove(bpRightAnchor);
        configureTable();
        configureSaleItemTable();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
//                        configureTable();
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(1000);
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

        try {
            businessData = BusinessManager.getCompanyInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calcSubTotal() {
        int rowIndex = tblInventory.getItems().size() - 1;
//        ProductsInTransaction rowList = tblInventory.getItems().get(rowIndex);
        double result = 0.0;
        for (ProductsInTransaction item : tblInventory.getItems()) {
//            result = result + item.getTotal();
            System.out.println(item.getTotal() + "");
        }
//        subTotalLbl.setText(qty + "");
//        lblTotal.setText(qty + "");
//        saleItems.put(rowList.getProductId(), result);
//        reSetItems();
        return result;
    }

    private void configureTable() {
        tblClmDate.setCellValueFactory(new PropertyValueFactory<>("receiptDate"));
        tblClmRecieptId.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        tblClmTicketNumber.setCellValueFactory(new PropertyValueFactory<>("ticketNumber"));
        tblClmEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeLastName"));
        tblClmSaleOutlet.setCellValueFactory(new PropertyValueFactory<>("salesOutletId"));
        tblClmModeOfPayment.setCellValueFactory(new PropertyValueFactory<>("modeOfPayment"));
        tblClmAmountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        tblClmBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        tblClmNoOfItems.setCellValueFactory(new PropertyValueFactory<>("numberOfItems"));
        tblClmTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblClmAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        tblClmAction.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new ButtonCell();
            }
        });

        tblSales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblSales.setItems(null);
        tableData = FXCollections.observableArrayList();
        try {
            tableData.addAll(SaleReportsManager.getSaleportForToday());
            tblSales.setItems(tableData);
        } catch (SQLException ex) {
            Logger.getLogger(SalesReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configureSaleItemTable() {
        tblClmItemId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblClmItemName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        tblClmDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        tblClmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityBought"));
        tblClmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        tblInventory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblInventory.setItems(null);

        tableDataProduct = FXCollections.observableArrayList();
//        try {
//            tableDataProduct.addAll(ProductsInTransactionManager.getProductsList("6700"));
//            tblInventory.setItems(tableDataProduct);
//        } catch (SQLException ex) {
//            Logger.getLogger(SalesReportsController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void btnHideOnAction(ActionEvent event) {
        tblInventory.getItems().clear();
        splitPane.getItems().remove(bpRightAnchor);
    }

    @FXML
    private void btnShowRnangeOnAction(ActionEvent event) throws SQLException {
        if (dpDateFrom.getValue() == null && dpDateTo.getValue() == null) {
            dpDateFrom.requestFocus();

        } else {
            System.out.println(dpDateFrom.getValue().toString());
            System.out.println(dpDateTo.getValue().toString());
            tblSales.getItems().clear();
            tableData.addAll(SaleReportsManager.getSaleportBetweenDateRange(dpDateFrom.getValue().toString(), dpDateTo.getValue().toString()));
            tblSales.setItems(tableData);

        }
    }

    @FXML
    private void btnReprintReceiptOnAction(ActionEvent event) throws JRException, SQLException {
        showReport(selectedReceipt);

    }

    @FXML
    private void btnExportPdfOnAction(ActionEvent event) {
        try {
            exportReportPdf();
        } catch (IOException ex) {
            Logger.getLogger(SalesReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(SalesReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnExportExcelOnAction(ActionEvent event) throws JRException {
        try {
            exportReportExcel();
        } catch (IOException ex) {
            Logger.getLogger(SalesReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Define the button cell
    private class ButtonCell extends TableCell<Record, Boolean> {

        final Button editButton = new Button("");

        ButtonCell() {
            editButton.setId("btnEdit");
            editButton.setPrefWidth(31);
            editButton.setPrefHeight(31);

            //Action when the button is pressed
            editButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    if (splitPane.getItems().contains(bpRightAnchor)) {
                        SaleReports selectedInvoice = (SaleReports) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                        tblInventory.getItems().clear();
                        try {

                            tableDataProduct.addAll(ProductsInTransactionManager.getProductsList(selectedInvoice.getTicketNumber()));
                            selectedReceipt = selectedInvoice.getTicketNumber();
                        } catch (SQLException ex) {
                            Logger.getLogger(SalesReportsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        lblSaleItemDate.setText(selectedInvoice.getReceiptDate());
                        tblInventory.setItems(tableDataProduct);
                        subTotalLbl.setText(selectedInvoice.getTotal().toString());
                        lblTotal.setText(selectedInvoice.getTotal().toString());
                        lblAmountPaid.setText(selectedInvoice.getAmountPaid().toString());
                        lblBalance.setText(selectedInvoice.getBalance().toString());
                    } else {

                        splitPane.getItems().add(1, bpRightAnchor);
                        splitPane.setDividerPosition(1, 0.7);
                        SaleReports selectedInvoice = (SaleReports) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());

                        try {

                            tableDataProduct.addAll(ProductsInTransactionManager.getProductsList(selectedInvoice.getTicketNumber()));
                            selectedReceipt = selectedInvoice.getTicketNumber();
                        } catch (SQLException ex) {
                            Logger.getLogger(SalesReportsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        lblSaleItemDate.setText(selectedInvoice.getReceiptDate());
                        tblInventory.setItems(tableDataProduct);
                        subTotalLbl.setText(selectedInvoice.getTotal().toString());
                        lblTotal.setText(selectedInvoice.getTotal().toString());
                        lblAmountPaid.setText(selectedInvoice.getAmountPaid().toString());
                        lblBalance.setText(selectedInvoice.getBalance().toString());
                    }
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);

            if (!empty) {
                HBox pane = new HBox(editButton);
                pane.setSpacing(10);
                pane.setAlignment(Pos.CENTER);
                setGraphic(pane);
            } else {
                setGraphic(null);
            }
        }
    }

    public void showReport(String ticketNumber) throws JRException {
        Map parameters = new HashMap();
        parameters.put("companyName", businessData.getName());
        parameters.put("location", businessData.getLocation());
        parameters.put("mobile", businessData.getMobile());
        parameters.put("address", businessData.getAddress());
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            JasperDesign jd = null;
            String sql = "SELECT * FROM productsbyticketnumber "
                    + "JOIN salereports on productsbyticketnumber.ticketNumber = salereports.ticketNumber "
                    + "WHERE productsbyticketnumber.ticketNumber = " + ticketNumber;

            jd = JRXmlLoader.load(getClass().getResourceAsStream("/reports/ReceiptsPOS.jrxml"));
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);

            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
//            JasperViewer.viewReport(jp, false);
            JasperPrintManager.printReport(jp, false);

                Notifications notification = Notifications.create()
                        .hideAfter(Duration.seconds(6))
//                        .title("Receipt Printed")
                        .text("Receipt Printed Successfully!")
                        .graphic(null)
                        .darkStyle()
                        .position(Pos.CENTER);
                notification.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tfSearchOnKeyReleased(KeyEvent event) {
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
    }

    @FXML
    private void cbDateRangeOnAction(ActionEvent event) {
        String value = cbDateRange.getSelectionModel().getSelectedItem();

        try {
            switch (value) {
                case "Yesterday":
                    tblSales.getItems().clear();
                    tableData.addAll(SaleReportsManager.getSaleportForYesterday());
                    tblSales.setItems(tableData);
                    break;
                case "This Week":
                    tblSales.getItems().clear();
                    tableData.addAll(SaleReportsManager.getSaleportForThisWeek());
                    tblSales.setItems(tableData);
                    break;
                case "This Month":
                    tblSales.getItems().clear();
                    tableData.addAll(SaleReportsManager.getSaleportForThisMonth(getCurrentMonth()));
//                    System.out.println("0"+ (getCurrentMonth() + 1));
                    tblSales.setItems(tableData);
                    break;
                case "Last Month":
                    tblSales.getItems().clear();
                    tableData.addAll(SaleReportsManager.getSaleportForThisMonth(getCurrentMonth()));
                    tblSales.setItems(tableData);
                    break;
                case "All Sales":
                    tblSales.getItems().clear();
                    tableData.addAll(SaleReportsManager.getAllSaleReport());
                    tblSales.setItems(tableData);
                    break;
                default:
                    tblSales.getItems().clear();
                    tableData.addAll(SaleReportsManager.getSaleportForToday());
                    tblSales.setItems(tableData);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SalesReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Integer getCurrentMonth() {
        int currentMonth = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
        return currentMonth;
    }

    public void exportReportExcel() throws JRException, IOException {
        String sourceFileName = ("src\\reports\\reportSales.jasper");
        ObservableList<SaleReports> beanSaleItemList = FXCollections.observableArrayList();
        for (SaleReports model : tableData) {
            SaleReports beanSaleItem = new SaleReports();
            beanSaleItem.setReceiptDate(model.getReceiptDate());
            beanSaleItem.setTicketNumber(model.getTicketNumber());
            beanSaleItem.setSalesOutletId(model.getSalesOutletId());
            beanSaleItem.setReceiptId(model.getReceiptId());
            beanSaleItem.setNumberOfItems(model.getNumberOfItems());
            beanSaleItem.setTotal(model.getTotal());
            beanSaleItem.setModeOfPayment(model.getModeOfPayment());
            beanSaleItem.setEmployeeLastName(model.getEmployeeLastName());
            beanSaleItem.setAmountPaid(model.getAmountPaid());
            beanSaleItem.setBalance(model.getBalance());
            beanSaleItemList.add(beanSaleItem);

        }
//        for (ProductsInTransaction beanSaleItemList1 : beanSaleItemList) {
//            System.out.println(beanSaleItemList1.getProductId());
//        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanSaleItemList);
        HashMap parameters = new HashMap<>();
//        parameters.put("receiptDate", dateLocal.getText());
        System.out.print("Hello");
        try {
            JasperPrint printFileName = JasperFillManager.fillReport(
                    sourceFileName, parameters, beanColDataSource);

            if (printFileName != null) {
                String outPdfName = "SalesReport.xlsx";
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setInitialDirectory(new File("src"));

                directoryChooser.setTitle("Save File To");
                Main instance = Main.getInstance();
                File selectedDirectory = directoryChooser.showDialog(instance.getPrimaryStage());
                System.out.print(selectedDirectory);

                JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter
                exporter.setExporterInput(new SimpleExporterInput(printFileName)); // set compiled report as input exporter
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(selectedDirectory.getAbsolutePath() + "/" + outPdfName)); // set output file via path with filename
                SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                configuration.setOnePagePerSheet(false); // setup configuration
                configuration.setRemoveEmptySpaceBetweenRows(true);
                configuration.setDetectCellType(true);
                exporter.setConfiguration(configuration); // set configuration
                exporter.exportReport();
            }
//            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void exportReportPdf() throws JRException, IOException {
        String sourceFileName = ("src\\reports\\reportSales.jasper");
        ObservableList<SaleReports> beanSaleItemList = FXCollections.observableArrayList();

        for (SaleReports model : tableData) {
            System.out.println(model.getReceiptId());
            SaleReports beanSaleItem = new SaleReports();
            beanSaleItem.setReceiptDate(model.getReceiptDate());
            beanSaleItem.setTicketNumber(model.getTicketNumber());
            beanSaleItem.setSalesOutletId(model.getSalesOutletId());
            beanSaleItem.setReceiptId(model.getReceiptId());
            beanSaleItem.setNumberOfItems(model.getNumberOfItems());
            beanSaleItem.setTotal(model.getTotal());
            beanSaleItem.setModeOfPayment(model.getModeOfPayment());
            beanSaleItem.setEmployeeLastName(model.getEmployeeLastName());
            beanSaleItem.setAmountPaid(model.getAmountPaid());
            beanSaleItem.setBalance(model.getBalance());
            beanSaleItemList.add(beanSaleItem);
        }
//        for (ProductsInTransaction beanSaleItemList1 : beanSaleItemList) {
//            System.out.println(beanSaleItemList1.getProductId());
//        }

        System.out.println(sourceFileName);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanSaleItemList);
        HashMap parameters = new HashMap<>();
//        parameters.put("receiptDate", dateLocal.getText());
        try {
            JasperPrint printFileName = JasperFillManager.fillReport(
                    sourceFileName, parameters, beanColDataSource);

            if (printFileName != null) {
                String outPdfName = "SalesReport.pdf";
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setInitialDirectory(new File("src"));

                directoryChooser.setTitle("Save File To");
                Main instance = Main.getInstance();
                File selectedDirectory = directoryChooser.showDialog(instance.getPrimaryStage());
                System.out.print(selectedDirectory);

                JRPdfExporter pdfExporter = new JRPdfExporter();
                pdfExporter.setExporterInput(new SimpleExporterInput(printFileName));
                pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(selectedDirectory.getAbsolutePath() + "/" + outPdfName));
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                configuration.setCreatingBatchModeBookmarks(true);
                pdfExporter.setConfiguration(configuration);
                pdfExporter.exportReport();
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

}
