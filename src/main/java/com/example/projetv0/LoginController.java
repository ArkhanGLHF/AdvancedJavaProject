package com.example.projetv0;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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
    void login(ActionEvent event) throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        String sql = "SELECT * FROM admin WHERE admin_name = ? AND admin_password = ?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, mailTxt.getText());
        statement.setString(2, passwordTxt.getText());
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            //connection
            System.out.println("Connected");
        }

    }
}
