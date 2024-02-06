package accurate.hooks;

import aut.accurate.utils.Constants;
import aut.accurate.utils.Utils;
import aut.accurate.webdriver.WebDriverInstance;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import com.google.gson.JsonObject;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import static aut.accurate.utils.Constants.ROOT;
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
            sendReportToSlack(env("SLACK_MESSAGE_FAILED"));
        } else {
            sendReportToSlack(env("SLACK_MESSAGE_SUCCESS"));
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
        payload.addProperty("text", errorMessage + "\nLink Report: <http://nas.dbo.id:8080/automation/result.html|Cucumber Report>");

        given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(env("SLACK_WEBHOOK_REPORT"))
                .then()
                .statusCode(200);
    }
}
