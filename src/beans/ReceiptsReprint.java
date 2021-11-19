/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javafx.beans.property.*;

/**
 *
 * @author KwabenaEpic
 */
public class ReceiptsReprint {

    private final IntegerProperty productId;
    private final StringProperty productName;
    private final StringProperty productDescription;
    private final DoubleProperty unitPrice;
    private final DoubleProperty total;
    private final DoubleProperty amountPaid;
    private final DoubleProperty change;
    private final IntegerProperty quantityBought;
    private final StringProperty ticketNumber;
    private final StringProperty receiptDate;
//    private final StringProperty ticketNumber;

    public ReceiptsReprint() {
        this.productId = new SimpleIntegerProperty();
        this.productName = new SimpleStringProperty();
        this.productDescription = new SimpleStringProperty();
        this.unitPrice = new SimpleDoubleProperty();
        this.total = new SimpleDoubleProperty();
        this.amountPaid = new SimpleDoubleProperty();
        this.change = new SimpleDoubleProperty();
        this.quantityBought = new SimpleIntegerProperty();
        this.ticketNumber = new SimpleStringProperty();
        this.receiptDate = new SimpleStringProperty();
    }

    public final double getChange() {
        return change.get();
    }

    public final void setChange(double value) {
        change.set(value);
    }

    public DoubleProperty changeProperty() {
        return change;
    }

    public final double getAmountPaid() {
        return amountPaid.get();
    }

    public final void setAmountPaid(double value) {
        amountPaid.set(value);
    }

    public DoubleProperty amountPaidProperty() {
        return amountPaid;
    }

    public final int getProductId() {
        return productId.get();
    }

    public final void setProductId(int value) {
        productId.set(value);
    }

    public IntegerProperty productIdProperty() {
        return productId;
    }

    public final String getProductName() {
        return productName.get();
    }

    public final void setProductName(String value) {
        productName.set(value);
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public final String getProductDescription() {
        return productDescription.get();
    }

    public final void setProductDescription(String value) {
        productDescription.set(value);
    }

    public StringProperty productDescriptionProperty() {
        return productDescription;
    }

    public final double getUnitPrice() {
        return unitPrice.get();
    }

    public final void setUnitPrice(double value) {
        unitPrice.set(value);
    }

    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public final double getTotal() {
        return total.get();
    }

    public final void setTotal(double value) {
        total.set(value);
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public final int getQuantityBought() {
        return quantityBought.get();
    }

    public final void setQuantityBought(int value) {
        quantityBought.set(value);
    }

    public IntegerProperty quantityBoughtProperty() {
        return quantityBought;
    }

    public final String getTicketNumber() {
        return ticketNumber.get();
    }

    public final void setTicketNumber(String value) {
        ticketNumber.set(value);
    }

    public StringProperty ticketNumberProperty() {
        return ticketNumber;
    }

    public final String getReceiptDate() {
        return receiptDate.get();
    }

    public final void setReceiptDate(String value) {
        receiptDate.set(value);
    }

    public StringProperty receiptDateProperty() {
        return receiptDate;
    }

}
