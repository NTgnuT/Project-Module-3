package config;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Format {
    public static String getCurrentYearMonth(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public static String formatMoney(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### ₫");
        return decimalFormat.format(amount);
    }
}
