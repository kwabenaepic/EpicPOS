package controller;

import beans.*;
import com.sun.prism.impl.Disposer.Record;
import database.ConnectionManager;
import epicpos.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.dialog.ProgressDialog;
import tables.*;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//
///**
// *
// * @author KwabenaEpic
// */
public class PosClientController implements Initializable {
    @FXML
    private Button btnTenderMomo;
    private Integer employeeId;
    private String securityGroup;

//    ObservableList<String> entries = FXCollections.observableArrayList();
    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Integer> itemIdColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, String> sizeColumn;

    private ObservableList<Product> data, tableItems;

    private ObservableList<String> entries;
//    final ObservableList<Product> tableContent = FXCollections.observableArrayList();
    private int currentTicket;
    private Double qty = 0.0;
    @FXML
    private Label dateLocal;
    @FXML
    private Label subTotalLbl;
    @FXML
    private Label taxLbl;
    @FXML
    private Label ticketNumberLbl;

    private Main application;
    private static ObservableList<Employee> staff;
    @FXML
    private Label staffNameLbl;
    @FXML
    private TableColumn<Record, Boolean> actionColumn;
    @FXML
    private Label lblBalance;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnDiscount;
    @FXML
    private Button btnHoldSale;
    @FXML
    private Label lblDiscount;
    @FXML
    private Label lblTotal;
    @FXML
    private CustomTextField tfFieldSearch;

    HashMap<Integer, Double> saleItems = new HashMap();
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private Label lblHour;
    @FXML
    private Label dateLocal1;
    @FXML
    private Label lblMinutes;
    @FXML
    private Label dateLocal11;
    @FXML
    private Label lblSeconds;
    @FXML
    private ImageView ivUserImage;
    @FXML
    private Button btnCheckout;

    private InetAddress ip;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnTenderCash;
    private String tenderType;
    @FXML
    private Label lblAmountPaid;
    @FXML
    private Button btnTenderCard;
    @FXML
    private Button btndashboard;

    SimpleDoubleProperty getBalance = new SimpleDoubleProperty();
    private Business businessData ;

    private Alert alert = null;
    Task copyWorker;
    ProgressDialog dialog = null;
    private BigDecimal amountPaid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            businessData = BusinessManager.getCompanyInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Runnable updater = new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                    }
