package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.io.IOException;
import java.sql.*;

public class HelloController {
    @FXML
    private VBox moviePresentationLayout;
    @FXML
    private Pane centerPane;
    @FXML
    private BorderPane bPane;
    @FXML
    private Label accountLbl;
    @FXML
    private Label topLbl;
    private boolean isInHomePage = true;
    private boolean isAdmin = false;
    private String profileName = "";
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
        //checking session
        if(!isConnected().equals("")){
            accountLbl.setText(isConnected());
        } else {
            accountLbl.setText("ACCOUNT");
        }

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
    void accountManagement(MouseEvent event) throws IOException, SQLException {
        isInHomePage = false;

        //if not connected, login
        FXMLLoader loader;
        Pane accountPane;
        if(isConnected().equals("")){
            loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            accountPane = loader.load();
        } else { //else, displaying profile page
            loader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));
            accountPane = loader.load();
            ProfileController pc = loader.getController();
            pc.setAction(isAdmin, profileName);
        }
        bPane.setCenter(null);
        bPane.setCenter(accountPane);

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
    String isConnected() throws SQLException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //searching for a connected account
        ResultSet rs = stat.executeQuery("SELECT * FROM `admin` WHERE admin_isConnected = 1");
        if(rs.next()){
            isAdmin = true;
            profileName = rs.getString("admin_name");
            return rs.getString("admin_name");
        }else {
            rs = stat.executeQuery("SELECT * FROM `member` WHERE member_isConnected = 1");
            if(rs.next()){
                isAdmin = false;
                profileName = rs.getString("admin_name");
                return rs.getString("member_name");
            }else{
                profileName = "";
                return "";
            }
        }
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
                    rs.getString("movie_genre"), rs.getString("movie_review"), rs.getString("movie_date"));
            bPane.setCenter(bookingBox);

        }else {
            System.out.println("id introuvable");
        }

    }
}