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
    private Pane centerPane;
    @FXML
    private Pane topPane;
    @FXML
    private BorderPane bPane;
    @FXML
    private Label topLbl;
    private boolean isInHomePage = true;
    private Session s;

    private static HelloController instance; // Variable pour stocker la référence du contrôleur principal

    public HelloController() {
        instance = this;
    }

    public static HelloController getInstance() {
        return instance;
    }
    @FXML
    void nameFilter(MouseEvent event) throws SQLException, IOException {
        //clearing pane
        moviePresentationLayout.getChildren().clear();

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs;
        if(isInHomePage){
            rs = stat.executeQuery("SELECT * FROM `movie` WHERE movie_date=2023 ORDER BY movie_name ASC ");
        }else{
            rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY movie_name ASC ");
        }
        while(rs.next()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"), rs.getInt("movie_id"));
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
        ResultSet rs;
        if(isInHomePage){
            rs = stat.executeQuery("SELECT * FROM `movie` WHERE movie_date=2023 ORDER BY movie_genre ASC ");
        }else {
            rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY movie_genre ASC ");
        }
        while(rs.next()){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"), rs.getInt("movie_id"));
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
        ResultSet rs;
        if(isInHomePage){
            rs = stat.executeQuery("SELECT * FROM `movie` WHERE movie_date=2023 ORDER BY movie_date ASC ");
        }else{
            rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY movie_date ASC ");
        }
        while(rs.next()){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"), rs.getInt("movie_id"));
            moviePresentationLayout.getChildren().add(filmbox);

        }
    }
    @FXML
    void reviewsFilter(MouseEvent event) throws SQLException, IOException {
        //clearing pane
        moviePresentationLayout.getChildren().clear();
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs;
        if(isInHomePage){
            rs = stat.executeQuery("SELECT * FROM `movie` WHERE movie_date=2023 ORDER BY movie_review DESC");
        }else {
            rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY movie_review DESC");
        }
        while(rs.next()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"), rs.getInt("movie_id"));
            moviePresentationLayout.getChildren().add(filmbox);
        }
    }
    void startPage() throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie` WHERE movie_date=2023 ORDER BY movie_review DESC");
        while(rs.next()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"), rs.getInt("movie_id"));
            moviePresentationLayout.getChildren().add(filmbox);
        }
    }
    @FXML
    void accountManagement(MouseEvent event) throws IOException {
        isInHomePage = false;
        //loading the account page
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("account.fxml"));
        BorderPane accountPane = loader.load();
        //displaying
        bPane.setCenter(accountPane);
        //if not connected, login
        loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Pane loginPane = loader.load();
        accountPane.setCenter(loginPane);
    }
    @FXML
    void home(MouseEvent event) throws SQLException, IOException {
        isInHomePage = true;
        //clearing pane
        bPane.setCenter(null);
        moviePresentationLayout.getChildren().clear();

        //updating
        topLbl.setText("TOPING THE CHARTS RIGHT NOW!");
        startPage();

        //displaying
        bPane.setCenter(centerPane);
    }
    @FXML
    void browseMovies(MouseEvent event) throws SQLException, IOException {
        isInHomePage = false;
        //clearing pane
        bPane.setCenter(null);
        moviePresentationLayout.getChildren().clear();
        topLbl.setText("VIEW ALL MOVIES!");

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //getting all movies by name
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY movie_name ASC ");
        while(rs.next()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("movieLayout.fxml"));
            HBox filmbox = fxmlLoader.load();

            MovieLayoutController filmController = fxmlLoader.getController();
            filmController.setData(rs.getString("movie_url"), rs.getString("movie_name"),rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"), rs.getInt("movie_id"));
            moviePresentationLayout.getChildren().add(filmbox);
        }

        //displaying
        bPane.setCenter(centerPane);
    }
    @FXML
    void browseCinemas(MouseEvent event) {
        isInHomePage = false;
    }

    void bookingPage(int movie_id) throws SQLException, IOException {
        bPane.setCenter(null);
        moviePresentationLayout.getChildren().clear();

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie` WHERE movie_id="+movie_id);

        if (rs.next()) {
            System.out.println("id trouve");
            System.out.println(rs.getString("movie_name"));
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("booking2.fxml"));
            Pane bookingBox = fxmlLoader.load();

            BookingController bookingController = fxmlLoader.getController();
            bookingController.setDataBooking(rs.getString("movie_url"),rs.getString("movie_name"), rs.getString("movie_synopsis"),
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"), movie_id);
            bPane.setCenter(bookingBox);

        }else {
            System.out.println("id introuvable");
        }

    }
}