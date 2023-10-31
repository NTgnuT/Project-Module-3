package model.category;

import model.account.Users;

import java.io.Serializable;

public class Category implements Serializable {
    private int categoryId = 1;
    private String categoryName;
    private boolean status = true;

    public Category() {
    }

    public Category(int categoryId, String categoryName, boolean status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", status=" + (status ? "Đang bán" : "Ngừng bán") +
                '}';
    }

//    @Override
//    public boolean equals(Object obj) {
//        Category c = (Category) obj;
//        return this.categoryId = c.getCategoryId();
//    }
}
