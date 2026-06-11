package pages.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
        WebElement el = wait.waitForElementVisible(element);
        el.clear();
        el.sendKeys(text);
    }

    protected void selectByVisibleText(WebElement element, String text) {
        Select select = new Select(wait.waitForElementVisible(element));
        select.selectByVisibleText(text);
    }
}
