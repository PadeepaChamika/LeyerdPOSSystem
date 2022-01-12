package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

public class DashBoardFormController {

    public TextField txtUserName;
    public PasswordField txtPassword;
    public AnchorPane loginAnchorPaneContext;
    public AnchorPane loginHomeAnchorPaneContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize() {
        loadDateAndTime();
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f= new SimpleDateFormat("MMM-dd-YYYY");
        lblDate.setText(f.format(date));

        Timeline time= new Timeline(new KeyFrame(Duration.ZERO, e-> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void openHomePage (ActionEvent actionEvent) throws IOException {
            if (txtUserName.getText().equalsIgnoreCase("Cashier") && txtPassword.getText().equals("C1234")) {
                URL resource = getClass().getResource("../views/CashierHomePageForm.fxml");
                Parent load = FXMLLoader.load(resource);
                loginHomeAnchorPaneContext.getChildren().clear();
                loginHomeAnchorPaneContext.getChildren().add(load);
            } else if (txtUserName.getText().equalsIgnoreCase("Admin") && txtPassword.getText().equals("A1234")) {
                URL resource = getClass().getResource("../views/AdministratorHomePageForm.fxml");
                Parent load = FXMLLoader.load(resource);
                loginHomeAnchorPaneContext.getChildren().clear();
                loginHomeAnchorPaneContext.getChildren().add(load);
            }
    }

    public void moveToPassword(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
}
