package com.inventory.inventory;

public class LowStockModel {
    private Integer productID;
    private String productName;
    private Integer quantity;

    public LowStockModel(Integer prodID, String productName, Integer quantity) {
        this.productID = prodID;
        this.productName = productName;
        this.quantity = quantity;
    }

    public Integer getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
