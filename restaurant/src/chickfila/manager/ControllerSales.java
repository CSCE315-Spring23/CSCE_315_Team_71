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
        ((ControllerManager) loader.getController()).setConnection(conn);


        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
    }
    public void initialize() throws SQLException, IOException{
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("salesRep.fxml"));
        // Parent root = loader.load();
        // ((ControllerSales) loader.getController()).setConnection(conn);
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("./salesRep.fxml"));
        // Parent root = loader.load();
        // //((ControllerSales) loader.getController()).setConnection(conn);


        // //Scene scene = new Scene(root);
        // // Stage stage = new Stage();
       

        // Scene scene = new Scene(root);
        // //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Stage stage = new Stage();
        // stage.setScene(new Scene(root, 500, 300));
        // stage.show();
        loadSales();
    }


    public void setConnection(DB db) {
        conn = db;
        System.out.println("asdfasgegegege3");
    }

    // public void showSales(ActionEvent event) throws IOException , SQLException{
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource("salesRep.fxml"));
    //     Parent root = loader.load();
    //     ((ControllerManager) loader.getController()).setConnection(conn);


    //     //Scene scene = new Scene(root);
    //     Stage stage = new Stage();
    //     stage.setScene(new Scene(root, 500, 300));
    //     stage.show();
    //     loadSales();
    // }

    private void loadSales() throws SQLException , IOException {
        //TableColumn<sales,Integer> idColumn = new TableColumn<>("sales_id");
        idColumn.setCellValueFactory(new PropertyValueFactory<sales,Integer>("sales_id"));

        //TableColumn<sales,Integer> dateColumn = new TableColumn<>("sales_date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<sales, String>("sales_date"));

        //TableColumn<sales,Integer> totalSalesColumn = new TableColumn<>("total_sales");
        totalSalesColumn.setCellValueFactory(new PropertyValueFactory<sales, Double>("total_sales"));

        //TableColumn<sales,Integer> totalTaxColumn = new TableColumn<>("total_tax");
        totalTaxColumn.setCellValueFactory(new PropertyValueFactory<sales,Double>("total_tax"));

        //tableView.getColumns().addAll(idColumn, dateColumn, totalSalesColumn, totalTaxColumn);
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("./salesRep.fxml"));
        // Parent root = loader.load();

        // ((ControllerSales) loader.getController()).setConnection(conn);


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
