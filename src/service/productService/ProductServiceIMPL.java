package service.productService;

import config.Config;
import model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceIMPL implements IProductService {
    static Config<List<Product>> config = new Config<>();
    public static List<Product> productList;
    static {
        productList = config.readFile(Config.URL_PRODUCT);
        if (productList == null) {
            productList = new ArrayList<>();
        }
    }


    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        // Kiểm tra sản phẩm có tồn tại trong danh sách sản phẩm hay không
        if (findById(product.getProductId()) == null ){
            // Nếu chưa tồn tại thì thêm mới
            productList.add(product);
            updateData();
        } else {
            // Nếu đã tồn tại thì set lại sản phẩm mới (update)
            productList.set(productList.indexOf(product), product);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        productList.remove(findById(id));
        updateData();
    }

    @Override
    public Product findById(int id) {
        for (Product product : productList) {
            if (product.getProductId() == id){
                return product;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Product product : productList) {
            if (product.getProductId() > idMax) {
                idMax = product.getProductId();
            }
        }
        return idMax + 1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_PRODUCT, productList);
    }

    @Override
    public Product findByName(String name) {
        for (Product product : productList) {
           if (product.getProductName().contains(name)) {
               return product;
           }
        }
        return null;
    }

    @Override
    public boolean existProductName(String productName) {
        for (Product product : productList) {
            if (product.getProductName().equals(productName)) {
                return true;
            }
        }
        return false;
    }
}
