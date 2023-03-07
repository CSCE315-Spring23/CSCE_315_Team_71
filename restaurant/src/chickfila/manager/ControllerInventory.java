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


public class ControllerInventory {
    private DB conn;
    @FXML
    private TableView <inventory> tableView;
    @FXML
    private Button backButton;
    @FXML
    private Button loadButton;
    @FXML 
    private TableColumn<inventory,Integer> idColumn;
    @FXML
    private TableColumn<inventory,Integer> quantityColumn;
    @FXML
    private TableColumn<inventory,String> nameColumn;


    @FXML
    public void closeButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./manager.fxml"));
        Parent root = loader.load();
        ((ControllerManager) loader.getController()).setConnection(conn);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);

    }
    public void initialize() throws SQLException, IOException{
        loadInventory();
    }
    
    public void setConnection(DB db) {
        conn = db;
        System.out.println("asdfasgegegege3");
    }

    private void loadInventory() throws SQLException , IOException {
        //THESE LINES TELLS THE COLUMNS TO GET THE CERTAIN ATTRIBUTES FROM THE inventoryItem class objects for the certain columns
                idColumn.setCellValueFactory(new PropertyValueFactory<inventory,Integer>("inventory_item_id"));
        
                quantityColumn.setCellValueFactory(new PropertyValueFactory<inventory, Integer>("quantity"));
        
                nameColumn.setCellValueFactory(new PropertyValueFactory<inventory, String>("inventory_item_name"));
        
                conn = new DB(dbSetup.user, dbSetup.pswd);
                ResultSet inventoryFetch = conn.select("SELECT * FROM inventory ORDER BY item_id;");
        
                ObservableList<inventory> data = FXCollections.observableArrayList();
                //need to make a list of all the lines from the database into menuItems;
                while (inventoryFetch.next()) {
                    int menuID = inventoryFetch.getInt("item_id");
                    int quantity = inventoryFetch.getInt("quantity");
                    String name = inventoryFetch.getString("item_name");;
                    inventory menu = new inventory(menuID, quantity, name);
                    data.add(menu);
                    System.out.println(menu);
                }
                //pushing all the menuItems to the table is enough afterwards for the table to be made.
                tableView.setItems(data);
            }
}
