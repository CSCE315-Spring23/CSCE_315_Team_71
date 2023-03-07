package chickfila.logic;

import java.util.*;

public class Order {

    private double price;
    private boolean isPaid;
    private HashMap<Integer, String[]> menu;
    private ArrayList<OrderItem> items;

    public Order(HashMap<Integer, String[]> menu) {
        this.price = 0;
        this.menu = menu;
        this.isPaid = false;

        items = new ArrayList<OrderItem>();
    }

    public void addItem(OrderItem item) {

        price += Double.parseDouble(menu.get(item.getID())[1]);
        items.add(item);
    }

    public void resetOrder() {
        price = 0;

        items.clear();
    }

    public double getPrice() {
        return price;
    }

}
