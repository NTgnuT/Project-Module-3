package model.category;

import model.account.Users;

import java.io.Serializable;
import static config.Color.*;
import static config.Color.RESET;

public class Category implements Serializable {
    private int categoryId = 1;
    private String categoryName;
    private String description;
    private boolean status = true;

    public Category() {
    }

    public Category(int categoryId, String categoryName, String description, boolean status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(BLUE +"|"+ RESET +"   %-5d   |   %-20s    |   %-60s  |   %-20s    "+ BLUE +"|"+ RESET,
                categoryId ,categoryName, description, (status ? GREEN+"Đang bán"+RESET : RED+"Ngừng bán"+RESET));
    }


}
