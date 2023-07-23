package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

/**
 * Page of a movie individually, displayed in the movie management page of the admin
 */
public class MovieManagementLayoutController {
    private int movie_id;
    @FXML
    private Text GenreTxt;
    @FXML
    private Text ReviewsTxt;
    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button submitEditButton;
    @FXML
    private ImageView movieImage;
    @FXML
    private Label movieNameLbl;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtGenre;
    @FXML
    private TextField txtLength;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtReview;
    @FXML
    private TextArea txtSynopsis;
    @FXML
    private TextField txtURL;
    @FXML
    private Text txtError;

    /**
     * Function to set the data of the movie
     */
    public void setData(String imageSrc, String name, String Genre, String Reviews, int movieId){
        //setting the data received
        Image image = new Image(imageSrc);
        movieImage.setImage(image);
        movieNameLbl.setText(name);
        ReviewsTxt.setText("Reviews: "+Reviews);
        GenreTxt.setText("Genre: "+Genre);
        movie_id = movieId;
    }

    /**
     * Function to display the edit boxes for the movie
     */
    @FXML
    void editMovie() throws SQLException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        String sql = "SELECT * FROM `movie` WHERE movie_id=" + movie_id;
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()) {
            //display edit boxes filled
            txtDate.setVisible(true);
            txtDate.setText(rs.getString("movie_date"));
            txtGenre.setVisible(true);
            txtGenre.setText(rs.getString("movie_genre"));
            txtLength.setVisible(true);
            txtLength.setText(rs.getString("movie_length"));
            txtName.setVisible(true);
            txtName.setText(rs.getString("movie_name"));
            txtReview.setVisible(true);
            txtReview.setText(rs.getString("movie_review"));
            txtSynopsis.setVisible(true);
            txtSynopsis.setText(rs.getString("movie_synopsis"));
            txtURL.setVisible(true);
            txtURL.setText(rs.getString("movie_url"));
            submitEditButton.setVisible(true);

            //removing former displaying
            movieNameLbl.setVisible(false);
            GenreTxt.setVisible(false);
            ReviewsTxt.setVisible(false);
            editButton.setVisible(false);
            removeButton.setVisible(false);
        }
    }

    /**
     * Function to edit the movie information in the database
    */
    @FXML
    void submitEdit(ActionEvent event) throws SQLException, IOException {
        //getting the values from the text-fields
        boolean flag = false;
        String name = txtName.getText();
        String genre = txtGenre.getText();
        String synopsis = txtSynopsis.getText();
        String url = txtURL.getText();

        //checking if texts are null
        if(name.equals("")){
            txtError.setText("Error: Name cannot be null");
            flag = true;
        } else if (genre.equals("")) {
            txtError.setText("Error: Genre cannot be null");
            flag = true;
        } else if (txtLength.getText().equals("")) {
            txtError.setText("Error: Length cannot be null");
            flag = true;
        } else if (txtReview.getText().equals("")) {
            txtError.setText("Error: Review cannot be null");
            flag = true;
        } else if (txtDate.getText().equals("")) {
            txtError.setText("Error: Release date cannot be null");
            flag = true;
        } else if (synopsis.equals("")) {
            txtError.setText("Error: Synopsis cannot be null");
            flag = true;
        } else if (url.equals("")) {
            txtError.setText("Error: URL cannot be null");
            flag = true;
        }
        if(!txtLength.getText().equals("")) {
            if(Integer.parseInt(txtLength.getText()) < 0){
                txtError.setText("Error: Movie length must be greater than 1");
                flag = true;
            }
        }

        //checking if values are in the right range
        if(!txtDate.getText().equals("")) {
            if(Integer.parseInt(txtDate.getText()) < 1900 || Integer.parseInt(txtDate.getText()) > 2023){
                txtError.setText("Error: Release date must be between 1900 and 2023");
                flag = true;
            }
        }
        if(!txtReview.getText().equals("")) {
            if(Float.parseFloat(txtReview.getText()) < 0 || Float.parseFloat(txtReview.getText()) > 10){
                txtError.setText("Error: Review must be between 0 and 10");
                flag = true;
            }
        }

        //if we had an error, display it
        if(flag){
            txtError.setVisible(true);
        } else{
            //casting the values
            int length = Integer.parseInt(txtLength.getText());
            int release = Integer.parseInt(txtDate.getText());
            float review = Float.parseFloat(txtReview.getText());

            //connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
            //updating movie in database
            String sql = "UPDATE movie SET movie_name=?, movie_genre=?, movie_length=?, movie_date=?, movie_review=?, movie_url=? WHERE movie_id=" + movie_id;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, genre);
            statement.setInt(3, length);
            statement.setInt(4, release);
            statement.setFloat(5, review);
            statement.setString(6, url);
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

    /**
     * Function to delete a movie from the database
     */
    @FXML
    void removeMovie(ActionEvent event) throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        //deleting movie from database
        String sql = "DELETE FROM `movie` WHERE movie_id = " + movie_id;
        stat.executeUpdate(sql);

        //reloading account management page
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

