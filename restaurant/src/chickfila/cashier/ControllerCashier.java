package chickfila.cashier;

import java.io.IOException;
import java.util.*;

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

import chickfila.logic.*;;

public class ControllerCashier {

    private DB conn;
    private ArrayList<OrderItem> currentOrder;

    @FXML 
    Button newOrder;

    @FXML
    Button completeOrder;

    @FXML
    Button n8, n12, gn8, gn12, cs, csSp, csGr, fries, sM, sC, sS;

    public void initialize() {

    }

    public void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../start.fxml"));
        Parent root = loader.load();
        ((Controller) loader.getController()).setConnection(conn);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    public void handleClick(ActionEvent event) {
        Button b = (Button) event.getSource();

        if (b.equals(n8)) {
            System.out.println("goop");
        }
        else if (b.equals(n12)) {
            System.out.println("goop2");
        }
        else if (b.equals(gn8)) {
            System.out.println("goop3");
        }
        else if (b.equals(gn12)) {
            System.out.println("goop4");
        }
        else if (b.equals(cs)) {
            System.out.println("goop5");
        }
        else if (b.equals(csSp)) {
            System.out.println("goop6");
        }
        else if (b.equals(csGr)) {
            System.out.println("goop7");
        }
        else if (b.equals(fries)) {
            System.out.println("goop8");
        }
        else if (b.equals(sM)) {
            System.out.println("goop9");
        }
        else if (b.equals(sC)) {
            System.out.println("goop10");
        }
        else if (b.equals(sS)) {
            System.out.println("goop12");
        }
    }

    public void setConnection(DB db) {
        conn = db;
        System.out.println("asdfasgegegege2");
    }
}
