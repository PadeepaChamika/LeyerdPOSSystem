package dto;

import java.util.ArrayList;

public class OrderDTO {
    private String orderId;
    private String orderDate;
    private String orderTime;
    private String custId;
    private ArrayList<OrderDetailDTO> items;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String orderDate, String orderTime, String custId, ArrayList<OrderDetailDTO> items) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCustId(custId);
        this.setItems(items);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public ArrayList<OrderDetailDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetailDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", custId='" + custId + '\'' +
                ", items=" + items +
                '}';
    }
}
