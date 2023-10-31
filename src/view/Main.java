package view;

import config.Config;
import model.account.Users;
import view.menu.ViewMainMenu;

public class Main {
    public static void main(String[] args) {
        Users userLogin = new Config<Users>().readFile(Config.URL_LOGIN);
        if (userLogin != null) {
            new ViewMainMenu().checkRoleLogin(userLogin);
        } else {
            new ViewMainMenu().menuHome();
        }
    }
}
