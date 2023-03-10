package chickfila.cashier;

import java.io.IOException;
import java.util.*;
import java.sql.*;

import chickfila.Controller;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import chickfila.logic.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

import javafx.stage.Stage;
import javafx.scene.Node;

import chickfila.logic.*;;

public class ControllerCashier {

    private DB conn;
    private HashMap<Integer, String[]> menu;
    private Order currentOrder;

    private boolean isSet;

    @FXML
    Tab newItemTab;

    @FXML
    Button newOrder;

    @FXML
    Button completeOrder;

    @FXML
    Button backButton;

    @FXML
    Button n8, n12, gn8, gn12, cs, csSp, csSpD, csD, csGr, csGrD, fries, friesL, chips, sM, sC, sS, sideSal, fruitCup,
            wrap1, brownie, cookie, iceDream, softDrinkM, softDrinkL, teaM, teaL, sunjM, sunjL, frosLemonade,
            frosCoffee, milkshakeCC, milkshakeCH, milkshakeS, milkshakeV, n12Meal, gn8Meal,
            gn12Meal, csMeal, csDMeal, csSpMeal, csSpDMeal, csGrMeal, csGrDMeal, n8Meal, wrapMeal, water;
    @FXML
    Label priceDisplay, taxDisplay, subtotalDisplay;

    @FXML
    AnchorPane newMenuItems;

    public void addNewItems() throws SQLException {

        if (isSet) {
            return;
        }

        ResultSet newItems = conn.select("SELECT * FROM menu_items WHERE menu_item_id > 55;");

        int numNew = 0;
        while (newItems.next()) {
            String nbName = newItems.getString("menu_item_name");
            int nbID = newItems.getInt("menu_item_id");
            Button b = new Button(nbName);

            b.setOnAction(e -> {
                currentOrder.addItem(new OrderItem(nbName, nbID));
                updateDisplay();
            });

            b.setPadding(new Insets(20, 20, 20, 20));
            b.setLayoutX(10 + (numNew % 4) * 150);
            b.setLayoutY(30 + (numNew / 4) * 100);
            b.setPrefSize(175, 65);

            newMenuItems.getChildren().add(b);

            numNew++;

        }

        isSet = true;
    }

    public void initialize() {
        isSet = false;
    }

    /**
     * 
     * Handles the action of clicking the back button by loading the "start.fxml"
     * file and setting the connection and menu properties of the controller.
     * 
     * @param event The event object representing the action of clicking the back
     *              button.
     * 
     * @throws IOException If an error occurs while loading the "start.fxml" file.
     */
    public void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../start.fxml"));
        Parent root = loader.load();
        ((Controller) loader.getController()).setConnection(conn, menu);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    /**
     * 
     * Handles the click of a menu item button and adds the corresponding item to
     * the current order.
     * 
     * @param event The ActionEvent object representing the click of a menu item
     *              button.
     */
    public void handleClick(ActionEvent event) {
        Button b = (Button) event.getSource();

        if (b.equals(n8)) {
            currentOrder.addItem(new OrderItem("nuggets (8ct)", 6));
        } else if (b.equals(n12)) {
            currentOrder.addItem(new OrderItem("nuggets (12ct)", 7));
        } else if (b.equals(gn8)) {
            currentOrder.addItem(new OrderItem("nuggets grilled (8ct)", 16));
        } else if (b.equals(gn12)) {
            currentOrder.addItem(new OrderItem("nuggets grilled (12ct)", 17));
        } else if (b.equals(cs)) {
            currentOrder.addItem(new OrderItem("chicken sandwich", 1));
        } else if (b.equals(csSp)) {
            currentOrder.addItem(new OrderItem("chicken sandwich spicy", 3));
        } else if (b.equals(csD)) {
            currentOrder.addItem(new OrderItem("chicken sandwich deluxe", 2));
        } else if (b.equals(csSpD)) {
            currentOrder.addItem(new OrderItem("chicken sandwich spicy deluxe", 4));
        } else if (b.equals(csGr)) {
            currentOrder.addItem(new OrderItem("chicken sandwich grilled", 18));
        } else if (b.equals(csGrD)) {
            currentOrder.addItem(new OrderItem("chicken sandwich grilled club", 34));
        } else if (b.equals(fries)) {
            currentOrder.addItem(new OrderItem("waffle fries (medium)", 12));
        } else if (b.equals(sM)) {
            currentOrder.addItem(new OrderItem("market salad", 19));
        } else if (b.equals(sC)) {
            currentOrder.addItem(new OrderItem("cobb salad", 38));
        } else if (b.equals(sS)) {
            currentOrder.addItem(new OrderItem("southwest salad", 39));
        } else if (b.equals(friesL)) {
            currentOrder.addItem(new OrderItem("waffle fries (large)", 52));
        } else if (b.equals(sideSal)) {
            currentOrder.addItem(new OrderItem("side salad", 55));
        } else if (b.equals(fruitCup)) {
            currentOrder.addItem(new OrderItem("fruit cup", 54));
        } else if (b.equals(wrap1)) {
            currentOrder.addItem(new OrderItem("chicken cool wrap", 37));
        } else if (b.equals(chips)) {
            currentOrder.addItem(new OrderItem("waffle chips", 53));
        } else if (b.equals(cookie)) {
            currentOrder.addItem(new OrderItem("chocolate chunk cookie", 51));
        } else if (b.equals(iceDream)) {
            currentOrder.addItem(new OrderItem("icedream cone", 49));
        } else if (b.equals(brownie)) {
            currentOrder.addItem(new OrderItem("chocolate fudge brownie", 50));
        } else if (b.equals(n12Meal)) {
            currentOrder.addItem(new OrderItem("nuggets (12ct) meal", 31));
        } else if (b.equals(gn8Meal)) {
            currentOrder.addItem(new OrderItem("nuggets grilled (8ct) meal", 32));
        } else if (b.equals(gn12Meal)) {
            currentOrder.addItem(new OrderItem("nuggets grilled (12ct) meal", 33));
        } else if (b.equals(csMeal)) {
            currentOrder.addItem(new OrderItem("chicken sandwich meal", 25));
        } else if (b.equals(csDMeal)) {
            currentOrder.addItem(new OrderItem("chicken sandwich deluxe meal", 26));
        } else if (b.equals(csSpMeal)) {
            currentOrder.addItem(new OrderItem("chicken sandwich spicy meal", 27));
        } else if (b.equals(csSpDMeal)) {
            currentOrder.addItem(new OrderItem("chicken sandwich spicy deluxe meal", 28));
        } else if (b.equals(csGrMeal)) {
            currentOrder.addItem(new OrderItem("chicken sandwich grilled meal", 29));
        } else if (b.equals(csGrDMeal)) {
            currentOrder.addItem(new OrderItem("chicken sandwich grilled club meal", 36));
        } else if (b.equals(n8Meal)) {
            currentOrder.addItem(new OrderItem("nuggets (8ct) meal", 30));
        } else if (b.equals(wrapMeal)) {
            currentOrder.addItem(new OrderItem("chicken cool wrap meal", 36));
        } else if (b.equals(softDrinkM)) {
            currentOrder.addItem(new OrderItem("soft drink (medium)", 9));
        } else if (b.equals(softDrinkL)) {
            currentOrder.addItem(new OrderItem("soft drink (large)", 42));
        } else if (b.equals(frosLemonade)) {
            currentOrder.addItem(new OrderItem("frosted lemonade", 47));
        } else if (b.equals(teaM)) {
            currentOrder.addItem(new OrderItem("tea (medium)", 40));
        } else if (b.equals(teaL)) {
            currentOrder.addItem(new OrderItem("tea (large)", 41));
        } else if (b.equals(sunjM)) {
            currentOrder.addItem(new OrderItem("sunjoy (medium)", 44));
        } else if (b.equals(sunjL)) {
            currentOrder.addItem(new OrderItem("sunjoy (large)", 45));
        } else if (b.equals(frosLemonade)) {
            currentOrder.addItem(new OrderItem("frosted lemonade", 47));
        } else if (b.equals(frosCoffee)) {
            currentOrder.addItem(new OrderItem("frosted coffee", 48));
        } else if (b.equals(milkshakeCC)) {
            currentOrder.addItem(new OrderItem("cookies & cream milkshake", 23));
        } else if (b.equals(milkshakeCH)) {
            currentOrder.addItem(new OrderItem("chocolate milkshake", 20));
        } else if (b.equals(milkshakeS)) {
            currentOrder.addItem(new OrderItem("strawberry milkshake", 22));
        } else if (b.equals(milkshakeV)) {
            currentOrder.addItem(new OrderItem("vanilla milkshake", 21));
        }

        updateDisplay();
    }

