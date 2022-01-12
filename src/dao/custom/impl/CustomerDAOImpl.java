package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer  VALUES (?,?,?,?,?,?,?)", dto.getCustId(), dto.getCustTitle(), dto.getCustName(),
                dto.getCustAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE id=?", id);
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET custTitle=?, name=?,address=?,city=?,province=?,postalCode=? WHERE id=?", dto.getCustTitle(), dto.getCustName(), dto.getCustAddress(),
                dto.getCity(), dto.getProvince(), dto.getPostalCode(),dto.getCustId());
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE id=?", id);
        rst.next();
        return new Customer(id, rst.getString("custTitle"), rst.getString("name"),
                rst.getString("address"), rst.getString("city"), rst.getString("province"),
                rst.getString("postalCode"));
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString("id"), rst.getString("custTitle"), rst.getString("name"),
                    rst.getString("address"), rst.getString("city"),
                    rst.getString("province"), rst.getString("postalCode")));
        }
        return allCustomers;
    }


    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT id FROM Customer WHERE id=?", id).next();
    }


    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            return id;
        }
        return  null;
    }
    @Override
    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.executeQuery("SELECT * FROM Customer WHERE id=?",id);

        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        } else {
            return null;
        }
    }
}
