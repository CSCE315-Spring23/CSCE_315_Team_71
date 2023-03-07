package chickfila.manager;

import java.io.IOException;
import chickfila.manager.sales;
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
import javafx.scene.control.TextField;

public class ControllerOrders {
    private DB conn;
    @FXML
    private TableView <orders> tableView;
    @FXML
    private Button backButton;
    @FXML
    private Button loadButton;
    @FXML 
    private TableColumn<orders,Integer> idColumn;
    @FXML
    private TableColumn<orders,Double> priceColumn;
    @FXML
    private TableColumn<orders,Boolean> paidColumn;
    @FXML
    private TableColumn<orders,Timestamp> timeColumn;
    @FXML
    private TextField beginningdate;
    @FXML
    private TextField enddate;
    @FXML
    private Label profit;
    @FXML
    public void closeButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./manager.fxml"));
        Parent root = loader.load();
        ((ControllerManager) loader.getController()).setConnection(conn);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);

    }

    public void setConnection(DB db) {
        conn = db;
        System.out.println("asdfasgegegege3");
    }

    public void loadOrders(ActionEvent event) throws SQLException , IOException {
        //THESE LINES TELLS THE COLUMNS TO GET THE CERTAIN ATTRIBUTES FROM THE inventoryItem class objects for the certain columns
        if (beginningdate.getText().equals("") || enddate.getText().equals("")) {
            System.out.println("input value");
        } 
        else {
                idColumn.setCellValueFactory(new PropertyValueFactory<orders,Integer>("orderId"));
        
                priceColumn.setCellValueFactory(new PropertyValueFactory<orders, Double>("price"));
        
                paidColumn.setCellValueFactory(new PropertyValueFactory<orders, Boolean>("paid"));

                timeColumn.setCellValueFactory(new PropertyValueFactory<orders, Timestamp>("orderTime"));

                conn = new DB(dbSetup.user, dbSetup.pswd);

                ResultSet ordersFetch = conn.select("SELECT * FROM orders JOIN order_items ON orders.order_id = order_items.order_id WHERE orders.order_time::date >= '"+beginningdate.getText()+"' AND orders.order_time::date <= '"+enddate.getText()+"';");
        
                ObservableList<orders> data = FXCollections.observableArrayList();
                //need to make a list of all the lines from the database into menuItems;
                double addProfit = 0.00;
                while (ordersFetch.next()) {
                    int orderId = ordersFetch.getInt("order_id");
                    double price = ordersFetch.getDouble("price");
                    Boolean paid = ordersFetch.getBoolean("is_paid");
                    Timestamp orderTime = ordersFetch.getTimestamp("order_time");;
                    addProfit += price;
                    orders menu = new orders(orderId, price, paid, orderTime);
                    data.add(menu);
                    System.out.println(menu);
                }

                String formattedProfit = String.format("%.02f", addProfit);
                profit.setText("Profit: $"+formattedProfit);
                //pushing all the menuItems to the table is enough afterwards for the table to be made.
                tableView.setItems(data);
            }
    }
}
