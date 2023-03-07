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
    private HashMap<String, Double> menu;

    @Override
    public void start(Stage primaryStage) throws Exception {

        conn = new DB(dbSetup.user, dbSetup.pswd);
        menu = new HashMap<String, Double>();
        loadMenu();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./hellofx.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    private void loadMenu() throws SQLException {
        ResultSet menuFetch = conn.select("SELECT * FROM menu_items;");

        while (menuFetch.next()) {
            String menuName = menuFetch.getString("menu_item_name");
            double menuPrice = menuFetch.getDouble("menu_item_price");
            menu.put(menuName, menuPrice);
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}