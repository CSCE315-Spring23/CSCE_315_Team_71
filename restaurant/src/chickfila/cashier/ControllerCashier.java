package chickfila.cashier;

import java.io.IOException;

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

public class ControllerCashier {

    private DB conn;
    @FXML 
    Button newOrder;

    @FXML
    Button completeOrder;

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

    public void setConnection(DB db) {
        conn = db;
        System.out.println("asdfasgegegege2");
    }
}
