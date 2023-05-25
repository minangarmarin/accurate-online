package accurate.hooks;

import aut.accurate.utils.Constants;
import aut.accurate.webdriver.WebDriverInstance;
import com.google.gson.JsonObject;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import static aut.accurate.utils.Utils.env;
import static aut.accurate.utils.Utils.loadElementProperties;
import static io.restassured.RestAssured.given;

public class WebDriveHooks {

    @Before
    public void initializeWebdriver() {
        WebDriverInstance.initialize();
        loadElementProperties(Constants.ELEMENTS);
    }

    @After
    public void quitWebdriver(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenShoot(scenario);
            sendReportToSlack(":rotating_light: *Scenario failed!*");
        } else {
            sendReportToSlack(":white_check_mark: *Scenario success!*");
        }
        WebDriverInstance.quit();
    }

    public static void takeScreenShoot(Scenario scenario) {
        try {
            final byte[] data = ((TakesScreenshot) WebDriverInstance.webDriver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(data, "image/png", "Failed Screenshot");
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }

    public void sendReportToSlack(String errorMessage) {
        JsonObject payload = new JsonObject();
        payload.addProperty("text", errorMessage);

        given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(env("SLACK_WEBHOOK_REPORT"))
                .then()
                .statusCode(200);
    }
}
