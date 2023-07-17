package com.example.projetv0;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MovieLayoutController {

    private int movie_id;

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

    @FXML
    void mouseEntered(MouseEvent event) {
        movieNameLbl.setUnderline(true);
    }

    @FXML
    void mouseExited(MouseEvent event) {
        movieNameLbl.setUnderline(false);
    }

    @FXML
    void clickTitle(MouseEvent event) throws IOException, SQLException {
        System.out.println("click");
        HelloController helloController = HelloController.getInstance();
        helloController.bookingPage(movie_id);
    }

    public void setData(String imageSrc, String name, String Synopsis, String Genre, String Reviews, String Release, int movieId){
        Image image = new Image(imageSrc);

        movieImage.setImage(image);
        movieNameLbl.setText(name);
        SynopsisTxt.setText("Synopsis: "+Synopsis);
        ReviewsTxt.setText("Reviews: "+Reviews);
        GenreTxt.setText("Genre: "+Genre);
        ReleaseDateTxt.setText("Release Date: "+Release);
        movie_id = movieId;
    }
}
