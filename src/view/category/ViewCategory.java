package view.category;

import config.Config;
import config.Validate;
import model.account.Users;
import model.category.Category;
import service.category.CategoryServiceIMPL;
import service.category.ICategoryService;

import java.util.List;

public class ViewCategory {
    ICategoryService iCategoryService = new CategoryServiceIMPL();
//    static Config<List<Category>> config = new Config<>();
//    public static List<Category> categoryList = config.readFile(Config.URL_CATEGORY);
    public void menuCategoryManager() {
        do {
            System.out.println("Xin chào: " + new Config<Users>().readFile(Config.URL_LOGIN).getName());
            System.out.println("******************Quản lý danh mục sản phẩm********************");
            System.out.println("1. Danh sách danh mục sản phẩm");
            System.out.println("2. Thêm mới danh mục sản phẩm");
            System.out.println("3. Sửa danh mục sản phẩm");
            System.out.println("4. Ẩn/hiện danh mục sản phẩm");
            System.out.println("5. Tìm kiếm danh mục sản phẩm");
            System.out.println("0. Quay lại");

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
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void findCategory() {
        System.out.println("Nhập tên danh mục sản phẩm bạn muốn tìm kiếm: ");
        String find = Validate.validateString();
        Category categoryfind = iCategoryService.findByName(find);
        if (categoryfind == null) {
            System.out.println("Không tìm thấy danh mục bạn cần!!!");
        } else {
            System.out.println(categoryfind);
        }
    }

    private void hiddenCategory() {
        System.out.println("Nhập ID danh mục bạn muốn thay đổi trạng thái: ");
        int n = Validate.validateInt();
        Category categoryEdit = iCategoryService.findById(n);
        if (categoryEdit == null) {
            System.out.println("Id bạn vừa nhập không có thông tin!!!");
        } else {
            categoryEdit.setStatus(!categoryEdit.isStatus());
            iCategoryService.save(categoryEdit);
            System.out.println(categoryEdit);
        }
    }

    private void editCategory() {
        System.out.println("Nhập ID danh mục bạn muốn thay đổi: ");
        int n = Validate.validateInt();
        Category categoryEdit = iCategoryService.findById(n);
        if (categoryEdit == null) {
            System.out.println("ID bạn vừa nhập không có thông tin!!!");
        } else {
            System.out.println(categoryEdit);
            System.out.println("Nhập tên mới cho danh mục");
            String newName = Validate.validateString();
            categoryEdit.setCategoryName(newName);
            iCategoryService.save(categoryEdit);
        }

    }

    private void showListCategory() {
        System.out.println("***** DANH SÁCH DANH MỤC SẢN PHẨM *****");
        if (iCategoryService.findAll().isEmpty()) {
            System.out.println("Danh sách danh mục sản phẩm của bạn đang trống!!");
        } else {
            iCategoryService.findAll().sort((c1, c2) -> c2.getCategoryId() - c1.getCategoryId());
            for (Category category: iCategoryService.findAll()) {
                System.out.println(category);
            }
        }
    }

    private void addCategory() {
        System.out.println("***** THÊM MỚI DANH MỤC SẢN PHẨM *****");
        System.out.println("Nhập số lượng danh mục bạn muốn thêm mới: ");
        Category category;
        int n = Validate.validateInt();
        for (int i = 0; i < n; i++) {
            category = new Category();
            System.out.println("Nhập danh mục thứ: " + (i+1));
            String newCate = Validate.validateString();
            if (iCategoryService.existCategoryName(newCate)) {
                System.out.println("Danh mục này đã tồn tại!!!");
            } else {
                category.setCategoryId(iCategoryService.getNewId());
                category.setCategoryName(newCate);
                iCategoryService.save(category);
            }
        }
    }

    public void showListCategoryForUser() {
        System.out.println("***** DANH SÁCH DANH MỤC SẢN PHẨM *****");
        if (iCategoryService.findAll().isEmpty()) {
            System.out.println("Danh sách danh mục sản phẩm của bạn đang trống!!");
        } else {
            iCategoryService.findAll().sort((c1, c2) -> c2.getCategoryId() - c1.getCategoryId());
            for (Category category: iCategoryService.findAll()) {
                if (category.isStatus()) {
                    System.out.println(category);
                }
            }
        }
    }

    public void findCategoryForUser() {
        System.out.println("Nhập tên danh mục sản phẩm bạn muốn tìm kiếm: ");
        String find = Validate.validateString();
        Category categoryfind = iCategoryService.findByName(find);
        if (categoryfind == null) {
            System.out.println("Không tìm thấy danh mục bạn cần!!!");
        } else {
            if (categoryfind.isStatus()) {
                System.out.println(categoryfind);
            } else {
                System.out.println("Không tìm thấy danh mục bạn cần");
            }
        }
    }
}
