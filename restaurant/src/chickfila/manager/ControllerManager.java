package chickfila.manager;

import java.io.IOException;
import java.net.URL;

import chickfila.manager.sales;
import java.sql.*;
import java.util.*;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import chickfila.Controller;
import chickfila.logic.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Arrays;
public class ControllerManager implements Initializable {

    private DB conn;

    @FXML
    private TableView <sales> tableView;
    
    @FXML 
    private TableColumn<sales,Integer> idColumn;
    @FXML
    private TableColumn<sales,String> dateColumn;
    @FXML
    private TableColumn<sales,Double> totalSalesColumn;
    @FXML
    private TableColumn<sales,Double> totalTaxColumn;
    @FXML
    private TextField item_name;
    @FXML
    private TextField ingredientlist;
    @FXML
    private Button addOrder;
    @FXML
    private TextField price;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

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
            for (int i = 0 ;i < splitString.length; i++){
                System.out.println("---->"+splitString[i]);
            }
            
            conn.performQuery("INSERT INTO menu_items (menu_item_id, menu_item_name, menu_item_price) VALUES ("+newMenuId+",'"+item_name.getText()+"' , "+price1+")");
            System.out.println("success");
        }
    }

    public void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../start.fxml"));
        Parent root = loader.load();
        ((Controller) loader.getController()).setConnection(conn);


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
    }

    public void setConnection(DB db) {
        conn = db;
        System.out.println("asdfasgegegege3");
    }

    public void showSales(ActionEvent event) throws IOException , SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./salesRep.fxml"));
        Parent root = loader.load();
        ((ControllerSales) loader.getController()).setConnection(conn);


        //Scene scene = new Scene(root);
        // Stage stage = new Stage();
        // stage.setScene(new Scene(root, 500, 300));

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        //loadSales();
    }

    // private void loadSales() throws SQLException {
    //     //TableColumn<sales,Integer> idColumn = new TableColumn<>("sales_id");
    //     // idColumn.setCellValueFactory(new PropertyValueFactory<sales,Integer>("sales_id"));

    //     // //TableColumn<sales,Integer> dateColumn = new TableColumn<>("sales_date");
    //     // dateColumn.setCellValueFactory(new PropertyValueFactory<sales, String>("sales_date"));

    //     // //TableColumn<sales,Integer> totalSalesColumn = new TableColumn<>("total_sales");
    //     // totalSalesColumn.setCellValueFactory(new PropertyValueFactory<sales, Double>("total_sales"));

    //     // //TableColumn<sales,Integer> totalTaxColumn = new TableColumn<>("total_tax");
    //     // totalTaxColumn.setCellValueFactory(new PropertyValueFactory<sales,Double>("total_tax"));

    //     //tableView.getColumns().addAll(idColumn, dateColumn, totalSalesColumn, totalTaxColumn);

    //     ResultSet salesFetch = conn.select("SELECT * FROM sales;");

    //     ObservableList<sales> data = FXCollections.observableArrayList();
        
    //     while (salesFetch.next()) {
    //         int salesID = salesFetch.getInt("sales_id");
    //         String date = salesFetch.getString("sales_date");
    //         double cost = salesFetch.getDouble("total_sales");
    //         double tax = salesFetch.getDouble("total_tax");
    //         sales sale = new sales(salesID, date,cost,tax);
    //         data.add(sale);
    //         System.out.println(sale);
    //     }
    //     tableView.setItems(data);
    // }
}
