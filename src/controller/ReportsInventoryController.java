/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Product;
import com.sun.prism.impl.Disposer.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tables.ProductManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author KwabenaEpic
 */
public class ReportsInventoryController implements Initializable {
    @FXML
    public TextField tfCategory;
    @FXML
    private AnchorPane acContent;
    @FXML
    private ComboBox<?> cbTankId;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnAdd;
    @FXML
    private TableView<Product> tblInventory;

    @FXML
    private TableColumn<Product, String> tblClmItemName;
    @FXML
    private TableColumn<Product, String> tblClmDescription;
    @FXML
    private TableColumn<Product, Double> tblClmCostPrice;
    @FXML
    private TableColumn<Product, Integer> tblClmQuantity;
    @FXML
    private TableColumn<Product, Integer> tblClmReorderLevel;
    @FXML
    private TableColumn<Product, String> tblClmCategory;
    @FXML
    private TableColumn<Product, Integer> tblClmItemId;
    @FXML
    private TableColumn<Record, Boolean> tblClmAction;
    @FXML
    private TableColumn<Product, String> tblClmVendorId;
    @FXML
    private TableColumn<Product, Double> tblClmUnitPrice;
    @FXML
    private TableColumn<Product, String> tblClmAttribute;
    @FXML
    private TableColumn<Product, String> tblClmSize;
    @FXML
    private TableColumn<Product, String> tblClmALU;
    @FXML
    private TableColumn<Product, String> tblClmUPC;
    private ObservableList<Product> tableData = FXCollections.observableArrayList();
    @FXML
    private TextField tfItemName;
    @FXML
    private ComboBox<?> cbDepartment;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfAttribute;
    @FXML
    private TextField tfSize;
    @FXML
    private TextField tfCostPrice;
    @FXML
    private TextField tfUnitPrice;
    @FXML
    private TextField tfQuantity;
    @FXML
    private TextField tfUPC;
    @FXML
    private BorderPane bpRoot;
    @FXML
    private AnchorPane bpRightAnchor;
    @FXML
    private SplitPane splitPane;
    @FXML
    private ComboBox<?> cbVendor;
    @FXML
    private TextField tfOrderCost;
    @FXML
    private TextField tfReorderPoint;
    @FXML
    private TextField tfItemNumber;
    @FXML
    private TextField tfALU;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCancel;
    private ObservableList<Product> masterData;
    @FXML
    private Button btnHide;

    private int productId;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
//        bpRightAnchor.setMinWidth(0);
        splitPane.getItems().remove(bpRightAnchor);

        configureTable();

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Product> filteredData = new FilteredList<>(tableData, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (product.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tblInventory.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tblInventory.setItems(sortedData);


    }

    @FXML
    private void cbAddDailyTankReconByTankIdOnAction(ActionEvent event) {
    }

    @FXML
    private void tfSearchOnKeyReleased(KeyEvent event) {
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) throws SQLException {

        refreshTable();

    }

    private void refreshTable() throws SQLException {
        tableData.clear();
        tableData.addAll(ProductManager.getProductsList());
        tblInventory.setItems(tableData);
    }


    private void configureTable() {
        tblClmItemId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblClmItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        //Turned off, client requested it
        tblClmCostPrice.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        tblClmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblClmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblClmReorderLevel.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));
        tblClmCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tblClmVendorId.setCellValueFactory(new PropertyValueFactory<>("vendorCode"));
//        tblClmAttribute.setCellValueFactory(new PropertyValueFactory<>("attribute"));
        tblClmSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        tblClmALU.setCellValueFactory(new PropertyValueFactory<>("ALU"));
        tblClmUPC.setCellValueFactory(new PropertyValueFactory<>("UPC"));
//        tblClmAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

//            @Override
//            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
//                return new SimpleBooleanProperty(p.getValue() != null);
//            }
//        });
//        //Adding the Button to the cell
//        tblClmAction.setCellFactory(
//                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
//
//            @Override
//            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
//                return new ButtonCell();
//            }
//
//        });

        // In order to limit the amount of setup in Getting Started we set the width
        // of the 3 columns programmatically but one can do it from SceneBuilder.
        tblClmItemId.setPrefWidth(40);

        tblClmItemName.setPrefWidth(100);
//
        tblClmItemId.setMinWidth(40);

