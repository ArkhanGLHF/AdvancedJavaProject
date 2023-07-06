package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MovieLayoutController {
    @FXML
    private Text GenreTxt;

    @FXML
    private Text ReleaseDateTxt;

    @FXML
    private Text ReviewsTxt;

    @FXML
    private Text SynopsisTxt;

    @FXML
    private ImageView movieImage;

    @FXML
    private Label movieNameLbl;

    public void setData(String imageSrc, String name, String Synopsis, String Genre, String Reviews, String Release){
        Image image = new Image(imageSrc);

        movieImage.setImage(image);
        movieNameLbl.setText(name);
        SynopsisTxt.setText("Synopsis: "+Synopsis);
        ReviewsTxt.setText("Reviews: "+Reviews);
        GenreTxt.setText("Genre: "+Genre);
        ReleaseDateTxt.setText("Release Date: "+Release);
    }
}
