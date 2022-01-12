package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CashierHomePageController {
    public void backToDashBoardForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/DashBoardForm.fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openAddNewCustomer(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/AddNewCustomerDetailForm.fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openModifyOrderDetail(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/ModifyOrderDetailForm.fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openRemoveOrderForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/RemoveOrderForm.fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openMakePaymentForm(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/MakePaymentForm .fxml"));
        Scene scene =new Scene(load);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
