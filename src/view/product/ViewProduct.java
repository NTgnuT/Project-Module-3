package view.product;

import config.Config;
import config.Validate;
import model.account.Users;
import model.category.Category;
import model.product.Product;
import service.categoryService.CategoryServiceIMPL;
import service.categoryService.ICategoryService;
import service.productService.IProductService;
import service.productService.ProductServiceIMPL;
import view.category.ViewCategory;

import java.util.ArrayList;
import java.util.List;

import static config.Color.*;
import static config.Color.RESET;

public class ViewProduct {
    IProductService iProductService = new ProductServiceIMPL();
    ICategoryService iCategoryService = new CategoryServiceIMPL();

    public void menuProductManager() {
        do {
            System.out.println(BLUE+"+------------------------------------------------------------------------+" + RESET);
            System.out.printf(BLUE+"|"+RESET+"   \uD83E\uDD7E \uD83D\uDC5F "+YELLOW_BRIGHT+"T.SHOE"+RESET+" \uD83D\uDC5F \uD83D\uDC5E                           Xin chào: %-20s \n" , new Config<Users>().readFile(Config.URL_LOGIN).getName() +" "+ BLUE + "      |");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|"+RESET+"                            QUẢN LÝ SẢN PHẨM                            "+BLUE+"|");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|"+RESET+"                  1. \uD83D\uDCD3 Hiển thị danh sách sản phẩm                     "+BLUE+"|");
            System.out.println("|"+RESET+"                  2. \u2795 Thêm mới sản phẩm                               "+BLUE+"|");
            System.out.println("|"+RESET+"                  3. \uD83D\uDD8A Chỉnh sửa thông tin sản phẩm                     "+BLUE+"|");
            System.out.println("|"+RESET+"                  4. \uD83D\uDD03 Thay đổi trạng thái sản phẩm                    "+BLUE+"|");
            System.out.println("|"+RESET+"                  5. \uD83D\uDD0D Tìm kiếm sản phẩm theo tên                      "+BLUE+"|");
            System.out.println("|"+RESET+"                  0. \u2B05 Quay lại                                         "+BLUE+"|");
            System.out.println("+------------------------------------------------------------------------+" +RESET);
            System.out.print("Nhập lựa chọn của bạn: ");

            switch (Validate.validateInt()) {
                case 1:
                    showListProduct();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
                    changeStatus();
                    break;
                case 5:
                    findProduct();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                    break;
            }
        } while (true);
    }

    private void findProduct() {
        System.out.println("Nhập tên sản phẩm bạn muốn tìm kiếm: ");
        String findName = Validate.validateString();
        boolean checkFind = false;
        System.out.println("                                                             DANH SÁCH SẢN PHẨM                       ");
        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |          TÊN SẢN PHẨM        |         DANH MỤC         |                MÔ TẢ               |         GIÁ        |  SÔ LƯỢNG |  TRẠNG THÁI  " + BLUE + "|");
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        for (Product product : iProductService.findAll()) {
            if (product.getProductName().toLowerCase().contains(findName.toLowerCase())) {
                System.out.println(product);
                checkFind = true;
            }
        }

        if(!checkFind) {
            System.out.println(BLUE +"|"+RESET+"                                                        "+RED+"Không tìm thấy sản phẩm bạn cần tìm!!!"+RESET+"                                                            "+BLUE+"|"+RESET);
        }

        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    private void changeStatus() {
        System.out.println("Nhập ID sản phẩm bạn muốn thay đổi trạng thái: ");
        int n = Validate.validateInt();
        Product productEdit = iProductService.findById(n);
        if (productEdit == null) {
            System.out.println(RED+"ID bạn muốn thay đổi trạng thái không tồn tại."+RESET);
        } else {
            productEdit.setStatus(!productEdit.isStatus());
            iProductService.save(productEdit);
            System.out.println(YELLOW+"Đổi trạng thái sản phẩm thành công"+RESET);
        }
    }

    private void editProduct() {
        System.out.println(PURPLE+"***** SỬA THÔNG TIN SẢN PHẨM *****"+RESET);
        System.out.println("Nhập ID sản phẩm bạn muốn sửa: ");
        int n = Validate.validateInt();
        Product productEdit = iProductService.findById(n);
        if (productEdit == null) {
            System.out.println(RED+"ID bạn muốn sửa không tồn tại."+RESET);
        } else {
            System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("| " + RESET + "   ID     |          TÊN SẢN PHẨM        |         DANH MỤC         |                MÔ TẢ               |         GIÁ        |  SÔ LƯỢNG |  TRẠNG THÁI  " + BLUE + "|");
            System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
            System.out.println(productEdit);
            System.out.println(BLUE+"+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);

            System.out.println(BLUE+"+------------------------------------+");
            System.out.println("|"+RESET+" 1. Sửa tên sản phẩm.               "+BLUE+"|");
            System.out.println("|"+RESET+" 2. Sửa danh mục sản phẩm.          "+BLUE+"|");
            System.out.println("|"+RESET+" 3. Sửa mô tả sản phẩm.             "+BLUE+"|");
            System.out.println("|"+RESET+" 4. Sửa giá bán sản phẩm.           "+BLUE+"|");
            System.out.println("|"+RESET+" 5. Sửa số lượng sản phẩm.          "+BLUE+"|");
            System.out.println("|"+RESET+" 0. Quay lại.                       "+BLUE+"|");
            System.out.println("+------------------------------------+"+RESET);
            System.out.println("Nhập lựa chọn của bạn: ");
            switch (Validate.validateInt()) {
                case 1:
                    System.out.println("Nhập tên mới cho sản phẩm: ");
                    while (true) {
                        String newName = Validate.validateString();
                        if (iProductService.existProductName(newName)) {
                            System.out.println(RED+"Đã có sản phẩm này, vui lòng nhập lại!!!"+RESET);
                        } else {
                            productEdit.setProductName(newName);
                            break;
                        }
                    }
                    break;
                case 2:
                    choiceCategory(productEdit);
                    break;
                case 3:
                    System.out.println("Nhập mô tả mới cho sản phẩm: ");
                    String newDes = Validate.validateString();
                    productEdit.setDescription(newDes);
                    break;
                case 4:
                    System.out.println("Nhập giá bán mới cho sản phẩm: ");
                    double newPrice = Validate.validateInt();
                    productEdit.setUnitPrice(newPrice);
                    break;
                case 5:
                    System.out.println("Nhập số lượng sản phẩm mới: ");
                    int newStock = Validate.validateInt();
                    productEdit.setStock(newStock);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED+"Lựa chọn của bạn không hợp lệ!"+RESET);
                    break;
            }
            iProductService.save(productEdit);
            System.out.println(YELLOW+"Sửa thông tin sản phẩm thành công"+RESET);
        }
    }

    private void showListProduct() {
        System.out.println("                                                             DANH SÁCH SẢN PHẨM                       ");
        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |         TÊN SẢN PHẨM         |         DANH MỤC         |                MÔ TẢ               |         GIÁ        |  SÔ LƯỢNG |  TRẠNG THÁI  " + BLUE + "|");
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        if (iProductService.findAll().isEmpty()) {
            System.out.println(BLUE +"|"+RESET+"                                                       "+RED+"Danh sách sản phẩm của bạn đang trống!!"+RESET+"                                                            "+BLUE+"|"+RESET);
        } else {
            iProductService.findAll().sort((p1, p2) -> p2.getProductId() - p1.getProductId());
            for (Product product : iProductService.findAll()) {
                System.out.println(product);
            }
        }
        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    private void addProduct() {
        System.out.println(PURPLE+"***** THÊM MỚI SẢN PHẨM *****"+RESET);
        System.out.println("Nhập số lượng sản phẩm bạn muốn thêm mới: ");
        int n = Validate.validateInt();
        Product product;
        for (int i = 0; i < n; i++) {
            product = new Product();
            System.out.println("Nhập sản phẩm thứ: " + (i + 1));
            product.setProductId(iProductService.getNewId());
            System.out.println("Nhập tên sản phẩm: ");
            while (true) {
                String proName = Validate.validateString();
                if (iProductService.existProductName(proName)) {
                    System.out.println(RED+"Sản phẩm này đã tồn tại, hãy nhập lại!!!"+RESET);
                } else {
                    product.setProductName(proName);
                    break;
                }
            }

            choiceCategory(product);

            System.out.println("Nhập mô tả cho sản phẩm: ");
            String des = Validate.validateString();
            product.setDescription(des);

            System.out.println("Nhập giá bán: ");
            double price = Validate.validateInt();
            product.setUnitPrice(price);

            System.out.println("Nhập số lượng sản phẩm: ");
            int num = Validate.validateInt();
            product.setStock(num);

            iProductService.save(product);
            System.out.println(YELLOW+"Thêm mới sản phẩm thành công"+RESET);
        }
//        System.out.println(iProductService.findAll());
    }

    private void choiceCategory (Product product) {
        new ViewCategory().showListCategoryForUser();
        while (true) {
            System.out.println("Chọn danh mục cho sản phẩm: ");
            int choice = Validate.validateInt();
            Category category = iCategoryService.findById(choice);
            if (category == null) {
                System.out.println(RED+"Không có danh mục này, hãy chọn lại hoặc thêm mới."+RESET);
                System.out.println(BLUE+"+----------------------------------+");
                System.out.println("|"+RESET+"  1. Chọn lại    |  2. Thêm mới   "+BLUE+"|");
                System.out.println("+----------------------------------+"+RESET);
                System.out.println("Nhập lựa chọn của bạn: ");
                switch (Validate.validateInt()) {
                    case 1:
                        choiceCategory(product);
                        break;
                    case 2:
                        new ViewCategory().addOneCategory();
                        break;
                    default:
                        System.out.println(RED+"Không có lựa chọn này"+RESET);
                        break;
                }
            } else {
                product.setCategory(category);
                break;
            }
        }
    }

    public void showListProductForUser() {
        System.out.println("                                                             DANH SÁCH SẢN PHẨM                       ");
        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |          TÊN SẢN PHẨM        |         DANH MỤC         |                MÔ TẢ               |         GIÁ        |  SÔ LƯỢNG |  TRẠNG THÁI  " + BLUE + "|");
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        if (iProductService.findAll().isEmpty()) {
            System.out.println(BLUE +"|"+RESET+"                                                       "+RED+"Danh sách sản phẩm của bạn đang trống!!"+RESET+"                                                            "+BLUE+"|"+RESET);
        } else {
            iProductService.findAll().sort((p1, p2) -> p2.getProductId() - p1.getProductId());
            for (Product product : iProductService.findAll()) {
                if (product.isStatus() && product.getCategory().isStatus()) {
                    System.out.println(product);
                }
//                else {
//                    System.out.println(BLUE +"|"+RESET+"                                                       "+RED+"Danh sách sản phẩm của bạn đang trống!!"+RESET+"                                                            "+BLUE+"|"+RESET);
//                }
            }
        }
        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    public void findProductForUser() {
        System.out.println("Nhập tên sản phẩm bạn muốn tìm kiếm: ");
        String findName = Validate.validateString();
        boolean checkFind = false;
        System.out.println("                                                             DANH SÁCH SẢN PHẨM                       ");
        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |          TÊN SẢN PHẨM        |         DANH MỤC         |                MÔ TẢ               |         GIÁ        |  SÔ LƯỢNG |  TRẠNG THÁI  " + BLUE + "|");
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        for (Product product : iProductService.findAll()) {
            if (product.getProductName().toLowerCase().contains(findName.toLowerCase())) {
                if (product.isStatus() && product.getCategory().isStatus()) {
                    System.out.println(product);
                    checkFind = true;
                }
            }
        }

        if(!checkFind) {
            System.out.println(BLUE +"|"+RESET+"                                                        "+RED+"Không tìm thấy sản phẩm bạn cần tìm!!!"+RESET+"                                                            "+BLUE+"|"+RESET);

        }
        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    public void sortListProduct () {
        System.out.println(BLUE+"+--------------------------------------------+");
        System.out.println("|"+RESET+"         Chọn cách sắp xếp sản phẩm         "+BLUE+"|");
        System.out.println("+--------------------------------------------+");
        System.out.println("|"+RESET+" 1. Sắp xếp sản phẩm theo giá tăng dần.     "+BLUE+"|");
        System.out.println("|"+RESET+" 2. Sắp xếp sản phẩm theo giá giảm dần.     "+BLUE+"|");
        System.out.println("+--------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn của bạn: ");
        switch (Validate.validateInt()){
            case 1:
                List<Product> productList = new ArrayList<>();
                System.out.println("                                                             DANH SÁCH SẢN PHẨM                       ");
                System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| " + RESET + "   ID     |         TÊN SẢN PHẨM         |         DANH MỤC         |                MÔ TẢ               |         GIÁ        |  SÔ LƯỢNG |  TRẠNG THÁI  " + BLUE + "|");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
                for (Product product : iProductService.findAll()) {
                    if (product.isStatus() && product.getCategory().isStatus()) {
                        productList.add(product);
                    }
                }

                if (productList.isEmpty()) {
                    System.out.println(BLUE +"|"+RESET+"                                                       "+RED+"Danh sách sản phẩm của bạn đang trống!!"+RESET+"                                                            "+BLUE+"|"+RESET);
                } else {
                    productList.sort((p1, p2) -> (int) (p1.getUnitPrice() - p2.getUnitPrice()));
//                    System.out.println("***** DANH SÁCH DANH MỤC SẢN PHẨM *****");
                    for (Product product : productList) {
                        System.out.println(product);
                    }
                }

                System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
                System.out.println(" ");
                break;
            case 2:
                List<Product> productList2 = new ArrayList<>();
                System.out.println("                                                             DANH SÁCH SẢN PHẨM                       ");
                System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| " + RESET + "   ID     |         TÊN SẢN PHẨM         |         DANH MỤC         |                MÔ TẢ               |         GIÁ        |  SÔ LƯỢNG |  TRẠNG THÁI  " + BLUE + "|");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
                for (Product product : iProductService.findAll()) {
                    if (product.isStatus() && product.getCategory().isStatus()) {
                        productList2.add(product);
                    }
                }

                if (productList2.isEmpty()) {
                    System.out.println(BLUE +"|"+RESET+"                                                       "+RED+"Danh sách sản phẩm của bạn đang trống!!"+RESET+"                                                            "+BLUE+"|"+RESET);
                } else {
                    productList2.sort((p1, p2) -> (int) (p2.getUnitPrice() - p1.getUnitPrice()));
//                    System.out.println("***** DANH SÁCH DANH MỤC SẢN PHẨM *****");
                    for (Product product : productList2) {
                        System.out.println(product);
                    }
                }

                System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
                System.out.println(" ");
                break;
            default:
                System.out.println(RED+"Lựa chọn của bạn không hợp lệ !!!"+RESET);
                break;
        }
    }
}
