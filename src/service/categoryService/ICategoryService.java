package service.categoryService;

import model.category.Category;
import service.IGenericService;

public interface ICategoryService extends IGenericService<Category> {
    boolean existCategoryName (String categoryName);
}
