package controller;

import bo.BoFactory;
import bo.Custom.CustomerBO;
import dto.CustomerDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddNewCustomerDetailFormController {
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    public AnchorPane addNewCustomerAnchorPaneContext;
    public TextField txtCustName;
    public TextField txtAddress;
    public TextField txtCustomerId;
    public TextField txtCity;
    public TextField txtPostalCode;
    public TextField txtCustomerTitle;
    public TextField txtProvince;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern titlePattern = Pattern.compile("^[A-z ]{3,50}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,50}$");
    Pattern addressPattern = Pattern.compile("^[A-z ]{3,60}$");
    Pattern cityPattern = Pattern.compile("^[A-z ]{3,60}$");
    Pattern provincePattern = Pattern.compile("^[A-z ]{3,60}$");
    Pattern postalCodePattern = Pattern.compile("^[0-9]{4,}$");

    private void storeValidations() {
        map.put(txtCustName, namePattern);
        map.put(txtCity, cityPattern);
        map.put(txtPostalCode, postalCodePattern);
        map.put(txtCustomerTitle, titlePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtProvince, provincePattern);
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

    public void initialize() throws SQLException, ClassNotFoundException {
        lastId();
        storeValidations();
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(id);
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id=txtCustomerId.getText();
        String title=txtCustomerTitle.getText();
        String name=txtCustName.getText();
        String address=txtAddress.getText();
        String city=txtCity.getText();
        String province=txtProvince.getText();
        String postalCode=txtPostalCode.getText();

        try {
            if (existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }
            CustomerDTO customerDTO = new CustomerDTO(id,title, name, address,city,province,postalCode);
            if(customerBO.addCustomer(customerDTO)){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                lastId();

                txtCustomerTitle.clear();
                txtCustName.clear();
                txtAddress.clear();
                txtCity.clear();
                txtProvince.clear();
                txtPostalCode.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void lastId() throws SQLException, ClassNotFoundException {
        String Id = customerBO.generateNewID();;
        String finalId = "C-001";

        if (Id != null) {

            String[] splitString = Id.split("-");
            int id = Integer.parseInt(splitString[1]);
            id++;

            if (id < 10) {
                finalId = "C-00" + id;
            } else if (id < 100) {
                finalId = "C-0" + id;
            } else {
                finalId = "C-" + id;
            }
            txtCustomerId.setText(finalId);
        } else {
            txtCustomerId.setText(finalId);
        }
    }

    public void OneAction(KeyEvent keyEvent) {
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
