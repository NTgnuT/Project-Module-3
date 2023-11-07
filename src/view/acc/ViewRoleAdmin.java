package view.acc;

import config.Config;
import config.Validate;
import model.account.Users;
import view.category.ViewCategory;
import view.menu.ViewMainMenu;
import view.order.ViewOrderForAdmin;
import view.product.ViewProduct;

import static config.Color.*;
import static config.Color.RESET;

public class ViewRoleAdmin {
    public void menuAdmin() {
        do {
            System.out.println(BLUE+"+------------------------------------------------------------------------+" + RESET);
            System.out.printf(BLUE+"|"+RESET+"   \uD83E\uDD7E \uD83D\uDC5F "+YELLOW_BRIGHT+"T.SHOE"+RESET+" \uD83D\uDC5F \uD83D\uDC5E                           Xin chào: %-20s \n" , new Config<Users>().readFile(Config.URL_LOGIN).getName() +" "+ BLUE + "      |");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|"+RESET+"                             TRANG CHỦ ADMIN                            "+BLUE+"|");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|"+RESET+"                       1. \uD83D\uDCC1 Quản lý danh mục                           "+BLUE+"|");
            System.out.println("|"+RESET+"                       2. \uD83D\uDCE6 Quản lý sản phẩm                           "+BLUE+"|");
            System.out.println("|"+RESET+"                       3. \uD83D\uDCDD Quản lý đơn hàng                           "+BLUE+"|");
            System.out.println("|"+RESET+"                       4. \uD83D\uDC64 Quản lý người dùng                         "+BLUE+"|");
            System.out.println("|"+RESET+"                       5. \uD83D\uDCD3 Hồ sơ người dùng                           "+BLUE+"|");
            System.out.println("|"+RESET+"                       0. \uD83D\uDEAA Đăng xuất                                  "+BLUE+"|");
            System.out.println("+------------------------------------------------------------------------+" +RESET);
            System.out.print("Nhập lựa chọn của bạn: ");

            switch (Validate.validateInt()) {
                case 1:
                    new ViewCategory().menuCategoryManager();
                    break;
                case 2:
                    new ViewProduct().menuProductManager();
                    break;
                case 3:
                    new ViewOrderForAdmin().menuHistoryForAdmin();
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
                    System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                    break;
            }
        } while (true);
    }
}
