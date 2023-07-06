package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.io.IOException;
import java.sql.*;

public class HelloController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private Pane leftPane;

    @FXML
    private VBox moviePresentationLayout;
    /*
    @FXML
    private ImageView movieImage;
    @FXML
    private Text movieGenreTxt;
    @FXML
    private Label movieNameLbl;
    @FXML
    private Text movieRelTxt;
    @FXML
    private Text movieRevTxt;
    @FXML
    private Text movieSynTxt;

     */

    void startPage() throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY movie_review DESC");
        while(rs.next()){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"));
            moviePresentationLayout.getChildren().add(filmbox);

            /*
            movieNameLbl.setText(rs.getString("movie_name"));
            movieGenreTxt.setText("Genre: " + rs.getString("movie_genre"));
            movieRelTxt.setText("Release date: " + rs.getString("movie_date"));
            movieRevTxt.setText("Reviews: " + rs.getString("movie_review"));
            movieImage.setImage(new Image(rs.getString("movie_url")));
            movieSynTxt.setText("Synopsis: " + rs.getString("movie_synopsis"));

             */
        }

        //hide and save leftPane (movie filter) in the home page
        //Pane leftContainer = leftPane;
        //mainPane.setLeft(null);
    }

    public void browseMovies(ActionEvent actionEvent) {
    }

    public void bookTickets(ActionEvent actionEvent) {
    }

    public void buyGiftCards(ActionEvent actionEvent) {
    }

    public void login(ActionEvent actionEvent) {
    }

    public void register(ActionEvent actionEvent) {
    }
}