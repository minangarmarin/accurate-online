package aut.accurate.pages;

import aut.accurate.base.BasePageObject;
import aut.accurate.data.BusinessData;
import aut.accurate.data.ReportData;
import aut.accurate.utils.Utils;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static aut.accurate.utils.Constants.ROOT;
import static aut.accurate.utils.Utils.*;

public class DashboardPage extends BasePageObject {

    public void isToolbarDashboardMenu() {
        assertIsPresent("TOOLBAR_DASHBOARD_MENU");
    }

    public void clickReportMenu() {
        wait(5);
        clickOn("DASHBOARD_REPORT_MENU");
        isListReportSubMenu();
    }

    public void isListReportSubMenu() {
        assertIsPresent("LIST_SUB_MENU_REPORT");
    }

    public void clickSubMenuReportList() {
        clickOn("SUB_MENU_REPORT_LIST");
    }

    public void clickMemorizeMenu() {
        clickOn("MEMORIZE_MENU");
    }

    public void getReportType(String element) {
        String reportType = getText(element);
        ReportData.setReportType(reportType);
    }

    public void clickSubMenuMemorize() {
        String element = "SUBMENU_MEMORIZE";
        getReportType(element);
        clickOn(element);
    }

    public void setReportDate() {
        String dateFromExcel = readExcel("/data/accurate-data", "DATE");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate currentDate = LocalDate.now();
        LocalDate reportDate = Utils.calculateYesterday(currentDate, 1);
        LocalDate calculateYesterdayDate = Utils.calculateYesterday(currentDate, Integer.parseInt(dateFromExcel));

        String formattedReportDate = reportDate.format(formatter);
        String formattedCalculateYesterdayDate = calculateYesterdayDate.format(formatter);

        String elementStartDate = "FIELD_REPORT_START_DATE";
        String elementEndDate = "FIELD_REPORT_END_DATE";

        // Input start date
        clickOn(elementStartDate);
        wait(2);
        typeOn(elementStartDate, formattedCalculateYesterdayDate);
        clickOn(elementStartDate);

        // input end date
        clickOn(elementEndDate);
        wait(2);
        typeOn(elementEndDate, formattedReportDate);
        clickOn(elementEndDate);
    }

    public void clickShowReportButton() {
        clickOn("BUTTON_SHOW_REPORT");
    }

    public void isValidBusinessNameReport() {
        isTextSame("TEXT_USER_FIRST_NAME", BusinessData.getBusinessName());
    }

    public void clickExportButton() {
        clickOn("BUTTON_EXPORT");
    }

    public void clickExportToExcelButton() {
        clickOn("BUTTON_EXPORT_TO_EXCEL");
        ReportData.setFileType(".xlxs");

        //For wait until file success download
        wait(5);
    }

    public void isSuccessDownloaded() {
        File folder = new File(ROOT + "/" + env("FOLDER_DOWNLOADED"));

        String expectedBusinessName = BusinessData.getBusinessName().replace(" ", "").toLowerCase().substring(0, 15);
        String expectedFileType = ReportData.getFileType();

        File[] files = folder.listFiles();
        assert files != null;
        String sourceDirectoryFileDownloaded = env("FOLDER_DOWNLOADED");
        String fileNameDownloaded = Utils.getLatestFile(sourceDirectoryFileDownloaded);
        if (!fileNameDownloaded.contains(expectedBusinessName) && !fileNameDownloaded.contains(expectedFileType)) {
            printError(String.format("Actual file name [%s] not equal with expected file name [%s]", fileNameDownloaded, expectedBusinessName + expectedFileType));
        } else {
            Utils.uploadToFtp(sourceDirectoryFileDownloaded + "/" + Utils.getLatestFile(sourceDirectoryFileDownloaded), sourceDirectoryFileDownloaded);
            wait(10);
        }
    }
}
