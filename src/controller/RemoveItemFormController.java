package controller;

import bo.BoFactory;
import bo.Custom.ItemBO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entity.Item;

import java.sql.SQLException;

public class RemoveItemFormController {
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public AnchorPane removeItemAnchorPaneContext;

    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(id);
    }

    public void removeItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String id = txtItemCode.getText();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            if(itemBO.deleteItem(id)){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();

                //clear text field
                txtDescription.clear();
                txtPackSize.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void selectAllDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ItemId = txtItemCode.getText();

        Item c1= itemBO.getItem(ItemId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }
    }
    void setData(Item c){
        txtItemCode.setText(c.getItemCode());
        txtDescription.setText(c.getDescription());
        txtPackSize.setText(c.getPackSize());
        txtUnitPrice.setText(String.valueOf(c.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(c.getQtyOnHand()));
    }
}
