package chickfila.logic;

public class OrderItem {

    private String name;
    private int id;
    private int quantity;

    public OrderItem(String name, int id) {
        this.name = name;
        this.id = id;
        this.quantity = 1;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

}
