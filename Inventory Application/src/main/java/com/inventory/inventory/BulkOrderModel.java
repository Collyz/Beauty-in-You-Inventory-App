package com.inventory.inventory;

public class BulkOrderModel {
    private Integer orderID;
    private String customerName;
    private String date;
    private String productName;
    private Integer quantity;
    private Double cost;
    private Double tax;
    private Double Total;

    public BulkOrderModel(Integer orderID, String customerName, String date, String productName, Integer quantity, Double cost, Double tax, Double total) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.date = date;
        this.productName = productName;
        this.quantity = quantity;
        this.cost = cost;
        this.tax = tax;
        Total = total;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }
}
