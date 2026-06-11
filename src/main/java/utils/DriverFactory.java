package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    private DriverFactory() {}
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver initDriver(String browser) throws RuntimeException {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driver.set(new ChromeDriver(chromeOptions));
                break;
            case "chrome-headless":
                WebDriverManager.chromedriver().setup();
                ChromeOptions headlessOption = new ChromeOptions();
                headlessOption.addArguments("--headless=new");
                headlessOption.addArguments("--window-size=1920,1080");
                headlessOption.addArguments("--no-sandbox");
                headlessOption.addArguments("--disable-dev-shm-usage");
                driver.set(new ChromeDriver(headlessOption));
                break;
            case "chrome-incognito":
                WebDriverManager.chromedriver().setup();
                ChromeOptions incognitoOption = new ChromeOptions();
                incognitoOption.addArguments("--incognito");
                driver.set(new ChromeDriver(incognitoOption));
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                driver.set(new EdgeDriver(edgeOptions));
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
            default:
                throw new RuntimeException("Browser not supported: " + browser);
        }
        return getDriver();
    }
    public static WebDriver getDriver() {
        return driver.get();
    }
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}