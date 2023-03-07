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
           // newStage = (Stage)((Node)e.getSource()).getScene().getWindow();
           //newStage= (Stage) currentSceneNode.getScene().getWindow();

        //    FXMLLoader loader= new FXMLLoader(getClass().getResource("./manager/manager.fxml"));
        //     Parent root;
        //     root = (Parent)loader.load();

        //     Stage stage = (Stage) root.getScene().getWindow();

            Scene currscene = password.getScene();
            Scene newScene = new Scene(root);
            Stage newStage = (Stage) currscene.getWindow();
            newStage.setScene(newScene);

            //newStage.setTitle("manager");
            //newStage.setScene(new Scene(root2, 600, 400));
            //newStage.show();
           // newStage.show();

        }else if(text.equals("client")){
            System.out.println("hello client");
        }else{
            System.out.println("wrong password");
        }
    }

    public void initialize() {

    }
}