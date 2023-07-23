package com.example.projetv0;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Controller for booking2.fxml
 */

public class BookingController {
    @FXML
    private Label genreLbl;

    @FXML
    private ImageView image;

    @FXML
    private Label releaseDateLbl;

    @FXML
    private Label reviewsLbl;

    @FXML
    private Text synopsisTxt;
    @FXML
    private Label titleLbl;


    @FXML
    private AnchorPane PaneDay1;


    @FXML
    private VBox performancePresentationLayout;

    private int movieID;

    /**
     *I set the animations to choose the day
     */
    public void translateAnimation(double duration, Node node, double width){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setByX(width);
        translateTransition.play();
    }

    int show=0;

    /**
     * Animation for swipe the day bar to the right
     */
    @FXML
    void next() {
        System.out.println(show);
        if (show==0){
            translateAnimation(0.5, PaneDay1, -1050);
            show++;
        }
    }

    /**
     * Swipe the day bar to the left
     */
    @FXML
    void back() {
        System.out.println(show);
        if (show==1){
            translateAnimation(0.5, PaneDay1, 1050);
            show--;
        }
    }

    /**
     *When you select a day, it displays all the cinemas with the hours of the performances of that day for the movie you want
     */
    @FXML
    void daySelected(MouseEvent event) throws SQLException, IOException {
        performancePresentationLayout.getChildren().clear();
        System.out.println("button clicked");
        Button clickedButton = (Button) event.getSource();
        String date = clickedButton.getId();
        System.out.println(date);
        List<Integer> cinemaIds = new ArrayList<>();
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        // displaying all the show at this date
        ResultSet perf = stat.executeQuery("SELECT * FROM performance WHERE performance_date='"+date+"'AND movie_id='"+movieID+"'");
        while (perf.next()) {
            // Lisez la valeur de la colonne cinema_id pour chaque ligne de résultat
            int cinemaId = perf.getInt("cinema_id");
            int exist;
            exist=0;

            for (Integer id : cinemaIds) {
                if (id != cinemaId) {
                    //does not exist
                    exist = 0;
                } else
                    exist = 1;

            }
            if (exist==0){
                // Ajoutez la valeur à la liste
                cinemaIds.add(cinemaId);
            }

        }
        for (Integer cinemaId : cinemaIds) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("cinemaPerformanceLayout.fxml"));
            VBox cinemaPerf = fxmlLoader.load();

            CinemaPerformanceLayout cinemaPerformanceLayout = fxmlLoader.getController();
            cinemaPerformanceLayout.setDataCinePerf(cinemaId, date);
            performancePresentationLayout.getChildren().add(cinemaPerf);

        }

    }


    /**
     * Set the datas we need to display
     */

    public void setDataBooking(String imageSrc, String title, String Synopsis, String Genre, String Reviews, String Release,
                               int IDmovie){
        Image img = new Image(imageSrc);

        image.setImage(img);
        titleLbl.setText(title);
        synopsisTxt.setText(Synopsis);
        reviewsLbl.setText("Reviews: "+Reviews);
        genreLbl.setText(Genre);
        releaseDateLbl.setText(Release);
        movieID = IDmovie;
    }
}
