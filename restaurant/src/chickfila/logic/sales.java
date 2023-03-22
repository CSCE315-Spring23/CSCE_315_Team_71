package chickfila.logic;

import java.util.Objects;

/**
*
* The sales class represents a sales record in a system. It contains information such as the ID of the sale,
*
* the date of the sale, the total sales amount, and the total tax amount.
*/
public class sales {
    private int sales_id;
    private String sales_date;
    private double total_sales;
    private double total_tax;

    /**
    *
    * Constructs an empty sales object.
    */
    public sales() {
    }

    /**
    * 
    * Constructs a sales object with the specified sales ID, sales date, total sales amount, and total tax amount.
    * @param sales_id the ID of the sale
    * @param sales_date the date of the sale
    * @param total_sales the total sales amount
    * @param total_tax the total tax amount
    */
    public sales(int sales_id, String sales_date, double total_sales, double total_tax) {
        this.sales_id = sales_id;
        this.sales_date = sales_date;
        this.total_sales = total_sales;
        this.total_tax = total_tax;
    }

    /**
    * 
    * Returns the ID of the sale.
    * @return the ID of the sale
    */
    public int getSales_id() {
        return this.sales_id;
    }

    /**
    * 
    * Sets the ID of the sale.
    * @param sales_id the ID of the sale
    */
    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
    }

    /**
    * 
    * Returns the date of the sale.
    * @return the date of the sale
    */
    public String getSales_date() {
        return this.sales_date;
    }

    /**
    * Sets the date of the sale.
    * @param sales_date the date of the sale
    */
    public void setSales_date(String sales_date) {
        this.sales_date = sales_date;
    }

    /**
    * 
    * Returns the total sales amount.
    * @return the total sales amount
    */
    public double getTotal_sales() {
        return this.total_sales;
    }

    /**
    *
    * Sets the total sales amount.
    * @param total_sales the total sales amount
    */
    public void setTotal_sales(double total_sales) {
        this.total_sales = total_sales;
    }

    /**
    * 
    * Returns the total tax amount.
    * @return the total tax amount
    */
    public double getTotal_tax() {
        return this.total_tax;
    }

    /**
    *
    * Sets the total tax amount.
    * @param total_tax the total tax amount
    */
    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    /**
    *
    * Sets the ID of the sale and returns the modified sales object.
    * @param sales_id the ID of the sale
    * @return the modified sales object
    */
    public sales sales_id(int sales_id) {
        setSales_id(sales_id);
        return this;
    }

    /**
    * 
    * Sets the date of the sale and returns the modified sales object.
    * @param sales_date the date of the sale
    * @return the modified sales object
    */
    public sales sales_date(String sales_date) {
        setSales_date(sales_date);
        return this;
    }

    /**
    * 
    * Sets the total sales amount for this sales object and returns the sales object.
    * @param total_sales the total sales amount to set for this sales object
    * @return the sales object with the updated total sales amount
    */
    public sales total_sales(double total_sales) {
        setTotal_sales(total_sales);
        return this;
    }

    /**
    *
    * Sets the total tax amount for this sales object and returns the sales object.
    * @param total_tax the total tax amount to set for this sales object
    * @return the sales object with the updated total tax amount
    */
    public sales total_tax(double total_tax) {
        setTotal_tax(total_tax);
        return this;
    }

    /**
    *
    * Determines whether the given object is equal to this sales object. Two sales objects are considered equal
    * if they have the same sales ID, sales date, total sales amount, and total tax amount.
    * @param o the object to compare to this sales object
    * @return true if the given object is equal to this sales object, false otherwise
    */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof sales)) {
            return false;
        }
        sales sales = (sales) o;
        return sales_id == sales.sales_id && Objects.equals(sales_date, sales.sales_date) && total_sales == sales.total_sales && total_tax == sales.total_tax;
    }

    /**
    *
    * Returns the hash code value for this sales object.
    * @return the hash code value for this sales object
    */
    @Override
    public int hashCode() {
        return Objects.hash(sales_id, sales_date, total_sales, total_tax);
    }

    /**
    *
    * Returns a string representation of this sales object.
    * @return a string representation of this sales object
    */
    @Override
    public String toString() {
        return "{" +
            " sales_id='" + getSales_id() + "'" +
            ", sales_date='" + getSales_date() + "'" +
            ", total_sales='" + getTotal_sales() + "'" +
            ", total_tax='" + getTotal_tax() + "'" +
            "}";
    }

}
