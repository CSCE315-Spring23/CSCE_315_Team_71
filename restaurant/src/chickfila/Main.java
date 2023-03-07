package chickfila;

import java.sql.*;
import java.util.*;

import chickfila.logic.DB;
import chickfila.logic.dbSetup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private DB conn;
    private HashMap<Integer, String[]> menu;

    @Override
    public void start(Stage primaryStage) throws Exception {

        conn = new DB(dbSetup.user, dbSetup.pswd);
        menu = new HashMap<Integer, String[]>();
        loadMenu();

        //CHECK MENU - UNCOMMENT TO VIEW IN CONSOLE
        for (Map.Entry<Integer, String[]> entry : menu.entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue()[0];
            double price = Double.parseDouble(entry.getValue()[1]);
            String meal = entry.getValue()[2];

            System.out.println(id + " - " + name + ": " + price + ", " + meal);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./start.fxml"));
        Parent root = loader.load();
        ((Controller) loader.getController()).setConnection(conn, menu);

        primaryStage.setTitle("Chick-Fil-A");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

    private void loadMenu() throws SQLException {
        ResultSet menuFetch = conn.select("SELECT * FROM menu_items;");

        while (menuFetch.next()) {
            int menuID = menuFetch.getInt("menu_item_id");
            String menuName = menuFetch.getString("menu_item_name");
            String menuPrice = menuFetch.getString("menu_item_price");
            String isMeal = menuFetch.getString("is_meal");
            menu.put(menuID, new String[]{menuName, menuPrice, isMeal});
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}