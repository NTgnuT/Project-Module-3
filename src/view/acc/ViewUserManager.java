package view.acc;

import config.Config;
import config.Validate;
import model.account.RoleName;
import model.account.Users;
import service.user.IUserService;
import service.user.UserServiceIMPL;
import view.menu.ViewMainMenu;

import static config.Color.*;

import java.util.List;

public class ViewUserManager {

    IUserService iUserService = new UserServiceIMPL();

    static Config<List<Users>> config = new Config<>();

    public static List<Users> userList = config.readFile(Config.URL_USERS);

    public void menuUserManager() {
        do {
            System.out.println("Xin chào: " + new Config<Users>().readFile(Config.URL_LOGIN).getName());
            System.out.println("******************Quản lý người dùng********************");
            System.out.println("1. Hiển thị danh sách người dùng");
            System.out.println("2. Thay đổi quyền");
            System.out.println("3. Khóa/mở khóa người dùng");
            System.out.println("4. Tìm kiếm tài khoản");
            System.out.println("5. Tìm kiếm tài khoản theo trạng thái");
            System.out.println("0. Quay lại");

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
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void findUserByStatus() {
        System.out.println("Chọn trạng thái tài khoản bạn muốn tìm kiếm.");
        System.out.println("1. Tài khoản khóa");
        System.out.println("2. Tài khoản mở");
        switch (Validate.validateInt()) {
            case 1:
                for (Users users : userList) {
                    if (!users.isStatus()) {
                        System.out.println(users);
                    }
                }
                break;
            case 2:
                for (Users users : userList) {
                    if (users.isStatus()) {
                        System.out.println(users);
                    }
                }
                break;
            default:
                System.out.println("Không tìm thấy lựa chọn bạn vừa nhập");
                break;
        }
    }

    private void findUser() {
        System.out.println("Nhập tên người dùng hoặc tên đăng nhập bạn muốn tìm");
        String s = Validate.validateString();
        Users findUser = iUserService.findByName(s);
        if (findUser == null) {
            System.out.println("Không tìm thấy tên bạn cần tìm!!!");
        } else {
            System.out.println(findUser);
        }

    }

    private void changerStatus() {
        System.out.println("Nhập ID người dùng bạn muốn thay đổi trạng thái");
        int n = Validate.validateInt();
        Users userEdit = iUserService.findById(n);
        if (userEdit == null) {
            System.out.println("ID bạn vừa nhập không có thông tin người dùng!");
        } else {
            if (userEdit.getRole().equals(RoleName.ADMIN)) {
                System.out.println("Tài khoản của admin không thể khóa!");
            } else {
                userEdit.setStatus(!userEdit.isStatus());
                iUserService.save(userEdit);
                System.out.println(userEdit);
            }
        }
    }

    private void changeRole() {
        System.out.println("Nhập id người dùng bạn muốn thay đổi quyền:");
        int n = Validate.validateInt();
        Users userChangeRole = iUserService.findById(n);
        if (userChangeRole == null) {
            System.out.println("ID bạn vừa nhập không có thông tin người dùng!");
        } else {
            if (!userChangeRole.isStatus()) {
                System.out.println("Tài khoản đang bị khóa, không thể thay đổi quyền");
            } else {
                userChangeRole.setRole(RoleName.ADMIN);
                iUserService.save(userChangeRole);
                System.out.println(userChangeRole);
            }
        }
    }

    private void showListUser() {
        for (Users users : iUserService.findAll()) {
            System.out.println(users);
        }
    }
}
