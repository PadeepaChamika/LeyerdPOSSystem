package controller;

import bo.BoFactory;
import bo.Custom.ReportBO;
import com.jfoenix.controls.JFXComboBox;
import db.DbConnection;
import dto.ReportDTO;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import entity.Report;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemReportFormController {
    public JFXComboBox <String>cmbSlectDate;
    public TextField txtDailyIncome;
    public JFXComboBox <String>cmbMonth;
    public TextField txtMonthIncome;
    public JFXComboBox <String>cmbYear;
    public TextField txtYearIncome;
    public JFXComboBox <String>cmbCustomerIds;
    public TextField txtCustomerIncome;
    public TableView tblIncomeReport;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colOrderQty;
    public TableColumn colCustomerId;
    public TableColumn colTotal;
    public JFXComboBox<String> cmbItem;
    public TextField txtItemSelect;

    private final ReportBO reportBO = (ReportBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.REPORT);

    public void initialize() throws SQLException, ClassNotFoundException {
        loadCustomerIds();
        loadYears();
        loadMonths();
        loadDates();

        cmbYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<ReportDTO> yearlyDetails = new ArrayList<>();
            try {

                yearlyDetails =reportBO.getYearlyDetails(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateIncome(yearlyDetails);

            colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblIncomeReport.setItems(FXCollections.observableArrayList(yearlyDetails));
        });

        cmbMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<ReportDTO> MonthlyDetails = new ArrayList<>();
            try {

                MonthlyDetails =reportBO.getMonthlyDetails(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateMonthIncome(MonthlyDetails);

            colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblIncomeReport.setItems(FXCollections.observableArrayList(MonthlyDetails));
        });

        cmbSlectDate.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<ReportDTO> DailyDetails = new ArrayList<>();
            try {

                DailyDetails = reportBO.getDailyDetails(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateDailyIncome(DailyDetails);

            colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblIncomeReport.setItems(FXCollections.observableArrayList(DailyDetails));
        });

        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<ReportDTO> CustomerIncomeDetails = new ArrayList<>();
            try {

                CustomerIncomeDetails = reportBO.getCustomerIncome(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateCustomerWiseIncome(CustomerIncomeDetails);

            colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblIncomeReport.setItems(FXCollections.observableArrayList(CustomerIncomeDetails));
        });

        ArrayList<String> MovaleList = new ArrayList<>();
        MovaleList.add("MostMovableItem");
        MovaleList.add("LeastMovableItem");

        for (String Movable : MovaleList) {
            cmbItem.getItems().add(Movable);
        }



        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<Report> CustomerIncomeDetails = new ArrayList<>();


            try {
                if(newValue=="MostMovableItem"){
                    String a=reportBO.getMost(newValue);
                    txtItemSelect.setText(a);
                }else{
                    String a=reportBO.getLeast(newValue);
                    txtItemSelect.setText(a);
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = reportBO
                .getCustomerIds();
        cmbCustomerIds.getItems().addAll(customerIds);
    }

    private void loadYears() throws SQLException, ClassNotFoundException {
        List<String> years =reportBO
                .getYears();
        cmbYear.getItems().addAll(years);
    }

    private void loadMonths() throws SQLException, ClassNotFoundException {
        List<String> months = reportBO
                .getMonth();
        cmbMonth.getItems().addAll(months);
    }
    private void loadDates() throws SQLException, ClassNotFoundException {
        List<String> dates = reportBO
                .getDates();
        cmbSlectDate.getItems().addAll(dates);
    }

    private void calculateIncome(ArrayList<ReportDTO> temp){
        double tPrice=0.0;
        for(ReportDTO r : temp){
            tPrice+= r.getTotal();
        }
        txtYearIncome.setText(String.valueOf(tPrice)+" /=");
    }

    private void calculateMonthIncome(ArrayList<ReportDTO> temp){
        double tPrice=0.0;
        for(ReportDTO r : temp){
            tPrice+= r.getTotal();
        }
        txtMonthIncome.setText(String.valueOf(tPrice)+" /=");
    }

    private void calculateDailyIncome(ArrayList<ReportDTO> temp){
        double tPrice=0.0;
        for(ReportDTO r : temp){
            tPrice+= r.getTotal();
        }
        txtDailyIncome.setText(String.valueOf(tPrice)+" /=");
    }


    private void calculateCustomerWiseIncome(ArrayList<ReportDTO> temp){
        double tPrice=0.0;
        for(ReportDTO r : temp){
            tPrice+= r.getTotal();
        }
        txtCustomerIncome.setText(String.valueOf(tPrice)+" /=");
    }

    public void printDailyIncome(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../views/Reports/DailyReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);


            String billDate = cmbSlectDate.getSelectionModel().getSelectedItem();
            System.out.println(billDate);

            HashMap map = new HashMap();
            map.put("SearchId",billDate);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);



        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printMonthlyIncome(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../views/Reports/MonthlyReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);


            String billDate = cmbMonth.getSelectionModel().getSelectedItem();
            System.out.println(billDate);

            HashMap map = new HashMap();
            map.put("SearchId",billDate);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);



        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printYearlyIncome(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../views/Reports/YearlyReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);


            String billDate = cmbYear.getSelectionModel().getSelectedItem();
            System.out.println(billDate);

            HashMap map = new HashMap();
            map.put("SearchId",billDate);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);



        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printCustomerWiseIncome(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../views/Reports/CustomerWiseReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);


            String billDate = cmbCustomerIds.getSelectionModel().getSelectedItem();
            System.out.println(billDate);

            HashMap map = new HashMap();
            map.put("SearchId",billDate);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);



        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
