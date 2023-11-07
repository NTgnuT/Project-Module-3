package model.account;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.io.Serializable;

import static config.Color.*;
import static config.Color.RESET;
import static config.Format.formatMoney;

public class Users implements Serializable {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private boolean status = true;
    private RoleName role = RoleName.USER;

    public Users() {
    }

    public Users(int id, String name, String username, String password, String email, boolean status, RoleName role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public Users(int id, String name, String username, String password, String email, String phoneNumber, boolean status, RoleName role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format(BLUE+"|"+RESET+"   %-5d   |     %-20s   |   %-20s   |   %-20s   |   %-20s   |   %-15s   |   %-5s   |    %-15s    "+BLUE+"|"+RESET,
                id, name, username, password, email, phoneNumber, role, (status ? GREEN+"Mở"+RESET : RED+"Khóa"+RESET));
    }

    @Override
    public boolean equals(Object obj) {
        Users u = (Users) obj;
        return this.id == u.getId();
    }
}
