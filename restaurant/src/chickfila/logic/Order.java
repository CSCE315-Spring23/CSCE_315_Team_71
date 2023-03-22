package chickfila.logic;

import java.util.*;



/**

The Order class represents an order made by a customer in a restaurant.

It keeps track of the order items and the total price of the order.

It also provides methods to add items to the order, reset the order,

get the price of the order, check if the order has been paid, and

get a list of the order items.
*/
public class Order {

    private double price;
    private boolean paid;
    private HashMap<Integer, String[]> menu;
    private ArrayList<OrderItem> items;

    /**
     * Constructs a new Order object with the specified menu.
     * @param menu the menu containing the items and their prices
    */
    public Order(HashMap<Integer, String[]> menu) {
        this.price = 0;
        this.menu = menu;
        this.paid = false;

        items = new ArrayList<OrderItem>();
    }

    /**
    * Adds the specified OrderItem to this order.

    * @param item the OrderItem to add to the order
    */
    public void addItem(OrderItem item) {

        price += (Double.parseDouble(menu.get(item.getID())[1]))* 1.0825 ;

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

    /**
    Resets the order by setting the price to 0 and clearing the items list.
    */
    public void resetOrder() {
        price = 0;

        items.clear();
    }

    /**
    Returns the total price of the order.
    @return the total price of the order
    */
    public double getPrice() {
        return price;
    }

    /**
    Returns true if the order has been paid, false otherwise.
    @return true if the order has been paid, false otherwise
    */
    public boolean isPaid() {
        return paid;
    }

    /**
    Returns true if the order has no items, false otherwise.
    @return true if the order has no items, false otherwise
    */
    public boolean isEmpty() {
        return items.size() == 0;
    }

    /**
    Returns a list of the OrderItems in this order.
    @return a list of the OrderItems in this order
    */
    public ArrayList<OrderItem> getOrderItems() {
        return items;
    }
}
