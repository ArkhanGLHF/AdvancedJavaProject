package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.io.IOException;
import java.sql.*;

public class HelloController {
    @FXML
    private VBox moviePresentationLayout;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Label nameLbl;
    @FXML
    private Label genreLbl;
    @FXML
    private Label releaseLbl;

    private Session s;
    @FXML
    void nameFilter(MouseEvent event) throws SQLException, IOException {
        //clearing pane
        moviePresentationLayout.getChildren().clear();

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY movie_name ASC ");
        while(rs.next()){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"));
            moviePresentationLayout.getChildren().add(filmbox);

        }

    }
    @FXML
    void genreFilter(MouseEvent event) throws SQLException, IOException {
        //clearing pane
        moviePresentationLayout.getChildren().clear();

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY movie_genre ASC ");
        while(rs.next()){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"));
            moviePresentationLayout.getChildren().add(filmbox);

        }
    }
    @FXML
    void releaseDateFilter(MouseEvent event) throws SQLException, IOException {
        //clearing pane
        moviePresentationLayout.getChildren().clear();

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY movie_date ASC ");
        while(rs.next()){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"));
            moviePresentationLayout.getChildren().add(filmbox);

        }
    }

    @FXML
    void reviewsFilter(MouseEvent event) throws SQLException, IOException {
        //clearing pane
        moviePresentationLayout.getChildren().clear();
        //displaying the start page
        startPage();

    }

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

        }
    }
    @FXML
    void accountManagement(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Start.fxml"));
        BorderPane accountPane = loader.load();
        mainPane.setCenter(accountPane);
        //if not connected, login
        loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Pane loginPane = loader.load();
        accountPane.setCenter(loginPane);
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