package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ProfileController {
    @FXML
    private BorderPane bPane;
    @FXML
    private Label profileNameLbl;
    @FXML
    private Label action1Lbl;
    @FXML
    private Label action2Lbl;
    @FXML
    private Label action3Lbl;
    private boolean isAdmin;
    void setAction(boolean a, String n){
        isAdmin = a;
        profileNameLbl.setText(n);
        if(a){
            action3Lbl.setVisible(true);
            action1Lbl.setText("MOVIES");
            action2Lbl.setText("PERFORMANCES");
            action3Lbl.setText("MEMBERS");
        }else{
            action1Lbl.setText("SEE TICKETS");
            action2Lbl.setText("MANAGE ACCOUNT");
            action3Lbl.setVisible(false);
        }
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        HelloController hc = fxmlLoader.getController();
        MouseEvent e = null;
        hc.home(e);
        Scene scene = new Scene(root);
        lstage.setScene(scene);
        lstage.show();
    }
    @FXML
    void action1clicked(MouseEvent event) throws SQLException, IOException {
        FXMLLoader loader;
        //if admin, manage movies
        if(isAdmin){
            loader = new FXMLLoader(HelloApplication.class.getResource("movieManagement.fxml"));
            Pane managementPane = loader.load();
            MovieManagementController mmc = loader.getController();
            mmc.movieManagement();
            bPane.setCenter(managementPane);
        }
        else{ //if member, see reservations

        }
    }

    @FXML
    void action2clicked(MouseEvent event) throws IOException, SQLException {
        FXMLLoader loader;
        if(isAdmin){
            //manage every performance
            loader = new FXMLLoader(HelloApplication.class.getResource("performanceManagement.fxml"));
            Pane managementPane = loader.load();
            PerformanceManagementController pmc = loader.getController();
            pmc.managePerformance();
            bPane.setCenter(managementPane);
        } else {
            //manage account for member
            loader = new FXMLLoader(HelloApplication.class.getResource("accountManagement.fxml"));
            Pane managementPane = loader.load();
            AccountManagementController amc = loader.getController();
            amc.manageAccount();
            bPane.setCenter(managementPane);
        }
    }

    @FXML
    void action3clicked(MouseEvent event) throws IOException, SQLException {
        FXMLLoader loader;
        if(isAdmin){
            loader = new FXMLLoader(HelloApplication.class.getResource("memberManagement.fxml"));
            Pane managementPane = loader.load();
            MemberManagementController mmc = loader.getController();
            mmc.memberManagement();
            bPane.setCenter(managementPane);
        }
        else{

        }
    }

    @FXML
    void action4clicked(MouseEvent event) {

    }

}
