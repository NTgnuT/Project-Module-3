package view.acc;

import config.Config;
import config.Validate;
import model.account.Users;
import model.category.Category;
import view.category.ViewCategory;
import view.menu.ViewMainMenu;

public class ViewRoleUser {
    public void menuUser () {
        do {
            System.out.println("Xin chào: " + new Config<Users>().readFile(Config.URL_LOGIN).getName());
            System.out.println("******************MENU USER********************");
            System.out.println("1. Danh sách danh mục");
            System.out.println("2. Danh sách sản phẩm");
            System.out.println("3. Tìm kiếm danh mục");
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println("5. Sắp xếp sản phẩm theo giá");
            System.out.println("6. Giỏ hàng của tôi");
            System.out.println("7. Hồ sơ người dùng");
            System.out.println("0. Đăng xuất");
            System.out.print("Mời lựa chọn (1/2/3/4/5/6/7/8): ");
            switch (Validate.validateInt()) {
                case 1:
                    new ViewCategory().showListCategoryForUser();
                    break;
                case 2:
                    break;
                case 3:
                    new ViewCategory().findCategoryForUser();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    new ViewMyProfile().menuProfile();
                    break;
                case 0:
                    new Config<Users>().writeFile(Config.URL_LOGIN, null);
                    new ViewMainMenu().menuHome();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }
}
