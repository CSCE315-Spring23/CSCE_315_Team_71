package chickfila.manager;

import java.util.Objects;

public class menuItems {
    private int menu_item_id;
    private String menu_item_name;
    private Double menu_item_price;
    private String size;
    private Boolean is_meal;

    public menuItems() {
    }

    public menuItems(int menu_item_id, String menu_item_name, Double menu_item_price, String size, Boolean is_meal) {
        this.menu_item_id = menu_item_id;
        this.menu_item_name = menu_item_name;
        this.menu_item_price = menu_item_price;
        this.size = size;
        this.is_meal = is_meal;
    }

    public int getMenu_item_id() {
        return this.menu_item_id;
    }

    public void setMenu_item_id(int menu_item_id) {
        this.menu_item_id = menu_item_id;
    }

    public String getMenu_item_name() {
        return this.menu_item_name;
    }

    public void setMenu_item_name(String menu_item_name) {
        this.menu_item_name = menu_item_name;
    }

    public Double getMenu_item_price() {
        return this.menu_item_price;
    }

    public void setMenu_item_price(Double menu_item_price) {
        this.menu_item_price = menu_item_price;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getIs_meal() {
        return this.is_meal;
    }

    public void setIsMeal(Boolean isMeal) {
        this.is_meal = isMeal;
    }

    public menuItems menu_item_id(int menu_item_id) {
        setMenu_item_id(menu_item_id);
        return this;
    }

    public menuItems menu_item_name(String menu_item_name) {
        setMenu_item_name(menu_item_name);
        return this;
    }

    public menuItems menu_item_price(Double menu_item_price) {
        setMenu_item_price(menu_item_price);
        return this;
    }

    public menuItems size(String size) {
        setSize(size);
        return this;
    }

    public menuItems isMeal(Boolean isMeal) {
        setIsMeal(isMeal);
        return this;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(menu_item_id, menu_item_name, menu_item_price, size, is_meal);
    }

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
