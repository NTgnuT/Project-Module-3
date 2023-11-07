package service.orderService;

import config.Config;
import model.Order.Order;
import model.category.Category;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceIMPL implements IOrderService{
    static Config<List<Order>> config = new Config<>();

    static List<Order> orderList;
    static {
        orderList = config.readFile(Config.URL_ORDER);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
    }

    @Override
    public List<Order> findAll() {
        return orderList;
    }

    @Override
    public void save(Order order) {
        if (findById(order.getId()) == null) {
            orderList.add(order);
            updateData();
        } else {
            orderList.set(orderList.indexOf(order), order);
            updateData();
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Order findById(int id) {
        for (Order order : orderList) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Order order : orderList) {
            if (order.getId() > idMax) {
                idMax = order.getId();
            }
        }
        return idMax + 1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_ORDER, orderList);
    }

    @Override
    public Order findByName(String name) {
        return null;
    }
}
