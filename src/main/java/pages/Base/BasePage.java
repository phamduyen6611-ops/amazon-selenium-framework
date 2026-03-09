package pages.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Wait;

public class BasePage {

    protected WebDriver driver;
    protected Wait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new Wait(driver);
    }

    protected void click(WebElement element) {
        wait.waitForElementClickable(element).click();
    }

    protected void type(WebElement element, String text) {
        wait.waitForElementVisible(element).sendKeys(text);
    }
}