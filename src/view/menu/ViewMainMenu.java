package view.menu;

import config.Config;
import config.Validate;
import model.account.RoleName;
import model.account.Users;
import service.user.IUserService;
import service.user.UserServiceIMPL;
import view.acc.ViewRoleAdmin;
import view.acc.ViewRoleUser;
import view.category.ViewCategory;

public class ViewMainMenu {
    IUserService iUserSevice = new UserServiceIMPL();
    public static Users userLogin;
    static Config<Users> config = new Config<>();

    public void menuHome() {
        do {
            System.out.println("*****************MENU LOGIN*******************");
            System.out.println("1. Đăng nhập.");
            System.out.println("2. Đăng ký.");
            System.out.println("3. Danh sách danh mục.");
            System.out.println("4. Danh sách sản phẩm.");
            System.out.println("5. Tìm kiếm danh mục.");
            System.out.println("6. Tìm kiếm sản phẩm.");
            System.out.println("0. Thoát");

            System.out.print("Mời lựa chọn (1/2/3): ");
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
                    break;
                case 5:
                    new ViewCategory().findCategoryForUser();
                    break;
                case 6:
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void login() {
        System.out.println("*** Form LOGIN ***");
        System.out.println("Nhập tên tài khoản: ");
        String username = Validate.validateString();
        System.out.println("Nhập mật khẩu: ");
        String pass = Validate.validateString();

        Users users = iUserSevice.checkLogin(username, pass);

        // Kiểm tra
        if (users == null){
            System.out.println("Sai tên tài khoản hoặc mật khẩu");
        } else {
            checkRoleLogin(users);
        }
    }

    public void checkRoleLogin (Users users) {
        if (!users.isStatus()) {
            System.out.println("Tài khoản của bạn bị khóa, vui lòng liên hệ Admin!");
        } else {
            if (users.getRole().equals(RoleName.ADMIN)) {
//                userLogin = users;
                config.writeFile(Config.URL_LOGIN, users); // ghi đối tượng user đang đăng nhập vào file
                System.out.println("Đăng nhập thành công");
                // Chuyển sang trang admin
                new ViewRoleAdmin().menuAdmin();
            } else {
                userLogin = users;
                config.writeFile(Config.URL_LOGIN, users);
                // Chuyển đến trang user
                System.out.println("Đăng nhập thành công");
                new ViewRoleUser().menuUser();
            }
        }
    }

    private void register() {
        System.out.println("*** Form REGISTER ***");
        Users users = new Users();

        users.setId(iUserSevice.getNewId());
        System.out.println("ID: " + users.getId());

        System.out.println("Nhập họ tên: ");
        users.setName(Validate.validateString());

        System.out.println("Nhập tên tài khoản");
        while (true) {
            String username = Validate.validateString();
            if (iUserSevice.existUsername(username)){
                System.out.println("Tên đăng nhập đã tồn tại, mời nhập lại!!!");
            } else {
                users.setUsername(username);
                break;
            }
        }

        System.out.println("Nhập mật khẩu");
        users.setPassword(Validate.validateString());

        System.out.println("Nhập lại mật khẩu");
        while (true) {
            String repeatPassword = Validate.validateString();
            if (users.getPassword().equals(repeatPassword)) {
                break;
            } else {
                System.out.println("Mật khẩu không khớp mời nhập lại!!!");
            }
        }

        System.out.println("Nhập email tài khoản");
        while (true) {
            String email = Validate.validateEmail();
            if (iUserSevice.existEmail(email)){
                System.out.println("Email đã tồn tại, mời nhập lại!!!");
            } else {
                users.setEmail(email);
                break;
            }
        }

        System.out.println("Nhập số điện thoại cho tài khoản.");
            users.setPhoneNumber(Validate.validatePhoneNumber());

        iUserSevice.save(users);
        System.out.println("Tạo tài khoản thành công!!");
    }
}
