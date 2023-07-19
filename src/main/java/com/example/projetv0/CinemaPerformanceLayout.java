package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;

public class CinemaPerformanceLayout {

    @FXML
    private Label cinemaName;

    @FXML
    private HBox performanceCinema;

    public void setDataCinePerf(int cineID,String date) throws SQLException, IOException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat1 = con.createStatement();
        Statement stat2 = con.createStatement();
        Statement stat3 = con.createStatement();
        //Parcours toutes les séances de chaque cinemas ayant une séance à cette date
        ResultSet cinePerf = stat1.executeQuery("SELECT * FROM performance WHERE cinema_id='"+cineID+"'AND performance_date='"+date+"'");
        while (cinePerf.next()){
            System.out.println("cine next ");
            //String cineName = cinePerf.getString("cinema_name");
            String startTime = cinePerf.getString("performance_start_time");
            String roomID = cinePerf.getString("room_id");
            ResultSet room = stat2.executeQuery("SELECT * FROM room WHERE room_id="+roomID);
            while (room.next()) {
                System.out.println("cine next et room next");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("performanceLayout.fxml"));
                AnchorPane cinemaPerf = fxmlLoader.load();

                PerformanceLayout performanceLayout = fxmlLoader.getController();
                performanceLayout.setDataPerfLayout(startTime,room.getString("room_number"));
                performanceCinema.getChildren().add(cinemaPerf);

            }
        }
        ResultSet cineName = stat3.executeQuery("SELECT * FROM cinema WHERE cinema_id="+cineID);
        while (cineName.next()){
            cinemaName.setText(cineName.getString("cinema_name"));
        }
    }

}