package controller;

import bo.BoFactory;
import bo.Custom.ItemBO;
import dto.ItemDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entity.Item;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class RegisterNewItemFormController {
    /*public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;*/
    public AnchorPane RegisterNewItemAnchorPaneContext;

    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQty;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern descriptionPattern = Pattern.compile("^[A-z ]{3,50}$");
    Pattern packSizePattern = Pattern.compile("^[0-9 ]{2,50}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9]{2,60}$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{2,40}$");

    private void storeValidations() {
        map.put(txtDescription,descriptionPattern);
        map.put(txtPackSize, packSizePattern);
        map.put(txtUnitPrice, unitPricePattern);
        map.put(txtQty, qtyPattern);

    }

    public void initialize() throws SQLException, ClassNotFoundException {
        lastId();
        storeValidations();
    }

    private Object validate() {
        //btnSaveBook.setDisable(true);

        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.getParent().setStyle("-fx-border-color: red");
                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green");
        }
        // btnSaveBook.setDisable(false);
        return true;
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(id);
    }

    public void saveItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id=txtItemCode.getText();
        String description=txtDescription.getText();
        String packSize=txtPackSize.getText();
        Double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtQty.getText());

        try {
            if (existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }
            ItemDTO itemDTO= new ItemDTO(id,description,packSize,unitPrice,qty);
            if(itemBO.addItem(itemDTO)){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                lastId();

                //clear text field
                txtDescription.clear();
                txtPackSize.clear();
                txtQty.clear();
                txtUnitPrice.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the Item" + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void lastId() throws SQLException, ClassNotFoundException {
        String Id = itemBO.generateNewID();;
        String finalId = "I-001";

        if (Id != null) {

            String[] splitString = Id.split("-");
            int id = Integer.parseInt(splitString[1]);
            id++;

            if (id < 10) {
                finalId = "I-00" + id;
            } else if (id < 100) {
                finalId = "I-0" + id;
            } else {
                finalId = "I-" + id;
            }
            txtItemCode.setText(finalId);
        } else {
            txtItemCode.setText(finalId);
        }
    }

    public void OnActionOne(KeyEvent keyEvent) {
        Object response = validate();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void onActionTwo(KeyEvent keyEvent) {
        Object response = validate();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }
}
