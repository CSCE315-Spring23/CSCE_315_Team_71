package chickfila.manager;

import java.io.IOException;
import chickfila.manager.sales;
import java.sql.*;
import java.util.*;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import chickfila.logic.DB;
import chickfila.logic.dbSetup;

public class ControllerMenu {

    private DB conn;

    @FXML
    private TableView <menuItems> tableView;
    @FXML
    private Button backButton;
    @FXML
    private Button loadButton;
    @FXML 
    private TableColumn<menuItems,Integer> idColumn;
    @FXML
    private TableColumn<menuItems,String> nameColumn;
    @FXML
    private TableColumn<menuItems,Double> priceColumn;
    @FXML
    private TableColumn<menuItems,String> sizeColumn;
    @FXML
    private TableColumn<menuItems,String> mealColumn;

    @FXML
    public void closeButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./manager.fxml"));
        Parent root = loader.load();
        ((ControllerManager) loader.getController()).setConnection(conn);


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
    }
    public void initialize() throws SQLException, IOException{
        loadMenu();
    }


    public void setConnection(DB db) {
        conn = db;
        System.out.println("asdfasgegegege3");
    }


    private void loadMenu() throws SQLException , IOException {

        idColumn.setCellValueFactory(new PropertyValueFactory<menuItems,Integer>("menu_item_id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<menuItems, String>("menu_item_name"));

        priceColumn.setCellValueFactory(new PropertyValueFactory<menuItems, Double>("menu_item_price"));

        sizeColumn.setCellValueFactory(new PropertyValueFactory<menuItems,String>("size"));

        mealColumn.setCellValueFactory(new PropertyValueFactory<menuItems,String>("is_meal"));

        conn = new DB(dbSetup.user, dbSetup.pswd);
        ResultSet menuFetch = conn.select("SELECT * FROM menu_items;");

        ObservableList<menuItems> data = FXCollections.observableArrayList();
        
        while (menuFetch.next()) {
            int menuID = menuFetch.getInt("menu_item_id");
            String name = menuFetch.getString("menu_item_name");
            double price = menuFetch.getDouble("menu_item_price");
            String size = menuFetch.getString("size");
            String meal = menuFetch.getString("is_meal");
            menuItems menu = new menuItems(menuID, name,price,size, meal);
            data.add(menu);
            System.out.println(menu);
        }
        tableView.setItems(data);
    }
}
