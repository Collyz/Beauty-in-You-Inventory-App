package com.inventory.inventory;

public class ProductTableModel {
    private Integer id;
    private String category;
    private String name;
    private Integer quantity;
    private Double price;
    private Integer shelfLife;

    public ProductTableModel(Integer id, String category, String name, Integer quantity, Double price, Integer shelfLife) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.shelfLife = shelfLife;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
    }
}
