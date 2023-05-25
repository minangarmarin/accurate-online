package aut.accurate.pages;

import aut.accurate.base.BasePageObject;

import static aut.accurate.utils.Utils.env;
import static aut.accurate.utils.Utils.readExcel;

public class LoginPage extends BasePageObject {
    public void loginToAccurate() {
        // parsing username and password from 'excel file'
        String excelFile = "/data/accurate-data";
        String username = readExcel(excelFile, "USERNAME");
        String password = readExcel(excelFile, "PASSWORD");

        inputUsername(username);
        inputPassword(password);
        clickButtonLogin();
        isSuccessLogin();
    }

    public void inputUsername(String username) {
        typeOn("FIELD_LOGIN_USERNAME", username);
    }

    public void inputPassword(String password) {
        typeOn("FIELD_LOGIN_PASSWORD", password);
    }

    public void clickButtonLogin() {
        clickOn("BUTTON_LOGIN");
    }

    public void isSuccessLogin() {
        assertIsPresent("BUTTON_CREATE_BUSINESS_DATA");
    }
}
