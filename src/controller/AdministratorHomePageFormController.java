package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AdministratorHomePageFormController {
    public AnchorPane administratorAnchorPaneContext;

    public void backToHome(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/DashBoardForm.fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openRegisterNewItem(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/RegisterNewItemForm.fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void modifyItemDetailsOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/ModifyItemDetailsForm.fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void removeItemOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/RemoveItemForm.fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openIncomeReport(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/SystemReportForm.fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