        tblClmItemName.setMinWidth(100);
//
        tblClmItemId.setMaxWidth(100);

        tblInventory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblInventory.setItems(null);
//
        tableData = FXCollections.observableArrayList();
        try {
            tableData.addAll(ProductManager.getProductsList());
            tblInventory.setItems(tableData);
        } catch (SQLException ex) {
            Logger.getLogger(ReportsInventoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void btnHideEditOnAction(ActionEvent event) {
//        splitPane.setDividerPositions(0.8);
        splitPane.getItems().add(1, bpRightAnchor);
        splitPane.setDividerPosition(1, 0.8);
    }

    @FXML
    private void cbDepartmentOnAction(ActionEvent event) {
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) throws SQLException {
        Product bean = ProductManager.getRow(Integer.parseInt(tfItemNumber.getText()));
        if (bean != null) {

            bean.setName(tfItemName.getText());
            bean.setDescription(taDescription.getText());
            bean.setCostPrice(Double.parseDouble(tfCostPrice.getText()));
            bean.setUnitPrice(Double.parseDouble(tfUnitPrice.getText()));
            bean.setQuantity(Integer.parseInt(tfQuantity.getText()));
            bean.setReorderLevel(Integer.parseInt(tfReorderPoint.getText()));

            try {
                boolean result = ProductManager.update(bean);
//                System.out.println("Update was" + result);
                if (result == true) {
                    tableData.clear();
                    tableData.addAll(ProductManager.getProductsList());
                    tblInventory.setItems(tableData);
//            showNotification();

                    Notifications notification = Notifications.create()
                            .hideAfter(Duration.seconds(5))
                            .title("Product Update")
                            .text("Update Successfull!")
                            .graphic(null)
                            .darkStyle()
                            .position(Pos.CENTER);
                    notification.show();
                }

            } catch (Exception ex) {
                Logger.getLogger(ReportsInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.err.println("Row not found");
            return;
        }
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
    }

    @FXML
    private void btnHideOnAction(ActionEvent event) {
        splitPane.getItems().remove(bpRightAnchor);

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
                    if (splitPane.getItems().contains(bpRightAnchor)) {
                        Product currentProduct = (Product) ReportsInventoryController.ButtonCell.this.getTableView().getItems().get(ReportsInventoryController.ButtonCell.this.getIndex());
                        tfItemName.setText(currentProduct.getName());
                        taDescription.setText(currentProduct.getDescription());
                        tfCategory.setText(currentProduct.getCategory());
                        //Turned off, client requested it
//                        tfCostPrice.setText(currentProduct.getCostPrice().toString());
                        tfUnitPrice.setText(currentProduct.getUnitPrice().toString());
                        tfQuantity.setText(currentProduct.getQuantity().toString());
                        tfReorderPoint.setText(currentProduct.getReorderLevel().toString());
                        tfItemNumber.setText(currentProduct.getProductId().toString());

                    } else {

                        splitPane.getItems().add(1, bpRightAnchor);
                        splitPane.setDividerPosition(2, 0.8);
                        Product currentProduct = (Product) ReportsInventoryController.ButtonCell.this.getTableView().getItems().get(ReportsInventoryController.ButtonCell.this.getIndex());
                        tfItemName.setText(currentProduct.getName());
                        taDescription.setText(currentProduct.getDescription());
                        tfCategory.setText(currentProduct.getCategory());
                        //Turned off, client requested it
//                        tfCostPrice.setText(currentProduct.getCostPrice().toString());
                        tfUnitPrice.setText(currentProduct.getUnitPrice().toString());
                        tfQuantity.setText(currentProduct.getQuantity().toString());
                        tfReorderPoint.setText(currentProduct.getReorderLevel().toString());
                        tfItemNumber.setText(currentProduct.getProductId().toString());
                    }
                }
            });

            //Action when delete button is pressed
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    Product currentProduct = (Product) ReportsInventoryController.ButtonCell.this.getTableView().getItems().get(ReportsInventoryController.ButtonCell.this.getIndex());

                    try {
                        ProductManager.deleteProduct(currentProduct.getProductId());
                        tableData.clear();
                        tableData.addAll(ProductManager.getProductsList());
                        tblInventory.setItems(tableData);
                    } catch (SQLException e) {
                        e.printStackTrace();
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

}
