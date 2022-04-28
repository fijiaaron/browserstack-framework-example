package browserstack.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public class BrowserManager
{
    Properties properties;
    Logger log;

    public BrowserManager(Properties properties)
    {
        log = Logger.getLogger(this.getClass().getName());
        this.properties = properties;
        log.info("properties: " + properties);
    }


    public WebDriver getDriver() throws MalformedURLException
    {
        String platform = properties.getProperty("platform", "localhost");

        log.info("platform: " + platform);

        switch (platform.toUpperCase())
        {
            case "LOCALHOST":
                return getLocalDriver();
            case "BROWSERSTACK":
                return getBrowserStackDriver();
            case "CHROMEDRIVER":
                return getChromeDriver();
            default:
                throw new RuntimeException("Unknown platform: " + platform);
        }
    }


    public WebDriver getBrowserStackDriver() throws MalformedURLException
    {
        String username = System.getenv("BROWSERSTACK_USERNAME");
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        String browserstackUrl = "https://{username}:{access_key}@hub-cloud.browserstack.com/wd/hub"
                .replace("{username}", username)
                .replace("{access_key}", accessKey);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browser", properties.getProperty("browser", "Chrome"));
        capabilities.setCapability("browser_version", properties.getProperty("browser_version", "latest"));
        capabilities.setCapability("os", properties.getProperty("os", "Windows"));
        capabilities.setCapability("os_version", properties.getProperty("os_version", "11"));
        capabilities.setCapability("name", properties.getProperty("test_name"));
        capabilities.setCapability("build", properties.getProperty("build"));

        log.info("url: " + browserstackUrl);
        log.info("capabilities: " + capabilities);

        URL url = new URL(browserstackUrl);
        WebDriver driver = new RemoteWebDriver(url, capabilities);
        return driver;
    }


    public WebDriver getLocalDriver() throws MalformedURLException
    {
        URL seleniumURL = new URL("http://localhost:4444/wd/hub");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", "any");
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "latest");

        log.info("url: " + seleniumURL);
        log.info("capabilities: " + capabilities);

        WebDriver driver = new RemoteWebDriver(seleniumURL, capabilities);
        return driver;
    }


    public WebDriver getChromeDriver()
    {
        String chromedriver_path = properties.getProperty("chromedriver_path");
        if (chromedriver_path != null)
        {
            System.setProperty("webdriver.chrome.driver", chromedriver_path);
        }

        log.info("using chromedriver: " + System.getProperty("webdriver.chrome.driver"));

        //TODO: add Chrome Options
        return new ChromeDriver();
    }
}