//                };
//
//                while (true) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {
//                    }
//                    // UI update is run on the Application thread
//                    Platform.runLater(updater);
//                }
//            }
//
//        });
//        //don't let thread prevent JVM shutdown
//        thread.setDaemon(true);
//        thread.start();


        try {
            this.ip = InetAddress.getLocalHost();
//            System.out.println(ip.getHostName());
        } catch (UnknownHostException ex) {
            Logger.getLogger(PosClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentTicket = ticketGenerator();
        ticketNumberLbl.setText(currentTicket + "");
        btnTenderCash.setDisable(true);
        btnTenderCard.setDisable(true);
        btnTenderMomo.setDisable(true);
        btnCheckout.setDisable(true);
        disPlayDateTime();
        displayDate();
        importItemsToSearch();
        configureTable();

        AutoCompletionBinding<String> acb = TextFields.bindAutoCompletion(tfFieldSearch, entries);
        acb.setOnAutoCompleted((AutoCompletionEvent<String> event) -> {
            String valueFromAutoCompletion = event.getCompletion();
            String[] splitData = valueFromAutoCompletion.split("\\s+");
            int productId = Integer.parseInt(splitData[splitData.length - 1]);

            for (Product model : data) {
                if (model.getProductId() == productId) {
                    model.setQuantity(Integer.parseInt(showDialog()));
                    tableItems.add(model);
                    table.setItems(tableItems);
                    tfFieldSearch.setText("");
                    calcSubTotal();
                }
            }
            btnTenderCash.setDisable(false);
        });

    }

    // uses round half up, or bankers' rounding
    public static double roundToTwoPlaces(double d) {
        return Math.round(d * 100) / 100.0;
    }

    private void disPlayDateTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Calendar cal = Calendar.getInstance();
            int second = cal.get(Calendar.SECOND);
            int minute = cal.get(Calendar.MINUTE);
            int hour = cal.get(Calendar.HOUR);
//            System.out.println(hour + ":" + (minute) + ":" + second);
////            time.setText(hour + ":" + (minute) + ":" + second);
            lblHour.setText(hour + "");
            lblMinutes.setText(minute + "");
            lblSeconds.setText(second + "");
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
// get inventory items to search textfield
    private void importItemsToSearch() {
        data = FXCollections.observableArrayList();
        tableItems = FXCollections.observableArrayList();
        entries = FXCollections.observableArrayList();
        try {
//            ProductManager.getProductsList();
            data = ProductManager.getProductsList();
            for (Product next : data) {
                // add product description to search field items
                entries.add(next.getName().concat(" ").concat(next.getDescription().concat(" ").concat(next.getSize().concat(" ")).concat(next.getUnitPrice().toString()).concat(" ").concat((next.getProductId().toString()))));

                //Working
//              entries.add(next.getName().concat(" ").concat(next.getDescription().concat(" ").concat(next.getUnitPrice().toString()).concat(" ").concat((next.getProductId().toString()))));
//
//                entries.add(next.getName().concat(" ").concat(next.getUnitPrice().toString()).concat(" ").concat((next.getProductId().toString())));
//
            }
        } catch (SQLException ex) {
            Logger.getLogger(PosClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayDate() {
        String s;
        Format formatter;
        Date date = new Date();
        // 29-Jan-02
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        s = formatter.format(date);
        dateLocal.setText(s);
    }

    public void calcSubTotal() {
        int rowIndex = table.getItems().size() - 1;
        Product rowList = table.getItems().get(rowIndex);
        double price = rowList.getUnitPrice();
        double quant = rowList.getQuantity();
        double result = price * quant;
        this.qty += result;
        saleItems.put(rowList.getProductId(), result);
        reSetItems();
    }

    private void reSetItems() {
        Double doubleSum = 0.0;
        for (Double d : saleItems.values()) {
            doubleSum += d;
        }
        subTotalLbl.setText(doubleSum + "");
        lblTotal.setText(doubleSum + "");
//        lblAmountPaid.setText(doubleSum + "");
    }

    private void onEditSaleItemsQty() {
        Double doubleSum = 0.0;
        for (Double d : saleItems.values()) {
            doubleSum += d;
        }
        subTotalLbl.setText(doubleSum + "");
        lblTotal.setText(doubleSum + "");
    }

    private String showDialog() {
        String qty = null;
        TextInputDialog dialog = new TextInputDialog("Add Quantity");
        dialog.setTitle("Add Quantity");
//        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Enter Quantity: ");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            qty = result.get();
        } else {
            tfFieldSearch.setText("");
        }
        return qty;
    }

    private String showDialogCash() {
        String qty = null;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Cash Paid");
        dialog.setHeaderText(null);
        dialog.setContentText("Cash Paid: ");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            qty = result.get();
        }
        amountPaid = new BigDecimal(qty);
        return qty;
    }

    public static int ticketGenerator() {
        Random rand = new Random();
        return rand.nextInt((100000 - 1) + 1) + 1;
    }

    private void enterReceipts() throws Exception {
        Receipts bean = new Receipts();
        bean.setEmployeeId(employeeId);
        bean.setTicketNumber(currentTicket + "");
        bean.setCustomerId(currentTicket + "");
        bean.setSalesOutletId(ip.getHostName());
        bean.setModeOfPayment(tenderType);
        bean.setAmountPaid(Double.parseDouble(lblAmountPaid.getText()));
        bean.setBalance(roundToTwoPlaces(Double.parseDouble(lblBalance.getText())));
        boolean result = ReceiptsManager.insert(bean);
        if (result) {
        }

    }

    private void updateInventoryQty() throws Exception {
        Product bean = new Product();
        for (Product model : tableItems) {
            bean.setQuantity(model.getQuantity());
            bean.setProductId(model.getProductId());
            boolean result = ProductManager.updateItemQuntityOnSale(bean);
            if (result) {
//                System.out.println("Product " + bean.getProductId() + " qty was reduced by !");
            }
        }
    }

    private void enterSaleItems() throws Exception {
        SaleItems bean = new SaleItems();
        for (Product model : tableItems) {
            bean.setProductId(model.getProductId());
            bean.setQuantity(model.getQuantity());
            bean.setTicketNumber(currentTicket + "");

            boolean result = SaleItemsManager.insert(bean);
            if (result) {
//                System.out.println("New row with primary key " + bean.getSaleId() + " was inserted!");
            }
        }
    }

    private void configureTable() {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        actionColumn.setCellValueFactory(p -> new SimpleBooleanProperty(p.getValue() != null));
        //Adding the Button to the cell
        actionColumn.setCellFactory(
                p -> new ButtonCell());
        nameColumn.setPrefWidth(200);
        nameColumn.setMinWidth(200);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(null);
//        actionCol.setSortable(false);

    }
//

    public void showReport() throws JRException {
        String sourceFileName = ("src\\reports\\ReceiptsPOS.jasper");
//      String sourceFileName = ("C:/Users/hp/Documents/NetBeansProjects/EpicPOS/src/reports/ReceiptsPOS.jasper");
        String printFileName = null;
        ObservableList<ProductsInTransaction> beanSaleItemList = FXCollections.observableArrayList();

        for (Product model : tableItems) {
            ProductsInTransaction beanSaleItem = new ProductsInTransaction();
            beanSaleItem.setProductName(model.getName() + " @ " + (model.getUnitPrice()));
            beanSaleItem.setQuantityBought(model.getQuantity());
            beanSaleItem.setUnitPrice(model.getUnitPrice() * model.getQuantity());
            beanSaleItem.setUnitPrice(Double.parseDouble(subTotalLbl.getText()));
            beanSaleItem.setTicketNumber(currentTicket + "");
            beanSaleItem.setReceiptDate(dateLocal.getText());
            beanSaleItem.setAmountPaid(Double.parseDouble(lblAmountPaid.getText()));
            beanSaleItem.setChange(Double.parseDouble(lblBalance.getText()));
            beanSaleItemList.add(beanSaleItem);

        }
        for (ProductsInTransaction beanSaleItemList1 : beanSaleItemList) {
            System.out.println(beanSaleItemList1.getChange());
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanSaleItemList);
        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("receiptDate", dateLocal.getText());
        try {
            printFileName = JasperFillManager.fillReportToFile(
                    sourceFileName, parameters, beanColDataSource);
            if (printFileName != null) {

                JasperPrintManager.printReport(printFileName, true);
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void showReport2(int ticketNumber) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void checkout(ActionEvent event) throws Exception {
        enterReceipts();
        enterSaleItems();
        updateInventoryQty();
        showReport2(Integer.parseInt(ticketNumberLbl.getText()));
        prepAnotherSale();
    }

    private void resetItems() {
        subTotalLbl.setText("0.0");
        taxLbl.setText("0.0");
        lblBalance.setText("0.0");
        lblAmountPaid.setText("");
        lblTotal.setText("0.0");
        lblDiscount.setText("0.0");
        lblAmountPaid.setText("0.0");
        btnCheckout.setDisable(true);
        btnTenderCash.setDisable(true);
    }

    private void prepAnotherSale() {
        Notifications notification = Notifications.create()
                .hideAfter(Duration.seconds(6))
//                .title("New Sale")
                .text("Make a New Sale!")
                .graphic(null)
                .darkStyle()
                .position(Pos.CENTER);
        notification.show();
        subTotalLbl.setText("");
        taxLbl.setText("");
        lblBalance.setText("");
        lblAmountPaid.setText("");
        lblTotal.setText("");
        lblDiscount.setText("");
        currentTicket = ticketGenerator();
        ticketNumberLbl.setText(currentTicket + "");
        saleItems.clear();
        table.getItems().clear();
        btnCheckout.setDisable(true);
        btnTenderCash.setDisable(true);

    }

    @FXML
    private void btnClearOnAction(ActionEvent event) {
        Product product = new Product();

        btnCheckout.setDisable(true);
        btnTenderCash.setDisable(true);
        table.getItems().clear();
        saleItems.clear();
        lblTotal.setText("0.0");
        subTotalLbl.setText("0.0");
        taxLbl.setText("0.0");
        lblBalance.setText("0.0");
        lblDiscount.setText("0.0");
        lblAmountPaid.setText("0.0");

//        tableItems.removeAll(product);
    }

    @FXML
    private void btnDiscountOnAction(ActionEvent event) {
//        showDialogCash();
    }

    @FXML
    private void btnHoldSaleOnAction(ActionEvent event) {
    }

    @FXML
    private void fieldSearchOnAction(ActionEvent event) {

    }

    @FXML
    private void btnLogoutOnAction(ActionEvent event) {
//        application.getPrimaryStage().close();
        application.userLogout();
    }

    @FXML
    private void btnTenderCashOnAction(ActionEvent event) {
        tenderType = "Cash";
        while (Double.parseDouble(showDialogCash()) < Double.parseDouble(lblTotal.getText())) {
            showDialogCash();
        }
        lblBalance.setText((this.amountPaid.subtract(new BigDecimal(lblTotal.getText()))).toString());
        lblAmountPaid.setText(this.amountPaid.toString());
        btnCheckout.setDisable(false);

    }

    @FXML
    private void btnTenderCardOnAction(ActionEvent event) {

    }

    @FXML
    private void btndashboardOnAction(ActionEvent event) {
//        this.application = application;
        if (securityGroup.equals("Manager")) {
            application.gotoDashboard();
        } else {
            Notifications notification = Notifications.create()
                    .hideAfter(Duration.seconds(6))
//                .title("New Sale")
                    .text("You do not have Authorization!")
                    .graphic(null)
                    .darkStyle()
                    .position(Pos.CENTER);
            notification.show();
        }

    }

    public void btnTenderMomoOnAction(ActionEvent actionEvent) {
    }

    //Define the button cell
    private class ButtonCell extends TableCell<Record, Boolean> {

        final Button editButton = new Button("");
        final Button deleteButton = new Button("");

        ButtonCell() {
            editButton.setId("btnEdit");
            editButton.setPrefWidth(31);
            editButton.setPrefHeight(31);
            deleteButton.setId("btnDelete");
            deleteButton.setPrefWidth(31);
            deleteButton.setPrefHeight(31);
            //Action when the button is pressed

            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    Product currentProduct = (Product) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                    currentProduct.setQuantity(Integer.parseInt(showDialog()));
                    tableItems.remove(currentProduct);
//                    saleItems.remove(currentProduct.getProductId());
                    tableItems.add(currentProduct);
                    calcSubTotal();
                }
            });

            //Action when the button is pressed
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    Product currentProduct = (Product) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                    saleItems.remove(currentProduct.getProductId());
                    tableItems.remove(currentProduct);
                    reSetItems();
                    if (saleItems.isEmpty()) {
                        resetItems();
                    }
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                HBox pane = new HBox(editButton, deleteButton);
                pane.setSpacing(10);
                pane.setAlignment(Pos.CENTER);
                setGraphic(pane);
            } else {
                setGraphic(null);
            }
        }
    }

    public void setApp(Main application) {
        this.application = application;
        Employee loggedUser = application.getLoggedUser();
        employeeId = loggedUser.getEmployeeId();
        securityGroup = loggedUser.getSecurityGroup();
        staffNameLbl.setText(loggedUser.getFirstName() + " " + loggedUser.getLastName());
        if (loggedUser.getImage() != null) {
            ByteArrayInputStream byteArrayInputStream = null;
            try {
                byteArrayInputStream = new ByteArrayInputStream(loggedUser.getImage().getBytes(1, (int) loggedUser.getImage().length()));

            } catch (SQLException ex) {
                Logger.getLogger(PosClientController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
//            ivUserImage.setImage(new Image(byteArrayInputStream));
        } else {
//            ivUserImage.setImage(null);
        }
    }

    private void isValidCondition() {
//        boolean validCondition = true;
        if (dialog == null) {
//            if (result == false) {
            copyWorker = createWorker();
            dialog = new ProgressDialog(copyWorker);
            dialog.setGraphic(null);
            dialog.setContentText("Connecting to server...");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setHeaderText("");

//            dialog.initStyle(StageStyle.UTILITY);
            new Thread(copyWorker).start();
            dialog.showAndWait();

//            } else {
//                validCondition = true;
//            }
        }
//        return validCondition;
    }

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() {
                final int max = 10000000;
                updateProgress(0, max);
                for (int i = 1; i <= max; i++) {
//                    Thread.sleep(5000);
                    updateMessage("Connecting to Server...");
                    updateProgress(i, max);
                }
                return true;
            }
        };
    }

}
