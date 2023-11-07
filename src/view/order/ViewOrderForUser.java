package view.order;

import config.Config;
import config.Validate;
import model.Order.Order;
import model.account.Users;
import model.product.Product;
import service.cartService.CartServiceIMPL;
import service.cartService.ICartService;
import service.orderService.IOrderService;
import service.orderService.OrderServiceIMPL;
import service.productService.IProductService;
import service.productService.ProductServiceIMPL;

import java.util.ArrayList;
import java.util.List;

import static config.Color.*;
import static config.Color.RESET;
import static config.OrderStatus.getStatusByCode;

public class ViewOrderForUser {
    IProductService iProductService = new ProductServiceIMPL();
    IOrderService iOrderService = new OrderServiceIMPL();
    Users userLogin = new Config<Users>().readFile(Config.URL_LOGIN);

    public void menuHistoryForUser() {
        do {
            System.out.println(BLUE + "+------------------------------------------------------------------------+" + RESET);
            System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                        Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                            LỊCH SỬ MUA HÀNG                            " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                 1. \uD83D\uDCCB Danh sách lịch sử mua hàng                       " + BLUE + "|");
            System.out.println("|" + RESET + "                 2. \uD83D\uDD52 Danh sách hóa đơn đang chờ                       " + BLUE + "|");
            System.out.println("|" + RESET + "                 3. ✅ Danh sách hóa đơn được xác nhận                  " + BLUE + "|");
            System.out.println("|" + RESET + "                 4. ❌ Hóa đơn bị từ chối                               " + BLUE + "|");
            System.out.println("|" + RESET + "                 0. ↩\uFE0F Quay lại                                         " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------+" + RESET);
            System.out.print("Nhập lựa chọn của bạn: ");


            switch (Validate.validateInt()) {
                case 1:
                    showHistoryForUser();
                    break;

                case 2:
                    boolean check1 = false;

                    showHeadTable();
                    for (Order order : iOrderService.findAll()) {
                        if (order.getIdUser() == userLogin.getId() && order.getStatus() == 0) {
                            System.out.println(order);
                            check1 = true;
                        }
                    }

                    if (!check1) {
                        System.out.println("|" + RESET + "                                 " + RED + "Bạn chưa có đơn đang chờ nào!!!" + RESET + "                                      " + BLUE + "|");
                    }

                    System.out.println("+------------------------------------------------------------------------------------------------------+");
                    System.out.println(" ");
                    break;

                case 3:
                    boolean check2 = false;

                    showHeadTable();
                    for (Order order : iOrderService.findAll()) {
                        if (order.getIdUser() == userLogin.getId() && order.getStatus() == 1) {
                            System.out.println(order);
                            check2 = true;
                        }
                    }

                    if (!check2) {
                        System.out.println("|" + RESET + "                             " + RED + "Bạn chưa có đơn hàng nào đã được xác nhận!!!" + RESET + "                             " + BLUE + "|");
                    }

                    System.out.println("+------------------------------------------------------------------------------------------------------+");
                    System.out.println(" ");
                    break;

                case 4:
                    boolean check3 = false;

                    showHeadTable();
                    for (Order order : iOrderService.findAll()) {
                        if (order.getIdUser() == userLogin.getId() && order.getStatus() == 2) {
                            System.out.println(order);
                            check3 = true;
                        }
                    }

                    if (!check3) {
                        System.out.println("|" + RESET + "                               " + RED + "Bạn chưa có đơn hàng nào đã bị hủy!!!" + RESET + "                                  " + BLUE + "|");
                    }

                    System.out.println("+------------------------------------------------------------------------------------------------------+");
                    System.out.println(" ");
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void showHistoryForUser() {
        boolean check = false;

        showHeadTable();
        for (Order order : iOrderService.findAll()) {
            if (order.getIdUser() == userLogin.getId()) {
                System.out.println(order);
                check = true;
            }
        }

        if (!check) {
            System.out.println("|" + RESET + "                                   " + RED + "Bạn chưa có đơn hàng nào!!!" + RESET + "                                        " + BLUE + "|");
        }

        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println(" ");
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|"+RESET+"       1. Xem chi tiết đơn hàng         |        2. Hủy đơn hàng         |       0. Quay lại          "+BLUE+"|");
        System.out.println("+------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn của bạn: ");
        switch (Validate.validateInt()) {
            case 1:
                new ViewOrderForAdmin().showInfoOrder();
                break;
            case 2:
                cancelOrderForUser();
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Lựa chọn không hợp lệ." + RESET);
                break;
        }
        
        
    }

    private void cancelOrderForUser() {
        List<Order> orderListUser = new ArrayList<>();
        boolean check = false;
        for (Order order : iOrderService.findAll()) {
            if (order.getIdUser() == userLogin.getId()) {
                orderListUser.add(order);
            }
        }

        System.out.println("Nhập ID đơn hàng bạn muốn hủy: ");
        int id = Validate.validateInt();

        for (Order order : orderListUser) {
            if (order.getId() == id) {
                check = true;
                System.out.println(GREEN+"Bạn có chắc chắn muốn hủy đơn hàng này không?"+RESET);
                System.out.println(BLUE+"+-------------------------------------------+");
                System.out.println("|"+RESET+" 1.  Chắc chắn  | 2. Để tôi suy nghĩ thêm  "+BLUE+"|");
                System.out.println("+-------------------------------------------+"+RESET);
                System.out.println("Nhập lựa chọn của bạn: ");
                switch (Validate.validateInt()) {
                    case 1:
                        if (order.getStatus() == 0) {
                            order.setStatus(2);
                            for (Product product : iProductService.findAll()) {
                                for (Integer idP : order.getOrderDetail().keySet()) {
                                    if (product.getProductId() == idP) {
                                        product.setStock(product.getStock() + order.getOrderDetail().get(idP));
                                        if (product.getStock() > 0) {
                                            product.setStatus(true);
                                        }
                                    }
                                }
                                iProductService.save(product);
                            }
                            iOrderService.save(order);
                            System.out.println(YELLOW+"Đơn hàng đã được hủy."+RESET);
                        } else if (order.getStatus() == 1 || order.getStatus() == 2) {
                            System.out.println("Đơn hàng này " + getStatusByCode(order.getStatus()));
                        }
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println(RED+"Lựa chọn không hợp lệ!!"+RESET);
                        break;
                }
            }
        }

        if (!check) {
            System.out.println(RED+"Không tìm thấy ID đơn hàng bạn muốn hủy!!!"+RESET);
        }

    }

    private void confirmCancelForUser() {

    }

    private void showHeadTable() {
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                                                      Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + " MÃ ĐƠN HÀNG | MÃ NGƯỜI MUA |  THỜI GIAN MUA  |         TỔNG ĐƠN         |         TRẠNG THÁI         " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");
    }
}
   