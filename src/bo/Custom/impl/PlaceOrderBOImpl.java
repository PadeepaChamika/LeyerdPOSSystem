package bo.Custom.impl;

import bo.Custom.PlaceOrderBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DbConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Customer;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = null;

        connection = DbConnection.getInstance().getConnection();
        boolean orderAvailable = orderDAO.ifOrderExist(dto.getOrderId());
        /*if order id already exist*/
        if (orderAvailable) {
            return false;
        }

        connection.setAutoCommit(false);
        /*Add Order*/
        Orders order = new Orders(dto.getOrderId(), dto.getOrderDate(), dto.getOrderTime(),dto.getCustId());
        boolean orderAdded = orderDAO.add(order);
        if (!orderAdded) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detail : dto.getItems()) {
            OrderDetail orderDetailDTO = new OrderDetail(dto.getOrderId(), detail.getItemCode(), detail.getOrderQty(), detail.getDiscount(),
                    detail.getOrderDate(),detail.getOrderTime(),detail.getCustomerId(),detail.getTotal());
            boolean orderDetailsAdded = orderDetailDAO.add(orderDetailDTO);
            if (!orderDetailsAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            Item search = itemDAO.search(detail.getItemCode());
            search.setQtyOnHand(search.getQtyOnHand() - detail.getOrderQty());
            boolean update = itemDAO.update(search);
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        //if every thing ok
        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewOrderId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCustId(), c.getCustTitle(), c.getCustName(),c.getCustAddress(),
                    c.getCity(),c.getProvince(),c.getPostalCode()));
        }
        return allCustomers;

    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getDescription(),item.getPackSize() ,item.getUnitPrice(), item.getQtyOnHand()));
        }
        return allItems;

    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        return new ItemDTO(item.getItemCode(), item.getDescription(),item.getPackSize() ,item.getUnitPrice(), item.getQtyOnHand());
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(code);
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(id);
    }

    @Override
    public CustomerDTO searchCustomer(String s) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(s);
        return new CustomerDTO(c.getCustId(), c.getCustTitle(), c.getCustName(),c.getCustAddress(),
                c.getCity(),c.getProvince(),c.getPostalCode());
    }
    @Override
    public Item getItem(String id) throws SQLException, ClassNotFoundException {
        Item item= itemDAO.getItem(id);
        return item;
    }
    @Override
    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer= customerDAO.getCustomer(id);
        return customer;
    }
    @Override
    public OrderDetail getOrderDetail(String id) throws SQLException, ClassNotFoundException {
        OrderDetail orderDetail= orderDetailDAO.getOrderDetail(id);
        return orderDetail;
    }

    @Override
    public boolean update(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException{
        return orderDetailDAO.update(new OrderDetail(orderDetailDTO.getOrderId(), orderDetailDTO.getItemCode(),orderDetailDTO.getOrderQty(), orderDetailDTO.getDiscount(),orderDetailDTO.getOrderDate(),orderDetailDTO.getOrderTime(),orderDetailDTO.getCustomerId(),orderDetailDTO.getTotal()));
    }
}
