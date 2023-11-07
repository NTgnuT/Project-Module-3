package model.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    private int id;
    private int idUser;
    private Map<Integer, Integer> productCart;
    private boolean status;

    public Cart() {
    }

    public Cart(int id, int idUser) {
        this.id = id;
        this.idUser = idUser;
        this.status = false;
    }

    public Cart(int id, int idUser, Map<Integer, Integer> productCart, boolean status) {
        this.id = id;
        this.idUser = idUser;
        this.productCart = productCart;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Map<Integer, Integer> getProductCart() {
        return productCart;
    }

    public void setProductCart(Map<Integer, Integer> productCart) {
        this.productCart = productCart;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", productCart=" + productCart +
                ", status=" + status +
                '}';
    }
}
