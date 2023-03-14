package chickfila.logic;

import java.util.Objects;

public class sales {
    private int sales_id;
    private String sales_date;
    private double total_sales;
    private double total_tax;


    public sales() {
    }

    public sales(int sales_id, String sales_date, double total_sales, double total_tax) {
        this.sales_id = sales_id;
        this.sales_date = sales_date;
        this.total_sales = total_sales;
        this.total_tax = total_tax;
    }

    public int getSales_id() {
        return this.sales_id;
    }

    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
    }

    public String getSales_date() {
        return this.sales_date;
    }

    public void setSales_date(String sales_date) {
        this.sales_date = sales_date;
    }

    public double getTotal_sales() {
        return this.total_sales;
    }

    public void setTotal_sales(double total_sales) {
        this.total_sales = total_sales;
    }

    public double getTotal_tax() {
        return this.total_tax;
    }

    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    public sales sales_id(int sales_id) {
        setSales_id(sales_id);
        return this;
    }

    public sales sales_date(String sales_date) {
        setSales_date(sales_date);
        return this;
    }

    public sales total_sales(double total_sales) {
        setTotal_sales(total_sales);
        return this;
    }

    public sales total_tax(double total_tax) {
        setTotal_tax(total_tax);
        return this;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(sales_id, sales_date, total_sales, total_tax);
    }

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
