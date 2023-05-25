package aut.accurate.data;

public class ReportData {
    private static String reportType;
    private static String fileType;

    public static String getFileType() {
        return fileType;
    }

    public static void setFileType(String fileType) {
        ReportData.fileType = fileType;
    }

    public static String getReportType() {
        return reportType;
    }

    public static void setReportType(String reportType) {
        ReportData.reportType = reportType;
    }
}
