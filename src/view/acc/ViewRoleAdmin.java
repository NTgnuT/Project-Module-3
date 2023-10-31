package view.acc;

import config.Config;
import config.Validate;
import model.account.Users;
import view.category.ViewCategory;
import view.menu.ViewMainMenu;

public class ViewRoleAdmin {
    public void menuAdmin() {
        do {
            System.out.println("Xin chào: " + new Config<Users>().readFile(Config.URL_LOGIN).getName());
            System.out.println("******************MENU ADMIN********************");
            System.out.println("1. Quản lý danh mục");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Quản lý đơn hàng");
            System.out.println("4. Quản lý người dùng");
            System.out.println("5. Hồ sơ người dùng");
            System.out.println("0. Đăng xuất");
            System.out.print("Mời lựa chọn (1/2/3/4): ");
            switch (Validate.validateInt()) {
                case 1:
                    new ViewCategory().menuCategoryManager();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    new ViewUserManager().menuUserManager();
                    break;
                case 5:
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
