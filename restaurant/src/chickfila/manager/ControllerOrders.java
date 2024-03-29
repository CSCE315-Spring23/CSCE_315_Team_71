package chickfila.manager;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import chickfila.Controller;
import chickfila.logic.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import chickfila.logic.DB;
import chickfila.logic.dbSetup;
import chickfila.logic.orders;
import javafx.scene.control.TextField;

public class ControllerOrders {
    private DB conn;

    private HashMap<Integer, String[]> menu;
    @FXML
    private TableView<orders> tableView;
    @FXML
    private Button backButton;
    @FXML
    private Button loadButton;
    @FXML
    private TableColumn<orders, Integer> idColumn;
    @FXML
    private TableColumn<orders, Double> priceColumn;
    @FXML
    private TableColumn<orders, Boolean> paidColumn;
    @FXML
    private TableColumn<orders, Timestamp> timeColumn;
    @FXML
    private TextField beginningdate;
    @FXML
    private TextField enddate;

    @FXML
    private Label profit;
    @FXML
    private Label taxAmount;
    @FXML
    private Label salesAmount;

    public ControllerOrders(DB conn, HashMap<Integer, String[]> menu) {
        this.conn = conn;
        this.menu = menu;
    }

    /**
     * 
     * Handles the action of clicking the back button by loading the "manager.fxml"
     * file and setting the connection and menu properties of the controller.
     * 
     * @param event The event object representing the action of clicking the back
     *              button.
     * 
     * @throws IOException If an error occurs while loading the "start.fxml" file.
     */

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
        // loadOrders();
    }

    /**
     * 
     * This method loads the orders data into the table view based on the beginning
     * date and end date entered by the user.
     * 
     * It sets the cell value factory for the table columns to retrieve the relevant
     * attributes from the Orders class objects.
     * 
     * It also calculates and displays the total profit, tax and sales amount of the
     * orders within the given date range.
     * 
     * @param event The action event generated by the user clicking the "Load
     *              Orders" button
     * 
     * @throws SQLException if there is an error in the SQL query
     * 
     * @throws IOException  if there is an error in reading from the database
     */
    public void loadOrders(ActionEvent event) throws SQLException, IOException {
        // THESE LINES TELLS THE COLUMNS TO GET THE CERTAIN ATTRIBUTES FROM THE orders
        // class objects for the certain columns
        if (beginningdate.getText().equals("") || enddate.getText().equals("")) {
            System.out.println("input value");
        } else {
            idColumn.setCellValueFactory(new PropertyValueFactory<orders, Integer>("orderId"));

            priceColumn.setCellValueFactory(new PropertyValueFactory<orders, Double>("price"));

            paidColumn.setCellValueFactory(new PropertyValueFactory<orders, Boolean>("paid"));

            timeColumn.setCellValueFactory(new PropertyValueFactory<orders, Timestamp>("orderTime"));

            // conn = new DB(dbSetup.user, dbSetup.pswd);

            ResultSet ordersFetch = conn.select("SELECT * FROM orders WHERE orders.order_time::date >= '"
                    + beginningdate.getText() + "' AND orders.order_time::date <= '" + enddate.getText() + "';");
            // JOIN order_items ON orders.order_id = order_items.order_id
            ObservableList<orders> data = FXCollections.observableArrayList();
            // need to make a list of all the lines from the database into orders
            double addProfit = 0.00;
            double tax = 0;
            while (ordersFetch.next()) {
                int orderId = ordersFetch.getInt("order_id");
                double price = ordersFetch.getDouble("price");
                Boolean paid = ordersFetch.getBoolean("is_paid");
                Timestamp orderTime = ordersFetch.getTimestamp("order_time");
                ;
                addProfit += price;
                orders menu = new orders(orderId, price, paid, orderTime);
                data.add(menu);
                System.out.println(menu);
            }
            String formattedSales = String.format("%.02f", addProfit);
            tax = addProfit * 0.0825;
            String formattedTax = String.format("%.02f", tax);
            addProfit -= tax;
            String formattedProfit = String.format("%.02f", addProfit);

            profit.setText("Profit: $" + formattedProfit);
            taxAmount.setText("Tax: $" + formattedTax);
            salesAmount.setText("Sales: $" + formattedSales);
            // pushing all the orders to the table is enough afterwards for the table to be
            // made.
            tableView.setItems(data);
        }
    }

}
