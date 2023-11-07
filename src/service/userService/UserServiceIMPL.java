package service.userService;

import config.Config;
import model.account.RoleName;
import model.account.Users;

import java.util.ArrayList;
import java.util.List;

public class UserServiceIMPL implements IUserService {
    static Config<List<Users>> config = new Config<>();

    public static List<Users> userList;

    static {
        userList = config.readFile(Config.URL_USERS);
        if (userList == null) {
            userList = new ArrayList<>();
            userList.add(new Users(0, "ADMIN", "admin", "admin", "admin@gmail.com", true, RoleName.ADMIN));
            new UserServiceIMPL().updateData();
        }

    }
    @Override
    public List<Users> findAll() {
        return userList;
    }

    @Override
    public void save(Users user) {
        // kiểm tra user có tồn tại trong userList hay không
        if (findById(user.getId()) == null ){
            // Nếu chưa tồn tại thì thêm mới
            userList.add(user);
            updateData();
        } else {
            // Nếu đã tồn tại thì set lại user mới (update)
            userList.set(userList.indexOf(user), user);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        userList.remove(findById(id));
        updateData();
    }

    @Override
    public Users findById(int id) {
        for (Users users : userList) {
            if (users.getId() == id) {
                return users;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Users users : userList) {
            if (users.getId() > idMax) {
                idMax = users.getId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_USERS, userList);
    }

    @Override
    public Users findByName(String name) {
        for (Users users : userList) {
            if (users.getName().toLowerCase().contains(name.toLowerCase())
                    || users.getUsername().toLowerCase().contains(name.toLowerCase())) {
                return users;
            }
        }
        return null;
    }

    @Override
    public boolean existUsername(String username) {
        for (Users users : userList) {
            if (users.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existEmail(String email) {
        for (Users users : userList) {
            if (users.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Users checkLogin(String username, String password) {
        for (Users users : userList) {
            if (users.getUsername().equals(username) && users.getPassword().equals(password)) {
                return users;
            }
        }
        return null;
    }
}
