package base;

import utils.ConfigReader;
import utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("baseUrl");

        driver = DriverFactory.initDriver(browser);
        driver.get(url);
    }
    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}