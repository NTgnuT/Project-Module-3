package config;

import static config.Color.*;
import static config.Color.RESET;

public class Validate {
    public static int validateInt() {
        int n;
        while (true) {
            try {
                n = Integer.parseInt(Config.scanner().nextLine());
                if (n >= 0) {
                    break;
                } else {
                    System.out.println(RED+"Không đươc nhập số âm"+RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED+"Sai định đạng mời nhập lại"+RESET);
            }
        }
        return n;
    }

    public static String validateString() {
        String s;
        while (true) {
            s = Config.scanner().nextLine();
            if (s.isEmpty()) {
                System.out.println(RED+"Không đươc để trống"+RESET);
            } else {
                break;
            }
        }
        return s;
    }

    public static String validateEmail() {
        String email;
        while (true) {
            email = Config.scanner().nextLine();
            if (email.matches("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-z]+){1,2}")) {
                break;
            } else {
                System.out.println(RED+"Email không đúng định dạng mời nhập lại"+RESET);
            }
        }
        return email;
    }

    public static String validatePhoneNumber() {
        String p;
        while (true) {
            p = (Config.scanner().nextLine());
            if (p.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                break;
            } else {
                System.out.println(RED+"Số điện thoại không đúng định dạng, mời nhập lại"+RESET);
            }
        }
        return p;
    }


}
