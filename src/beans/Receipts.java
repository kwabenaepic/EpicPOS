
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
public class Receipts {
    private final IntegerProperty receiptId;
    private final StringProperty  customerId;
    private final StringProperty  receiptDate;
    private final IntegerProperty employeeId;
    private final StringProperty  modeOfPayment;
    private final StringProperty  salesOutletId;
    private final StringProperty  ticketNumber;
    private final DoubleProperty  amountPaid;
    private final DoubleProperty  balance;

    public Receipts() {
        this.receiptId     = new SimpleIntegerProperty();
        this.customerId    = new SimpleStringProperty();
        this.receiptDate   = new SimpleStringProperty();
        this.employeeId    = new SimpleIntegerProperty();
        this.modeOfPayment = new SimpleStringProperty();
        this.salesOutletId = new SimpleStringProperty();
        this.ticketNumber  = new SimpleStringProperty();
        this.amountPaid    = new SimpleDoubleProperty();
        this.balance       = new SimpleDoubleProperty();
    }

    public final DoubleProperty amountPaidProperty() {
        return amountPaid;
    }

    public final DoubleProperty balanceProperty() {
        return balance;
    }

    public final StringProperty customerIdProperty() {
        return customerId;
    }

    public final IntegerProperty employeeIdProperty() {
        return employeeId;
    }

    public final StringProperty modeOfPaymentProperty() {
        return modeOfPayment;
    }

    public final StringProperty receiptDateProperty() {
        return receiptDate;
    }

    public final IntegerProperty receiptIdProperty() {
        return receiptId;
    }

    public final StringProperty salesOutletIdProperty() {
        return salesOutletId;
    }

    public final StringProperty ticketNumberProperty() {
        return ticketNumber;
    }

    public final Double getAmountPaid() {
        return amountPaid.get();
    }

    public final void setAmountPaid(Double value) {
        amountPaid.set(value);
    }

    public final Double getBalance() {
        return balance.get();
    }

    public final void setBalance(Double value) {
        balance.set(value);
    }

    public final String getCustomerId() {
        return customerId.get();
    }

    public final void setCustomerId(String value) {
        customerId.set(value);
    }

    public final Integer getEmployeeId() {
        return employeeId.get();
    }

    public final void setEmployeeId(Integer value) {
        employeeId.set(value);
    }

    public final String getModeOfPayment() {
        return modeOfPayment.get();
    }

    public final void setModeOfPayment(String value) {
        modeOfPayment.set(value);
    }

    public final String getReceiptDate() {
        return receiptDate.get();
    }

    public final void setReceiptDate(String value) {
        receiptDate.set(value);
    }

    public final Integer getReceiptId() {
        return receiptId.get();
    }

    public final void setReceiptId(Integer value) {
        receiptId.set(value);
    }

    public final String getSalesOutletId() {
        return salesOutletId.get();
    }

    public final void setSalesOutletId(String value) {
        salesOutletId.set(value);
    }

    public final String getTicketNumber() {
        return ticketNumber.get();
    }

    public final void setTicketNumber(String value) {
        ticketNumber.set(value);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
