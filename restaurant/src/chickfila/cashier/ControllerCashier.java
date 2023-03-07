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
            wrap1, brownie, cookie, iceDream, softDrinkM, softDrinkL, teaM, teaL, sunjM, sunjL, frosLemonade,
            frosCoffee, milkshakeCC, milkshakeCH, milkshakeS, milkshakeV;

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
        } else if (b.equals(csGr)) {
            currentOrder.addItem(new OrderItem("chicken sandwich grilled", 18));
        } else if (b.equals(fries)) {
            currentOrder.addItem(new OrderItem("waffle fries", 12));
        } else if (b.equals(sM)) {
            currentOrder.addItem(new OrderItem("market salad", 19));
        } else if (b.equals(sC)) {
            currentOrder.addItem(new OrderItem("cobb salad", 38));
        } else if (b.equals(sS)) {
            currentOrder.addItem(new OrderItem("southwest salad", 39));
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
