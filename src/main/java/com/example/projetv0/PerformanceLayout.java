package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for performanceLayout.fxml
 */
public class PerformanceLayout {

    @FXML
    private Label hourPerf;

    @FXML
    private Label screenPerf;

    @FXML
    private Rectangle rectanglePerf;

    private int performance_id;

    /**
     * Function to inverse the color when the mouse pass over the text
     */
    @FXML
    void mouseEntered() {
        // Change background color of pane and labels when mouse over
        rectanglePerf.setFill(Color.WHITE);
        hourPerf.setStyle("-fx-text-fill: #2b2b2b;");
        screenPerf.setStyle("-fx-text-fill: #2b2b2b;");
    }

    /**
     * This function call a function of the HelloController to redirect on seats reservation
     */
    @FXML
    void clickPerf() throws IOException, SQLException {
        System.out.println("click");
        HelloController helloController = HelloController.getInstance();
        helloController.seatsReservation(performance_id);
    }
    /**
     * Function to reset the color when the mouse exit the text
     */
    @FXML
    void mouseExited() {
        rectanglePerf.setFill(Color.web("#2b2b2b"));
        hourPerf.setStyle("-fx-text-fill: #ffffff;");
        screenPerf.setStyle("-fx-text-fill: #ffffff;");
    }

    /**
     *Set the attributes of the class
     */
    public void setDataPerfLayout(String hour,String screen, int perf_id){
        hourPerf.setText(hour+":00");
        screenPerf.setText("Screen "+screen);
        performance_id = perf_id;
    }
}
