package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Main page for the members account management
 */
public class AccountManagementController {
    private int member_id;
    @FXML
    private ImageView profileImage;
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
     * Displaying the information of the connected account
     */
    void manageAccount() throws SQLException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the information of the connected account
        ResultSet rs = stat.executeQuery("SELECT * FROM `member` WHERE member_isConnected=1");
        if(rs.next()){
            member_id = rs.getInt("member_id");
            txtMail.setText(rs.getString("member_mail"));
            txtName.setText(rs.getString("member_name"));
            txtPassword.setText(rs.getString("member_password"));
            txtURL.setText(rs.getString("member_url"));
            Image image = new Image(txtURL.getText());
            profileImage.setImage(image);
        }
    }

    /**
     * Function to delete your account
     */
    @FXML
    void deleteAccount(ActionEvent event) {
        //Create a verification alert before deleting an account
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Deleting account");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");
        Button deleteButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        deleteButton.setText("Delete");
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText("Cancel");
        alert.showAndWait().ifPresent(response -> {
            //if we confirm
            if (response == ButtonType.OK) {
                Connection con;
                try {
                    //connection to database
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
                    Statement stat = con.createStatement();
                    //deleting account
                    String sql = "DELETE FROM `member` WHERE member_id = " + member_id;
                    stat.executeUpdate(sql);
                    //loading home page
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
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Function to edit the information of your account
     */
    @FXML
    void editAccount(ActionEvent event) throws SQLException, IOException {
        //getting the new values
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
            txtError.setText("Error: Mail cannot be null");
            flag = true;
        } else if (password.equals("")) {
            txtError.setText("Error: Password cannot be null");
            flag = true;
        } else if (url.equals("")) {
            txtError.setText("Error: Image URL cannot be null");
            flag = true;
        }

        //checking if mail adress is already used by another member
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        String sql = "SELECT * FROM member";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        //if the mail is found in the database and by another member, flag
        while (rs.next()){
            if(rs.getString("member_mail").equals(mail) && rs.getInt("member_id")!= member_id){
                txtError.setText("Error: Mail already used by another member");
                flag = true;
                break;
            }
        }

        //if an error has been found, we display the error
        if(flag){
            txtError.setVisible(true);
        } else{
            //else we update the information
            sql = "UPDATE member SET member_name=?, member_mail=?, member_password=?, member_url=? WHERE member_id=" + member_id;
            statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, mail);
            statement.setString(3, password);
            statement.setString(4, url);
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
}
