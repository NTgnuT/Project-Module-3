package view.menu;

import config.Config;
import config.Validate;
import model.account.RoleName;
import model.account.Users;
import service.userService.IUserService;
import service.userService.UserServiceIMPL;
import view.acc.ViewRoleAdmin;
import view.acc.ViewRoleUser;
import view.category.ViewCategory;
import view.product.ViewProduct;
import static config.Color.*;
public class ViewMainMenu {
    IUserService iUserSevice = new UserServiceIMPL();
    public static Users userLogin;
    static Config<Users> config = new Config<>();

    public void menuHome() {
        do {
            System.out.println(BLUE+"+--------------------------------------------------------+" + RESET);
            System.out.println(BLUE+"|"+RESET+"                   \uD83E\uDD7E \uD83D\uDC5F "+YELLOW_BRIGHT+"T.SHOE"+RESET+" \uD83D\uDC5F \uD83D\uDC5E                  "+BLUE+"|");
            System.out.println("+--------------------------------------------------------+");
            System.out.println("|"+RESET+"                      TRANG ĐĂNG NHẬP                   "+BLUE+"|");
            System.out.println("+--------------------------------------------------------+");
            System.out.println("|"+RESET+"                  1. \uD83D\uDD12 Đăng nhập                       "+BLUE+"|");
            System.out.println("|"+RESET+"                  2. \uD83D\uDC68\u200D\uD83D\uDCBB Đăng ký                         "+BLUE+"|");
            System.out.println("|"+RESET+"                  3. \uD83D\uDCD2 Danh sách danh mục              "+BLUE+"|");
            System.out.println("|"+RESET+"                  4. \uD83D\uDCD3 Danh sách sản phẩm              "+BLUE+"|");
            System.out.println("|"+RESET+"                  5. \uD83D\uDD0D Tìm kiếm danh mục               "+BLUE+"|");
            System.out.println("|"+RESET+"                  6. \uD83D\uDD0E Tìm kiếm sản phẩm               "+BLUE+"|");
            System.out.println("|"+RESET+"                  0. \uD83D\uDEAB Thoát                           "+BLUE+"|");
            System.out.println("+--------------------------------------------------------+" +RESET);
            System.out.print("Nhập lựa chọn của bạn: ");
            switch (Validate.validateInt()) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    new ViewCategory().showListCategoryForUser();
                    break;
                case 4:
                    new ViewProduct().showListProductForUser();
                    break;
                case 5:
                    new ViewCategory().findCategoryForUser();
                    break;
                case 6:
                    new ViewProduct().findProductForUser();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                    break;
            }
        } while (true);
    }

    private void login() {
        System.out.println(PURPLE+"*** ĐĂNG NHẬP ***"+RESET);
        System.out.println("Nhập tên tài khoản: ");
        String username = Validate.validateString();
        System.out.println("Nhập mật khẩu: ");
        String pass = Validate.validateString();

        Users users = iUserSevice.checkLogin(username, pass);

        // Kiểm tra
        if (users == null){
            System.out.println(RED+"Sai tên tài khoản hoặc mật khẩu"+RESET);
        } else {
            checkRoleLogin(users);
        }
    }

    public void checkRoleLogin (Users users) {
        if (!users.isStatus()) {
            System.out.println(RED+"Tài khoản của bạn bị khóa, vui lòng liên hệ Admin!"+RESET);
        } else {
            if (users.getRole().equals(RoleName.ADMIN)) {
//                userLogin = users;
                config.writeFile(Config.URL_LOGIN, users); // ghi đối tượng user đang đăng nhập vào file
                System.out.println(YELLOW+"Đăng nhập thành công"+RESET);
                // Chuyển sang trang admin
                new ViewRoleAdmin().menuAdmin();
            } else {
                userLogin = users;
                config.writeFile(Config.URL_LOGIN, users);
                // Chuyển đến trang user
                System.out.println(YELLOW+"Đăng nhập thành công"+RESET);
                new ViewRoleUser().menuUser();
            }
        }
    }

    private void register() {
        System.out.println(PURPLE+"*** ĐĂNG KÝ ***"+RESET);
        Users users = new Users();

        users.setId(iUserSevice.getNewId());
        System.out.println("ID: " + users.getId());

        System.out.println("Nhập họ tên: ");
        users.setName(Validate.validateString());

        System.out.println("Nhập tên tài khoản: ");
        while (true) {
            String username = Validate.validateString();
            if (iUserSevice.existUsername(username)){
                System.out.println(RED+"Tên đăng nhập đã tồn tại, mời nhập lại!!!"+RESET);
            } else {
                users.setUsername(username);
                break;
            }
        }

        System.out.println("Nhập mật khẩu: ");
        users.setPassword(Validate.validateString());

        System.out.println("Nhập lại mật khẩu: ");
        while (true) {
            String repeatPassword = Validate.validateString();
            if (users.getPassword().equals(repeatPassword)) {
                break;
            } else {
                System.out.println(RED+"Mật khẩu không khớp mời nhập lại!!!"+RESET);
            }
        }

        System.out.println("Nhập email tài khoản: ");
        while (true) {
            String email = Validate.validateEmail();
            if (iUserSevice.existEmail(email)){
                System.out.println(RED+"Email đã tồn tại, mời nhập lại!!!"+RESET);
            } else {
                users.setEmail(email);
                break;
            }
        }

        System.out.println("Nhập số điện thoại cho tài khoản: ");
            users.setPhoneNumber(Validate.validatePhoneNumber());

        iUserSevice.save(users);
        System.out.println(YELLOW+"Tạo tài khoản thành công!!"+RESET);
    }
}
