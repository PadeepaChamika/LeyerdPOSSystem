package bo;

import bo.Custom.PlaceOrderBO;
import bo.Custom.impl.CustomerBOImpl;
import bo.Custom.impl.ItemBOImpl;
import bo.Custom.impl.PlaceOrderBOImpl;
import bo.Custom.impl.ReportBOImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case ITEM:
                return new ItemBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case REPORT:
                return new ReportBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBOImpl() {
                };
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, PLACE_ORDER, REPORT
    }
}
