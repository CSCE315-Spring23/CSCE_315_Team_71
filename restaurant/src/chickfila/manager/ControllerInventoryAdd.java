package chickfila.manager;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

/**
 * @author Alan Nguyen
 * 
 */
public class ControllerInventoryAdd {
    private DB conn;
    private HashMap<Integer, String[]> menu;

    @FXML
    private TextField inventoryId;
    @FXML
    private TextField quantity;
    @FXML
    private TextField itemName;
    @FXML
    private Button addButton;


    
    public ControllerInventoryAdd(DB conn, HashMap<Integer, String[]> menu) {
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

    /**
     * This method updates the inventory item in the database with the new values
     * entered in the UI. It checks if the inventory ID
     * 
     * field is empty, and if so, prints an error message. If the ID field is not
     * empty, it checks if either the quantity or item
     * 
     * name fields are empty, and if so, prints another error message. Otherwise, it
     * retrieves the item ID from the inventory ID
     * 
     * field and updates the database with the new quantity and/or item name values.
     * 
     * @param event The action event triggered by clicking the "update" button
     * 
     * @throws SQLException If there is an error accessing the database
     * 
     * @throws IOException  If there is an error reading from the database
     */
    public void updateItem(ActionEvent event) throws SQLException, IOException {
        if (inventoryId.getText().equals("")) {
            System.out.println("input value inventory ID");
        } else {
            if (quantity.getText().equals("") && itemName.getText().equals("")) {
                System.out.println("input value quantity or item Name");
            } else {
                int itemId = Integer.parseInt(inventoryId.getText());
                if (quantity.getText() != "") {
                    int quantityQuery = Integer.parseInt(quantity.getText());
                    conn.performQuery(
                            "UPDATE inventory SET quantity = " + quantityQuery + " WHERE item_id = " + itemId + ";");
                }
                if (itemName.getText() != "") {
                    String nameQuery = itemName.getText();
                    conn.performQuery(
                            "UPDATE inventory SET item_name = '" + nameQuery + "' WHERE item_id = " + itemId + ";");
                }
            }
        }
    }

    /**
     * 
     * This method adds a new item to the inventory table in the database. It checks
     * if either the quantity or item name
     * 
     * fields are empty, and if so, prints an error message. Otherwise, it retrieves
     * the new quantity and item name values
     * 
     * entered in the UI, generates a new item ID for the item, and inserts the new
     * item into the inventory table in the database.
     * 
     * @param event The action event triggered by clicking the "add" button
     * 
     * @throws SQLException If there is an error accessing the database
     * 
     * @throws IOException  If there is an error reading from the database
     */
    public void addItem(ActionEvent event) throws SQLException, IOException {
        if (quantity.getText().equals("") || itemName.getText().equals("")) {
            System.out.println("input value");
        } else {
            int quantity_query = Integer.parseInt(quantity.getText());
            String name = itemName.getText();
            Integer max_id = 0;
            ResultSet sizeFetch = conn.select("SELECT MAX(item_id) FROM inventory;");
            while (sizeFetch.next()) {
                max_id = sizeFetch.getInt("max");

                System.out.println(max_id);
            }
            Integer newMenuId = max_id + 1;
            conn.performQuery("INSERT INTO inventory (item_id,quantity, item_name) VALUES (" + newMenuId + ","
                    + quantity_query + " , '" + name + "');");
            System.out.println("successnv");
        }
    }


    /**
     * If the user does not provide an inventory ID, a message is printed to the console.
     * Removes an item from the inventory table in the database based on the inventory ID provided by the user.
     * @param event the action event that triggers the method
     * @throws SQLException if there is an error performing the database query
     * @throws IOException if there is an error printing the success message to the console
    */
    public void removeItem(ActionEvent event) throws SQLException, IOException {
        if (inventoryId.getText().equals("")) {
            System.out.println("input value");
        } else {
            int menuID = Integer.parseInt(inventoryId.getText());
            conn.performQuery("DELETE FROM inventory WHERE item_id = "+ menuID +";");
            System.out.println("successnv");
        }
    }
}
