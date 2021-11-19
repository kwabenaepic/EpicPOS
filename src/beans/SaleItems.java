/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javafx.beans.property.*;

public class SaleItems {

    private final IntegerProperty saleId;
    private final StringProperty ticketNumber;
    private final IntegerProperty productId;
    private final IntegerProperty quantity;

    public SaleItems() {
        this.saleId = new SimpleIntegerProperty();
        this.productId = new SimpleIntegerProperty();
        this.quantity = new SimpleIntegerProperty();
        this.ticketNumber = new SimpleStringProperty();

    }

    public final void setSaleId(Integer value) {
        saleId.set(value);
    }

    public final Integer getSaleId() {
        return saleId.get();
    }

    public final IntegerProperty saleIdProperty() {
        return saleId;
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

    public final void setProductId(Integer value) {
        productId.set(value);
    }

    public final Integer getProductId() {
        return productId.get();
    }

    public final IntegerProperty productIdProperty() {
        return productId;
    }

    public final void setQuantity(Integer value) {
        quantity.set(value);
    }

    public final Integer getQuantity() {
        return quantity.get();
    }

    public final IntegerProperty quantityProperty() {
        return quantity;
    }

}
