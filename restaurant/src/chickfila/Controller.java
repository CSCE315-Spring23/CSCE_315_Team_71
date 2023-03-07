package chickfila;

import java.io.IOException;
import java.sql.*;

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

    @FXML
    Button cashier, manager;

    public void initialize() {

    }

    public void handleCashier(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./cashier/cashier.fxml"));
        Parent root = loader.load();
        ((ControllerCashier) loader.getController()).setConnection(conn);

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void handleManager(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./manager/manager.fxml"));
        Parent root = loader.load();
        ((ControllerManager) loader.getController()).setConnection(conn);

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    public void setConnection(DB db) {
        conn = db;
        System.out.println("asdfasgegegege");
    }

}