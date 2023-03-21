package chickfila.manager;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import chickfila.Controller;
import chickfila.logic.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import chickfila.logic.DB;
import chickfila.logic.dbSetup;
import chickfila.logic.sales;
import chickfila.logic.orders;

public class ControllerSales {

    private DB conn;
    private HashMap<Integer, String[]> menu;

    @FXML
    private TableView<sales> tableView;
    @FXML
    private Button backButton, loadButton;
    @FXML
    private TableColumn<sales, Integer> idColumn;
    @FXML
    private TableColumn<sales, String> dateColumn;
    @FXML
    private TableColumn<sales, Double> totalSalesColumn, totalTaxColumn;
    @FXML
    private TextField createSalesReport;

    @FXML
    private TabPane tabs;
    @FXML
    private TableView<String[]> view;
    @FXML
    private AnchorPane p;
    @FXML
    private TextField firstDate, secondDate;
    @FXML
    private Button getAmount;
    @FXML
    TableColumn<String[], String> nameCol, soldCol, mealCol, sizeCol;
    @FXML
    private Button makeXreport;
    @FXML 
    private Text showSales;
    @FXML 
    private Text showTax;
    @FXML 
    private Text showDate;
    @FXML
    private TableView <String[] > xTable;
    @FXML 
    private TableColumn<String[] , String> xTotalSales, xDate , xTotalTax;
    protected String salesForXReport;


    public ControllerSales(DB conn, HashMap<Integer, String[]> menu) {
        this.conn = conn;
        this.menu = menu;
    }

