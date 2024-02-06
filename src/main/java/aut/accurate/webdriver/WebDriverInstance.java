package aut.accurate.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static aut.accurate.utils.Constants.CHROME_DRIVER;
import static aut.accurate.utils.Constants.ROOT;
import static aut.accurate.utils.Utils.env;
import static aut.accurate.utils.Utils.printError;

public class WebDriverInstance {
    public static WebDriver webDriver;

    public static void initialize() {
        File downloadPath = new File(ROOT + "/" + env("FOLDER_DOWNLOADED"));

        if (!downloadPath.exists()) {
            boolean isCreated = downloadPath.mkdir();
            if (!isCreated) {
                printError("Failed to create download directory!");
            }
        }

        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", downloadPath.toString());

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", chromePrefs);

        if (env("HEADLESS")!=null){
        chromeOptions.addArguments("--headless");
        }
        
            
        chromeOptions.addArguments("--window-size=1920,1080");

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);

        webDriver = new ChromeDriver(chromeOptions);
        webDriver.get(env("URL"));
    }

    public static void quit() {
        webDriver.quit();
    }
}
