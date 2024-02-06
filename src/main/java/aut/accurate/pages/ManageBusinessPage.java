package aut.accurate.pages;

import aut.accurate.base.BasePageObject;
import aut.accurate.data.BusinessData;

public class ManageBusinessPage extends BasePageObject {
    public void isBusinessDataAvailable() {
        assertIsPresent("CARD_BUSINESS_DATA");
    }

    public void clickBusinessData() {
        isBusinessDataAvailable();

        getBusinessName();


        clickOn("CARD_BUSINESS_DATA");

        // Switch to dashboard page menu tab
        switchBrowserTab(1);
        wait(20);

        // Verify dashboard toolbar menu is present
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.isToolbarDashboardMenu();
    }

    public void getBusinessName() {
        String firstname = getText("CARD_TEXT_BUSINESS_NAME");
        BusinessData.setBusinessName(firstname);
    }
}
