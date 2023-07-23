package com.example.projetv0;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

/**
 * Page of a ticket individually
 */
public class SeeTicketLayoutController {
    @FXML
    private Text cinemaTxt;
    @FXML
    private Text dateTxt;
    @FXML
    private ImageView movieImage;
    @FXML
    private Label movieNameLbl;
    @FXML
    private Text roomTxt;
    @FXML
    private Text seatTxt;
    int ticket_id;

    /**
     * Function to set the data for a ticket
     */
    public void setData(int ticketID, int performanceID, int roomID, int cinemaID, int seatNumber) throws SQLException {
        //setting the data
        ticket_id = ticketID;
        seatTxt.setText("Seat: " + String.valueOf(seatNumber));
        int movieID = -1;
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM cinema WHERE cinema_id=" + cinemaID);
        if(rs.next()){
            cinemaTxt.setText("Cinema: "+rs.getString("cinema_name"));
        }
        rs = stat.executeQuery("SELECT * FROM performance WHERE performance_id=" + performanceID);
        if(rs.next()){
            dateTxt.setText("Date: "+rs.getString("performance_date")+" / " + rs.getString("performance_start_time") + "h00");
            movieID = rs.getInt("movie_id");
        }
        rs = stat.executeQuery("SELECT * FROM room WHERE room_id=" + roomID);
        if(rs.next()){
            roomTxt.setText("Room: "+rs.getString("room_number"));
        }
        rs = stat.executeQuery("SELECT * FROM movie WHERE movie_id=" + movieID);
        if(rs.next()){
            movieNameLbl.setText(rs.getString("movie_name"));
            movieImage.setImage(new Image(rs.getString("movie_url")));
        }
    }

    /**
     * Function to cancel a ticket
     */
    @FXML
    void cancelTicket(ActionEvent event) throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        //delete the ticket from the database
        String sql = "DELETE FROM `ticket` WHERE ticket_id = " + ticket_id;
        stat.executeUpdate(sql);

        //reloading the management page
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage lstage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        HelloController hc = fxmlLoader.getController();
        MouseEvent e = null;
        hc.accountManagement(e);
        Scene scene = new Scene(root);
        lstage.setScene(scene);
        lstage.show();
    }
}
