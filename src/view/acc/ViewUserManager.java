package view.acc;

import config.Config;
import config.Validate;
import model.account.RoleName;
import model.account.Users;
import service.userService.IUserService;
import service.userService.UserServiceIMPL;

import static config.Color.*;

import java.util.List;
import java.util.stream.Collectors;

public class ViewUserManager {
    IUserService iUserService = new UserServiceIMPL();


    public void menuUserManager() {
        do {
            System.out.println(BLUE+"+------------------------------------------------------------------------+" + RESET);
            System.out.printf(BLUE+"|"+RESET+"   \uD83E\uDD7E \uD83D\uDC5F "+YELLOW_BRIGHT+"T.SHOE"+RESET+" \uD83D\uDC5F \uD83D\uDC5E                           Xin chào: %-20s \n" , new Config<Users>().readFile(Config.URL_LOGIN).getName() +" "+ BLUE + "      |");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|"+RESET+"                           QUẢN LÝ NGƯỜI DÙNG                           "+BLUE+"|");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|"+RESET+"                  1. \uD83D\uDC65 Hiển thị danh sách người dùng                   "+BLUE+"|");
            System.out.println("|"+RESET+"                  2. \uD83D\uDD10 Thay đổi quyền                                  "+BLUE+"|");
            System.out.println("|"+RESET+"                  3. \uD83D\uDD12 Khóa/mở khóa người dùng                         "+BLUE+"|");
            System.out.println("|"+RESET+"                  4. \uD83D\uDD0D Tìm kiếm tài khoản                              "+BLUE+"|");
            System.out.println("|"+RESET+"                  5. \uD83D\uDCD1 Tìm kiếm tài khoản theo trạng thái              "+BLUE+"|");
            System.out.println("|"+RESET+"                  0. \u2B05 Quay lại                                         "+BLUE+"|");
            System.out.println("+------------------------------------------------------------------------+" +RESET);
            System.out.print("Nhập lựa chọn của bạn: ");

            switch (Validate.validateInt()) {
                case 1:
                    showListUser();
                    break;
                case 2:
                    changeRole();
                    break;
                case 3:
                    changerStatus();
                    break;
                case 4:
                    findUser();
                    break;
                case 5:
                    findUserByStatus();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                    break;
            }
        } while (true);
    }

    private void findUserByStatus() {
        System.out.println("Chọn trạng thái tài khoản bạn muốn tìm kiếm.");
        System.out.println(BLUE+"+-----------------------------------------+");
        System.out.println("|"+RESET+" 1. Tài khoản khóa   | 2. Tài khoản mở   "+BLUE+"|");
        System.out.println("+-----------------------------------------+"+RESET);
        System.out.println("Nhập lựa chọn của bạn: ");
        switch (Validate.validateInt()) {
            case 1:
                boolean check = false;
                System.out.println("                                                                DANH SÁCH TÀI KHOẢN ĐANG KHÓA                       ");
                System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| " + RESET + "   ID     |          HỌ VÀ TÊN         |       TÊN ĐĂNG NHẬP      |          MẬT KHẨU        |           EMAIL          |    SỐ ĐIỆN THOẠI    |   QUYỀN   | TRẠNG THÁI " + BLUE + "|");
                System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
//                List<Users>usersList=iUserService.findAll().stream().filter(o->!o.isStatus()).collect(Collectors.toList());
                for (Users users : iUserService.findAll()) {
                    if (!users.isStatus()) {
                        System.out.println(users);
                        check = true;
                    }
                }

                if (!check) {
                    System.out.println(BLUE+"|"+RESET+"                                                                      "+RED+"Không có tài khoản nào đang khóa"+RESET+"                                                                  "+BLUE+"|"+RESET);

                }

                System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
                System.out.println(" ");
                break;
            case 2:
                System.out.println("                                                                DANH SÁCH TÀI KHOẢN ĐANG MỞ                       ");
                System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| " + RESET + "   ID     |          HỌ VÀ TÊN         |       TÊN ĐĂNG NHẬP      |          MẬT KHẨU        |           EMAIL          |    SỐ ĐIỆN THOẠI    |   QUYỀN   | TRẠNG THÁI " + BLUE + "|");
                System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
                for (Users users : iUserService.findAll()) {
                    if (users.isStatus()) {
                        System.out.println(users);
                    }
                }
                System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
                System.out.println(" ");
                break;
            default:
                System.out.println(RED+"Không tìm thấy lựa chọn bạn vừa nhập"+RESET);
                break;
        }
    }

    private void findUser() {
        System.out.println("Nhập tên người dùng hoặc tên đăng nhập bạn muốn tìm: ");
        String s = Validate.validateString();
        boolean checkFind = false;
        System.out.println("                                                                    DANH SÁCH TÀI KHOẢN                        ");
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |          HỌ VÀ TÊN         |       TÊN ĐĂNG NHẬP      |          MẬT KHẨU        |           EMAIL          |    SỐ ĐIỆN THOẠI    |   QUYỀN   | TRẠNG THÁI " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        for (Users users : iUserService.findAll()) {
            if (users.getName().toLowerCase().contains(s.toLowerCase()) || users.getUsername().toLowerCase().contains(s.toLowerCase())) {
                System.out.println(users);
                checkFind = true;
            }
        }

        if (!checkFind) {
            System.out.println(BLUE+"|"+RESET+"                                                                   "+RED+"Không tìm thấy tài khoản bạn cần tìm!!!"+RESET+"                                                              "+BLUE+"|"+RESET);
        }
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    private void changerStatus() {
        System.out.println("Nhập ID người dùng bạn muốn thay đổi trạng thái: ");
        int n = Validate.validateInt();
        Users userEdit = iUserService.findById(n);
        if (userEdit == null) {
            System.out.println(RED+"ID bạn vừa nhập không có thông tin người dùng!"+RESET);
        } else {
            if (userEdit.getRole().equals(RoleName.ADMIN)) {
                System.out.println(RED+"Tài khoản của admin không thể khóa!"+RESET);
            } else {
                userEdit.setStatus(!userEdit.isStatus());
                iUserService.save(userEdit);
                System.out.println(YELLOW+"Thay đổi trạng thái của tài khoản "+ RESET +userEdit.getUsername()+ YELLOW + " thành công"+RESET);
            }
        }
    }

    private void changeRole() {
        System.out.println("Nhập id người dùng bạn muốn thay đổi quyền: ");
        int n = Validate.validateInt();
        Users userChangeRole = iUserService.findById(n);
        if (userChangeRole == null) {
            System.out.println(RED+"ID bạn vừa nhập không có thông tin người dùng!"+RESET);
        } else {
            if (!userChangeRole.isStatus()) {
                System.out.println(RED+"Tài khoản đang bị khóa, không thể thay đổi quyền"+RESET);
            } else {
                if (userChangeRole.getRole() == RoleName.ADMIN) {
                    System.out.println(RED+"Quyền của người dùng đang là ADMIN, không thể thay đổi!!!"+RESET);
                } else {
                    userChangeRole.setRole(RoleName.ADMIN);
                    iUserService.save(userChangeRole);
                    System.out.println(YELLOW+"Thay đổi quyền của tài khoản "+ RESET +userChangeRole.getUsername()+ YELLOW + " thành công"+RESET);
                }
            }
        }
    }

    private void showListUser() {
        System.out.println("                                                                      DANH SÁCH TÀI KHOẢN                       ");
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |          HỌ VÀ TÊN         |       TÊN ĐĂNG NHẬP      |          MẬT KHẨU        |           EMAIL          |    SỐ ĐIỆN THOẠI    |   QUYỀN   | TRẠNG THÁI " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        for (Users users : iUserService.findAll()) {
            System.out.println(users);
        }
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }
}
