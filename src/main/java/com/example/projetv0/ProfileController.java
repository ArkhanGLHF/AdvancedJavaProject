package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ProfileController {
    @FXML
    private Pane profilePane;
    @FXML
    private Label action1Lbl;
    @FXML
    private Label action2Lbl;
    @FXML
    private Label action3Lbl;
    @FXML
    private Label action4Lbl;
    private Pane centerPane;
    void setCenterPane(Pane p){
        centerPane = p;
    }
    @FXML
    void logout(MouseEvent event) throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM `admin` WHERE admin_isConnected = 1");
        //if account is admin and is connected
        if(rs.next()){
            //logout in database
            int id = rs.getInt("admin_id");
            String sql = "UPDATE admin SET admin_isConnected = 0 WHERE admin_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } else {
            //else if account is member
            rs = stat.executeQuery("SELECT * FROM `member` WHERE member_isConnected = 1");
            if(rs.next()){
                //logout in database
                int id = rs.getInt("member_id");
                String sql = "UPDATE member SET member_isConnected = 0 WHERE member_id = ?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        }
    }
}
