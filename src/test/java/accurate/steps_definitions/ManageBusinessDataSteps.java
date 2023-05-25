package accurate.steps_definitions;

import aut.accurate.pages.ManageBusinessPage;
import io.cucumber.java8.En;

public class ManageBusinessDataSteps implements En {

    ManageBusinessPage manageBusinessPage = new ManageBusinessPage();

    public ManageBusinessDataSteps() {
        When("User click business data", () -> manageBusinessPage.clickBusinessData());
    }
}
