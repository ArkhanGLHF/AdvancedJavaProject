package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PerformanceLayout {

    @FXML
    private Label hourPerf;

    @FXML
    private Label screenPerf;

    public void setDataPerfLayout(String hour,String screen){
        hourPerf.setText(hour+":00");
        screenPerf.setText("Screen "+screen);
    }
}
