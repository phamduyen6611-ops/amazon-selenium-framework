package base;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;
import utils.DriverFactory;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("baseUrl");
        driver = DriverFactory.initDriver(browser);
        driver.get(url);
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Step("Assert that condition is true: {message}")
    protected void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Step("Assert that values are equal: Expected [{expected}], Actual [{actual}]")
    protected void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }
}
