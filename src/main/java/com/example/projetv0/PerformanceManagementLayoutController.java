package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

/**
 * Page of a performance individually, displayed in the performance management page of the admin
 */
public class PerformanceManagementLayoutController {
    @FXML
    private Text cinemaTxt;
    @FXML
    private Text dateTxt;
    @FXML
    private Text roomTxt;
    @FXML
    private Button editButton;
    @FXML
    private Text hourTxt;
    @FXML
    private ImageView movieImage;
    @FXML
    private Label movieNameLbl;
    @FXML
    private Button removeButton;
    @FXML
    private Button submitEditButton;
    @FXML
    private Text txtError;
    @FXML
    private ComboBox<String> comboCine;
    @FXML
    private ComboBox<String> comboMovie;
    @FXML
    private ComboBox<String> comboRoom;
    @FXML
    private TextField editDate;
    @FXML
    private TextField editStartTime;
    int movie_id;
    int cinema_id;
    int room_id;
    String cinema_name;
    String room_num;
    String d;
    String startT;
    int performance_id;

    /**
     * Function to set the data of the performance
     */
    void setData(int movieId, int cinemaId, int roomId, String date, int hour, int perf_id) throws SQLException {
        //getting the data sent
        movie_id = movieId;
        cinema_id = cinemaId;
        room_id = roomId;
        performance_id = perf_id;

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        String sql = "SELECT * FROM `movie` WHERE movie_id="+movieId;
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
            movieNameLbl.setText(rs.getString("movie_name"));
            movieImage.setImage(new Image(rs.getString("movie_url")));
        }
        sql = "SELECT * FROM `cinema` WHERE cinema_id="+cinemaId;
        rs = stat.executeQuery(sql);
        if(rs.next()){
            cinemaTxt.setText("Cinema: "+rs.getString("cinema_name"));
            cinema_name = rs.getString("cinema_name");
        }
        sql = "SELECT * FROM `room` WHERE room_id="+roomId;
        rs = stat.executeQuery(sql);
        if(rs.next()){
            roomTxt.setText("Room: "+rs.getString("room_number"));
            room_num = rs.getString("room_number");
        }
        dateTxt.setText("Date: "+date);
        d = date;
        hourTxt.setText("Start Time: "+hour);
        startT = String.valueOf(hour);
    }

    /**
     * Function to display the edit performance for the performance
     */
    @FXML
    void editPerformance() throws SQLException {
        //hiding the text
        movieNameLbl.setVisible(false);
        cinemaTxt.setVisible(false);
        dateTxt.setVisible(false);
        hourTxt.setVisible(false);
        roomTxt.setVisible(false);
        editButton.setVisible(false);
        removeButton.setVisible(false);

        //displaying edit boxes and fields
        comboMovie.setVisible(true);
        comboMovie.setValue(movieNameLbl.getText());
        comboRoom.setVisible(true);
        comboRoom.setValue(room_num);
        comboCine.setVisible(true);
        comboCine.setValue(cinema_name);
        editDate.setVisible(true);
        editDate.setText(d);
        editStartTime.setVisible(true);
        editStartTime.setText(startT);
        submitEditButton.setVisible(true);

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        //filling the movies
        ResultSet rs = stat.executeQuery("SELECT * FROM `movie`");
        while(rs.next()){
            String movieName = rs.getString("movie_name");
            comboMovie.getItems().add(movieName);
        }
        //fillinf the cinemas
        rs = stat.executeQuery("SELECT * FROM `cinema`");
        while(rs.next()){
            String cinemaName = rs.getString("cinema_name");
            comboCine.getItems().add(cinemaName);
        }
        //filling the cinema's rooms
        rs = stat.executeQuery("SELECT * FROM `room` WHERE cinema_id="+cinema_id);
        while(rs.next()){
            String room = rs.getString("room_number");
            comboRoom.getItems().add(room);
        }
    }
    /**
     * Function to edit the room ComboBox with the cinema rooms
     */
    @FXML
    void cinemaChoice() throws SQLException {
        //when a cinema is chosen
        int cinemaID = -1;
        comboRoom.getItems().clear();
        String cinemaName = comboCine.getSelectionModel().getSelectedItem();

        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        //getting the cinema id
        String sql = "SELECT * FROM `cinema` WHERE cinema_name="+'"'+cinemaName+'"';
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
            cinemaID=rs.getInt("cinema_id");
        }
        //filling the cinema's rooms
        sql = "SELECT * FROM `room` WHERE cinema_id="+cinemaID;
        rs = stat.executeQuery(sql);
        while (rs.next()){
            int roomNum = rs.getInt("room_number");
            comboRoom.getItems().add(String.valueOf(roomNum));
        }
    }

    /**
     * Function to remove a performance from the database
     */
    @FXML
    void removePerformance(ActionEvent event) throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        //deleting the performance from the database
        String sql = "DELETE FROM `performance` WHERE movie_id = " + movie_id;
        stat.executeUpdate(sql);

        //reloading the management page
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        HelloController hc = fxmlLoader.getController();
        hc.accountManagement();
        Scene scene = new Scene(root);
        lstage.setScene(scene);
        lstage.show();
    }

    /**
     * Function to send the edited information to the database
     */
    @FXML
    void submitEdit(ActionEvent event) throws SQLException, IOException {
        //get all values chosen
        boolean flag = false;
        String name = comboMovie.getSelectionModel().getSelectedItem();
        String cine = comboCine.getSelectionModel().getSelectedItem();
        String room = comboRoom.getSelectionModel().getSelectedItem();
        String date = editDate.getText();
        String start = editStartTime.getText();

        //checking if texts are null
        if(comboMovie.getValue()==null){
            txtError.setText("Error: Movie cannot be null");
            flag = true;
        } else if (comboCine.getValue()==null) {
            txtError.setText("Error: Cinema cannot be null");
            flag = true;
        } else if (comboRoom.getValue()==null) {
            txtError.setText("Error: Room cannot be null");
            flag = true;
        } else if (date.equals("")) {
            txtError.setText("Error: Date cannot be null");
            flag = true;
        } else if (start.equals("")) {
            txtError.setText("Error: Start time cannot be null");
            flag = true;
        }

        //connection to database
        int cinemaID = -1;
        int roomID=-1;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        //getting cinema ID
        String sql = "SELECT * FROM `cinema` WHERE cinema_name="+'"'+cine+'"';
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()){
            cinemaID = rs.getInt("cinema_id");
            cinema_id = cinemaID;
        }
        //getting the room ID
        sql = "SELECT * FROM `room` WHERE cinema_id="+cinemaID+" AND room_number="+room;
        rs = statement.executeQuery(sql);
        if(rs.next()){
            roomID = rs.getInt("room_id");
            room_id = roomID;
        }
        //checking every performance in this room at the same date
        sql = "SELECT * FROM `performance` WHERE `room_id`=" + roomID + " AND performance_date='" + date + "'";
        rs = statement.executeQuery(sql);
        while (rs.next()){
            if((Integer.parseInt(start) + 3 >= rs.getInt("performance_start_time") &&  Integer.parseInt(start) <= rs.getInt("performance_start_time")) || ( Integer.parseInt(start) >= rs.getInt("performance_start_time") && (Integer.parseInt(start) <= rs.getInt("performance_start_time")+3))){
                flag = true;
                txtError.setText("Error: A performance is already planned in this room");
                break;
            }
        }
        //if we had an error, display it
        if(flag){
            txtError.setVisible(true);
        } else { //else edit the performance in the database
            sql = "SELECT * FROM `movie` WHERE movie_name="+'"'+name+'"';
            rs = statement.executeQuery(sql);
            if(rs.next()){
                movie_id = rs.getInt("movie_id");
            }
            sql = "UPDATE performance SET movie_id=?, cinema_id=?, room_id=?, performance_date=?, performance_start_time=? WHERE performance_id=" + performance_id;
            PreparedStatement stat = con.prepareStatement(sql);
            stat.setInt(1, movie_id);
            stat.setInt(2, cinema_id);
            stat.setInt(3, room_id);
            stat.setString(4, date);
            stat.setInt(5, Integer.parseInt(start));
            stat.executeUpdate();

            //reloading management page
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            HelloController hc = fxmlLoader.getController();
            hc.accountManagement();
            Scene scene = new Scene(root);
            lstage.setScene(scene);
            lstage.show();
        }
    }
}
