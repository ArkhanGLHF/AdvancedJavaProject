package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.sql.SQLException;

public class PerformanceLayout {

    @FXML
    private Label hourPerf;

    @FXML
    private Label screenPerf;

    @FXML
    private Rectangle rectanglePerf;

    private int performance_id;
    @FXML
    void mouseEntered(MouseEvent event) {
        // Change background color of pane and labels when mouse over
        rectanglePerf.setFill(Color.WHITE);
        hourPerf.setStyle("-fx-text-fill: #2b2b2b;");
        screenPerf.setStyle("-fx-text-fill: #2b2b2b;");
    }

    @FXML
    void clickPerf(MouseEvent event) throws IOException, SQLException {
        System.out.println("click");
        HelloController helloController = HelloController.getInstance();
        helloController.seatsReservation(performance_id);
    }

    @FXML
    void mouseExited(MouseEvent event) {
        rectanglePerf.setFill(Color.web("#2b2b2b"));
        hourPerf.setStyle("-fx-text-fill: #ffffff;");
        screenPerf.setStyle("-fx-text-fill: #ffffff;");
    }

    public void setDataPerfLayout(String hour,String screen, int perf_id){
        hourPerf.setText(hour+":00");
        screenPerf.setText("Screen "+screen);
        performance_id = perf_id;
    }
}
