package com.example.projetv0;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SeatChoice {
    @FXML
    private CheckBox checkbox1;
    @FXML
    private CheckBox checkbox2;
    @FXML
    private CheckBox checkbox3;
    @FXML
    private CheckBox checkbox4;
    @FXML
    private CheckBox checkbox5;
    @FXML
    private CheckBox checkbox6;
    @FXML
    private CheckBox checkbox7;
    @FXML
    private CheckBox checkbox8;
    @FXML
    private CheckBox checkbox9;
    @FXML
    private CheckBox checkbox10;
    @FXML
    private CheckBox checkbox11;
    @FXML
    private CheckBox checkbox12;
    @FXML
    private CheckBox checkbox13;
    @FXML
    private CheckBox checkbox14;
    @FXML
    private CheckBox checkbox15;
    @FXML
    private CheckBox checkbox16;
    @FXML
    private CheckBox checkbox17;
    @FXML
    private CheckBox checkbox18;
    @FXML
    private CheckBox checkbox19;
    @FXML
    private CheckBox checkbox20;
    @FXML
    private CheckBox checkbox21;
    @FXML
    private CheckBox checkbox22;
    @FXML
    private CheckBox checkbox23;
    @FXML
    private CheckBox checkbox24;
    @FXML
    private CheckBox checkbox25;
    @FXML
    private CheckBox checkbox26;
    @FXML
    private CheckBox checkbox27;
    @FXML
    private CheckBox checkbox28;
    @FXML
    private CheckBox checkbox29;
    @FXML
    private CheckBox checkbox30;
    @FXML
    private CheckBox checkbox31;
    @FXML
    private CheckBox checkbox32;
    @FXML
    private CheckBox checkbox33;
    @FXML
    private CheckBox checkbox34;
    @FXML
    private CheckBox checkbox35;
    @FXML
    private CheckBox checkbox36;
    @FXML
    private CheckBox checkbox37;
    @FXML
    private CheckBox checkbox38;
    @FXML
    private CheckBox checkbox39;
    @FXML
    private CheckBox checkbox40;
    @FXML
    private CheckBox checkbox41;
    @FXML
    private CheckBox checkbox42;
    @FXML
    private CheckBox checkbox43;
    @FXML
    private CheckBox checkbox44;
    @FXML
    private CheckBox checkbox45;
    @FXML
    private CheckBox checkbox46;
    @FXML
    private CheckBox checkbox47;
    @FXML
    private CheckBox checkbox48;
    @FXML
    private CheckBox checkbox49;
    @FXML
    private CheckBox checkbox50;

    @FXML
    private Label datePerf;

    @FXML
    private Text filmTitle;

    @FXML
    private Label hourPerf;

    @FXML
    private Label numberPlaces;

    @FXML
    private Label total;

    @FXML
    private ImageView filmImage;

    @FXML
    private Text textChoice;

    private int performance_id;
    private int number_of_places;


    // Associate an int to each checkbox
    private Map<Integer, CheckBox> checkBoxMap = new HashMap<>();
    private ObservableList<CheckBox> selectedCheckBoxes = FXCollections.observableArrayList();


    // Initialization method
    public void initialize() {
        checkBoxMap.put(1, checkbox1);
        checkBoxMap.put(2, checkbox2);
        checkBoxMap.put(3, checkbox3);
        checkBoxMap.put(4, checkbox4);
        checkBoxMap.put(5, checkbox5);
        checkBoxMap.put(6, checkbox6);
        checkBoxMap.put(7, checkbox7);
        checkBoxMap.put(8, checkbox8);
        checkBoxMap.put(9, checkbox9);
        checkBoxMap.put(10, checkbox10);
        checkBoxMap.put(11, checkbox11);
        checkBoxMap.put(12, checkbox12);
        checkBoxMap.put(13, checkbox13);
        checkBoxMap.put(14, checkbox14);
        checkBoxMap.put(15, checkbox15);
        checkBoxMap.put(16, checkbox16);
        checkBoxMap.put(17, checkbox17);
        checkBoxMap.put(18, checkbox18);
        checkBoxMap.put(19, checkbox19);
        checkBoxMap.put(20, checkbox20);
        checkBoxMap.put(21, checkbox21);
        checkBoxMap.put(22, checkbox22);
        checkBoxMap.put(23, checkbox23);
        checkBoxMap.put(24, checkbox24);
        checkBoxMap.put(25, checkbox25);
        checkBoxMap.put(26, checkbox26);
        checkBoxMap.put(27, checkbox27);
        checkBoxMap.put(28, checkbox28);
        checkBoxMap.put(29, checkbox29);
        checkBoxMap.put(30, checkbox30);
        checkBoxMap.put(31, checkbox31);
        checkBoxMap.put(32, checkbox32);
        checkBoxMap.put(33, checkbox33);
        checkBoxMap.put(34, checkbox34);
        checkBoxMap.put(35, checkbox35);
        checkBoxMap.put(36, checkbox36);
        checkBoxMap.put(37, checkbox37);
        checkBoxMap.put(38, checkbox38);
        checkBoxMap.put(39, checkbox39);
        checkBoxMap.put(40, checkbox40);
        checkBoxMap.put(41, checkbox41);
        checkBoxMap.put(42, checkbox42);
        checkBoxMap.put(43, checkbox43);
        checkBoxMap.put(44, checkbox44);
        checkBoxMap.put(45, checkbox45);
        checkBoxMap.put(46, checkbox46);
        checkBoxMap.put(47, checkbox47);
        checkBoxMap.put(48, checkbox48);
        checkBoxMap.put(49, checkbox49);
        checkBoxMap.put(50, checkbox50);



    }
    public void setDataSeatChoice(int perf_id) throws SQLException {
        performance_id = perf_id;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnesflix?useSSL=FALSE", "root", "");
        Statement stat1 = con.createStatement();
        Statement stat2 = con.createStatement();
        Statement stat3 = con.createStatement();
        //Search the good perf
        ResultSet actualPerf = stat1.executeQuery("SELECT * FROM performance WHERE performance_id='"+performance_id+"'");
        while (actualPerf.next()){
            hourPerf.setText("Start Time: "+actualPerf.getString("performance_start_time")+"H");
            datePerf.setText("Date: "+actualPerf.getString("performance_date"));
            ResultSet movieWanted = stat2.executeQuery("SELECT * FROM movie WHERE movie_id='"+actualPerf.getString("movie_id")+"'");
            while (movieWanted.next()){
                filmTitle.setText(movieWanted.getString("movie_name"));
                Image img = new Image(movieWanted.getString("movie_url"));
                filmImage.setImage(img);
            }
            ResultSet seatsBooked = stat3.executeQuery("SELECT * FROM ticket WHERE performance_id='"+performance_id+"'");
            while (seatsBooked.next()){
                CheckBox targetCheckBox = checkBoxMap.get(seatsBooked.getInt("seat_number"));
                targetCheckBox.setDisable(true);
            }

        }
    }

    @FXML
    void handleCheckBoxSelection(MouseEvent event) {
        CheckBox clickedCheckBox = (CheckBox) event.getSource();
        if (clickedCheckBox.isSelected()){
            selectedCheckBoxes.add(clickedCheckBox);
        }else{
            selectedCheckBoxes.remove(clickedCheckBox);
        }
        updateSeatsSelected();
    }

    void updateSeatsSelected(){
        textChoice.setText("");
        numberPlaces.setText("");
        for (CheckBox checkBox : selectedCheckBoxes) {
            String existingText = textChoice.getText();
            // Add infos
            String additionalInfo = checkBox.getId();
            String updatedText = existingText + ", " + additionalInfo;
            // Update
            textChoice.setText(updatedText);
        }
        numberPlaces.setText(String.valueOf(selectedCheckBoxes.size()));
        total.setText(String.valueOf(selectedCheckBoxes.size()*10)+" £");
    }

    @FXML
    private BorderPane borderPaneSeats;
    @FXML
    private VBox vBoxPaiement;
    @FXML
    void buttonProceedClicked(MouseEvent event) throws IOException {
        if (selectedCheckBoxes.size()!=0) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("paiementLayout.fxml"));
            Pane payment = fxmlLoader.load();

            PaiementLayout paymentLayoutController = fxmlLoader.getController();
            paymentLayoutController.setDataPaiement(selectedCheckBoxes, performance_id);
            vBoxPaiement.getChildren().add(payment);
        }
    }
}
