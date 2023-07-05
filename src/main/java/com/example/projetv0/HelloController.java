package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.sql.*;

public class HelloController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private Pane leftPane;
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

    void startPage() throws SQLException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        //displaying the best reviewed movie
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie` ORDER BY review DESC LIMIT 1");
        if(rs.next()){
            movieNameLbl.setText(rs.getString("name"));
            movieGenreTxt.setText("Genre: " + rs.getString("genre"));
            movieRelTxt.setText("Release date: " + rs.getString("date"));
            movieRevTxt.setText("Reviews: " + rs.getString("review"));
            movieImage.setImage(new Image(rs.getString("url")));
            movieSynTxt.setText("Synopsis: " + rs.getString("synopsis"));
        }

        //hide and save leftPane (movie filter) in the home page
        Pane leftContainer = leftPane;
        mainPane.setLeft(null);
    }
}