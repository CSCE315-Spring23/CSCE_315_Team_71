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
/**

* This method is used to add ingredients to a recipe.
* It is triggered by an ActionEvent when the user clicks the "Add Ingredient" button.
* If the user has not entered a value for the ingredient or quantity, an error message is displayed.
* Otherwise, the ingredient and its corresponding quantity are appended to a string variable, separated by a comma.
* If there are already ingredients in the string, they are separated by a hyphen.
* After adding the ingredient, the method displays a success message and clears the ingredient and quantity fields.
* @param event The ActionEvent triggered by clicking the "Add Ingredient" button
*/
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

/**

* This method is used to create a new order by inserting a new menu item and its corresponding recipe into the database.
* It is triggered by an ActionEvent when the user clicks the "Create Order" button.
* If the user has not entered a value for the item name or price, an error message is displayed.
* If there are no ingredients in the recipe, an error message is displayed.
* If all input values are valid, the method generates a new menu item ID by querying the database for the highest existing ID and incrementing it by 1.
* It then inserts the new menu item into the database with the given item name, price, and ID.
*
* Next, it splits the ingredientString into individual ingredients and quantities and inserts each one as a recipe for the new menu item.
* If an ingredient in the recipe is not found in the available inventory, the method displays an error message and clears the input fields.
* @param event The ActionEvent triggered by clicking the "Create Order" button
* @throws SQLException if there is an error executing SQL statements
*/
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


/**
* This private method is used to load the available inventory items and their IDs into a HashMap called "inventory".
* It queries the database for all rows in the inventory table and iterates over the ResultSet to extract the item ID, quantity, and name for each inventory item.
* It then adds the item name and ID as a key-value pair to the inventory HashMap.
* @throws SQLException if there is an error executing SQL statements
*/
    private void loadInventory() throws SQLException {
        ResultSet invFetch = conn.select("SELECT * FROM inventory;");

        while (invFetch.next()) {
            int invID = invFetch.getInt("item_id");
            int quantity = invFetch.getInt("quantity");
            String invName = invFetch.getString("item_name");
            inventory.put(invName, invID);
        }
    }


    /**
     * 
     * This method is called when the "close" button is pressed in the UI. It loads
     * the "manager.fxml" file and sets up the
     * 
     * connection and menu for the controller. Then, it creates a new scene using
     * the root, and sets the scene to be displayed in the current stage.
     * 
     * @param event The action event triggered by the "close" button press
     * 
     * @throws IOException If there is an error loading the "manager.fxml" file
     */
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

    /**
    *
    * This public method is used to handle the "Show Sales" button click and load the salesRep.fxml file.
    * It is triggered by an ActionEvent when the user clicks the "Show Sales" button.
    *
    * The method loads the salesRep.fxml file and sets the controller to a new instance of the 
    * ControllerSales class with the same connection and menu as the current instance.
    *
    * It then sets the loaded Parent as the root of a new Scene and sets the Scene of the current Stage to the new Scene.
    @param event The ActionEvent triggered by clicking the "Show Sales" button
    @throws IOException if there is an error loading the salesRep.fxml file
    @throws SQLException if there is an error executing SQL statements
    */
    public void showSales(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./salesRep.fxml"));
        ControllerSales c = new ControllerSales(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    /**
    *
    * This public method is used to handle the "Show Menu" button click and load the showMenu.fxml file.
    * It is triggered by an ActionEvent when the user clicks the "Show Menu" button.
    *
    * The method loads the showMenu.fxml file and sets the controller to a new instance of the ControllerMenu 
    * class with the same connection and menu as the current instance.
    *
    *It then sets the loaded Parent as the root of a new Scene and sets the Scene of the current Stage to the new Scene.
    @param event The ActionEvent triggered by clicking the "Show Menu" button
    @throws IOException if there is an error loading the showMenu.fxml file
    @throws SQLException if there is an error executing SQL statements
    */
    public void showMenu(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./showMenu.fxml"));
        ControllerMenu c = new ControllerMenu(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

    }

    /**
    *
    * This public method is used to handle the "Show Menu" button click and load the inventory.fxml file.
    * It is triggered by an ActionEvent when the user clicks the "Show Menu" button.
    *
    * The method loads the showMenu.fxml file and sets the controller to a new instance of the ControllerInventory
    * class with the same connection and menu as the current instance.
    *
    *It then sets the loaded Parent as the root of a new Scene and sets the Scene of the current Stage to the new Scene.
    @param event The ActionEvent triggered by clicking the "Show Menu" button
    @throws IOException if there is an error loading the inventory.fxml file
    @throws SQLException if there is an error executing SQL statements
    */
    public void showInventory(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./inventory.fxml"));
        ControllerInventory c = new ControllerInventory(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

    }

    /**
    *
    * This public method is used to handle the "Show Menu" button click and load the inventory_add.fxml file.
    * It is triggered by an ActionEvent when the user clicks the "Show Menu" button.
    *
    * The method loads the showMenu.fxml file and sets the controller to a new instance of the ControllerInventoryAdd
    * class with the same connection and menu as the current instance.
    *
    *It then sets the loaded Parent as the root of a new Scene and sets the Scene of the current Stage to the new Scene.
    @param event The ActionEvent triggered by clicking the "Show Menu" button
    @throws IOException if there is an error loading the inventory.fxml file
    @throws SQLException if there is an error executing SQL statements
    */
    public void showInventoryAdd(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./inventory_add.fxml"));
        ControllerInventoryAdd c = new ControllerInventoryAdd(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

    }

    /**
    *
    * This public method is used to handle the "Show Menu" button click and load the orders.fxml file.
    * It is triggered by an ActionEvent when the user clicks the "Show Menu" button.
    *
    * The method loads the showMenu.fxml file and sets the controller to a new instance of the ControllerOrders
    * class with the same connection and menu as the current instance.
    *
    *It then sets the loaded Parent as the root of a new Scene and sets the Scene of the current Stage to the new Scene.
    @param event The ActionEvent triggered by clicking the "Show Menu" button
    @throws IOException if there is an error loading the inventory.fxml file
    @throws SQLException if there is an error executing SQL statements
    */
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
