package view.category;

import config.Config;
import config.Validate;
import model.account.Users;
import model.category.Category;
import model.product.Product;
import service.categoryService.CategoryServiceIMPL;
import service.categoryService.ICategoryService;
import service.productService.IProductService;
import service.productService.ProductServiceIMPL;

import static config.Color.*;
import static config.Color.RESET;

public class ViewCategory {
    ICategoryService iCategoryService = new CategoryServiceIMPL();

    IProductService iProductService = new ProductServiceIMPL();

    public void menuCategoryManager() {
        do {
            System.out.println(BLUE + "+------------------------------------------------------------------------+" + RESET);
            System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                           Xin chào: %-20s \n", new Config<Users>().readFile(Config.URL_LOGIN).getName() + " " + BLUE + "      |");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                        QUẢN LÝ DANH MỤC SẢN PHẨM                       " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                  1. \uD83D\uDCD3 Danh sách danh mục sản phẩm                     " + BLUE + "|");
            System.out.println("|" + RESET + "                  2. \u2795 Thêm mới danh mục sản phẩm                      " + BLUE + "|");
            System.out.println("|" + RESET + "                  3. \uD83D\uDD8A Sửa danh mục sản phẩm                            " + BLUE + "|");
            System.out.println("|" + RESET + "                  4. \uD83D\uDD03 Thay đổi trạng thái danh mục sản phẩm           " + BLUE + "|");
            System.out.println("|" + RESET + "                  5. \uD83D\uDD0D Tìm kiếm danh mục sản phẩm                      " + BLUE + "|");
            System.out.println("|" + RESET + "                  0. \u2B05 Quay lại                                         " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------+" + RESET);
            System.out.print("Nhập lựa chọn của bạn: ");

            switch (Validate.validateInt()) {
                case 1:
                    showListCategory();
                    break;
                case 2:
                    addCategory();
                    break;
                case 3:
                    editCategory();
                    break;
                case 4:
                    hiddenCategory();
                    break;
                case 5:
                    findCategory();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                    break;
            }
        } while (true);
    }

    private void findCategory() {
        System.out.println("Nhập tên danh mục sản phẩm bạn muốn tìm kiếm: ");
        String find = Validate.validateString();
        boolean checkFind = false;
        System.out.println(BLUE + "+--------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |      TÊN DANH MỤC         |                          MÔ TẢ DANH MỤC                         |   TRẠNG THÁI   " + BLUE + "|");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+" + RESET);
        for (Category category : iCategoryService.findAll()) {
            if (category.getCategoryName().toLowerCase().contains(find.toLowerCase())){
                System.out.println(category);
                checkFind = true;
            }
        }

        if(!checkFind) {
            System.out.println(BLUE+"|"+RESET+"                                        "+RED+"Không tìm thấy danh mục bạn cần tìm!!!"+RESET+"                                            "+BLUE+"|"+RESET);
        }

        System.out.println(BLUE + "+--------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    private void hiddenCategory() {
        System.out.println("Nhập ID danh mục bạn muốn thay đổi trạng thái: ");
        int n = Validate.validateInt();
        Category categoryEdit = iCategoryService.findById(n);
        if (categoryEdit == null) {
            System.out.println(RED + "Id bạn vừa nhập không có thông tin!!!" + RESET);
        } else {
            categoryEdit.setStatus(!categoryEdit.isStatus());
            iCategoryService.save(categoryEdit);
            updateProduct(categoryEdit);
//            System.out.println(categoryEdit.isStatus());
            System.out.println(YELLOW + "Thay đổi trạng thái danh mục có ID " + n + " thành công." + RESET);
        }
    }

    private void editCategory() {
        System.out.println("Nhập ID danh mục bạn muốn thay đổi: ");
        int n = Validate.validateInt();
        Category categoryEdit = iCategoryService.findById(n);
        if (categoryEdit == null) {
            System.out.println(RED + "ID bạn vừa nhập không có thông tin!!!" + RESET);
        } else {
            System.out.println(categoryEdit);
            System.out.println(BLUE+"+-----------------------------------------------------+");
            System.out.println("|"+RESET+"   1. Sửa tên danh mục   |   2. Sửa mô tả danh mục   "+BLUE+"|");
            System.out.println("+-----------------------------------------------------+" + RESET);
            System.out.println("Nhập lựa chọn của bạn: ");
            switch (Validate.validateInt()) {
                case 1:
                    System.out.println("Nhập tên mới cho danh mục: ");
                    String newName = Validate.validateString();
                    categoryEdit.setCategoryName(newName);
//                    iCategoryService.save(categoryEdit);
                    break;
                case 2:
                    System.out.println("Nhập mô tả mới cho danh mục: ");
                    String newDes = Validate.validateString();
                    categoryEdit.setDescription(newDes);
                    break;
                default:
                    System.out.println(RED +"Không có lựa chọn này!!!" + RESET);
                    break;
            }
            iCategoryService.save(categoryEdit);
            updateProduct(categoryEdit);
            System.out.println(YELLOW+ "Sửa thành công" + RESET);
        }
    }

    private void showListCategory() {
        System.out.println("                                            DANH SÁCH DANH MỤC SẢN PHẨM                       ");
        System.out.println(BLUE + "+--------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |      TÊN DANH MỤC         |                          MÔ TẢ DANH MỤC                         |   TRẠNG THÁI   " + BLUE + "|");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+" + RESET);
        if (iCategoryService.findAll().isEmpty()) {
            System.out.println(BLUE +"|"+RESET+"                                  "+RED+"Danh sách danh mục sản phẩm của bạn đang trống!!"+RESET+"                                        "+BLUE+"|"+RESET);
        } else {
            iCategoryService.findAll().sort((c1, c2) -> c2.getCategoryId() - c1.getCategoryId());
            for (Category category : iCategoryService.findAll()) {
                System.out.println(category);
            }
        }
        System.out.println(BLUE + "+--------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    private void addCategory() {
        System.out.println(PURPLE+"***** THÊM MỚI DANH MỤC SẢN PHẨM *****"+RESET);
        System.out.println("Nhập số lượng danh mục bạn muốn thêm mới: ");
        Category category;
        int n = Validate.validateInt();
        for (int i = 0; i < n; i++) {
            category = new Category();
            System.out.println("Nhập danh mục thứ: " + (i + 1));
            category.setCategoryId(iCategoryService.getNewId());
            while (true) {
                String newCate = Validate.validateString();
                if (iCategoryService.existCategoryName(newCate)) {
                    System.out.println(RED + "Danh mục này đã tồn tại, hãy nhập 1 tên danh mục khác" + RESET);
                } else {
                    category.setCategoryName(newCate);
                    break;
                }
            }
            System.out.println("Nhập mô tả cho danh mục: ");
            String des = Validate.validateString();
            category.setDescription(des);
            iCategoryService.save(category);
            System.out.println(YELLOW + "Thêm danh mục mới thành công" + RESET);
            System.out.println(" ");
        }
    }

    public void addOneCategory() {
        System.out.println(PURPLE+"***** THÊM MỚI DANH MỤC SẢN PHẨM *****"+RESET);
        Category category = new Category();
        category.setCategoryId(iCategoryService.getNewId());
        while (true) {
            String newCate = Validate.validateString();
            if (iCategoryService.existCategoryName(newCate)) {
                System.out.println(RED + "Danh mục này đã tồn tại, hãy nhập 1 tên danh mục khác" + RESET);
            } else {
                category.setCategoryName(newCate);
                break;
            }
        }
        System.out.println("Nhập mô tả cho danh mục: ");
        String des = Validate.validateString();
        category.setDescription(des);
        iCategoryService.save(category);
        System.out.println(YELLOW + "Thêm danh mục mới thành công" + RESET);
        System.out.println(" ");
    }

    public void showListCategoryForUser() {
        System.out.println("                                            DANH SÁCH DANH MỤC SẢN PHẨM                       ");
        System.out.println(BLUE + "+--------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |      TÊN DANH MỤC         |                          MÔ TẢ DANH MỤC                         |   TRẠNG THÁI   " + BLUE + "|");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+" + RESET);
        if (iCategoryService.findAll().isEmpty()) {
            System.out.println(BLUE +"|"+RESET+"                                  "+RED+"Danh sách danh mục sản phẩm của bạn đang trống!!"+RESET+"                                        "+BLUE+"|"+RESET);
        } else {
            iCategoryService.findAll().sort((c1, c2) -> c2.getCategoryId() - c1.getCategoryId());
            for (Category category : iCategoryService.findAll()) {
                if (category.isStatus()) {
                    System.out.println(category);
                }
            }
        }
        System.out.println(BLUE + "+--------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    public void findCategoryForUser() {
        System.out.println("Nhập tên danh mục sản phẩm bạn muốn tìm kiếm: ");
        String find = Validate.validateString();
        boolean checkFind = false;
        System.out.println(BLUE + "+--------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |      TÊN DANH MỤC         |                          MÔ TẢ DANH MỤC                         |   TRẠNG THÁI   " + BLUE + "|");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------+" + RESET);
        for (Category category : iCategoryService.findAll()) {
            if (category.getCategoryName().toLowerCase().contains(find.toLowerCase())){
                if (category.isStatus()){
                    System.out.println(category);
                    checkFind = true;
                }
            }
        }

        if(!checkFind) {
            System.out.println(BLUE+"|"+RESET+"                                        "+RED+"Không tìm thấy danh mục bạn cần tìm!!!"+RESET+"                                            "+BLUE+"|"+RESET);
        }

        System.out.println(BLUE + "+--------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    private void updateProduct(Category category) {
        for (Product product : iProductService.findAll()) {
            if (product.getCategory().getCategoryId() == category.getCategoryId()) {
                product.setCategory(category);
                iProductService.save(product);
            }
        }
    }
}
