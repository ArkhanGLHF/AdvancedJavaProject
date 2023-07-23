package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;

/**
 * Page to see every ticket from the connected member account
 */
public class SeeTicketController {
    @FXML
    private VBox TicketsLayout;
    int member_id;

    /**
     * Function to display every ticket from the account
     */
    void seeTicket() throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();
        //getting the id of the member connected
        ResultSet rs = stat.executeQuery("SELECT * FROM member WHERE member_isConnected = 1");
        if(rs.next()){
            member_id = rs.getInt("member_id");
        }
        //displaying all his tickets
        String sql = "SELECT * FROM `ticket` WHERE member_id="+member_id;
        rs = stat.executeQuery(sql);
        while (rs.next()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("SeeTicketLayout.fxml"));
            HBox box = fxmlLoader.load();

            SeeTicketLayoutController ticketController = fxmlLoader.getController();
            ticketController.setData(rs.getInt("ticket_id"), rs.getInt("performance_id"), rs.getInt("room_id"), rs.getInt("cinema_id"), rs.getInt("seat_number"));
            TicketsLayout.getChildren().add(box);
        }
    }
}
