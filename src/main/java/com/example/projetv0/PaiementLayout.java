package com.example.projetv0;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import java.io.IOException;
import java.sql.*;

/**
 * Controller of fxml file paiementLayout where you can fill your informations of payment.
 */

public class PaiementLayout {

    private ObservableList<CheckBox> selected_CheckBoxes = FXCollections.observableArrayList();
    private int id_performance;

    /**
     * Create the user tickets when he fills his information of payment
     */
    @FXML
    void okPaiement() throws SQLException, IOException {
        //connection to database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat = con.createStatement();

        String id_room = null;
        String id_cinema = null;
        String seat_number;

        ResultSet perf = stat.executeQuery("SELECT * FROM `performance` WHERE performance_id = '"+id_performance+"'");
        while (perf.next()){
            id_room = perf.getString("room_id");
            id_cinema = perf.getString("cinema_id");

        }
        //searching for a connected account
        ResultSet rs = stat.executeQuery("SELECT * FROM `member` WHERE member_isConnected = 1");
        if(rs.next()){
            int id_member = rs.getInt("member_id");
            // ticket Creation
            String createTicketQuery = "INSERT INTO `ticket` (member_id, performance_id, room_id, cinema_id, seat_number) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement createTicketStmt = con.prepareStatement(createTicketQuery);
            for (CheckBox checkBox : selected_CheckBoxes) {
                seat_number = checkBox.getId();
                createTicketStmt.setInt(1, id_member);
                createTicketStmt.setString(2, String.valueOf(id_performance));
                createTicketStmt.setString(3, id_room);
                createTicketStmt.setString(4, id_cinema);
                createTicketStmt.setString(5, seat_number);
                int rowsAffected = createTicketStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Ticket inserted successfully.");
                    HelloController helloController = HelloController.getInstance();
                    helloController.home();
                } else {
                    System.out.println("Error inserting ticket.");
                }
            }

        }


        //redirection to recap of booking
    }

    /**
     * This function set the information needed in the fxml file
     */

    public void setDataPaiement(ObservableList<CheckBox> selectedCheckBoxes, int performance_id ){
        selected_CheckBoxes = selectedCheckBoxes;
        id_performance = performance_id;
    }
}
