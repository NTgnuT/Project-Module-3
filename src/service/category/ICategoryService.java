package service.category;

import model.category.Category;
import service.IGenericService;

import java.util.Locale;

public interface ICategoryService extends IGenericService<Category> {
    boolean existCategoryName (String categoryName);
}
