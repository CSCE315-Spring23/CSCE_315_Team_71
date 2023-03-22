package chickfila.logic;

/**
*
* The OrderItem class represents an item in an order.
*/
public class OrderItem {

    private String name;
    private int id;
    private int quantity;

    /**
    *
    * Constructs an OrderItem with the specified name and ID.
    * @param name the name of the item
    * @param id the ID of the item
    */
    public OrderItem(String name, int id) {
        this.name = name;
        this.id = id;
        this.quantity = 1;
    }

    /**
    *
    * Increments the quantity of the item by 1.
    */
    public void incrementQuantity() {
        quantity++;
    }

    /**
    *
    * Returns the name of the item.
    * @return the name of the item
    */
    public String getName() {
        return name;
    }

    /**
    *
    * Returns the ID of the item.
    * @return the ID of the item
    */
    public int getID() {
        return id;
    }

    /**
    *
    * Returns the quantity of the item.
    * @return the quantity of the item
    */
    public int getQuantity() {
        return quantity;
    }

}
