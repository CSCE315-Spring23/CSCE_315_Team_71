package chickfila.logic;

import java.util.*;

public class Order {

    private double price;
    private boolean isPaid;
    private HashMap<String, Double> menu;

    public Order(HashMap<String, Double> menu) {
        this.price = 0;

        this.menu = menu;
    }

    public void addItem(OrderItem item) {

        price += menu.get(item.getName());
    }
}
