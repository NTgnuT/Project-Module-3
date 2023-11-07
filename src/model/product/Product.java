package model.product;

import config.Format;
import model.category.Category;

import java.io.Serializable;

import static config.Format.formatMoney;
import static config.Color.*;
import static config.Color.RESET;

public class Product implements Serializable {
    private int productId = 1;
    private String productName;
    private Category category;
    private String description;
    private double unitPrice;
    private int stock;
    private boolean status = true;

    public Product() {
    }

    public Product(int productId, String productName, Category category, String description, double unitPrice, int stock, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(BLUE+"|"+RESET+"   %-5d   |     %-22s   |   %-20s   |   %-30s   |   %-14s   |   %-5d   |   %-10s   "+BLUE+"|"+RESET,
                productId, productName, category.getCategoryName(), description, formatMoney(unitPrice), stock, (status ? GREEN+"Còn hàng"+RESET : RED+"Hết hàng"+RESET));
    }
}
