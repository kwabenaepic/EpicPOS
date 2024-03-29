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
public class Product {

    public final void setCategory(String value) {
        category.set(value);
    }

    public final String getCategory() {
        return category.get();
    }

    public final StringProperty categoryProperty() {
        return category;
    }

    public final void setCostPrice(Double value) {
        costPrice.set(value);
    }

    public final Double getCostPrice() {
        return costPrice.get();
    }

    public final DoubleProperty costPriceProperty() {
        return costPrice;
    }

    public final void setDescription(String value) {
        description.set(value);
    }

    public final String getDescription() {
        return description.get();
    }

    public final StringProperty descriptionProperty() {
        return description;
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

//    public final void setProductNumber(String value) {
//        productNumber.set(value);
//    }
//
//    public final String getProductNumber() {
//        return productNumber.get();
//    }
//
//    public final StringProperty productNumberProperty() {
//        return productNumber;
//    }
    public final void setName(String value) {
        name.set(value);
    }

    public final String getName() {
        return name.get();
    }

    public final StringProperty nameProperty() {
        return name;
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

    public final void setReorderLevel(Integer value) {
        reorderLevel.set(value);
    }

    public final Integer getReorderLevel() {
        return reorderLevel.get();
    }

    public final IntegerProperty reorderLevelProperty() {
        return reorderLevel;
    }

//    public final void setSuppleirId(String value) {
//        suppleirId.set(value);
//    }
//
//    public final String getSuppleirId() {
//        return suppleirId.get();
//    }
//
//    public final StringProperty suppleirIdProperty() {
//        return suppleirId;
//    }
    public final void setUnitPrice(Double value) {
        unitPrice.set(value);
    }

    public final Double getUnitPrice() {
        return unitPrice.get();
    }

    public final DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

//    public final void setAttribute(String value) {
//        attribute.set(value);
//    }
//
//    public final String getAttribute() {
//        return attribute.get();
//    }
//
//    public final StringProperty attributeProperty() {
//        return attribute;
//    }
    public final void setSize(String value) {
        size.set(value);
    }

    public final String getSize() {
        return size.get();
    }

    public final StringProperty sizeProperty() {
        return size;
    }
    public final void setALU(String value) {
        ALU.set(value);
    }

    public final String getALU() {
        return ALU.get();
    }

    public final StringProperty ALUProperty() {
        return ALU;
    }

    public final void setUPC(String value) {
        UPC.set(value);
    }

    public final String getUPC() {
        return UPC.get();
    }

    public final StringProperty UPCProperty() {
        return UPC;
    }

    public final void setVendorCode(String value) {
        vendorCode.set(value);
    }

    public final String getVendorCode() {
        return vendorCode.get();
    }

    public final StringProperty vendorCodeProperty() {
        return vendorCode;
    }

    private final StringProperty category;
    private final DoubleProperty costPrice;
    private final StringProperty description;
    private final IntegerProperty productId;
    private final StringProperty name;
    private final IntegerProperty quantity;
    private final IntegerProperty reorderLevel;
//    private final StringProperty suppleirId;
    private final DoubleProperty unitPrice;
//    private final StringProperty attribute;
    private final StringProperty size;
    private final StringProperty ALU;
    private final StringProperty UPC;
    private final StringProperty vendorCode;

    public Product() {
        this.category = new SimpleStringProperty();
        this.costPrice = new SimpleDoubleProperty();
        this.description = new SimpleStringProperty();
        this.productId = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();
        this.reorderLevel = new SimpleIntegerProperty();
//        this.suppleirId = new SimpleStringProperty();
        this.unitPrice = new SimpleDoubleProperty();
//        this.attribute = new SimpleStringProperty();
        this.size = new SimpleStringProperty();
        this.ALU = new SimpleStringProperty();
        this.UPC = new SimpleStringProperty();
        this.vendorCode = new SimpleStringProperty();
    }

}
