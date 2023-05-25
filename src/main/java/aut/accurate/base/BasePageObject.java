package aut.accurate.base;

import aut.accurate.utils.Constants;
import aut.accurate.utils.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

import static aut.accurate.utils.Utils.printError;
import static aut.accurate.webdriver.WebDriverInstance.webDriver;

public class BasePageObject {

    public WebDriver getDriver() {
        return webDriver;
    }

    public By element(String elementLocator) {
        String elementValue = Utils.ELEMENTS.getProperty(elementLocator);

        if (elementValue == null) {
            throw new NoSuchElementException("Couldn't find element : " + elementLocator + " ! Please check properties file!");
        } else {
            String[] locator = elementValue.split("_");
            String locatorType = locator[0];
            String locatorValue = elementValue.substring(elementValue.indexOf("_") + 1);
            switch (locatorType) {
                case "id":
                    return By.id(locatorValue);
                case "name":
                    return By.name(locatorValue);
                case "xpath":
                    return By.xpath(locatorValue);
                case "containsText":
                    return By.xpath(String.format("//*[contains(text(), '%s')]", locatorValue));
                case "class":
                    return By.className(locatorValue);
                case "css":
                    return By.cssSelector(locatorValue);
                default:
                    throw new IllegalStateException("Unexpected locator type: " + locatorType);
            }
        }
    }

    public WebElement waitUntil(ExpectedCondition<WebElement> conditions, Integer timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(conditions);
    }

    public WebElement waitUntilClickable(String element) {
        return waitUntil(ExpectedConditions.elementToBeClickable(element(element)), Constants.TIMEOUT);
    }

    public WebElement waitUntilVisible(String element) {
        return waitUntil(ExpectedConditions.visibilityOfElementLocated(element(element)), Constants.TIMEOUT);
    }

    public WebElement waitUntilPresent(By by) {
        return waitUntil(ExpectedConditions.presenceOfElementLocated(by), Constants.TIMEOUT);
    }

    public Boolean isPresent(String element) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Constants.TIMEOUT));
            wait.ignoring(NoSuchElementException.class);
            wait.ignoring(ElementNotInteractableException.class);
            wait.ignoring(StaleElementReferenceException.class);
            wait.ignoring(NoSuchFrameException.class);

            wait.until(ExpectedConditions.presenceOfElementLocated(element(element)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement find(String element) {
        return waitUntilPresent(element(element));
    }

    public void wait(int second) {
        try {
            Thread.sleep(Duration.ofSeconds(second).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickOn(String element) {
        waitUntilClickable(element).click();
    }

    public void typeOn(String by, String text) {
        waitUntilVisible(by).clear();
        waitUntilVisible(by).sendKeys(text);
    }

    public void assertIsPresent(String element) {
        if (!isPresent(element)) {
            printError("Element " + element(element) + " not found!");
        }
    }

    public void mouseOver(String element) {
        Actions act = new Actions(getDriver());
        act.moveToElement(find(element));
        act.build().perform();
    }

    public void switchBrowserTab(int index) {
        ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        int listTab = tabs.size();

        if (listTab == 1) {
            printError(String.format("Found %d browser tab!", listTab));
        } else {
            getDriver().switchTo().window(tabs.get(index));
        }
    }

    public String getText(String element) {
        waitUntilVisible(element);
        return find(element).getText();
    }

    public void isTextSame(String element, String expectedText) {
        String getText = getText(element);
        if (!getText.equalsIgnoreCase(expectedText)) {
            printError(String.format("Text on element [%s] is [%s] not equal with text [%s]", element(element), getText, expectedText));
        }
    }
}
