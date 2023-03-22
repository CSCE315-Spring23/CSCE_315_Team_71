package chickfila.logic;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;


/**
The orders class represents an order, containing the order ID, price, payment status, and timestamp.
*/

public class orders {
    private int orderId;
    private double price;
    private boolean paid;
    private Timestamp orderTime;

    public orders() {
    }

    /**
     * Constructs an order with the specified order ID, price, payment status, and timestamp.
     * @param orderId the order ID
     * @param price the price of the order
     * @param paid the payment status of the order
     * @param orderTime the timestamp of when the order was placed
     */
    public orders(int orderId, double price, boolean paid, Timestamp orderTime) {
        this.orderId = orderId;
        this.price = price;
        this.paid = paid;
        this.orderTime = orderTime;
    }

    /**
     * Returns the order ID.
     * @return the order ID
     */
    public int getOrderId() {
        return this.orderId;
    }

    /**
     * Sets the order ID.
     * @param orderId the new order ID
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Returns the price of the order.
     * @return the price of the order
     */
    public double getPrice() {
        return this.price;
    }
    
    /**
     * Sets the price of the order.
     * @param price the new price of the order
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns the payment status of the order.
     * @return the payment status of the order
     */
    public Boolean getPaid() {
        return this.paid;
    }

    /**
     * Sets the payment status of the order.
     * @param paid the new payment status of the order
     */
    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    /**
     * Returns the timestamp of when the order was placed.
     * @return the timestamp of when the order was placed
     */
    public Timestamp getOrderTime() {
        return this.orderTime;
    }

    /**
     * Sets the timestamp of when the order was placed.
     * @param orderTime the new timestamp of when the order was placed
     */
    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * Sets the order ID and returns the order.
     * @param orderId the new order ID
     * @return the order with the new order ID
     */
    public orders orderId(int orderId) {
        setOrderId(orderId);;
        return this;
    }

    /**
     * Sets the price of the order and returns the order.
     * @param price the new price of the order
     * @return the order with the new price
     */
    public orders price(double price) {
        setPrice(price);
        return this;
    }

    /**
     * Sets the payment status of the order and returns the order.
     * @param paid the new payment status of the order
     * @return the order with the new payment status
     */
    public orders paid(Boolean paid) {
        setPaid(paid);
        return this;
    }

    /**
    Sets the order time of the current order object and returns the object.
    @param orderTime the new order time to set
    @return the current order object with the new order time
    */
    public orders orderTime(Timestamp orderTime) {
        setOrderTime(orderTime);
        return this;
    }

    /**
    Compares this orders object with the specified object for equality.
    @param o the object to be compared with this orders object for equality.
    @return true if the specified object is equal to this orders object, false otherwise.
    */
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

    /**
    Returns a hash code value for this orders object.
    @return a hash code value for this orders object.
    */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, price, paid, orderTime);
    }

    /**
    Returns a string representation of this orders object.
    @return a string representation of this orders object.
    */
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
