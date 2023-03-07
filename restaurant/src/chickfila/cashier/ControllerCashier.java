package chickfila.cashier;

import java.io.IOException;
import java.util.*;
import java.sql.*;



import chickfila.Controller;
import javafx.fxml.Initializable;
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

import javafx.stage.Stage;
import javafx.scene.Node;



import chickfila.logic.*;;

public class ControllerCashier {

    private DB conn;
    private HashMap<Integer, String[]> menu;
    private Order currentOrder;

    @FXML
    Button newOrder;

    @FXML
    Button completeOrder;

    @FXML
    Button backButton;

    @FXML
    Button n8, n12, gn8, gn12, cs, csSp, csSpD, csD, csGr, csGrD, fries, friesL, chips, sM, sC, sS, sideSal, fruitCup,
            wrap, brownie, cookie, iceDream, softDrinkM, softDrinkL, teaM, teaL, sunjM, sunjL, frosLemonade,
            frosCoffee, milkshakeCC, milkshakeCH, milkshakeS, milkshakeV,n12Meal,gn8Meal, 
            gn12Meal, csMeal, csDMeal,csSpMeal, csSpDMeal, csGrMeal, csGrDMeal, n8Meal, wrapMeal;
    @FXML
    Label priceDisplay;

    public void initialize() {
    }

    public void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../start.fxml"));
        Parent root = loader.load();
        ((Controller) loader.getController()).setConnection(conn, menu);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }
    public void setConnection(DB db) {
        conn = db;
        System.out.println("asdfasgegegege2");
    }

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
        } else if (b.equals(wrap)) {
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
            currentOrder.addItem(new OrderItem("iced tea (medium)", 40)); 
        } else if (b.equals(teaL)) {
            currentOrder.addItem(new OrderItem("iced tea (large)", 41)); 
        } else if (b.equals(sunjL)) {
            currentOrder.addItem(new OrderItem("sunjoy (large)", 45)); 
        } else if (b.equals(sunjM)) {
            currentOrder.addItem(new OrderItem("sunjoy (medium)", 44)); 
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

        priceDisplay.setText("Total: " + String.format("%.2f", currentOrder.getPrice()) + "$");
    }

    public void handleNewOrder() {
        priceDisplay.setText("Total: 0.00$");
        currentOrder.resetOrder();

    }

    public void setConnection(DB db, HashMap<Integer, String[]> menu) {
        conn = db;
        this.menu = menu;
        currentOrder = new Order(menu);
        System.out.println("asdfasgegegege2");
    }
}
