package view.acc;

import config.Config;
import config.Validate;
import model.account.Users;
import service.userService.IUserService;
import service.userService.UserServiceIMPL;

import static config.Color.*;
import static config.Color.RESET;

public class ViewMyProfile {
    IUserService iUserService = new UserServiceIMPL();
    public static Users userLogin = (Users) new Config<>().readFile(Config.URL_LOGIN);


    public void menuProfile() {
        do {
            System.out.println(BLUE+"+------------------------------------------------------------------------+" + RESET);
            System.out.printf(BLUE+"|"+RESET+"   \uD83E\uDD7E \uD83D\uDC5F "+YELLOW_BRIGHT+"T.SHOE"+RESET+" \uD83D\uDC5F \uD83D\uDC5E                           Xin chào: %-20s \n" , new Config<Users>().readFile(Config.URL_LOGIN).getName() +" "+ BLUE + "      |");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|"+RESET+"                           HỒ SƠ NGƯỜI DÙNG                             "+BLUE+"|");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|"+RESET+"                  1. \uD83D\uDC64 Thông tin người dùng                            "+BLUE+"|");
            System.out.println("|"+RESET+"                  2. \uD83D\uDD04 Thay đổi thông tin người dùng                   "+BLUE+"|");
            System.out.println("|"+RESET+"                  3. \uD83D\uDD10 Đổi mật khẩu                                    "+BLUE+"|");
            System.out.println("|"+RESET+"                  0. \u2B05 Quay lại                                         "+BLUE+"|");
            System.out.println("+------------------------------------------------------------------------+" +RESET);
            System.out.print("Nhập lựa chọn của bạn: ");

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
                    System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                    break;
            }
        } while (true);
    }

    private void changePassword() {
        System.out.println(PURPLE+"***** THAY ĐỔI MÂT KHẨU *****"+RESET);
        System.out.println("Nhập mật khẩu cũ của bạn:");
        String oldPass;
        while (true) {
            oldPass = Validate.validateString();
            if (!userLogin.getPassword().equals(oldPass)) {
                System.out.println(RED+"Mật khẩu không đúng, mời nhập lại!!!"+RESET);
            } else {
                break;
            }
        }
        System.out.println("Nhập mật khẩu mới: ");
        String newPass;
        while (true) {
            newPass = Validate.validateString();
            if (userLogin.getPassword().equals(newPass)) {
                System.out.println(RED+"Mật khẩu mới không được khớp với mật khẩu cũ!!!"+RESET);
            } else {
                break;
            }
        }
        System.out.println("Nhập lại mật khẩu: ");
        while (true) {
            String confirmNewPass = Validate.validateString();
            if (!confirmNewPass.equals(newPass)) {
                System.out.println(RED+"Mật khẩu không trùng khớp!!"+RESET);
            } else {
                break;
            }
        }
        userLogin.setPassword(newPass);
        iUserService.save(userLogin);
        System.out.println(YELLOW+"Thay đổi mật khẩu thành công"+RESET);
    }

    private void changeInfoProfile() {
        System.out.println("Chọn thông tin bạn muốn thay đổi:");
        System.out.println(BLUE+"+---------------------------------+");
        System.out.println("|"+RESET+" 1. Tên người dùng               "+BLUE+"|");
        System.out.println("|"+RESET+" 2. Tên đăng nhập                "+BLUE+"|");
        System.out.println("|"+RESET+" 3. Email                        "+BLUE+"|");
        System.out.println("|"+RESET+" 4. Số điện thoại                "+BLUE+"|");
        System.out.println("+---------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn của bạn: ");
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
                        System.out.println(RED+"Tên đăng nhập đã tồn tại, vui lòng nhập lại"+RESET);
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
                        System.out.println(RED+"Email đã tồn tại, vui lòng nhập lại"+RESET);
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
                System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                break;

        }
        iUserService.save(userLogin);
        new Config<>().writeFile(Config.URL_LOGIN,userLogin);
        System.out.println(YELLOW+"Thay đổi thông tin thành công"+RESET);

    }

    private void showProfile() {
        System.out.println("                                                                      THÔNG TIN TÀI KHOẢN                        ");
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |          HỌ VÀ TÊN         |       TÊN ĐĂNG NHẬP      |          MẬT KHẨU        |           EMAIL          |    SỐ ĐIỆN THOẠI    |   QUYỀN   | TRẠNG THÁI " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(userLogin);
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }
}
