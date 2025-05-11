package base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.URL;
import java.time.Duration;

import static org.openqa.selenium.By.id;

public class Base {

    static final ThreadLocal<RemoteWebDriver> remoteWebdriver = new ThreadLocal<RemoteWebDriver>();
     static final ThreadLocal<WebDriverWait> wait = new  ThreadLocal<WebDriverWait>();
    public void setWait() {
        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(20)));
    }

    public WebDriverWait getWait() {
        return wait.get();
    }

    public static RemoteWebDriver  getDriver() {
        return remoteWebdriver.get();
    }

    public void setDriver(String browser, boolean headless, String runMode) {
        try {
            MutableCapabilities options;
            RemoteWebDriver driver;

            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chrome = new ChromeOptions();
                    chrome.addArguments("--start-maximized", "--disable-notifications", "--incognito");
                    if (headless) chrome.addArguments("--headless=new");
                    options = chrome;
                    break;

                case "firefox":
                    FirefoxOptions firefox = new FirefoxOptions();
                    if (headless) firefox.addArguments("--headless");
                    options = firefox;
                    break;

                case "edge":
                    EdgeOptions edge = new EdgeOptions();
                    options = edge;
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            if (runMode.equalsIgnoreCase("grid")) {
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
            } else {
                driver = switch (browser.toLowerCase()) {
                    case "chrome" -> {
                        assert options instanceof ChromeOptions;
                        yield new ChromeDriver((ChromeOptions) options);
                    }
                    case "firefox" -> {
                        assert options instanceof FirefoxOptions;
                        yield new FirefoxDriver((FirefoxOptions) options);
                    }
                    case "edge" -> {
                        assert options instanceof EdgeOptions;
                        yield new EdgeDriver((EdgeOptions) options);
                    }
                    default -> throw new IllegalArgumentException("Unsupported browser for local run: " + browser);
                };
            }

            remoteWebdriver.set(driver);

        } catch (Exception e) {
            throw new RuntimeException("Driver setup failed: " + e.getMessage(), e);
        }
    }






    @Parameters("browser")
    @BeforeMethod
    public void startApp(String browser) throws InterruptedException {
        setDriver(browser, false,"grid");
        setWait();
        getDriver().manage().window().maximize();
        getDriver().get("https://login.salesforce.com");
        sendKeys(id("username"),"mailtosiva44@gmail.com");
        sendKeys(id("password"),"Shankar@sdet9497");
        click(id("Login"));


    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (getDriver() != null) {
            if (result.getStatus() == ITestResult.SKIP || result.getStatus() == ITestResult.FAILURE ||result.getStatus() == ITestResult.SUCCESS) {
                getDriver().quit();
                remoteWebdriver.remove();  // clean ThreadLocal
                wait.remove();             // clean ThreadLocal
            }
        }
    }


    public void click(By by) {
        WebElement element=null;
        try {
            element = getWait()
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
            element.click();
        } catch (JavascriptException e) {
            getDriver()
                    .executeScript("arguments[0].click()",element);
        }
    }



    public void sendKeys(By locator, String value)  {
        WebElement element = getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
        getDriver().executeScript("arguments[0].scrollIntoView(true);", element);

        // Optional wait for animation to settle
        new WebDriverWait(getDriver(), Duration.ofSeconds(3))
                .until(driver1 -> element.isDisplayed() && element.isEnabled());

        element.click();  // This triggers expansion
//        Thread.sleep(500); // Let the animation complete (can be adjusted)
        element.clear();
        element.sendKeys(value);
    }

}
