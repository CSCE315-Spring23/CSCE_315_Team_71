package chickfila.logic;

public class OrderItem {

    private String name;
    private int id;

    public OrderItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }
}
