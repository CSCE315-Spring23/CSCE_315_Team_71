package chickfila.logic;

import java.util.Objects;

/**

The inventory class represents an inventory item that includes its id, quantity, name, and minimum amount.
*/

public class inventory {
    private int inventory_item_id;
    private int quantity;
    private String inventory_item_name;
    private int min_amount;

    public inventory() {
    }
/**

Creates an inventory object with the specified id, quantity, name, and minimum amount.
@param inventory_item_id the id of the inventory item
@param quantity the quantity of the inventory item
@param inventory_item_name the name of the inventory item
@param min_amount the minimum amount of the inventory item
*/
    public inventory(int inventory_item_id, int quantity, String nameString, int min_amount) {
        this.inventory_item_id = inventory_item_id;
        this.quantity = quantity;
        this.inventory_item_name = nameString;
        this.min_amount = min_amount;
    }
/**

Gets the minimum amount of the inventory item.
@return the minimum amount of the inventory item
*/

    public int getMin_amount() {
        return this.min_amount;
    }
/**

Sets the minimum amount of the inventory item.
@param min_amount the minimum amount of the inventory item to set
*/
    public void setMin_amount() {
        this.min_amount = min_amount;
    }
/**

Gets the id of the inventory item.
@return the id of the inventory item
*/
    public int getInventory_item_id() {
        return this.inventory_item_id;
    }
/**

Sets the id of the inventory item.
@param inventory_item_id the id of the inventory item to set
*/
    public void setInventory_item_id(int inventory_item_id) {
        this.inventory_item_id = inventory_item_id;
    }
/**

Gets the quantity of the inventory item.
@return the quantity of the inventory item
*/
    public int getQuantity() {
        return this.quantity;
    }
/**

Sets the quantity of the inventory item.
@param quantity the quantity of the inventory item to set
*/
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
/**

Gets the name of the inventory item.
@return the name of the inventory item
*/
    public String getInventory_item_name() {
        return this.inventory_item_name;
    }
/**

Sets the name of the inventory item.
@param inventory_item_name the name of the inventory item to set
*/
    public void setInventory_item_name(String nameString) {
        this.inventory_item_name = nameString;
    }
/**

Sets the id of the inventory item and returns this inventory object.
@param inventory_item_id the id of the inventory item to set
@return this inventory object
*/
    public inventory inventory_item_id(int inventory_item_id) {
        setInventory_item_id(inventory_item_id);
        return this;
    }
/**

Sets the quantity of the inventory item and returns this inventory object.
@param quantity the quantity of the inventory item to set
@return this inventory object
*/
    public inventory quantity(int quantity) {
        setQuantity(quantity);
        return this;
    }
/**

Sets the name of the inventory item and returns this inventory object.
@param quantity inventory item name
@return this inventory object
*/
    public inventory inventory_item_name(String inventory_item_name) {
        inventory_item_name(inventory_item_name);
        return this;
    }
/**

Compares this inventory object to another object to determine if they are equal. Equality is determined by comparing
the inventory_item_id, inventory_item_name, quantity, and min_amount fields.
@param o the object to compare to
@return true if the objects are equal, false otherwise
*/
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof inventory)) {
            return false;
        }
        inventory inventory = (inventory) o;
        return inventory_item_id == inventory.inventory_item_id
                && Objects.equals(inventory_item_name, inventory.inventory_item_name)
                && Objects.equals(quantity, inventory.quantity) && Objects.equals(min_amount, inventory.min_amount);
    }
/**

Returns a hash code value for the inventory object, computed using its inventory item ID, quantity, and inventory item name.
@return a hash code value for the inventory object
*/

    @Override
    public int hashCode() {
        return Objects.hash(inventory_item_id, quantity, inventory_item_name);
    }
/**

Returns a string representation of the inventory object, formatted as a JSON object with the inventory item ID, quantity, and inventory item name.
@return a string representation of the inventory object
*/
    @Override
    public String toString() {
        return "{" +
                " menu_item_id='" + getInventory_item_id() + "'" +
                ", menu_item_name='" + getQuantity() + "'" +
                ", menu_item_price='" + getInventory_item_name() + "'" +
                "}";
    }
}
