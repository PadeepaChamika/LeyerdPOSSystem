package bo.Custom.impl;

import bo.Custom.ReportBO;
import dao.DAOFactory;
import dao.custom.ReportDAO;
import dto.ReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportBOImpl implements ReportBO {
    private final ReportDAO reportDAO = (ReportDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.REPORT);
    @Override
    public String getMost(String iCode) throws SQLException, ClassNotFoundException {
        return reportDAO.getMost(iCode);
    }

    @Override
    public String getLeast(String iCode) throws SQLException, ClassNotFoundException {
        return reportDAO.getLeast(iCode);
    }

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> all = reportDAO.getCustomerIds();
        return all;
    }

    @Override
    public ArrayList<String> getYears() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = reportDAO.getYears();
        return all;
    }

    @Override
    public boolean isYearExists(String string, ArrayList<String> year) {
        for(String y : year){
            if(y.equals(string)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> getMonth() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = reportDAO.getMonth();
        return all;
    }

    @Override
    public ArrayList<String> getDates() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = reportDAO.getDates();
        return all;
    }

    @Override
    public ArrayList<ReportDTO> getYearlyDetails(String year) throws SQLException, ClassNotFoundException {
        ArrayList<ReportDTO> alllYears = new ArrayList<>();
        ArrayList<ReportDTO> all = reportDAO.getYearlyDetails(year);
        for (ReportDTO i : all) {
            alllYears.add(new ReportDTO(i.getOrderId(),i.getItemCode(),i.getOrderQty(),i.getDiscount(),i.getOrderDate(),i.getOrderTime(),i.getCustomerId(),i.getTotal()));
        }
        return alllYears;
    }

    @Override
    public ArrayList<ReportDTO> getMonthlyDetails(String month) throws SQLException, ClassNotFoundException {
        ArrayList<ReportDTO> allMonths = new ArrayList<>();
        ArrayList<ReportDTO> all = reportDAO.getMonthlyDetails(month);
        for (ReportDTO i : all) {
            allMonths.add(new ReportDTO(i.getOrderId(),i.getItemCode(),i.getOrderQty(),i.getDiscount(),i.getOrderDate(),i.getOrderTime(),i.getCustomerId(),i.getTotal()));
        }
        return allMonths;
    }

    @Override
    public ArrayList<ReportDTO> getDailyDetails(String Day) throws SQLException, ClassNotFoundException {
        ArrayList<ReportDTO> allDaily = new ArrayList<>();
        ArrayList<ReportDTO> all = reportDAO.getDailyDetails(Day);
        for (ReportDTO i : all) {
            allDaily.add(new ReportDTO(i.getOrderId(),i.getItemCode(),i.getOrderQty(),i.getDiscount(),i.getOrderDate(),i.getOrderTime(),i.getCustomerId(),i.getTotal()));
        }
        return allDaily;
    }

    @Override
    public ArrayList<ReportDTO> getCustomerIncome(String id) throws SQLException, ClassNotFoundException {
        ArrayList<ReportDTO> allCus = new ArrayList<>();
        ArrayList<ReportDTO> all = reportDAO.getCustomerIncome(id);
        for (ReportDTO i : all) {
            allCus.add(new ReportDTO(i.getOrderId(),i.getItemCode(),i.getOrderQty(),i.getDiscount(),i.getOrderDate(),i.getOrderTime(),i.getCustomerId(),i.getTotal()));
        }
        return allCus;
    }
}
