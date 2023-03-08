package chickfila.logic;

import java.util.*;

public class Order {

    private double price;
    private boolean paid;
    private HashMap<Integer, String[]> menu;
    private ArrayList<OrderItem> items;

    public Order(HashMap<Integer, String[]> menu) {
        this.price = 0;
        this.menu = menu;
        this.paid = false;

        items = new ArrayList<OrderItem>();
    }

    public void addItem(OrderItem item) {

        price += Double.parseDouble(menu.get(item.getID())[1]);

        boolean duplicate = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(item.getName())) {
                items.get(i).incrementQuantity();
                duplicate = true;
                break;
            }
            
        }

        if (!duplicate) {
            items.add(item);
        }
    }

    public void resetOrder() {
        price = 0;

        items.clear();
    }

    public double getPrice() {
        return price;
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isEmpty() {
        return items.size() == 0;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return items;
    }
}
