package view.acc;

import config.Config;
import config.Format;
import config.Validate;
import model.Order.Order;
import model.account.Users;
import model.cart.Cart;
import model.product.Product;
import service.cartService.CartServiceIMPL;
import service.cartService.ICartService;
import service.orderService.IOrderService;
import service.orderService.OrderServiceIMPL;
import service.productService.IProductService;
import service.productService.ProductServiceIMPL;
import view.category.ViewCategory;
import view.menu.ViewMainMenu;
import view.order.ViewOrderForUser;
import view.product.ViewProduct;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static config.Color.*;
import static config.Color.RESET;

public class ViewRoleUser {
    IProductService iProductService = new ProductServiceIMPL();
    ICartService iCartService = new CartServiceIMPL();
    IOrderService iOrderService = new OrderServiceIMPL();


    public void menuUser() {
        do {
            System.out.println(BLUE + "+------------------------------------------------------------------------+" + RESET);
            System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                        Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                             TRANG CHỦ USER                             " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                       1.  \uD83D\uDCD3 Danh sách danh mục                        " + BLUE + "|");
            System.out.println("|" + RESET + "                       2.  \uD83D\uDCE6 Danh sách sản phẩm                        " + BLUE + "|");
            System.out.println("|" + RESET + "                       3.  \uD83D\uDD0D Tìm kiếm danh mục                         " + BLUE + "|");
            System.out.println("|" + RESET + "                       4.  \uD83D\uDD0E Tìm kiếm sản phẩm                         " + BLUE + "|");
            System.out.println("|" + RESET + "                       5.  \u2B50 Sản phẩm nổi bật                          " + BLUE + "|");
            System.out.println("|" + RESET + "                       6.  \uD83D\uDCB5 Sắp xếp sản phẩm theo giá                 " + BLUE + "|");
            System.out.println("|" + RESET + "                       7.  \uD83D\uDED2 Đặt hàng                                  " + BLUE + "|");
            System.out.println("|" + RESET + "                       8.  \uD83D\uDC5C Giỏ hàng của tôi                          " + BLUE + "|");
            System.out.println("|" + RESET + "                       9.  \uD83D\uDD52 Lịch sử mua hàng                          " + BLUE + "|");
            System.out.println("|" + RESET + "                       10. \uD83D\uDC64 Hồ sơ người dùng                          " + BLUE + "|");
            System.out.println("|" + RESET + "                       0.  \uD83D\uDEAA Đăng xuất                                 " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------+" + RESET);
            System.out.print("Nhập lựa chọn của bạn: ");


            switch (Validate.validateInt()) {
                case 1:
                    new ViewCategory().showListCategoryForUser();
                    break;
                case 2:
                    new ViewProduct().showListProductForUser();
                    break;
                case 3:
                    new ViewCategory().findCategoryForUser();
                    break;
                case 4:
                    new ViewProduct().findProductForUser();
                    break;
                case 5:
                    outstandingProduct();
                    break;
                case 6:
                    new ViewProduct().sortListProduct();
                    break;
                case 7:
                    orderProduct();
                    break;
                case 8:
                    myCart();
                    break;
                case 9:
                    new ViewOrderForUser().menuHistoryForUser();
                    break;
                case 10:
                    new ViewMyProfile().menuProfile();
                    break;
                case 0:
                    new Config<Users>().writeFile(Config.URL_LOGIN, null);
//                    ViewMyProfile.userLogin = null;
                    new ViewMainMenu().menuHome();
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void myCart() {
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                                                      Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + "                                        GIỎ HÀNG CỦA TÔI                                              " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + " MÃ SẢN PHẨM |        TÊN SẢN PHẨM        | SỐ LƯỢNG |         GIÁ         |        TỔNG TIỀN         " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");

        Cart cart = iCartService.findCartByUserLogin();
        if (cart == null || cart.getProductCart().equals(new HashMap<>())) {
            System.out.println("|" + RESET + "                                  " + RED + "GIỎ HÀNG CỦA BẠN ĐANG TRỐNG" + RESET + "                                         " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------------+" + RESET);

            return;
        }

//        double totalBill = 0;
        for (int idPro : cart.getProductCart().keySet()) {
            Product product = iProductService.findById(idPro);
            System.out.printf("|" + RESET + "     %-5d   |   %-22s   |   %-5d  |   %-15s   |   %-20s   " + BLUE + "|\n",
                    idPro, product.getProductName(), cart.getProductCart().get(idPro), Format.formatMoney(product.getUnitPrice()), Format.formatMoney(cart.getProductCart().get(idPro) * product.getUnitPrice()));
//            totalBill += cart.getProductCart().get(idPro) * product.getUnitPrice();
        }

        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.printf("|" + RESET + "                                                      Tổng tiền đơn hàng:  |   " + CYAN + "%-20s" + RESET + "   " + BLUE + "|\n", Format.formatMoney(totalBill()));
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println(" ");
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + " 1. Đặt hàng  | 2. Thay đổi số lượng  |  3. Xóa 1 sản phẩm  | 4. Xóa toàn bộ sản phẩm  | 0. Quay lại  " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn của bạn: ");
        switch (Validate.validateInt()) {
            case 1:
                order();
                break;
            case 2:
                changeNumberInCart();
                break;
            case 3:
                deleteProduct();
                break;
            case 4:
                deleteAllProduct();
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Lựa chọn không hợp lệ!!!" + RESET);
                break;
        }
    }

    private void order() {
        System.out.println(PURPLE + "***** ĐẶT HÀNG *****" + RESET);
        Cart cart = iCartService.findCartByUserLogin();
        if (cart.getProductCart() == null || cart.getProductCart().equals(new HashMap<>())) {
            System.out.println(RED + "Giỏ hàng của bạn đang không có sản phẩm để đặt hàng!!!" + RESET);

        } else {
            Order order = new Order();

            for (Product product : iProductService.findAll()) {
                for (Integer id : cart.getProductCart().keySet()) {
                    if (product.getProductId() == id) {
                        if (!iProductService.findById(id).isStatus() || !iProductService.findById(id).getCategory().isStatus() || iProductService.findById(id).getStock() == 0) {
                            System.out.println(RED+"Sản phẩm "+RESET + iProductService.findById(id).getProductName() + RED + " đã hết hàng, vui lòng đặt sang sản phẩm khác."+RESET);
                            return;
                        }
                        product.setStock(product.getStock() - cart.getProductCart().get(id));
                        if (product.getStock() == 0) {
                            product.setStatus(false);
                        }
                    }
                }

                iProductService.save(product);
            }

            order.setId(iOrderService.getNewId());
            order.setIdUser(cart.getIdUser());
            System.out.println("Nhập tên người nhận hàng: ");
            order.setReceiver(Validate.validateString());
            System.out.println("Nhập số điện thoại người nhận hàng: ");
            order.setNumberPhone(Validate.validatePhoneNumber());
            System.out.println("Nhập địa chỉ nhận hàng: ");
            order.setAddress(Validate.validateString());
            order.setTotal(totalBill());
            order.setBuyDate(LocalDateTime.now());
            order.setOrderDetail(cart.getProductCart());

            iOrderService.save(order);
            cart.setProductCart(new HashMap<>());
            iCartService.save(cart);

            System.out.println(YELLOW + "Đặt hàng thành công" + RESET);
        }
    }


    private void deleteAllProduct() {
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("| Bạn có chắc chắn muốn xóa tất cả sản phẩm trong giỏ hàng? |");
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("|            1. Có           |           2. Không           |");
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("Xác nhận lựa chọn của bạn: ");
        switch (Validate.validateInt()) {
            case 1:
                Cart cart = iCartService.findCartByUserLogin();
                cart.setProductCart(new HashMap<>());
                iCartService.save(cart);
                System.out.println(YELLOW + "Xóa toàn bộ sản phẩm trong giỏ hàng thành công." + RESET);
                break;
            case 2:
                return;
            default:
                System.out.println(RED + "Lựa chọn không hợp lệ!!!" + RESET);
                break;
        }
    }

    private void deleteProduct() {
        Cart cart = iCartService.findCartByUserLogin();
        System.out.println("Nhập ID sản phẩm trong giỏ hàng bạn muốn xóa: ");
        int idDelete = Validate.validateInt();
        if (cart.getProductCart().containsKey(idDelete)) {
            cart.getProductCart().remove(idDelete);
            iCartService.save(cart);
            System.out.println(YELLOW + "Xóa sản phẩm khỏi giỏ hàng thành công." + RESET);
        } else {
            System.out.println(RED + "Không có ID sản phẩm này trong giỏ hàng của bạn!!!" + RESET);
        }
    }

    private void changeNumberInCart() {
        Cart cart = iCartService.findCartByUserLogin();
        System.out.println("Nhập ID sản phẩm trong giỏ hàng bạn muốn thay đổi số lượng: ");
        int idChange = Validate.validateInt();
        boolean check = false;

        if (cart.getProductCart().containsKey(idChange)) {
            System.out.println("Nhập số lượng bạn muốn thay đổi: ");
            int newNumber = Validate.validateInt();
            for (Product product : iProductService.findAll()) {
                if (product.getProductId() == idChange && product.getStock() >= newNumber) {
                    cart.getProductCart().put(idChange, newNumber);
                    iCartService.save(cart);
                    System.out.println(YELLOW + "Cập nhật số lượng thành công." + RESET);
                    check = true;
                    break;
                }
            }

            if (!check) {
                System.out.println(RED + "Số lượng sản phẩm vượt quá tồn kho" + RESET);
                changeNumberInCart();
            }
        } else {
            System.out.println(RED + "Không có ID sản phẩm này trong giỏ hàng của bạn!!!" + RESET);
            changeNumberInCart();
        }
    }

    private void orderProduct() {
        new ViewProduct().showListProductForUser();
        System.out.println(BLUE + "+------------------------------------------------+");
        System.out.println("|" + RESET + " Chọn ID của sản phẩm đặt hàng | Chọn 0 : Thoát " + BLUE + "|");
        System.out.println("+------------------------------------------------+" + RESET);

        int idBuy = Validate.validateInt();
        Product productBuy = iProductService.findById(idBuy);

        if (idBuy == 0) {
            return;
        }

        if (productBuy == null || !productBuy.isStatus() || !productBuy.getCategory().isStatus()) {
            System.out.println(RED + "Không tồn tại sản phẩm có ID vừa nhập!!!" + RESET);
        } else {
            Cart cart = iCartService.findCartByUserLogin();
            Users userLogin = new Config<Users>().readFile(Config.URL_LOGIN);

            if (cart == null) {
                cart = new Cart(iCartService.getNewId(), userLogin.getId(), new HashMap<>(), false);
            }

            if (cart.getProductCart().containsKey(idBuy)) {
                for (Product product : iProductService.findAll()) {
                    if (product.getProductId() == idBuy) {
                        if (cart.getProductCart().get(idBuy) < product.getStock()) {
                            cart.getProductCart().put(idBuy, cart.getProductCart().get(idBuy) + 1);
                            System.out.println(YELLOW + "Thêm sản phẩm vào giỏ hàng thành công." + RESET);
                        } else {
                            System.out.println(RED + "Số lượng sản phẩm còn lại không đủ!!!" + RESET);
                            break;
                        }
                    }
                }
            } else {
//                cart.setIdUser(userLogin.getId());
                cart.getProductCart().put(idBuy, 1);
                System.out.println(YELLOW + "Thêm sản phẩm vào giỏ hàng thành công." + RESET);
            }

            iCartService.save(cart);
//            System.out.println(YELLOW+"Thêm sản phẩm vào giỏ hàng thành công."+RESET);

        }

    }

    private void outstandingProduct() {
        List<Product> productList = iProductService.findAll().stream().filter(p -> p.isStatus() && p.getCategory().isStatus()).sorted((p1, p2) -> p2.getProductId() - p1.getProductId()).limit(3).collect(Collectors.toList());

        System.out.println("                                                          DANH SÁCH SẢN PHẨM NỔI BẬT                       ");
        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| " + RESET + "   ID     |          TÊN SẢN PHẨM        |         DANH MỤC         |                MÔ TẢ               |         GIÁ        |  SÔ LƯỢNG |  TRẠNG THÁI  " + BLUE + "|");
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        for (Product product : productList) {
            System.out.println(product);
        }
        System.out.println(BLUE + "+----------------------------------------------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println(" ");
    }

    public double totalBill() {
        double totalBill = 0;
        for (int id : iCartService.findCartByUserLogin().getProductCart().keySet()) {
            totalBill += iCartService.findCartByUserLogin().getProductCart().get(id) * iProductService.findById(id).getUnitPrice();
        }
        return totalBill;
    }


}
