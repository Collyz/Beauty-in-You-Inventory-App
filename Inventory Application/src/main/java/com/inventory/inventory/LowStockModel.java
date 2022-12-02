package com.inventory.inventory;

public class LowStockModel {
    private Integer prodID;
    private String prodName;
    private Integer quantity;

    public LowStockModel(Integer prodID, String prodName, Integer quantity) {
        this.prodID = prodID;
        this.prodName = prodName;
        this.quantity = quantity;
    }

    public Integer getProdID() {
        return prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProdID(Integer prodID) {
        this.prodID = prodID;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
