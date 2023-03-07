package chickfila;

import java.io.IOException;
import java.sql.*;

import chickfila.logic.DB;
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
        Scene scene = new Scene(root);

        // Get the current stage from the button's scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene for the stage
        stage.setScene(scene);

    }

    public void handleManager(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./cashier/cashier.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Get the current stage from the button's scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene for the stage
        stage.setScene(scene);

    }

    public void setConnection(DB db) {
        conn = db;
    }

}