package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailDAO extends CrudDAO<OrderDetail , String > {
    public OrderDetail getOrderDetail(String id) throws SQLException, ClassNotFoundException ;
}
