package chickfila.logic;

import java.util.Objects;
/**

The menuItems class represents a menu item in a restaurant. It contains information such as the menu item id, name, price,

size, and whether it is a meal or not.
*/
public class menuItems {
    private int menu_item_id;
    private String menu_item_name;
    private Double menu_item_price;
    private String size;
    private Boolean is_meal;
/**

Default constructor for the menuItems class.
*/
    public menuItems() {
    }
    /**

Constructor for the menuItems class.
@param menu_item_id The menu item's unique ID.
@param menu_item_name The name of the menu item.
@param menu_item_price The price of the menu item.
@param size The size of the menu item.
@param is_meal A boolean value indicating whether the menu item is a meal or not.
*/

    public menuItems(int menu_item_id, String menu_item_name, Double menu_item_price, String size, Boolean is_meal) {
        this.menu_item_id = menu_item_id;
        this.menu_item_name = menu_item_name;
        this.menu_item_price = menu_item_price;
        this.size = size;
        this.is_meal = is_meal;
    }
/**

Returns the menu item's unique ID.
@return The menu item's unique ID.
*/
    public int getMenu_item_id() {
        return this.menu_item_id;
    }
/**

Sets the menu item's unique ID.
@param menu_item_id The menu item's unique ID.
*/
    public void setMenu_item_id(int menu_item_id) {
        this.menu_item_id = menu_item_id;
    }
/**

Returns the name of the menu item.
@return The name of the menu item.
*/
    public String getMenu_item_name() {
        return this.menu_item_name;
    }
/**

Sets the name of the menu item.
@param menu_item_name The name of the menu item.
*/
    public void setMenu_item_name(String menu_item_name) {
        this.menu_item_name = menu_item_name;
    }
/**

Returns the price of the menu item.
@return The price of the menu item.
*/
    public Double getMenu_item_price() {
        return this.menu_item_price;
    }
/**

Sets the price of the menu item.
@param menu_item_price The price of the menu item.
*/
    public void setMenu_item_price(Double menu_item_price) {
        this.menu_item_price = menu_item_price;
    }
/**

Returns the size of the menu item.
@return The size of the menu item.
*/
    public String getSize() {
        return this.size;
    }
/**

Sets the size of the menu item.
@param size The size of the menu item.
*/
    public void setSize(String size) {
        this.size = size;
    }
/**

Returns a boolean value indicating whether the menu item is a meal or not.
@return A boolean value indicating whether the menu item is a meal or not.
*/
    public Boolean getIs_meal() {
        return this.is_meal;
    }
/**

Sets a boolean value indicating whether the menu item is a meal or not.
@param isMeal A boolean value indicating whether the menu item is a meal or not.
*/
    public void setIsMeal(Boolean isMeal) {
        this.is_meal = isMeal;
    }
/**

Sets the menu item id for this menu item.
@param menu_item_id the menu item id to set
@return a reference to this menuItems object
*/
    public menuItems menu_item_id(int menu_item_id) {
        setMenu_item_id(menu_item_id);
        return this;
    }
/**

Sets the menu item name for this menu item.
@param menu_item_name the menu item name to set
@return a reference to this menuItems object
*/
    public menuItems menu_item_name(String menu_item_name) {
        setMenu_item_name(menu_item_name);
        return this;
    }
/**

Sets the menu item price for this menu item.
@param menu_item_price the menu item price to set
@return a reference to this menuItems object
*/
    public menuItems menu_item_price(Double menu_item_price) {
        setMenu_item_price(menu_item_price);
        return this;
    }
/**

Sets the size for this menu item.
@param size the size to set
@return a reference to this menuItems object
*/
    public menuItems size(String size) {
        setSize(size);
        return this;
    }
/**

Sets whether this menu item is a meal or not.
@param isMeal true if this menu item is a meal, false otherwise
@return a reference to this menuItems object
*/
    public menuItems isMeal(Boolean isMeal) {
        setIsMeal(isMeal);
        return this;
    }
/**

The equals() method checks if this object is equal to another object.
Two menu items are considered equal if they have the same menu item ID,
menu item name, menu item price, size, and is_meal.
@param o the object to compare
@return true if the objects are equal, false otherwise
*/
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof menuItems)) {
            return false;
        }
        menuItems menuItems = (menuItems) o;
        return menu_item_id == menuItems.menu_item_id && Objects.equals(menu_item_name, menuItems.menu_item_name)
                && Objects.equals(menu_item_price, menuItems.menu_item_price) && Objects.equals(size, menuItems.size)
                && Objects.equals(is_meal, menuItems.is_meal);
    }
/**

The hashCode() method returns the hash code value for this menu item.
The hash code value is computed using the menu item ID, menu item name,
menu item price, size, and is_meal fields.
@return the hash code value for this menu item
*/
    @Override
    public int hashCode() {
        return Objects.hash(menu_item_id, menu_item_name, menu_item_price, size, is_meal);
    }
/**

The toString() method returns a string representation of this menu item.
The string contains the menu item ID, menu item name, menu item price,
size, and is_meal fields.
@return a string representation of this menu item
*/
    @Override
    public String toString() {
        return "{" +
                " menu_item_id='" + getMenu_item_id() + "'" +
                ", menu_item_name='" + getMenu_item_name() + "'" +
                ", menu_item_price='" + getMenu_item_price() + "'" +
                ", size='" + getSize() + "'" +
                ", isMeal='" + getIs_meal() + "'" +
                "}";
    }
}
