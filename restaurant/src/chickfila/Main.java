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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./start.fxml"));
        Controller c = new Controller(conn, menu);
        loader.setController(c);
        Parent root = loader.load();

        primaryStage.setTitle("Chick-Fil-A");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        conn.close();
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