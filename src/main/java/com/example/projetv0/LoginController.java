package com.example.projetv0;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML
    private AnchorPane createPane;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private TextField mailTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Text errorTxt;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtURL;
    @FXML
    private Text txtError;
    @FXML
    void createAccount(MouseEvent event) {
        loginPane.setVisible(false);
        createPane.setVisible(true);
    }
    @FXML
    void create(ActionEvent event) throws SQLException, IOException {
        boolean flag = false;
        if(txtName.getText().equals("")){
            flag = true;
            txtError.setText("Error: Name cannot be null");
        } else if(txtMail.getText().equals("")){
            flag = true;
            txtError.setText("Error: Mail adress cannot be null");
        } else if(txtPassword.getText().equals("")){
            flag = true;
            txtError.setText("Error: Password cannot be null");
        }

        //checking if mail already used
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        String sql = "SELECT * FROM member";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){ //if the mail is found in the database and by another member
            if(rs.getString("member_mail").equals(txtMail.getText())){
                txtError.setText("Error: Mail already used");
                flag = true;
                break;
            }
        }

        if(flag){
            txtError.setVisible(true);
        } else{
            if(txtURL.getText().equals("")){
                txtURL.setText("https://cdn.discordapp.com/attachments/1131006802713120819/1131190939386392586/5770f01a32c3c53e90ecda61483ccb08.png");
            }
            //connection to database
            sql = "INSERT INTO member (member_name, member_mail, member_password, member_url, member_isConnected) VALUES (?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, txtName.getText());
            statement.setString(2, txtMail.getText());
            statement.setString(3, txtPassword.getText());
            statement.setString(4, txtURL.getText());
            statement.setInt(5, 0);
            statement.executeUpdate();

            //reloading management page
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            HelloController hc = fxmlLoader.getController();
            MouseEvent e = null;
            hc.accountManagement(e);
            Scene scene = new Scene(root);
            lstage.setScene(scene);
            lstage.show();
        }
    }
    @FXML
    void log(MouseEvent event) {
        createPane.setVisible(false);
        loginPane.setVisible(true);
    }
    @FXML
    void login(ActionEvent event) throws SQLException, IOException {
        boolean connected = false;
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
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
            connected = true;
        } else { //if account is member and exist
            sql = "SELECT * FROM member WHERE member_name = ? AND member_password = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, mailTxt.getText());
            statement.setString(2, passwordTxt.getText());
            rs = statement.executeQuery();
            if(rs.next()){
                //setting it connected in database
                int id = rs.getInt("member_id");
                sql = "UPDATE member SET member_isConnected = 1 WHERE member_id = ?";
                statement = con.prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();
                connected = true;
            }
        }
        if(connected){
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
        } else {
            errorTxt.setText("Error: Incorrect informations");
            errorTxt.setVisible(true);
        }


    }
}
