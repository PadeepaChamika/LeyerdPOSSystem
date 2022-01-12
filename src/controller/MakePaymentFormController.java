package controller;

import bo.BoFactory;
import bo.Custom.PlaceOrderBO;
import com.jfoenix.controls.JFXButton;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import entity.Customer;
import entity.Item;
import views.tm.OrderDetailTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class MakePaymentFormController {
    public Label lblOrderId;
    public Label lblDate;
    public Label lblTime;
    public ComboBox<String> cmbCustomerId;
    public TextField txtCustomerTitle;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public ComboBox <String>cmbItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TableView<OrderDetailTm> tblOrderTable;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblTotal;
    public TextField txtQty;
    public AnchorPane makePaymentAnchorPaneContext;
    public TableColumn colOrderId;
    public JFXButton btnSave;


    private final PlaceOrderBO placeOrderBO = (PlaceOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PLACE_ORDER);

    public void initialize() throws SQLException, ClassNotFoundException {


        loadCustomerIds();
        loadItemIds();
        lastId();
        loadDateAndTime();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        cmbCustomerId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerData( newValue);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData( newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnSave.setDisable(newItemCode == null);

            if (newItemCode != null) {
                try {
                    if (!existItem(newItemCode + "")) {
                    }
                    /*Find Item*/
                    ItemDTO item = placeOrderBO.searchItem(newItemCode + "");
                    txtDescription.setText(item.getDescription());
                    txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                    Optional<OrderDetailTm> optOrderDetail = tblOrderTable.getItems().stream().filter(detail -> detail.getItemCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getOrderQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {
            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getItemCode());
                btnSave.setText("Update");
                txtQty.setText(selectedOrderDetail.getOrderQty() + "");
            } else {
                btnSave.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }
        });

    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return placeOrderBO.ifItemExist(code);
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        Item i=placeOrderBO.getItem(itemCode);
        if (i == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(i.getDescription());
            txtPackSize.setText(i.getPackSize());
            txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(i.getQtyOnHand()));
        }
    }

    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer c1 = placeOrderBO.getCustomer(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtCustomerTitle.setText(c1.getCustTitle());
            txtCustomerName.setText(c1.getCustName());
            txtCustomerAddress.setText(c1.getCustAddress());
            txtCity.setText(c1.getCity());
            txtProvince.setText(c1.getProvince());
            txtPostalCode.setText(c1.getPostalCode());
        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        try {
            ArrayList<ItemDTO> all = placeOrderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemCode.getItems().add(dto.getItemCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        try {
            ArrayList<CustomerDTO> all = placeOrderBO.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems().add(customerDTO.getCustId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
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

    ObservableList<OrderDetailTm> obList= FXCollections.observableArrayList();

    public void addToCardOnAction(ActionEvent actionEvent) {
        String id=lblOrderId.getText();
        String itemCode=cmbItemCode.getSelectionModel().getSelectedItem();
        int  qtyOnHand=Integer.parseInt(txtQtyOnHand.getText());
        Double discount=50.00;
        String orderDate=lblDate.getText();
        String orderTime=lblTime.getText();
        String customerId=cmbCustomerId.getSelectionModel().getSelectedItem();
        Double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int orderQty=Integer.parseInt(txtQty.getText());
        Double total=unitPrice*(new Double(orderQty))-discount;

        if (qtyOnHand<orderQty) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
        }else {

            boolean exists = tblOrderTable.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(itemCode));

            if (exists) {
                OrderDetailTm orderDetailTM = tblOrderTable.getItems().stream().filter(detail -> detail.getItemCode().equals(itemCode)).findFirst().get();

                if (btnSave.getText().equalsIgnoreCase("Update")) {
                    orderDetailTM.setOrderQty(orderQty);
                    orderDetailTM.setTotal(total);
                    tblOrderTable.getSelectionModel().clearSelection();
                } else {
                    orderDetailTM.setOrderQty(orderDetailTM.getOrderQty()+ orderQty);
                    orderDetailTM.setTotal(total);
                }
                tblOrderTable.refresh();
            } else {
                tblOrderTable.getItems().add(new OrderDetailTm(id,itemCode,orderQty,discount,orderDate,orderTime,customerId,total));
            }
            calculateTotal();
            cmbItemCode.getSelectionModel().clearSelection();
            txtDescription.clear();
            txtPackSize.clear();
            txtQtyOnHand.clear();
            txtUnitPrice.clear();
            txtQty.clear();

        }
    }

    public void saveOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean b = saveOrder(lblOrderId.getText(), lblDate.getText(),lblTime.getText(), cmbCustomerId.getValue(),
                tblOrderTable.getItems().stream().map(tm -> new OrderDetailDTO(lblOrderId.getText(), tm.getItemCode(), tm.getOrderQty(), tm.getDiscount(),tm.getOrderDate(),tm.getOrderTime(),tm.getCustomerId(),tm.getTotal())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        lastId();
        cmbCustomerId.getSelectionModel().clearSelection();
        txtCustomerTitle.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderTable.getItems().clear();
        txtQty.clear();


        calculateTotal();
    }

    public boolean saveOrder(String orderId, String orderDate, String orderTime, String customerId, List<OrderDetailDTO> items) {
        try {
            OrderDTO orderDTO = new OrderDTO(orderId,orderDate,orderTime,customerId, (ArrayList<OrderDetailDTO>) items);
            return placeOrderBO.purchaseOrder(orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void lastId() throws SQLException, ClassNotFoundException {
        String Id = placeOrderBO.generateNewOrderId();;
        String finalId = "O-001";

        if (Id != null) {

            String[] splitString = Id.split("-");
            int id = Integer.parseInt(splitString[1]);
            id++;

            if (id < 10) {
                finalId = "O-00" + id;
            } else if (id < 100) {
                finalId = "O-0" + id;
            } else {
                finalId = "O-" + id;
            }
            lblOrderId.setText(finalId);
        } else {
            lblOrderId.setText(finalId);
        }
    }

    private void calculateTotal() {
        Double total = 0.00;
        for (OrderDetailTm detail : tblOrderTable.getItems()) {
            total = detail.getTotal();
        }
        lblTotal.setText("Total: " + total);
    }
}
