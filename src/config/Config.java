package config;

import java.io.*;
import java.util.Scanner;

public class Config<T> {

    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    public static final String URL_USERS = "src/data/users.txt";
    public static final String URL_LOGIN = "src/data/userLogin.txt";
    public static final String URL_CATEGORY = "src/data/category.txt";
    public static final String URL_PRODUCT = "src/data/product.txt";
    public static final String URL_CART = "src/data/cart.txt";
    public static final String URL_ORDER = "src/data/order.txt";

    public void writeFile(String PATH_FILE, T t) {
        File file = new File(PATH_FILE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Lỗi ghi file!!!");
        }
    }

    public T readFile(String PATH_FILE) {
        File file = new File(PATH_FILE);
        T t = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            t = (T) ois.readObject();
            if (fis != null) {
                fis.close();
            }
            ois.close();
        } catch (FileNotFoundException f) {
            System.out.println("Không tìm thấy file!!!");
         } catch (IOException i) {
            System.out.println("File rỗng!!!");
        } catch (ClassNotFoundException e) {
            System.out.println("Lớp ngoại lệ! ");
        }
        return t;
    }
}
