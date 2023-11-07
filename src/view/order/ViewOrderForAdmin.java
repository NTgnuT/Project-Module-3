package view.order;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
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
import service.userService.IUserService;
import service.userService.UserServiceIMPL;

import java.util.ArrayList;
import java.util.List;

import static config.Color.*;
import static config.Color.RESET;
import static config.Format.formatMoney;
import static config.Format.getCurrentYearMonth;
import static config.OrderStatus.getStatusByCode;

public class ViewOrderForAdmin {
    IProductService iProductService = new ProductServiceIMPL();
    IOrderService iOrderService = new OrderServiceIMPL();
    IUserService iUserService = new UserServiceIMPL();

    public void menuHistoryForAdmin() {
        do {
            System.out.println(BLUE + "+------------------------------------------------------------------------+" + RESET);
            System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                        Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                            QUẢN LÝ ĐƠN HÀNG                            " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                 1. \uD83D\uDD52 Danh sách hóa đơn chưa xác nhận                  " + BLUE + "|");
            System.out.println("|" + RESET + "                 2. ✅ Danh sách hóa đơn đã xác nhận                    " + BLUE + "|");
            System.out.println("|" + RESET + "                 3. ❌ Danh sách hóa đơn đã hủy                         " + BLUE + "|");
            System.out.println("|" + RESET + "                 4. \uD83D\uDD0D Tìm kiếm hóa đơn theo tên người dùng             " + BLUE + "|");
            System.out.println("|" + RESET + "                 5. \uD83D\uDD0D Tìm kiếm hóa đơn theo ID hóa đơn                 " + BLUE + "|");
            System.out.println("|" + RESET + "                 0. ↩\uFE0F Quay lại                                         " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------+" + RESET);
            System.out.print("Nhập lựa chọn của bạn: ");


            switch (Validate.validateInt()) {
                case 1:
                    showListPendingOrder();
                    break;
                case 2:
                    showListAcceptOrder();
                    break;
                case 3:
                    showListCancle();
                    break;
                case 4:
                    findOrderByName();
                    break;
                case 5:
                    findOrderById();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void findOrderByName() {
        System.out.println("Nhập tên đăng nhập bạn muốn tìm kiếm đơn hàng: ");
        List<Users> listFind = new ArrayList<>();
        boolean checkName = false;
        boolean checkId = false;

        do {
            String usernameFind = Validate.validateString();
            for (Users user : iUserService.findAll()) {
                if (user.getUsername().toLowerCase().contains(usernameFind.toLowerCase())) {
                    listFind.add(user);
                    checkName = true;
                }
            }

            if (!checkName) {
                System.out.println(RED+"Không tìm thấy tên bạn muốn tìm kiếm, vui lòng nhập lại!!!"+RESET);
            }
        } while (!checkName);

        System.out.println(BLUE+"+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + " MÃ ĐƠN HÀNG |   TÊN NGƯỜI MUA    |  THỜI GIAN MUA  |       TỔNG ĐƠN       |        TRẠNG THÁI        " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        for (Users user : listFind) {
            for (Order order : iOrderService.findAll()) {
                if (user.getId() == order.getIdUser()) {
                    System.out.printf("|"+RESET+"      %-5d  |    %-15s | %-15s |     %-15s  |     %-15s    "+BLUE+"|\n",
                            order.getId(), iUserService.findById(order.getIdUser()).getUsername(), getCurrentYearMonth(order.getBuyDate()) , formatMoney(order.getTotal()), getStatusByCode(order.getStatus()));
                    checkId = true;
                }
            }
            if (!checkId) {
                System.out.println("|"+RESET+"                          "+RED+"Người dùng bạn vừa nhập không có đơn hàng nào!!!"+RESET+"                            "+BLUE+"|");
            }
        }
        System.out.println("+------------------------------------------------------------------------------------------------------+"+ RESET);
        System.out.println(" ");
    }

    private void findOrderById() {
        System.out.println("Nhập ID đơn hàng bạn muốn tìm kiếm: ");
        int n = Validate.validateInt();

        Order order = iOrderService.findById(n);
        if (order == null) {
            System.out.println(RED+"ID bạn tìm kiếm không có đơn hàng nào!!!"+RESET);
        } else {
            System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------+" + RESET);
            System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                                                      Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
            System.out.println("+------------------------------------------------------------------------------------------------------+");
            System.out.printf("|" + RESET + "  ID đơn hàng: %-5d                                                                                  " + BLUE + "|\n", order.getId());
            System.out.printf("|" + RESET + "  Người đặt hàng: %-20s                    Người nhận hàng: %-20s       " + BLUE + "|\n", iUserService.findById(order.getIdUser()).getName(), order.getReceiver());
            System.out.printf("|" + RESET + "  Số điện thoại nhận hàng: %-15s                Ngày đặt hàng: %-15s              " + BLUE + "|\n", order.getNumberPhone(), getCurrentYearMonth(order.getBuyDate()));
            System.out.printf("|" + RESET + "  Tổng giá trị đơn hàng: %-15s                  Trạng thái đơn hàng: %-15s      " + BLUE + "|\n", formatMoney(order.getTotal()), getStatusByCode(order.getStatus()));
            System.out.printf("|" + RESET + "  Địa chỉ nhận hàng: %-30s                                                   " + BLUE + "|\n", order.getAddress());
            System.out.println("+------------------------------------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "         Tên sản phẩm         |        Giá        |     Số lượng     |         Tổng tiền sản phẩm     " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------------+");


            for (Integer key : order.getOrderDetail().keySet()) {
                System.out.printf("|" + RESET + "     %-22s   |   %-15s |        %-5d     |        %-20s    " + BLUE + "|\n",
                        iProductService.findById(key).getProductName(), formatMoney(iProductService.findById(key).getUnitPrice()), order.getOrderDetail().get(key), formatMoney(iProductService.findById(key).getUnitPrice() * order.getOrderDetail().get(key)));
            }

            System.out.println("+------------------------------------------------------------------------------------------------------+" + RESET);
            System.out.println(" ");
        }
    }

    private void showListCancle() {
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                                                      Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + "                                       ĐƠN HÀNG BỊ HỦY                                                " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + " MÃ ĐƠN HÀNG | MÃ NGƯỜI MUA |  THỜI GIAN MUA  |         TỔNG ĐƠN         |         TRẠNG THÁI         " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");

        boolean check = false;
        for (Order order : iOrderService.findAll()) {
            if (order.getStatus() == 2) {
                System.out.println(order);
                check = true;
            }
        }

        if (!check) {
            System.out.println("|" + RESET + "                                  " + RED + "Không có đơn hàng bị hủy!!!" + RESET + "                                         " + BLUE + "|");
        }


        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println(" ");
    }

    private void showListAcceptOrder() {
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                                                      Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + "                                     ĐƠN HÀNG ĐÃ XÁC NHẬN                                             " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + " MÃ ĐƠN HÀNG | MÃ NGƯỜI MUA |  THỜI GIAN MUA  |         TỔNG ĐƠN         |         TRẠNG THÁI         " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");

        boolean check = false;
        for (Order order : iOrderService.findAll()) {
            if (order.getStatus() == 1) {
                System.out.println(order);
                check = true;
            }
        }

        if (!check) {
            System.out.println("|" + RESET + "                                 " + RED + "Không có đơn hàng đã được xác nhận!!!" + RESET + "                                " + BLUE + "|");
        }


        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println(" ");
    }

    private void showListPendingOrder() {
        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                                                      Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + "                                     ĐƠN HÀNG CHƯA XÁC NHẬN                                           " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + " MÃ ĐƠN HÀNG | MÃ NGƯỜI MUA |  THỜI GIAN MUA  |         TỔNG ĐƠN         |         TRẠNG THÁI         " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+");

        boolean check = false;
        for (Order order : iOrderService.findAll()) {
            if (order.getStatus() == 0) {
//                System.out.printf("| %-5d | %-5d | %-15s | %-20s | %-20s |\n", order.getId(), order.getIdUser(), getCurrentYearMonth(order.getBuyDate()) , formatMoney(order.getTotal()), order.getStatus());
                System.out.println(order);
                check = true;
            }
        }

        if (!check) {
            System.out.println("|" + RESET + "                                 " + RED + "Không có đơn hàng chưa được xác nhận!!!" + RESET + "                              " + BLUE + "|");
        }


        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println(" ");
        System.out.println("+------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + "   1. Xem chi tiết đơn hàng    |   2. Xác nhận đơn hàng    |   3. Hủy đơn hàng    |   0. Quay lại     " + BLUE + "|");
        System.out.println("+------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn của bạn: ");
        System.out.println(" ");

        switch (Validate.validateInt()) {
            case 1:
                showInfoOrder();
                break;
            case 2:
                confirmOrder();
                break;
            case 3:
                cancelOrder();
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Lựa chọn không hợp lệ." + RESET);
                break;
        }


    }

    private void cancelOrder() {
        System.out.println("Nhập ID đơn hàng bạn muốn hủy: ");
        int id = Validate.validateInt();

        Order order = iOrderService.findById(id);
        if (order == null) {
            System.out.println(RED + "Không có ID đơn hàng bạn vừa nhập!!!" + RESET);
        } else {
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
                System.out.println(YELLOW+"Đơn hàng đã bị hủy."+RESET);
            } else if (order.getStatus() == 1 || order.getStatus() == 2) {
                System.out.println("Đơn hàng này " + getStatusByCode(order.getStatus()));
            }
        }
    }

    private void confirmOrder() {
        System.out.println("Nhập ID đơn hàng bạn muốn xác nhận: ");
        int id = Validate.validateInt();

        Order order = iOrderService.findById(id);
        if (order == null) {
            System.out.println(RED + "Không có ID đơn hàng bạn vừa nhập!!!" + RESET);
        } else {
            if (order.getStatus() == 0) {
                order.setStatus(1);
                iOrderService.save(order);
                System.out.println(YELLOW + "Đơn hàng đã được xác nhận." + RESET);
            } else if (order.getStatus() == 1 || order.getStatus() == 2) {
                System.out.println("Đơn hàng này " + getStatusByCode(order.getStatus()));
            }
        }
    }

    public void showInfoOrder() {
        System.out.println("Nhập ID đơn hàng bạn muốn xem chi tiết: ");
        int n = Validate.validateInt();

        System.out.println(BLUE + "+------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.printf(BLUE + "|" + RESET + "   \uD83E\uDD7E \uD83D\uDC5F " + YELLOW_BRIGHT + "T.SHOE" + RESET + " \uD83D\uDC5F \uD83D\uDC5E                                                      Xin chào: %-15s " + BLUE + "|\n", new Config<Users>().readFile(Config.URL_LOGIN).getName());
        System.out.println("+------------------------------------------------------------------------------------------------------+");

        Order order = iOrderService.findById(n);
        if (order == null) {
            System.out.println("|" + RESET + "                           " + RED + "ID đơn hàng bạn muốn xem chi tiết không tồn tại" + RESET + "                            " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------------+" + RESET);
            System.out.println(" ");

        } else {
//            System.out.println("+------------------------------------------------------------------------------------------------------+");
            System.out.printf("|" + RESET + "  ID đơn hàng: %-5d                                                                                  " + BLUE + "|\n", order.getId());
            System.out.printf("|" + RESET + "  Người đặt hàng: %-20s                    Người nhận hàng: %-20s       " + BLUE + "|\n", iUserService.findById(order.getIdUser()).getName(), order.getReceiver());
            System.out.printf("|" + RESET + "  Số điện thoại nhận hàng: %-15s                Ngày đặt hàng: %-15s              " + BLUE + "|\n", order.getNumberPhone(), getCurrentYearMonth(order.getBuyDate()));
            System.out.printf("|" + RESET + "  Tổng giá trị đơn hàng: %-15s                  Trạng thái đơn hàng: %-15s      " + BLUE + "|\n", formatMoney(order.getTotal()), getStatusByCode(order.getStatus()));
            System.out.printf("|" + RESET + "  Địa chỉ nhận hàng: %-30s                                                   " + BLUE + "|\n", order.getAddress());
            System.out.println("+------------------------------------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "         Tên sản phẩm         |        Giá        |     Số lượng     |         Tổng tiền sản phẩm     " + BLUE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------------+");


            for (Integer key : order.getOrderDetail().keySet()) {
                System.out.printf("|" + RESET + "     %-22s   |   %-15s |        %-5d     |        %-20s    " + BLUE + "|\n",
                        iProductService.findById(key).getProductName(), formatMoney(iProductService.findById(key).getUnitPrice()), order.getOrderDetail().get(key), formatMoney(iProductService.findById(key).getUnitPrice() * order.getOrderDetail().get(key)));
            }

            System.out.println("+------------------------------------------------------------------------------------------------------+" + RESET);
            System.out.println(" ");
        }
    }


}
