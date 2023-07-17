package com.example.projetv0;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private TextField mailTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Pane loginPane;
    private BorderPane bPane;
    void setBorderPane(BorderPane p){
        bPane = p;
    }
    @FXML
    void login(ActionEvent event) throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        String sql = "SELECT * FROM admin WHERE admin_name = ? AND admin_password = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, mailTxt.getText());
        statement.setString(2, passwordTxt.getText());
        ResultSet rs = statement.executeQuery();

        //if account is admin and exist
        if(rs.next()){
            //setting it connected in database
            int id = rs.getInt("admin_id");
            sql = "UPDATE admin SET admin_isConnected = 1 WHERE admin_id = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } else { //if account is member and exist
            sql = "SELECT * FROM member WHERE member_name = ? AND member_password = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, mailTxt.getText());
            statement.setString(2, passwordTxt.getText());
            rs = statement.executeQuery();
            if(rs.next()){
                //setting it connected in database
                int id = rs.getInt("admin_id");
                sql = "UPDATE member SET member_isConnected = 1 WHERE member_id = ?";
                statement = con.prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

    }
}
