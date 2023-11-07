package service.cartService;

import model.cart.Cart;
import service.IGenericService;

public interface ICartService extends IGenericService<Cart> {
    Cart findCartByUserLogin();
}
