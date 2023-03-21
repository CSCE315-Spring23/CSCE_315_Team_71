package chickfila.manager;

import java.io.IOException;
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
import chickfila.logic.menuItems;
import javafx.scene.control.TextField;

//BASIC OVERVIEW OF HOW CONTROLLER MENU WORKS: CREATES A TABLEVIEW OBJECT. TABLE VIEW HAS TABLECOLUMN OBJECTS IN IT
// TABLEVIEW IS THE TABLE AND TABLECOLUMNS ARE THE COLUMNS.
//THESE ARE POPULATED WITH menuItems OBJECTS. THESE ARE BASIC CLASSES CREATED FOR OUR DATABASE ITEMS. LOOK AT THE CLASS FILE FOR QUICK REF.
//THE COLUMNS ARE POPULATED WITH THE COLUMN VAUES AND THEN ALL ADDED TOGETHER AT THE END. THIS IS DONE IN LOADMENU

public class ControllerMenu {

    private DB conn;
    private HashMap<Integer, String[]> menu;

    @FXML
    private TableView<menuItems> tableView;
    @FXML
    private Button backButton;
    @FXML
    private Button loadButton;
    @FXML
    private TableColumn<menuItems, Integer> idColumn;
    @FXML
    private TableColumn<menuItems, String> nameColumn;
    @FXML
    private TableColumn<menuItems, Double> priceColumn;
    @FXML
    private TableColumn<menuItems, String> sizeColumn;
    @FXML
    private TableColumn<menuItems, Boolean> mealColumn;

    @FXML
    private TextField menuItemInput;
    @FXML
    private TextField priceInput;
    @FXML
    private TextField nameInput;

    public ControllerMenu(DB conn, HashMap<Integer, String[]> menu) {
        this.conn = conn;
        this.menu = menu;
    }

    public void closeButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./manager.fxml"));
        ControllerManager c = new ControllerManager(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    public void initialize() throws SQLException, IOException {
        loadMenu();
    }

    private void loadMenu() throws SQLException, IOException {
        // THESE LINES TELLS THE COUMNS TO GET THE CERTAIN ATTRIBUTES FROM THE menuItem
        // class objects for the certain columns
        idColumn.setCellValueFactory(new PropertyValueFactory<menuItems, Integer>("menu_item_id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<menuItems, String>("menu_item_name"));

        priceColumn.setCellValueFactory(new PropertyValueFactory<menuItems, Double>("menu_item_price"));

        sizeColumn.setCellValueFactory(new PropertyValueFactory<menuItems, String>("size"));

        mealColumn.setCellValueFactory(new PropertyValueFactory<menuItems, Boolean>("is_meal"));

        ResultSet menuFetch = conn.select("SELECT * FROM menu_items ORDER BY menu_item_id;");

        ObservableList<menuItems> data = FXCollections.observableArrayList();
        // need to make a list of all the lines from the database into menuItems;
        while (menuFetch.next()) {
            int menuID = menuFetch.getInt("menu_item_id");
            String name = menuFetch.getString("menu_item_name");
            double price = menuFetch.getDouble("menu_item_price");
            String size = menuFetch.getString("size");
            Boolean meal = menuFetch.getString("is_meal").equals("t");
            menuItems menu = new menuItems(menuID, name, price, size, meal);
            data.add(menu);
        }
        // pushing all the menuItems to the table is enough afterwards for the table to
        // be made.
        // CONTROLLERSALES works exaclty like this as well
        tableView.setItems(data);
    }

    public void modifyItem(ActionEvent event) throws SQLException, IOException {
        if (menuItemInput.getText().equals("")) {
            System.out.println("input value menu_item_id");
        } else {
            if (priceInput.getText().equals("") && nameInput.getText().equals("")) {
                System.out.println("input value name or price");

            } else {
                int menuItemId = Integer.parseInt(menuItemInput.getText());

                if (priceInput.getText() != "") {
                    Double updatedPrice = Double.parseDouble(priceInput.getText());
                    conn.performQuery("UPDATE menu_items SET menu_item_price = " + updatedPrice
                            + " WHERE menu_item_id = " + menuItemId + ";");
                }

                if (nameInput.getText() != "") {
                    String updatedName = nameInput.getText();
                    conn.performQuery("UPDATE menu_items SET menu_item_name = '" + updatedName
                            + "' WHERE menu_item_id = " + menuItemId + ";");
                }
                loadMenu();
            }
        }
    }

    public void removeMenuItem(ActionEvent event) throws SQLException, IOException {
        if (menuItemInput.getText().equals("")) {
            System.out.println("input value");
        } else {
            // int itemId = Integer.parseInt(inventoryId.getText());
            int menuID = Integer.parseInt(menuItemInput.getText());
            conn.performQuery("DELETE FROM menu_items WHERE menu_item_id = "+ menuID +";");
            conn.performQuery("DELETE FROM recipes WHERE menu_item_id = "+ menuID +";");
            System.out.println("successnv");
        }
        loadMenu();
    }

}
