package accurate.steps_definitions;

import aut.accurate.pages.LoginPage;
import io.cucumber.java8.En;

public class LoginSteps implements En {

    LoginPage loginPage = new LoginPage();

    public LoginSteps() {
        Given("User login to accurate", () -> loginPage.loginToAccurate());
    }
}
