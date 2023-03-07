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

public class ControllerSales {

    private DB conn;
    private HashMap<Integer, String[]> menu;

    @FXML
    private TableView <sales> tableView;
    @FXML
    private Button backButton;
    @FXML
    private Button loadButton;
    @FXML 
    private TableColumn<sales,Integer> idColumn;
    @FXML
    private TableColumn<sales,String> dateColumn;
    @FXML
    private TableColumn<sales,Double> totalSalesColumn;
    @FXML
    private TableColumn<sales,Double> totalTaxColumn;

    @FXML
    public void closeButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./manager.fxml"));
        Parent root = loader.load();
        ((ControllerManager) loader.getController()).setConnection(conn, menu);


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
    }
    public void initialize() throws SQLException, IOException{
        loadSales();
    }


    public void setConnection(DB db, HashMap<Integer, String[]> menu) {
        conn = db;
        this.menu = menu;
        System.out.println("asdfasgegegege3");
    }


    private void loadSales() throws SQLException , IOException {
        idColumn.setCellValueFactory(new PropertyValueFactory<sales,Integer>("sales_id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<sales, String>("sales_date"));
        totalSalesColumn.setCellValueFactory(new PropertyValueFactory<sales, Double>("total_sales"));
        totalTaxColumn.setCellValueFactory(new PropertyValueFactory<sales,Double>("total_tax"));

        conn = new DB(dbSetup.user, dbSetup.pswd);
        ResultSet salesFetch = conn.select("SELECT * FROM sales;");

        ObservableList<sales> data = FXCollections.observableArrayList();
        
        while (salesFetch.next()) {
            int salesID = salesFetch.getInt("sales_id");
            String date = salesFetch.getString("sales_date");
            double cost = salesFetch.getDouble("total_sales");
            double tax = salesFetch.getDouble("total_tax");
            sales sale = new sales(salesID, date,cost,tax);
            data.add(sale);
            System.out.println(sale);
        }
        tableView.setItems(data);
    }
}
