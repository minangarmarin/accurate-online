package accurate;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"json:build/cucumber.json", "pretty", "html:build/result.html"},
        features = "src/test/resources/features")
public class CucumberRunner {
}
