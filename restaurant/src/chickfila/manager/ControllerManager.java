package chickfila.manager;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControllerManager {

    public void initialize() {

    }

    public void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../start.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Get the current stage from the button's scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene for the stage
        stage.setScene(scene);
    }
}
