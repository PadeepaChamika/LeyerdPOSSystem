package controller;

import bo.BoFactory;
import bo.Custom.ItemBO;
import dto.ItemDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import entity.Item;

import java.sql.SQLException;

public class ModifyItemDetailsFormController {
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public AnchorPane modifyItemAnchorPaneContext;

    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(id);
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String id=txtItemCode.getText();
        String description=txtDescription.getText();
        String packSize=txtPackSize.getText();
        Double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtQtyOnHand.getText());

        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + id).show();
            }
            ItemDTO itemDTO = new ItemDTO(id,description,packSize,unitPrice,qty);
            if(itemBO.updateItem(itemDTO)){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();

                //clear text field
                txtDescription.clear();
                txtPackSize.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the Item " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    void setData(Item c){
        txtItemCode.setText(c.getItemCode());
        txtDescription.setText(c.getDescription());
        txtPackSize.setText(c.getPackSize());
        txtUnitPrice.setText(String.valueOf(c.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(c.getQtyOnHand()));
    }

    public void selectAllDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemId = txtItemCode.getText();

        Item c1= itemBO.getItem(itemId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }

    }
}
