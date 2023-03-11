package chickfila;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

import chickfila.cashier.ControllerCashier;
import chickfila.logic.DB;
import chickfila.manager.ControllerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.*;

public class Controller {

    private DB conn;
    private HashMap<Integer, String[]> menu;

    @FXML
    Button cashier, manager;

    public Controller(DB conn, HashMap<Integer, String[]> menu) {
        this.conn = conn;
        this.menu = menu;
    }

    public void initialize() {
        cashier.setOnMouseEntered(e -> cashier.setStyle("-fx-background-color: #ff3c46;"));
        cashier.setOnMouseExited(e -> cashier.setStyle("-fx-background-color: #e60e33;"));

        manager.setOnMouseEntered(e -> manager.setStyle("-fx-background-color: #ff3c46;"));
        manager.setOnMouseExited(e -> manager.setStyle("-fx-background-color: #e60e33;"));
    }

    public void handleCashier(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./cashier/cashier.fxml"));
        ControllerCashier c = new ControllerCashier(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void handleManager(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./manager/manager.fxml"));
        ControllerManager c = new ControllerManager(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

}