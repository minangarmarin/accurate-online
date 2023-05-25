package aut.accurate.data;

public class BusinessData {
    private static String businessName;

    public static String getBusinessName() {
        return businessName;
    }

    public static void setBusinessName(String businessName) {
        BusinessData.businessName = businessName;
    }
}
