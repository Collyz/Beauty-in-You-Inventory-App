package com.inventory.inventory;

public class ProductTableModel {
    private Integer ID;
    private String category;
    private String name;
    private Integer quantity;
    private Double price;
    private Integer life;

    public ProductTableModel(Integer ID, String productCategory, String name, Integer quantity, Double price, Integer life) {
        this.ID = ID;
        this.category = productCategory;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.life = life;
    }

    public Integer getID() {
        return ID;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getLife() {
        return life;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setCategory(String productCategory) {
        this.category = productCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

}
