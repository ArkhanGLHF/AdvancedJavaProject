package com.example.projetv0;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BookingController {
    @FXML
    private Label genreLbl;

    @FXML
    private ImageView image;

    @FXML
    private Label releaseDateLbl;

    @FXML
    private Label reviewsLbl;

    @FXML
    private Text synopsisTxt;
    @FXML
    private Label titleLbl;


    @FXML
    private AnchorPane PaneDay1;

    @FXML
    private AnchorPane PaneDay2;

    public void translateAnimation(double duration, Node node, double width){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setByX(width);
        translateTransition.play();
    }

    int show=0;

    @FXML
    void next(MouseEvent event) {
        System.out.println(show);
        if (show==0){
            translateAnimation(0.5, PaneDay1, -1050);
            show++;
        }
    }
    @FXML
    void back(MouseEvent event) {
        System.out.println(show);
        if (show==1){
            translateAnimation(0.5, PaneDay1, 1050);
            show--;
        }
    }





    public void setDataBooking(String imageSrc, String title, String Synopsis, String Genre, String Reviews, String Release){
        Image img = new Image(imageSrc);

        image.setImage(img);
        titleLbl.setText(title);
        synopsisTxt.setText(Synopsis);
        reviewsLbl.setText("Reviews: "+Reviews);
        genreLbl.setText(Genre);
        releaseDateLbl.setText(Release);
    }
}
