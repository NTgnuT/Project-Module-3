package config;

import static config.Color.*;
import static config.Color.RESET;
public class OrderStatus {
    public static int WAITING =0;
    public static int ACCEPT =1;
    public static int CANCEL =2;

    public static String getStatusByCode(int code){

        switch (code){
            case 0 :
                return YELLOW+"Đang chờ xác nhận"+RESET;
            case 1:
                return GREEN+"Đã được chấp nhận"+RESET;
            case 2:
                return RED+"Đã bị hủy"+RESET;
            default:
                return  "Không hợp lệ";
        }
    }
}
