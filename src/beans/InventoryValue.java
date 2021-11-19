/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javafx.beans.property.*;

public class InventoryValue {

    private final IntegerProperty productId;
    private final StringProperty productName;
    private final IntegerProperty quantity;
    private final DoubleProperty unitPrice;
    private final DoubleProperty totalValue;

    public InventoryValue() {

        this.productId = new SimpleIntegerProperty();
        this.productName = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();
        this.unitPrice = new SimpleDoubleProperty();
        this.totalValue = new SimpleDoubleProperty();
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

    public final double getTotalValue() {
        return totalValue.get();
    }

    public final void setTotalValue(double value) {
        totalValue.set(value);
    }

    public DoubleProperty totalValueProperty() {
        return totalValue;
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

    public final int getQuantity() {
        return quantity.get();
    }

    public final void setQuantity(int value) {
        quantity.set(value);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
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

}
