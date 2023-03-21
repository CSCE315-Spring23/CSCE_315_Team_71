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

    /**
    * 
    * Initializes the controller class. Sets up the mouse entered and exited event handlers for the
    * cashier and manager buttons, changing their background color.
    */
    public void initialize() {
        cashier.setOnMouseEntered(e -> cashier.setStyle("-fx-background-color: #ff3c46;"));
        cashier.setOnMouseExited(e -> cashier.setStyle("-fx-background-color: #e60e33;"));

        manager.setOnMouseEntered(e -> manager.setStyle("-fx-background-color: #ff3c46;"));
        manager.setOnMouseExited(e -> manager.setStyle("-fx-background-color: #e60e33;"));
    }

    /**
    *
    * Handles the event when the "Cashier" button is clicked. Loads the Cashier view,
    * creates a new instance of the ControllerCashier class and sets it as the controller
    * for the view. Sets the new scene and shows the stage.
    * @param event the ActionEvent triggered by the "Cashier" button
    * @throws IOException if the FXMLLoader fails to load the FXML file
    */
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
    
    /**
    *
    * Handles the event when the manager button is clicked. 
    * Loads the Manager.fxml file, sets its controller to a new instance of ControllerManager,
    * and sets the scene to display the loaded file on the current stage.
    * @param event The action event triggered by the manager button.
    * @throws IOException If an input/output error occurs during the loading of the FXML file.
    */
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