package chickfila.manager;

import java.io.IOException;
import java.net.URL;

import java.sql.*;
import java.util.*;

import javafx.scene.control.TextField;

import chickfila.Controller;
import chickfila.logic.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import javafx.scene.Node;

public class ControllerManager {

    private DB conn;
    private HashMap<Integer, String[]> menu;

    @FXML
    private TextField item_name;
    @FXML
    private TextField ingredientlist;
    @FXML
    private TextField quantityIngredient;
    @FXML
    private Button addOrder;
    @FXML
    private TextField price;
    @FXML
    private Button menu_item;

    @FXML
    private Button addIngredient;

    protected String ingredientString;
    private HashMap<String, Integer> inventory;

    public ControllerManager(DB conn, HashMap<Integer, String[]> menu) {
        this.conn = conn;
        this.menu = menu;
        this.ingredientString = "";
        this.inventory = new HashMap<String, Integer>();
    }

    public void ingredientAdder(ActionEvent event) {
        if (ingredientlist.getText().equals("") || quantityIngredient.getText().equals("")) {
            System.out.println("needs the values");
        } else {
            if (ingredientString.equals("")) {
                ingredientString += (ingredientlist.getText() + "," + quantityIngredient.getText());
            } else {
                ingredientString += ("-" + ingredientlist.getText() + "," + quantityIngredient.getText());
            }
        }
        System.out.println("succesfully added " + ingredientlist.getText());
        ingredientlist.setText("");
        quantityIngredient.setText("");

    }

    // creates order when add order is clicked on
    // see instruction on the app to see how the string format is implemented and
    // how parsing works.
    // FIXME: WON'T WORK IF INPUT ISN'T PROVIDED IN SPECIFIC FORMAT AND IF NAME OF
    // INGREDIENT ISN'T THE EXACT SAME AS INGREDIENTS. NEED TO USE TRY CATCH FOR IT
    public void orderCreation(ActionEvent event) throws SQLException {
        boolean noInput = false;
        boolean noIng = false;
        if (item_name.getText().equals("") || price.getText().equals("")) {
            System.out.println("input value in name and price");
            noInput = true;
        }
        if (ingredientString.equals("")) {
            System.out.println("ingredients haven't been uploaded");
            noIng = true;
        }
        if (noIng == false && noInput == false) {
            Integer max_id = 0;
            ResultSet salesFetch = conn.select("SELECT MAX(menu_item_id) FROM menu_items;");
            while (salesFetch.next()) {
                max_id = salesFetch.getInt("max");

                System.out.println(max_id);
            }
            Integer newMenuId = max_id + 1;
            String ingredients = ingredientString;
            ingredientString = "";
            String[] splitString = ingredients.split("-");
            double price1 = Double.parseDouble(price.getText());

            conn.performQuery("INSERT INTO menu_items (menu_item_id, menu_item_name, menu_item_price) VALUES ("
                    + newMenuId + ",'" + item_name.getText() + "' , " + price1 + ");");
            loadInventory();

            for (int i = 0; i < splitString.length; i++) {
                System.out.println("---->" + splitString[i]);
                // conn.performQuery("INSERT INTO recipes (menu_item_id, inventory_id,
                // quantity)");
                String[] commaSplit = splitString[i].split(",");
                // inventory.get(commaSplit[0]);
                if (inventory.containsKey(commaSplit[0])) {
                    System.out.println(inventory.get(commaSplit[0]));
                    System.out.println(commaSplit[1]);
                    conn.performQuery("INSERT INTO recipes (menu_item_id,inventory_id,quantity) VALUES (" + newMenuId
                            + ", " + inventory.get(commaSplit[0]) + "," + commaSplit[1] + ");");
                } else {
                    ingredientString = "";
                    System.out.println("fix ingredients entered as they are not in the available ingredients");
                    item_name.setText("");
                    price.setText("");
                    break;

                }
            }
            System.out.println("success");
        }

    }

    // creates a hashmap of all the inventory items. It's used to get inventory_id
    // from inventory_name for creation of recipes.
    private void loadInventory() throws SQLException {
        ResultSet invFetch = conn.select("SELECT * FROM inventory;");

        while (invFetch.next()) {
            int invID = invFetch.getInt("item_id");
            int quantity = invFetch.getInt("quantity");
            String invName = invFetch.getString("item_name");
            inventory.put(invName, invID);
        }
    }

    // changes scene to start onclick of back button
    public void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../start.fxml"));
        Controller c = new Controller(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }
    // db connection stuff, don't know deets

    // switches to sales report stage. This just opens the seperate fxml file. Sales
    // report code goes in ControllerSales
    public void showSales(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./salesRep.fxml"));
        ControllerSales c = new ControllerSales(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    // switches to menu recipes stage. This just opens the seperate fxml file. Sales
    // report code goes in ControllerMenu
    public void showMenu(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./showMenu.fxml"));
        ControllerMenu c = new ControllerMenu(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

    }

    // switches to menu recipes stage. This just opens the seperate fxml file.
    public void showInventory(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./inventory.fxml"));
        ControllerInventory c = new ControllerInventory(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

    }

    // switches to Inventory_Add stage. This just opens the seperate fxml file.
    public void showInventoryAdd(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./inventory_add.fxml"));
        ControllerInventoryAdd c = new ControllerInventoryAdd(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

    }

    // switches to Orders stage. This just opens the seperate fxml file.
    public void showOrders(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./orders.fxml"));
        ControllerOrders c = new ControllerOrders(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

    }
}
