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
    private TableColumn<inventory, Integer> idColumn;
    @FXML
    private TableColumn<inventory, Integer> quantityColumn;
    @FXML
    private TableColumn<inventory, String> nameColumn;

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

        ResultSet inventoryFetch = conn.select("SELECT * FROM inventory ORDER BY item_id;");

        ObservableList<inventory> data = FXCollections.observableArrayList();
        // need to make a list of all the lines from the database into menuItems;
        while (inventoryFetch.next()) {
            int menuID = inventoryFetch.getInt("item_id");
            int quantity = inventoryFetch.getInt("quantity");
            String name = inventoryFetch.getString("item_name");
            ;
            inventory menu = new inventory(menuID, quantity, name);
            data.add(menu);
        }
        // pushing all the menuItems to the table is enough afterwards for the table to
        // be made.
        tableView.setItems(data);
    }
}
