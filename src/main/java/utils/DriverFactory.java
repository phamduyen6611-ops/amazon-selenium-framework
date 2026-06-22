package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Locale;

public class DriverFactory {
    private DriverFactory() {}
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initDriver(String browser) throws RuntimeException {
        if (browser == null || browser.isBlank()) {
            throw new IllegalArgumentException("Browser must be configured");
        }

        switch (browser.trim().toLowerCase(Locale.ROOT)) {
            case "chrome":
                driver.set(new ChromeDriver(getChromeOptions()));
                break;
            case "chrome-headless":
                driver.set(new ChromeDriver(getHeadlessChromeOptions()));
                break;
            case "chrome-incognito":
                driver.set(new ChromeDriver(getIncognitoChromeOptions()));
                break;
            case "edge":
                driver.set(new EdgeDriver(getEdgeOptions()));
                break;
            case "firefox":
                driver.set(new FirefoxDriver(getFirefoxOptions()));
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

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return options;
    }

    private static ChromeOptions getHeadlessChromeOptions() {
        ChromeOptions options = getChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return options;
    }

    private static ChromeOptions getIncognitoChromeOptions() {
        ChromeOptions options = getChromeOptions();
        options.addArguments("--incognito");
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        return options;
    }
}
