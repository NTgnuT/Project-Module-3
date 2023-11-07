package service.productService;

import model.product.Product;
import service.IGenericService;

public interface IProductService extends IGenericService<Product> {
    boolean existProductName (String productName);
}
