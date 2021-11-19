/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.InventoryValue;
import epicpos.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import tables.InventoryValueManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author KwabenaEpic
 */
public class InventoryValueController implements Initializable {

    @FXML
    private AnchorPane acContent;
    @FXML
    private BorderPane bpRoot;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnAdd;
    @FXML
    private SplitPane splitPane;
    @FXML
    private TableView<InventoryValue> tblInventory;
    @FXML
    private TableColumn<InventoryValue, Integer> tblClmItemId;
    @FXML
    private TableColumn<InventoryValue, String> tblClmItemName;
    @FXML
    private TableColumn<InventoryValue, Double> tblClmUnitPrice;
    @FXML
    private TableColumn<InventoryValue, Integer> tblClmQuantity;
    @FXML
    private TableColumn<InventoryValue, Double> tblClmTotalValue;

    private ObservableList<InventoryValue> tableData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configureTable();
    }

    private void configureTable() {
        tblClmItemId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblClmItemName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        tblClmTotalValue.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
        tblClmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblClmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        tblInventory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblInventory.setItems(null);

        tableData = FXCollections.observableArrayList();
        try {
            tableData.addAll(InventoryValueManager.getInventoryValueList());
            tblInventory.setItems(tableData);
        } catch (SQLException ex) {
            Logger.getLogger(InventoryValueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void tfSearchOnKeyReleased(KeyEvent event) {
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) throws SQLException {
        tblInventory.getItems().clear();
//        tableData.addAll(InventoryValueManager.getInventoryValueList());
//        tblInventory.setItems(tableData);

    }

    @FXML
    private void btnAddItemOnAction(ActionEvent event) {
    }

    @FXML
    private void btnExportPdfOnAction(ActionEvent event) {
        try {
            try {
                exportReportPdf();
            } catch (JRException ex) {
                Logger.getLogger(InventoryValueController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(InventoryValueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnExportExcelOnAction(ActionEvent event) {
        try {
            try {
                exportReportExcel();
            } catch (JRException ex) {
                Logger.getLogger(InventoryValueController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(InventoryValueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportReportExcel() throws JRException, IOException {
        String sourceFileName = ("src\\reports\\InventoryValue.jasper");
        ObservableList<InventoryValue> beanInventoryValueList = FXCollections.observableArrayList();
        for (InventoryValue model : tableData) {
//            System.out.println(model.getReceiptId());
            InventoryValue beanInventoryItem = new InventoryValue();
            beanInventoryItem.setProductId(model.getProductId());
            beanInventoryItem.setProductName(model.getProductName());
            beanInventoryItem.setQuantity(model.getQuantity());
            beanInventoryItem.setUnitPrice(model.getUnitPrice());
            beanInventoryItem.setTotalValue(model.getTotalValue());
            beanInventoryValueList.add(beanInventoryItem);
        }
//        for (ProductsInTransaction beanSaleItemList1 : beanSaleItemList) {
//            System.out.println(beanSaleItemList1.getProductId());
//        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanInventoryValueList);
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
        String sourceFileName = ("src\\reports\\InventoryValue.jasper");
        ObservableList<InventoryValue> beanInventoryValueList = FXCollections.observableArrayList();

        for (InventoryValue model : tableData) {
//            System.out.println(model.getReceiptId());
            InventoryValue beanInventoryItem = new InventoryValue();
            beanInventoryItem.setProductId(model.getProductId());
            beanInventoryItem.setProductName(model.getProductName());
            beanInventoryItem.setQuantity(model.getQuantity());
            beanInventoryItem.setUnitPrice(model.getUnitPrice());
            beanInventoryItem.setTotalValue(model.getTotalValue());
            beanInventoryValueList.add(beanInventoryItem);
        }

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanInventoryValueList);
        HashMap parameters = new HashMap<>();
//        parameters.put("receiptDate", dateLocal.getText());
        try {
            JasperPrint printFileName = JasperFillManager.fillReport(
                    sourceFileName, parameters, beanColDataSource);

            if (printFileName != null) {
                String outPdfName = "InventoryValue.pdf";
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