    public void handleNewOrder() {
        subtotalDisplay.setText("Subtotal: $0.00");
        taxDisplay.setText("Tax: $0.00");
        priceDisplay.setText("Total: $0.00");
        currentOrder.resetOrder();

    }

    /**
     * 
     * Handles the inventory for a given order item by updating the quantity of each
     * ingredient in the database.
     * Retrieves the recipe for the menu item associated with the order item and
     * updates the quantity of each ingredient
     * accordingly.
     * 
     * @param item the order item to handle the inventory for
     * @throws SQLException if a database access error occurs
     */
    public void handleInventory(OrderItem item) throws SQLException {
        ResultSet recipe = conn.select(String.format("SELECT * FROM recipes WHERE menu_item_id = %d", item.getID()));

        while (recipe.next()) {
            int invID = recipe.getInt("inventory_id");
            int q = recipe.getInt("quantity");
            conn.performQuery(String.format("UPDATE inventory SET quantity = quantity - %d WHERE item_id = %d;",
                    item.getQuantity() * q, invID));
        }

    }

    /**
     * 
     * Completes the current order by inserting it into the database as a new order
     * with paid status and current time,
     * and updating the inventory for each order item. After the order is completed,
     * a new empty order is created.
     * 
     * @throws SQLException if a database access error occurs
     */
    public void handleCompleteOrder() throws SQLException {
        if (currentOrder.isEmpty()) {
            System.out.println("nothing in order");
            return;
        }

        conn.performQuery(String.format("INSERT INTO orders (price, is_paid, order_time) VALUES (%.2f, %b, NOW());",
                currentOrder.getPrice(), true));

        ResultSet lastOrderID = conn.select("SELECT order_id from orders ORDER BY order_id DESC LIMIT 1;");
        int newID = 0;

        if (lastOrderID.next()) {

            newID = lastOrderID.getInt("order_id");

        }

        for (OrderItem item : currentOrder.getOrderItems()) {

            conn.performQuery(
                    String.format("INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (%d, %d, %d);",
                            newID, item.getID(), item.getQuantity()));

            handleInventory(item);
        }

        handleNewOrder();
    }

    public void setConnection(DB db, HashMap<Integer, String[]> menu) {
        conn = db;
        this.menu = menu;
        currentOrder = new Order(menu);
    }

    private void updateDisplay() {
        double subtotal = (currentOrder.getPrice()) / 1.0825;
        subtotalDisplay.setText("Subtotal: " + "$" + String.format("%.2f", subtotal));
        taxDisplay.setText("Tax: " + "$" + String.format("%.2f", (subtotal * 0.0825)));
        priceDisplay.setText("Total: " + "$" + String.format("%.2f", currentOrder.getPrice()));
    }
}
