package service.category;

import config.Config;
import model.category.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceIMPL implements ICategoryService{
    static Config<List<Category>> config = new Config<>();
    public static List<Category> categoryList;
    static {
        categoryList = config.readFile(Config.URL_CATEGORY);
        if (categoryList == null) {
            categoryList = new ArrayList<>();
        }
    }

    @Override
    public List<Category> findAll() {
        return categoryList;
    }

    @Override
    public void save(Category category) {
        if (findById(category.getCategoryId()) == null) {
            categoryList.add(category);
            updateData();
        } else {
            categoryList.set(categoryList.indexOf(category), category);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        categoryList.remove(findById(id));
        updateData();
    }

    @Override
    public Category findById(int id) {
        for (Category category : categoryList) {
            if (category.getCategoryId() == id) {
                return category;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Category category : categoryList) {
            if (category.getCategoryId() > idMax) {
                idMax = category.getCategoryId();
            }
        }
        return idMax + 1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_CATEGORY, categoryList);
    }

    @Override
    public Category findByName(String name) {
        for (Category category : categoryList) {
            if (category.getCategoryName().toLowerCase().contains(name.toLowerCase())) {
                return category;
            }
        }
        return null;
    }

    @Override
    public boolean existCategoryName(String categoryName) {
        for (Category category : categoryList) {
            if (category.getCategoryName().equals(categoryName)) {
                return true;
            }
        }
        return false;
    }
}
