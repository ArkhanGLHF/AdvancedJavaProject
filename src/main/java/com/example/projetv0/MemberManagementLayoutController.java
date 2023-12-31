package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Page of a member individually, displayed in the member management page of the admin
 */
public class MemberManagementLayoutController {
    private int member_id;
    @FXML
    private ImageView memberImage;
    @FXML
    private Button editButton;
    @FXML
    private Text mailTxt;
    @FXML
    private Label memberNameLbl;
    @FXML
    private Text passwordTxt;
    @FXML
    private Button removeButton;
    @FXML
    private Button submitEditButton;
    @FXML
    private Text txtError;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextArea txtURL;

    /**
     * Function to receive the data for the member
     */
    public void setData(String imageSrc, String name, String password, String mail, int memberID){
        //setting the data to display
        Image image = new Image(imageSrc);
        memberImage.setImage(image);
        memberNameLbl.setText(name);
        passwordTxt.setText("Password: " + password);
        mailTxt.setText("Mail adress: " + mail);
        member_id = memberID;
    }

    /**
     * Function to display the edit fields for a member
     */
    @FXML
    void editMember() throws SQLException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        String sql = "SELECT * FROM `member` WHERE member_id=" + member_id;
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()) {
            //display edit boxes filled
            txtName.setVisible(true);
            txtName.setText(rs.getString("member_name"));
            txtMail.setVisible(true);
            txtMail.setText(rs.getString("member_mail"));
            txtPassword.setVisible(true);
            txtPassword.setText(rs.getString("member_password"));
            txtURL.setVisible(true);
            txtURL.setText(rs.getString("member_url"));
            submitEditButton.setVisible(true);

            //removing former displaying
            memberNameLbl.setVisible(false);
            passwordTxt.setVisible(false);
            mailTxt.setVisible(false);
            editButton.setVisible(false);
            removeButton.setVisible(false);
        }
    }

    /**
     * Function to delete a member account
     */
    @FXML
    void removeMember(ActionEvent event) throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        //deleting account from database
        String sql = "DELETE FROM `member` WHERE member_id = " + member_id;
        stat.executeUpdate(sql);

        //reloading account management page
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        HelloController hc = fxmlLoader.getController();
        hc.accountManagement();
        Scene scene = new Scene(root);
        lstage.setScene(scene);
        lstage.show();
    }

    /**
     * Function to send the edited information to the database
     */
    @FXML
    void submitEdit(ActionEvent event) throws SQLException, IOException {
        //getting all values entered in text-fields
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
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        String sql = "SELECT * FROM member";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            //if the mail is found in the database and by another member
            if(rs.getString("member_mail").equals(mail) && rs.getInt("member_id")!= member_id){
                txtError.setText("Error: Mail already used by another member");
                flag = true;
                break;
            }
        }

        //if we had an error, display it
        if(flag){
            txtError.setVisible(true);
        } else{
            //we update the information in the database
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
            hc.accountManagement();
            Scene scene = new Scene(root);
            lstage.setScene(scene);
            lstage.show();
        }
    }
}
