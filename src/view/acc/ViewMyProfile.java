package view.acc;

import config.Config;
import config.Validate;
import model.account.Users;
import service.user.IUserService;
import service.user.UserServiceIMPL;
import view.menu.ViewMainMenu;

import java.sql.SQLOutput;
import java.util.List;

public class ViewMyProfile {
    IUserService iUserService = new UserServiceIMPL();
    public static Users userLogin = (Users) new Config<>().readFile(Config.URL_LOGIN);


    public void menuProfile() {
        do {
            System.out.println("Xin chào: " + userLogin.getName());
            System.out.println("******************MENU PROFILE********************");
            System.out.println("1. Thông tin người dùng");
            System.out.println("2. Thay đổi thông tin người dùng");
            System.out.println("3. Đổi mật khẩu");
            System.out.println("0. Quay lại");
            System.out.print("Mời lựa chọn (1/2/3): ");
            switch (Validate.validateInt()) {
                case 1:
                    showProfile();
                    break;
                case 2:
                    changeInfoProfile();
                    break;
                case 3:
                    changePassword();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void changePassword() {
        System.out.println("***** THAY ĐỔI MÂT KHẨU *****");
        System.out.println("Nhập mật khẩu cũ của bạn:");
        String oldPass;
        while (true) {
            oldPass = Validate.validateString();
            if (!userLogin.getPassword().equals(oldPass)) {
                System.out.println("Mật khẩu không đúng, mời nhập lại!!!");
            } else {
                break;
            }
        }
        System.out.println("Nhập mật khẩu mới: ");
        String newPass;
        while (true) {
            newPass = Validate.validateString();
            if (userLogin.getPassword().equals(newPass)) {
                System.out.println("Mật khẩu mới không được khớp với mật khẩu cũ!!!");
            } else {
                break;
            }
        }
        System.out.println("Nhập lại mật khẩu: ");
        while (true) {
            String confirmNewPass = Validate.validateString();
            if (!confirmNewPass.equals(newPass)) {
                System.out.println("Mật khẩu không trùng khớp!!");
            } else {
                break;
            }
        }
        userLogin.setPassword(newPass);
        iUserService.save(userLogin);
        System.out.println("Thay đổi mật khẩu thành công");
    }

    private void changeInfoProfile() {
        System.out.println("Chọn thông tin bạn muốn thay đổi.");
        System.out.println("1. Tên người dùng");
        System.out.println("2. Tên đăng nhập");
        System.out.println("3. Email");
        System.out.println("4. Số điện thoại");
        switch (Validate.validateInt()) {
            case 1:
                System.out.println("Nhập tên người dùng bạn muốn thay đổi: ");
                String name = Validate.validateString();
                userLogin.setName(name);
                break;
            case 2:
                System.out.println("Nhập tên đăng nhập bạn muốn thay đổi: ");
                while (true) {
                    String username = Validate.validateString();
                    if (iUserService.existUsername(username)) {
                        System.out.println("Tên đăng nhập đã tồn tại, vui lòng nhập lại");
                    } else {
                        userLogin.setUsername(username);
                        break;
                    }
                }
                break;
            case 3:
                System.out.println("Nhập email bạn muốn thay đổi: ");
                while (true) {
                    String email = Validate.validateString();
                    if (iUserService.existEmail(email)) {
                        System.out.println("Email đã tồn tại, vui lòng nhập lại");
                    } else {
                        userLogin.setEmail(email);
                        break;
                    }
                }
                break;
            case 4:
                System.out.println("Nhập số điện thoại bạn muốn thay đổi: ");
                String phoneNumber = Validate.validatePhoneNumber();
                userLogin.setPhoneNumber(phoneNumber);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                break;

        }
        iUserService.save(userLogin);
        new Config<>().writeFile(Config.URL_LOGIN,userLogin);
        System.out.println("Thay đổi thông tin thành công");

    }

    private void showProfile() {
        System.out.println("***** THÔNG TIN NGƯỜI DÙNG *****");
        System.out.println(userLogin);
    }
}
