package bo.Custom;

import bo.SuperBO;
import dto.ReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ReportBO extends SuperBO {
    public String getMost(String iCode) throws SQLException, ClassNotFoundException ;

    public String getLeast(String iCode) throws SQLException, ClassNotFoundException ;
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException ;

    public ArrayList<String> getYears() throws SQLException, ClassNotFoundException;

    public boolean isYearExists(String string , ArrayList<String> year);
    public ArrayList<String> getMonth() throws SQLException, ClassNotFoundException;

    public ArrayList<String> getDates() throws SQLException, ClassNotFoundException ;

    public ArrayList<ReportDTO> getYearlyDetails(String year) throws SQLException, ClassNotFoundException ;
    public ArrayList<ReportDTO> getMonthlyDetails(String month) throws SQLException, ClassNotFoundException ;

    public ArrayList<ReportDTO> getDailyDetails(String Day) throws SQLException, ClassNotFoundException ;

    public ArrayList<ReportDTO> getCustomerIncome(String id) throws SQLException, ClassNotFoundException ;
}
