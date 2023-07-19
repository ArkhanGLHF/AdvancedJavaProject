package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.io.IOException;
import java.sql.*;

public class MovieManagementController {
    @FXML
    private VBox moviePresentationLayout;
    @FXML
    private TextField txtDate;
    @FXML
    private Text txtError;
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
    void addMovie(ActionEvent event) throws SQLException, IOException {
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

        if(flag){
            txtError.setVisible(true);
        } else{
            int length = Integer.parseInt(txtLength.getText());
            int release = Integer.parseInt(txtDate.getText());
            float review = Float.parseFloat(txtReview.getText());

            //connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
            String sql = "INSERT INTO movie (movie_name, movie_genre, movie_length, movie_date, movie_review, movie_url, movie_synopsis) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, genre);
            statement.setInt(3, length);
            statement.setInt(4, release);
            statement.setFloat(5, review);
            statement.setString(6, url);
            statement.setString(7, synopsis);
            statement.executeUpdate();

            moviePresentationLayout.getChildren().clear();
            movieManagement();
        }

    }
    void movieManagement() throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");
        while (rs.next()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieManagementLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieManagementLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"), rs.getString("movie_genre"), rs.getString("movie_review"), rs.getInt("movie_id"));
            moviePresentationLayout.getChildren().add(filmbox);
        }
    }
}
