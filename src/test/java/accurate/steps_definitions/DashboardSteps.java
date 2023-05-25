package accurate.steps_definitions;

import aut.accurate.pages.DashboardPage;
import io.cucumber.java8.En;

public class DashboardSteps implements En {

    DashboardPage dashboardPage = new DashboardPage();

    public DashboardSteps() {
        And("User click report menu", () -> dashboardPage.clickReportMenu());

        And("User click sub menu report list", () -> dashboardPage.clickSubMenuReportList());

        And("User click memorize menu", () -> dashboardPage.clickMemorizeMenu());

        And("User click sub menu memorize", () -> dashboardPage.clickSubMenuMemorize());

        And("User set report start and end date", () -> dashboardPage.setReportDate());

        And("User click show sales report button", () -> dashboardPage.clickShowReportButton());

        And("User see pdf sales report is valid business name", () -> dashboardPage.isValidBusinessNameReport());

        And("User click export button", () -> dashboardPage.clickExportButton());

        And("User click export to excel button", () -> dashboardPage.clickExportToExcelButton());

        Then("User verify success downloaded", () -> dashboardPage.isSuccessDownloaded());
    }
}