    public void closeButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./manager.fxml"));
        ControllerManager c = new ControllerManager(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    public void initialize() throws SQLException, IOException {


        salesForXReport = "";
        ResultSet salesFetch = conn.select("select * from sales order by sales_id desc limit 1;");
        showDate.setText("");
        showTax.setText("");
        showSales.setText("");
        while (salesFetch.next()) {
            //int salesID = salesFetch.getInt("sales_id");
            salesForXReport = salesFetch.getString("sales_date");
            //double cost = salesFetch.getDouble("total_sales");
            //double tax = salesFetch.getDouble("total_tax");
            //sales sale = new sales(salesID, date, cost, tax);
            //data.add(sale);
        }
        System.out.println(salesForXReport);
        loadSales();

        getAmount.setOnAction(event -> {
            try {
                loadItems(firstDate.getText(), secondDate.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            firstDate.setText(null);
            secondDate.setText(null);
        });
    }

    /**
     * 
     * Loads sales data from the database and populates the table view with the
     * data.
     * 
     * Sets the cell value factories for the table columns.
     * 
     * @throws SQLException if there is an error executing the SQL query
     * 
     * @throws IOException  if there is an error with the input/output operations
     */
    private void loadSales() throws SQLException, IOException {

        idColumn.setCellValueFactory(new PropertyValueFactory<sales, Integer>("sales_id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<sales, String>("sales_date"));
        totalSalesColumn.setCellValueFactory(new PropertyValueFactory<sales, Double>("total_sales"));
        totalTaxColumn.setCellValueFactory(new PropertyValueFactory<sales, Double>("total_tax"));

        ResultSet salesFetch = conn.select("SELECT * FROM sales;");

        ObservableList<sales> data = FXCollections.observableArrayList();

        while (salesFetch.next()) {
            int salesID = salesFetch.getInt("sales_id");
            String date = salesFetch.getString("sales_date");
            double cost = salesFetch.getDouble("total_sales");
            double tax = salesFetch.getDouble("total_tax");
            sales sale = new sales(salesID, date, cost, tax);
            data.add(sale);
        }
        tableView.setItems(data);
    }

    // Adds Functionality to create a sales Report for a specific day
    /**
     * 
     * Adds a new sales report to the database.
     * 
     * Calculates profits and taxes based on the total sales for the given date.
     * 
     * Loads updated sales data into the table view.
     * 
     * @param event the action event triggered by the user
     * 
     * @throws SQLException if there is an error executing the SQL query
     * 
     * @throws IOException  if there is an error with the input/output operations
     */
    public void addSalesReport(ActionEvent event) throws SQLException, IOException {
        double profits;
        double taxes;
        Button b = (Button)event.getSource();        

        if (createSalesReport.getText().equals("") && (b.getId()).equals("addSalesRep")) {
            System.out.println("input a date");
        } else {
            // int itemId = Integer.parseInt(inventoryId.getText());
            // int quantity_query = Integer.parseInt(quantity.getText());
            // String name = itemName.getText();
            Integer max_id = 0;
            ResultSet sizeFetch = conn.select("SELECT MAX(sales_id) FROM sales;");
            while (sizeFetch.next()) {
                max_id = sizeFetch.getInt("max");
            }

            Integer newSalesRepId = max_id + 1;
            Double sales;
            String date;

            if((b.getId()).contains("ZReport")) {
                date = (java.time.LocalDate.now()).toString();
                salesForXReport = date;
            }
            else {
                date = createSalesReport.getText();
            }

            sales = getTotalDaySales(date);
            taxes = sales * 0.0825;
            profits = sales - taxes;

            conn.performQuery("INSERT INTO sales (sales_id, sales_date, total_sales, total_tax) VALUES ("
                    + newSalesRepId + ",'" + date + "' , " + profits + " , " + taxes + ");");
            loadSales();

        }
    }



    public void addXReport(ActionEvent event) throws SQLException, IOException {
        double profits;
        double taxes;

        ResultSet total = conn.select("SELECT SUM(price) FROM orders WHERE orders.order_time::date > '"+ salesForXReport+"';");
        Double total_price = 0.0;
        Double tax = 0.0;
        while (total.next()){
            total_price = total.getDouble("sum");

        }
        ResultSet ordersFetch = conn.select("SELECT * FROM orders WHERE orders.order_time::date > '"+ salesForXReport+"';");
        String orderTime = "";
        System.out.println(total_price);
        while (ordersFetch.next()) {
            int orderId = ordersFetch.getInt("order_id");
            double price = ordersFetch.getDouble("price");
            Boolean paid = ordersFetch.getBoolean("is_paid");
            orderTime = ordersFetch.getString("order_time");
            // addProfit += price;
            //orders menu = new orders(orderId, price, paid, orderTime);
            // data.add(menu);
           // System.out.println(menu);
        }
        
        tax = total_price * 0.0825;
        xTable.getItems().clear();
        // showDate.setText(orderTime);
        // showTax.setText(Double.toString(tax));
        // showSales.setText(Double.toString(total_price));

        String[] columnItems = new String[3];
        // columnItems[0] = showSales.getText();
        // columnItems[1]= showDate.getText();
        // columnItems[2] = showTax.getText();

        columnItems[0] = Double.toString(total_price);
        columnItems[1]= orderTime;
        columnItems[2] = Double.toString(tax);
        xTable.getItems().add(columnItems);

        xTotalSales.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        xDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        xTotalTax.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        //sizeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));




        }
    

   

    /**
     * 
     * Calculates the total sales for a given day by summing the price of all orders
     * made on that day.
     * 
     * @param day the date for which to calculate the total sales (in the format
     *            "yyyy-MM-dd")
     * 
     * @return the total sales for the given day
     * 
     * @throws SQLException if there is an error executing the SQL query
     * 
     * @throws IOException  if there is an error with the input/output operations
     */

    public double getTotalDaySales(String day) throws SQLException, IOException {
        double dailySales = 0;
        // conn = new DB(dbSetup.user, dbSetup.pswd);

        ResultSet ordersFetch = conn
                .select("SELECT SUM(price) FROM orders WHERE orders.order_time::date = '" + day + "';");

        while (ordersFetch.next()) {
            dailySales = ordersFetch.getDouble(1);
        }

        return dailySales;
    }


    public void loadItems(String start, String end) throws SQLException {

        view.getItems().clear();
        
        ResultSet itemSoldAmount = conn.select(
                "SELECT a.size, a.is_meal, a.menu_item_name, SUM(a.count) AS count FROM " +
                "(SELECT size, is_meal, menu_item_name, quantity*count(*) AS count FROM order_items JOIN orders ON " +
                        "orders.order_id=order_items.order_id JOIN menu_items ON order_items.menu_item_id = menu_items.menu_item_id "
                        +
                        "WHERE order_time::date >= '" + start + "' AND order_time::date <= '" + end
                        + "' GROUP BY menu_item_name, is_meal, size, quantity ORDER BY menu_item_name) a GROUP BY menu_item_name, is_meal, size;");

        while (itemSoldAmount.next()) {
            String[] data = new String[4];
            data[0] = itemSoldAmount.getString("menu_item_name");
            data[1] = itemSoldAmount.getString("count") ;            
            data[2] = itemSoldAmount.getString("is_meal");
            data[3] = itemSoldAmount.getString("size");
            view.getItems().add(data);

        }

        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        soldCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        mealCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        sizeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
    }
}