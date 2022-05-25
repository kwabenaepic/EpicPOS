/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author KwabenaEpic
 */
public class ProductsInTransaction {

    private final IntegerProperty productId;
    private final StringProperty productName;
    private final StringProperty productDescription;
    private final DoubleProperty unitPrice;
    private final IntegerProperty quantityBought;
    private final StringProperty ticketNumber;

    public String getSize() {
        return size.get();
    }

    public StringProperty sizeProperty() {
        return size;
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    private final StringProperty size;
    private final StringProperty receiptDate;
    private final DoubleProperty amountPaid;
    private final DoubleProperty change;
    private final DoubleProperty total;

    public final void setTotal(Double value) {
        total.set(value);
    }

    public final Double getTotal() {
        return total.get();
    }

    public final DoubleProperty totalProperty() {
        return total;
    }

    public final void setProductId(Integer value) {
        productId.set(value);
    }

    public final Integer getProductId() {
        return productId.get();
    }

    public final IntegerProperty productIdProperty() {
        return productId;
    }

    public final void setProductName(String value) {
        productName.set(value);
    }

    public final String getProductName() {
        return productName.get();
    }

    public final StringProperty productNameProperty() {
        return productName;
    }

    public final void setProductDescription(String value) {
        productDescription.set(value);
    }

    public final String getProductDescription() {
        return productDescription.get();
    }

    public final StringProperty productDescriptionProperty() {
        return productDescription;
    }

    public final void setUnitPrice(Double value) {
        unitPrice.set(value);
    }

    public final Double getUnitPrice() {
        return unitPrice.get();
    }

    public final DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public final void setQuantityBought(Integer value) {
        quantityBought.set(value);
    }

    public final Integer getQuantityBought() {
        return quantityBought.get();
    }

    public final IntegerProperty quantityBoughtProperty() {
        return quantityBought;
    }

    public final void setTicketNumber(String value) {
        ticketNumber.set(value);
    }

    public final String getTicketNumber() {
        return ticketNumber.get();
    }

    public final StringProperty ticketNumberProperty() {
        return ticketNumber;
    }

    public final void setReceiptDate(String value) {
        receiptDate.set(value);
    }

    public final String getReceiptDate() {
        return receiptDate.get();
    }

    public final StringProperty receiptDateProperty() {
        return receiptDate;
    }

    public final void setAmountPaid(Double value) {
        amountPaid.set(value);
    }

    public final Double getAmountPaid() {
        return amountPaid.get();
    }

    public final DoubleProperty amountPaidProperty() {
        return amountPaid;
    }

    public final void setChange(Double value) {
        change.set(value);
    }

    public final Double getChange() {
        return change.get();
    }

    public final DoubleProperty changeProperty() {
        return change;
    }

    public ProductsInTransaction() {
        this.productId = new SimpleIntegerProperty();
        this.productName = new SimpleStringProperty();
        this.productDescription = new SimpleStringProperty();
        this.size = new SimpleStringProperty();
        this.unitPrice = new SimpleDoubleProperty();
        this.quantityBought = new SimpleIntegerProperty();
        this.ticketNumber = new SimpleStringProperty();
        this.receiptDate = new SimpleStringProperty();
        this.amountPaid = new SimpleDoubleProperty();
        this.change = new SimpleDoubleProperty();
        this.total = new SimpleDoubleProperty();
    }

}
