package chickfila.manager;
import java.io.IOException;
import chickfila.manager.sales;
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

    @FXML
    public void closeButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./manager.fxml"));
        Parent root = loader.load();
        ((ControllerManager) loader.getController()).setConnection(conn, menu);
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
    }
    public void setConnection(DB db, HashMap<Integer, String[]> menu) {
        conn = db;
        this.menu = menu;
        System.out.println("asdfasgegegege3");
    }

    //Takes in the Quantity and itemID and changes the Quantity in the database
    public void updateQuantity(ActionEvent event) throws SQLException , IOException  {
    if (inventoryId.getText().equals("") || quantity.getText().equals("")) {
            System.out.println("input value");
    } 
    else {
        int itemId = Integer.parseInt(inventoryId.getText());
        int quantity_query = Integer.parseInt(quantity.getText());
        conn.performQuery("UPDATE inventory SET quantity = "+quantity_query+" WHERE item_id = "+itemId+";");
    }
}

public void addItem(ActionEvent event) throws SQLException , IOException  {
    if (quantity.getText().equals("")||itemName.getText().equals("")) {
            System.out.println("input value");
    } 
    else {
        //int itemId = Integer.parseInt(inventoryId.getText());
        int quantity_query = Integer.parseInt(quantity.getText());
        String name = itemName.getText();
        Integer max_id = 0;
        ResultSet sizeFetch = conn.select("SELECT MAX(item_id) FROM inventory;");
        while(sizeFetch.next()){
            max_id = sizeFetch.getInt("max");

            System.out.println(max_id);
        }
        Integer newMenuId= max_id +1;
        conn.performQuery("INSERT INTO inventory (item_id,quantity, item_name) VALUES ("+newMenuId+","+quantity_query+" , '"+name+"');");
        System.out.println("successnv");
    }
}
}
