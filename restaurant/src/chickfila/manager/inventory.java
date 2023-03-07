package chickfila.manager;

import java.util.Objects;
public class inventory {
    private int inventory_item_id;
    private int quantity;
    private String inventory_item_name;



    public inventory() {
    }

    public inventory(int inventory_item_id, int quantity, String nameString) {
        this.inventory_item_id = inventory_item_id;
        this.quantity = quantity;
        this.inventory_item_name = nameString;
    }

    public int getInventory_item_id() {
        return this.inventory_item_id;
    }

    public void setInventory_item_id(int inventory_item_id) {
        this.inventory_item_id = inventory_item_id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInventory_item_name() {
        return this.inventory_item_name;
    }

    public void setInventory_item_name(String nameString) {
        this.inventory_item_name = nameString;
    }


    public inventory inventory_item_id(int inventory_item_id) {
        setInventory_item_id(inventory_item_id);
        return this;
    }

    public inventory quantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    public inventory inventory_item_name(String inventory_item_name) {
        inventory_item_name(inventory_item_name);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof inventory)) {
            return false;
        }
        inventory inventory = (inventory) o;
        return inventory_item_id == inventory.inventory_item_id && Objects.equals(inventory_item_name, inventory.inventory_item_name) && Objects.equals(quantity, inventory.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventory_item_id, quantity, inventory_item_name);
    }

    @Override
    public String toString() {
        return "{" +
            " menu_item_id='" + getInventory_item_id() + "'" +
            ", menu_item_name='" + getQuantity() + "'" +
            ", menu_item_price='" + getInventory_item_name() + "'" +
            "}";
    }
}
