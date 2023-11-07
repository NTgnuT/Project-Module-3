package service.cartService;

import config.Config;
import model.account.Users;
import model.cart.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartServiceIMPL implements ICartService {
    static Config<List<Cart>> config = new Config<>();
    static List<Cart> cartList;
    static {
        cartList = config.readFile(Config.URL_CART);
        if (cartList == null){
            cartList = new ArrayList<>();
        }
    }

    @Override
    public List<Cart> findAll() {
        return cartList;
    }

    @Override
    public void save(Cart cart) {
        Cart oldCart = findById(cart.getId());
        if (oldCart == null) {
            cartList.add(cart);
            updateData();
        } else {
            oldCart.setProductCart(cart.getProductCart());
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        cartList.remove(findById(id));
        updateData();
    }

    @Override
    public Cart findById(int id) {
        for (Cart cart : cartList) {
            if (cart.getId() == id) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Cart cart : cartList) {
            if (cart.getId() > idMax) {
                idMax = cart.getId();
            }
        }
        return idMax + 1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_CART, cartList);
    }

    @Override
    public Cart findByName(String name) {
        return null;
    }

    @Override
    public Cart findCartByUserLogin() {
        Users userLogin = new Config<Users>().readFile(Config.URL_LOGIN);
        for (Cart cart : cartList) {
            if (cart.getIdUser() == userLogin.getId() && !cart.isStatus()) {
                return cart;
            }
        }
        return null;
    }
}
