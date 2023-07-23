package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.sql.*;

/**
 * Main page of the performance management page for the admin, displaying every performance and functions to add/delete/edit
 */
public class PerformanceManagementController {
    @FXML
    private VBox PerformancePresentationLayout;
    @FXML
    private ComboBox<String> cinemaBox;
    @FXML
    private TextField dateTxt;
    @FXML
    private TextField hourTxt;
    @FXML
    private Pane memberManagementPane;
    @FXML
    private ComboBox<String> nameBox;
    @FXML
    private ComboBox<String> roomBox;
    @FXML
    private Text txtError;

    /**
     * Function to display every performance in the database
     */
    void managePerformance() throws SQLException, IOException {
        //resetting every box
        nameBox.getItems().clear();
        cinemaBox.getItems().clear();
        roomBox.getItems().clear();
        roomBox.setDisable(true);
        dateTxt.setText("");
        hourTxt.setText("");
        txtError.setVisible(false);

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        //filling the combo-boxes
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");
        while(rs.next()){
            String movieName = rs.getString("movie_name");
            nameBox.getItems().add(movieName);
        }
        rs = stat.executeQuery("SELECT * FROM `cinema`");
        while(rs.next()){
            String cinemaName = rs.getString("cinema_name");
            cinemaBox.getItems().add(cinemaName);
        }
        //display every performance
        rs = stat.executeQuery("SELECT * FROM `performance` ORDER BY `movie_id` ASC");
        while (rs.next()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("performanceManagementLayout.fxml"));
            HBox box = fxmlLoader.load();

            PerformanceManagementLayoutController performanceController = fxmlLoader.getController();
            performanceController.setData(rs.getInt("movie_id"), rs.getInt("cinema_id"), rs.getInt("room_id"), rs.getString("performance_date"), rs.getInt("performance_start_time"), rs.getInt("performance_id"));
            PerformancePresentationLayout.getChildren().add(box);
        }
    }

    /**
     * Function to add a performance in the database
     */
    @FXML
    void addPerformance(ActionEvent event) throws SQLException, IOException {
        //getting the values from the fields
        boolean flag = false;
        String name = nameBox.getSelectionModel().getSelectedItem();
        String cinema = cinemaBox.getSelectionModel().getSelectedItem();
        String room = roomBox.getSelectionModel().getSelectedItem();
        String date = dateTxt.getText();
        String startTime = hourTxt.getText();

        //checking if every value has been entered
        if(nameBox.getValue()==null){
            txtError.setText("Error: Movie cannot be null");
            flag = true;
        } else if (cinemaBox.getValue()==null) {
            txtError.setText("Error: Cinema cannot be null");
            flag = true;
        } else if(date.equals("")){
            txtError.setText("Error: Date cannot be null");
            flag = true;
        } else if(startTime.equals("")){
            txtError.setText("Error: Start time cannot be null");
            flag = true;
        } else if(roomBox.getValue()==null){
            txtError.setText("Error: Room cannot be null");
            flag = true;
        }

        int cinemaID = -1;
        int roomID=-1;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        //getting cinema ID
        String sql = "SELECT * FROM `cinema` WHERE cinema_name="+'"'+cinema+'"';
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()){
            cinemaID = rs.getInt("cinema_id");
        }
        //getting the room ID
        sql = "SELECT * FROM `room` WHERE cinema_id="+cinemaID+" AND room_number="+room;
        rs = statement.executeQuery(sql);
        if(rs.next()){
            roomID = rs.getInt("room_id");
        }
        //checking every performance in this room at the same date
        sql = "SELECT * FROM `performance` WHERE `room_id`=" + roomID + " AND performance_date='" + date + "'";
        rs = statement.executeQuery(sql);
        while (rs.next()){
            if((Integer.parseInt(startTime) + 3 >= rs.getInt("performance_start_time") &&  Integer.parseInt(startTime) <= rs.getInt("performance_start_time")) || ( Integer.parseInt(startTime) >= rs.getInt("performance_start_time") && (Integer.parseInt(startTime) <= rs.getInt("performance_start_time")+3))){
                flag = true;
                txtError.setText("Error: A performance is already planned in this room");
                break;
            }
        }

        //if we had an error, display it
        if(flag){
            txtError.setVisible(true);
        } else {
            //else add the new performance in the database
            int movieID=-1;
            sql = "SELECT * FROM `movie` WHERE movie_name="+'"'+name+'"';
            rs = statement.executeQuery(sql);
            if(rs.next()){
                movieID=rs.getInt("movie_id");
            }
            sql = "INSERT INTO performance (movie_id, cinema_id, room_id, performance_date, performance_start_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement s = con.prepareStatement(sql);
            s.setInt(1, movieID);
            s.setInt(2, cinemaID);
            s.setInt(3, roomID);
            s.setString(4, date);
            s.setInt(5, Integer.parseInt(startTime));
            s.executeUpdate();

            //clearing and displaying the updated performance management page
            PerformancePresentationLayout.getChildren().clear();
            managePerformance();
        }
    }

    /**
     * Function to edit the room ComboBox with the cinema rooms
     */
    @FXML
    void cinemaChoice(ActionEvent event) throws SQLException {
        //when a cinema has been chosed
        int cinemaID = -1;
        roomBox.setDisable(false);
        roomBox.getItems().clear();
        String cinemaName = cinemaBox.getSelectionModel().getSelectedItem();

        //get the cinema id
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        String sql = "SELECT * FROM `cinema` WHERE cinema_name="+'"'+cinemaName+'"';
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
            cinemaID=rs.getInt("cinema_id");
            System.out.println(cinemaID);
        }
        //fill the combo-box with the rooms of the cinema chosen
        sql = "SELECT * FROM `room` WHERE cinema_id="+cinemaID;
        rs = stat.executeQuery(sql);
        while (rs.next()){
            int roomNum = rs.getInt("room_number");
            roomBox.getItems().add(String.valueOf(roomNum));
        }
    }
}
