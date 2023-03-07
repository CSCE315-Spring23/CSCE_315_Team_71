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

//BASIC OVERVIEW OF HOW CONTROLLER MENU WORKS: CREATES A TABLEVIEW OBJECT. TABLE VIEW HAS TABLECOLUMN OBJECTS IN IT
// TABLEVIEW IS THE TABLE AND TABLECOLUMNS ARE THE COLUMNS.
//THESE ARE POPULATED WITH menuItems OBJECTS. THESE ARE BASIC CLASSES CREATED FOR OUR DATABASE ITEMS. LOOK AT THE CLASS FILE FOR QUICK REF.
//THE COLUMNS ARE POPULATED WITH THE COLUMN VAUES AND THEN ALL ADDED TOGETHER AT THE END. THIS IS DONE IN LOADMENU

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
    private TableColumn<menuItems,Boolean> mealColumn;

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

    //FIXME: CAN'T FIND is_meal for some reason.
    private void loadMenu() throws SQLException , IOException {
//THESE LINES TELLS THE COUMNS TO GET THE CERTAIN ATTRIBUTES FROM THE menuItem class objects for the certain columns
        idColumn.setCellValueFactory(new PropertyValueFactory<menuItems,Integer>("menu_item_id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<menuItems, String>("menu_item_name"));

        priceColumn.setCellValueFactory(new PropertyValueFactory<menuItems, Double>("menu_item_price"));

        sizeColumn.setCellValueFactory(new PropertyValueFactory<menuItems,String>("size"));

        mealColumn.setCellValueFactory(new PropertyValueFactory<menuItems,Boolean>("is_meal"));

        conn = new DB(dbSetup.user, dbSetup.pswd);
        ResultSet menuFetch = conn.select("SELECT * FROM menu_items;");

        ObservableList<menuItems> data = FXCollections.observableArrayList();
        //need to make a list of all the lines from the database into menuItems;
        while (menuFetch.next()) {
            int menuID = menuFetch.getInt("menu_item_id");
            String name = menuFetch.getString("menu_item_name");
            double price = menuFetch.getDouble("menu_item_price");
            String size = menuFetch.getString("size");
            Boolean meal = menuFetch.getBoolean("is_meal");
            menuItems menu = new menuItems(menuID, name,price,size, meal);
            data.add(menu);
            System.out.println(menu);
        }
        //pushing all the menuItems to the table is enough afterwards for the table to be made.
        //CONTROLLERSALES works exaclty like this as well
        tableView.setItems(data);
    }
}