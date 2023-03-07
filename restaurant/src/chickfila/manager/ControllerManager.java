package chickfila.manager;

import java.io.IOException;
import java.net.URL;


import java.sql.*;
import java.util.*;

import javafx.scene.control.TextField;

import chickfila.Controller;
import chickfila.logic.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import javafx.scene.Node;

public class ControllerManager implements Initializable {

    private DB conn;
    private HashMap<Integer, String[]> menu;

    @FXML
    private TextField item_name;
    @FXML
    private TextField ingredientlist;
    @FXML
    private Button addOrder;
    @FXML
    private TextField price;
    @FXML
    private Button menu_item;

    private HashMap<String, Integer> inventory;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inventory = new HashMap<String, Integer>();
    }

    // creates order when add order is clicked on
    //see instruction on the app to see how the string format is implemented and how parsing works.
    //FIXME: WON'T WORK IF INPUT ISN'T PROVIDED IN SPECIFIC FORMAT AND IF NAME OF INGREDIENT ISN'T THE EXACT SAME AS INGREDIENTS. NEED TO USE TRY CATCH FOR IT
    public void orderCreation(ActionEvent event) throws SQLException {
    
        if(item_name.getText().equals("") || ingredientlist.getText().equals("")||price.getText().equals("")){
            System.out.println("input value");
        }else{
            Integer max_id = 0;
            ResultSet salesFetch = conn.select("SELECT MAX(menu_item_id) FROM menu_items;");
            while(salesFetch.next()){
                max_id = salesFetch.getInt("max");

                System.out.println(max_id);
            }
            Integer newMenuId= max_id +1;
            String ingredients = ingredientlist.getText();
            String[] splitString = ingredients.split("-");
            double price1 = Double.parseDouble(price.getText());
            
            
            conn.performQuery("INSERT INTO menu_items (menu_item_id, menu_item_name, menu_item_price) VALUES ("+newMenuId+",'"+item_name.getText()+"' , "+price1+");");
            loadInventory();

            for (int i = 0 ;i < splitString.length; i++){
                System.out.println("---->"+splitString[i]);
                //conn.performQuery("INSERT INTO recipes (menu_item_id, inventory_id, quantity)");
                String[] commaSplit = splitString[i].split(",");
                //inventory.get(commaSplit[0]);
                System.out.println(inventory.get(commaSplit[0]));
                System.out.println(commaSplit[1]);
                conn.performQuery("INSERT INTO recipes (menu_item_id,inventory_id,quantity) VALUES ("+newMenuId+", "+inventory.get(commaSplit[0])+","+commaSplit[1]+");");
            }
            System.out.println("success");
        }

    }
    //creates a hashmap of all the inventory items. It's used to get inventory_id from inventory_name for creation of recipes.
    private void loadInventory() throws SQLException {
        ResultSet invFetch = conn.select("SELECT * FROM inventory;");

        while (invFetch.next()) {
            int invID = invFetch.getInt("item_id");
            int quantity = invFetch.getInt("quantity");
            String invName = invFetch.getString("item_name");
            inventory.put(invName, invID);
        }
    }
    //changes scene to start onclick of back button
    public void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../start.fxml"));
        Parent root = loader.load();
        ((Controller) loader.getController()).setConnection(conn, menu);


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
    }
    //db connection stuff, don't know deets
    public void setConnection(DB db, HashMap<Integer, String[]> menu) {
        conn = db;
        this.menu = menu;
        System.out.println("asdfasgegegege3");
    }

    //switches to sales report stage. This just opens the seperate fxml file. Sales report code goes in ControllerSales
    public void showSales(ActionEvent event) throws IOException , SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./salesRep.fxml"));
        Parent root = loader.load();
        ((ControllerSales) loader.getController()).setConnection(conn, menu);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }
    //switches to menu recipes stage. This just opens the seperate fxml file. Sales report code goes in ControllerMenu
    public void showMenu(ActionEvent event) throws IOException , SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./showMenu.fxml"));
        Parent root = loader.load();
        ((ControllerMenu) loader.getController()).setConnection(conn, menu);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    
    }

        //switches to menu recipes stage. This just opens the seperate fxml file. 
        public void showInventory(ActionEvent event) throws IOException , SQLException{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./inventory.fxml"));
            Parent root = loader.load();
            ((ControllerInventory) loader.getController()).setConnection(conn, menu);
    
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
            stage.setScene(scene);
        
        }
        //switches to Inventory_Add stage. This just opens the seperate fxml file.
        public void showInventoryAdd(ActionEvent event) throws IOException , SQLException{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./inventory_add.fxml"));
            Parent root = loader.load();
            ((ControllerInventoryAdd) loader.getController()).setConnection(conn, menu);
    
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
            stage.setScene(scene);
        
        }
        //switches to Orders stage. This just opens the seperate fxml file.
        public void showOrders(ActionEvent event) throws IOException , SQLException{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./orders.fxml"));
            Parent root = loader.load();
            ((ControllerOrders) loader.getController()).setConnection(conn, menu);
    
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
            stage.setScene(scene);
        
        }
}
