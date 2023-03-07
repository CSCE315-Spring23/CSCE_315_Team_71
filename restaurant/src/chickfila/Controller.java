package chickfila;

import java.sql.*;

import chickfila.logic.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    private DB conn;

    public void initialize() {

    }

    public void setConnection(DB db) {
        conn = db;
    }

}