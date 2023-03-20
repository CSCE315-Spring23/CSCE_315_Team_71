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
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import chickfila.logic.DB;
import chickfila.logic.dbSetup;
import chickfila.logic.inventory;

/**
 * @author Alan Nguyen
 */
public class ControllerInventory {

    private DB conn;
    private HashMap<Integer, String[]> menu;

    @FXML
    private TableView<inventory> tableView;
    @FXML
    private Button backButton;
    @FXML
    private Button loadButton;
    @FXML
    private TableColumn<inventory, Integer> idColumn, quantityColumn, minColumn;
    @FXML
    private TableColumn<inventory, String> nameColumn;
    @FXML
    private TableView<String[]> view;
    @FXML
    TableColumn<String[], String> nCol, qCol, mCol;
    @FXML
    private TableView<String[]> excessView;
    @FXML
    private Button getExcess;
    @FXML
    private TextField timestamp;
    @FXML
    TableColumn<String[], String> nameCol, soldCol, qtyCol;

    public ControllerInventory(DB conn, HashMap<Integer, String[]> menu) {
        this.conn = conn;
        this.menu = menu;
    }

    /**
     * 
     * This method is called when the "close" button is pressed in the UI. It loads
     * the "manager.fxml" file and sets up the
     * 
     * connection and menu for the controller. Then, it creates a new scene using
     * the root, and sets the scene to be displayed
     * 
     * in the current stage.
     * 
     * @param event The action event triggered by the "close" button press
     * 
     * @throws IOException If there is an error loading the "manager.fxml" file
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
        loadInventory();
        loadRestock();

        getExcess.setOnAction(event -> {
            try {
                loadItems2(timestamp.getText(), (java.time.LocalDate.now()).toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            timestamp.setText(null);
        });
    }

    /**
     * 
     * This method loads the inventory data from the database and displays it in the
     * table view. It sets up the table columns
     * 
     * to display the appropriate attributes from the "inventory" class objects. It
     * retrieves the inventory data from the database
     * 
     * and adds it to an observable list. Finally, it sets the items of the table
     * view to be the data in the observable list.
     * 
     * @throws SQLException If there is an error accessing the database
     * 
     * @throws IOException  If there is an error reading from the database
     */
    private void loadInventory() throws SQLException, IOException {
        // THESE LINES TELLS THE COLUMNS TO GET THE CERTAIN ATTRIBUTES FROM THE
        // inventoryItem class objects for the certain columns
        idColumn.setCellValueFactory(new PropertyValueFactory<inventory, Integer>("inventory_item_id"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<inventory, Integer>("quantity"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<inventory, String>("inventory_item_name"));
        minColumn.setCellValueFactory(new PropertyValueFactory<inventory, Integer>("min_amount"));

        ResultSet inventoryFetch = conn.select("SELECT * FROM inventory ORDER BY item_id;");

        ObservableList<inventory> data = FXCollections.observableArrayList();
        // need to make a list of all the lines from the database into menuItems;
        while (inventoryFetch.next()) {
            int menuID = inventoryFetch.getInt("item_id");
            int quantity = inventoryFetch.getInt("quantity");
            String name = inventoryFetch.getString("item_name");
            int minAmount = inventoryFetch.getInt("min_amount");
            inventory menu = new inventory(menuID, quantity, name, minAmount);
            data.add(menu);
        }
        // pushing all the menuItems to the table is enough afterwards for the table to
        // be made.
        tableView.setItems(data);
    }

    private void loadRestock() throws SQLException {
        ResultSet restock = conn.select("SELECT * FROM inventory WHERE quantity <= min_amount ORDER BY item_name;");

        while (restock.next()) {
            String[] data = new String[3];
            data[0] = restock.getString("item_name");
            data[1] = restock.getString("quantity");
            data[2] = restock.getString("min_amount");
            view.getItems().add(data);
        }

        nCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        qCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        mCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
    }

    public void loadItems2(String start, String end) throws SQLException {

        excessView.getItems().clear();


        //logic flows from innermost to outermost
        //same logic from sales to get menu iems sold, map with recipes, 
        //then map with inventory and determine which items has sold less than 10%
        ResultSet itemSoldAmount = conn.select(
        "SELECT item_name, SUM(qty) AS qty, quantity FROM " +
            "(SELECT coalesce(j.qty,0) AS qty, item_name, quantity FROM inventory FULL OUTER JOIN "+ 

                "(SELECT inventory_id, quantity*count AS qty FROM "+
                    
                    "(SELECT m.menu_item_id, SUM(count) AS count FROM " +
                        "(SELECT order_items.menu_item_id, quantity*count(*) AS count FROM order_items JOIN orders ON " +
                                "orders.order_id=order_items.order_id JOIN menu_items ON order_items.menu_item_id = menu_items.menu_item_id "
                                +
                                "WHERE order_time::date >= '" + start + "' AND order_time::date <= '" + end
                                + "' GROUP BY order_items.menu_item_id, quantity ORDER BY menu_item_id) m"+
                            " GROUP BY menu_item_id) k " +

                    "JOIN recipes ON k.menu_item_id = recipes.menu_item_id GROUP BY inventory_id, qty) j"+
        
                " ON j.inventory_id = inventory.item_id" +
                " WHERE quantity > 0" +
                " GROUP BY item_name, qty, quantity ORDER BY item_name) n"+ 
            " GROUP BY item_name, quantity" +
            " HAVING SUM(qty) < 0.1 * (SUM(qty) + quantity);"
            
        );



        while (itemSoldAmount.next()) {
            String[] data = new String[3];
            data[0] = itemSoldAmount.getString("item_name");
            data[1] = itemSoldAmount.getString("qty");
            data[2] = itemSoldAmount.getString("quantity");
            excessView.getItems().add(data);
        }

        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        soldCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        qtyCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));


    }
}
