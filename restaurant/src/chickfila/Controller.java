package chickfila;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class Controller {

    @FXML
    private Label label;
    @FXML
    private TextField password;

    private Stage newStage;
    private Parent root2;

    // password.setOnAction(event ->{
    //     String text = password.getText();

    // });
    

    public void passwordcheck (ActionEvent e) throws IOException{
        String text = password.getText();
        System.out.println("You entered "+text);
        if(text.equals("manager")){

            System.out.println("hi manager");
            Parent root = FXMLLoader.load(getClass().getResource("/chickfila/manager/manager.fxml"));

            Scene currscene = password.getScene();
            Scene newScene = new Scene(root);
            Stage newStage = (Stage) currscene.getWindow();
            newStage.setScene(newScene);

        }else if(text.equals("cashier")){
            System.out.println("hello cashier");

            Parent root = FXMLLoader.load(getClass().getResource("/chickfila/cashier/cashier.fxml"));

            Scene currscene = password.getScene();
            Scene newScene = new Scene(root);
            Stage newStage = (Stage) currscene.getWindow();
            newStage.setScene(newScene);
        }else{
            System.out.println("wrong password");
        }
    }

    public void initialize() {

    }
}