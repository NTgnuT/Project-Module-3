package service.user;

import model.account.Users;
import service.IGenericService;

public interface IUserService extends IGenericService<Users> {
    boolean existUsername(String username);
    boolean existEmail(String email);

    Users checkLogin (String username, String password);
}
