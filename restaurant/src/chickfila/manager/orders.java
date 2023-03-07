package chickfila.manager;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class orders {
    private int orderId;
    private double price;
    private boolean paid;
    private Timestamp orderTime;

    public orders() {
    }

    public orders(int orderId, double price, boolean paid, Timestamp orderTime) {
        this.orderId = orderId;
        this.price = price;
        this.paid = paid;
        this.orderTime = orderTime;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getPaid() {
        return this.paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Timestamp getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }
    public orders orderId(int orderId) {
        setOrderId(orderId);;
        return this;
    }

    public orders price(double price) {
        setPrice(price);
        return this;
    }

    public orders paid(Boolean paid) {
        setPaid(paid);
        return this;
    }

    public orders orderTime(Timestamp orderTime) {
        setOrderTime(orderTime);
        return this;
    }
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof orders)) {
            return false;
        }
        orders order = (orders) o;
        return orderId == order.orderId && Objects.equals(price, order.price) && Objects.equals(paid, order.paid) && Objects.equals(orderTime, order.orderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, price, paid, orderTime);
    }

    @Override
    public String toString() {
        return "{" +
            " menu_item_id='" + getOrderId() + "'" +
            ", menu_item_name='" + getPrice() + "'" +
            ", menu_item_price='" + getPaid() + "'" +
            ", menu_item_price='" + getOrderTime() + "'" +
            "}";
    }
}
