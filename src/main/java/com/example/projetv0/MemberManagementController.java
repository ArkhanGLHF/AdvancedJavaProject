package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;

/**
 * Main page of the member management page for the admin, displaying every member and functions to add/delete/edit
 */
public class MemberManagementController {
    @FXML
    private VBox memberPresentationLayout;
    @FXML
    private Text txtError;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtURL;

    /**
     * Function to add a new member
     */
    @FXML
    void addMember() throws SQLException, IOException {
        //getting the values entered in the text fields
        boolean flag = false;
        String name = txtName.getText();
        String mail = txtMail.getText();
        String password = txtPassword.getText();
        String url = txtURL.getText();

        //checking if texts are null
        if(name.equals("")){
            txtError.setText("Error: Name cannot be null");
            flag = true;
        } else if (mail.equals("")) {
            txtError.setText("Error: Mail adress cannot be null");
            flag = true;
        } else if(password.equals("")){
            txtError.setText("Error: Password cannot be null");
            flag = true;
        }

        //checking if mail adress is already used by another member
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        String sql = "SELECT * FROM member";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){ //if the mail is already found in the database
            if(rs.getString("member_mail").equals(mail)){
                txtError.setText("Error: Mail already used");
                flag = true;
                break;
            }
        }

        //if we had an error, display it
        if(flag){
            txtError.setVisible(true);
        } else{ //else add the new member in the database
            if(txtURL.getText().equals("")){
                //if no profile picture, set the default one
                url = "https://cdn.discordapp.com/attachments/1131006802713120819/1131190939386392586/5770f01a32c3c53e90ecda61483ccb08.png";
            }
            sql = "INSERT INTO member (member_name, member_mail, member_password, member_url, member_isConnected) VALUES (?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, mail);
            statement.setString(3, password);
            statement.setString(4, url);
            statement.setInt(5, 0);
            statement.executeUpdate();

            //clear and display again the member management page
            memberPresentationLayout.getChildren().clear();
            memberManagement();
        }
    }

    /**
     * Function to display every member in the database
     */
    void memberManagement() throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying every member
        ResultSet rs = stat.executeQuery("SELECT * FROM `member`");
        while (rs.next()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("memberManagementLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MemberManagementLayoutController memberController = fxmlLoader.getController();
            memberController.setData(rs.getString("member_url"), rs.getString("member_name"), rs.getString("member_password"), rs.getString("member_mail"),rs.getInt("member_id"));
            memberPresentationLayout.getChildren().add(filmbox);
        }
    }
}
