package browserstack.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class BrowserManager
{
    Properties properties;

    String username = System.getenv("BROWSERSTACK_USERNAME");
    String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
    String browserstackUrl = "https://{username}:{access_key}@hub-cloud.browserstack.com/wd/hub";

    public BrowserManager(Properties properties)
    {
        this.properties = properties;
    }

    public WebDriver getDriver() throws MalformedURLException
    {
        String platform = properties.getProperty("platform", "localhost");

        //TODO USE LOGGER
        System.out.println("platform: " + platform);

        switch (platform.toUpperCase())
        {
            case "LOCALHOST":
                return getLocalDriver();
            case "BROWSERSTACK":
                return getBrowserStackDriver();
            default:
                throw new RuntimeException("Unknown platform: " + platform);
        }
    }

    public WebDriver getBrowserStackDriver() throws MalformedURLException
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browser", properties.getProperty("browser", "Chrome"));
        capabilities.setCapability("browser_version", properties.getProperty("browser_version", "latest"));
        capabilities.setCapability("os", properties.getProperty("os", "Windows"));
        capabilities.setCapability("os_version", properties.getProperty("os_version", "11"));

        browserstackUrl = browserstackUrl
                .replace("{username}", username)
                .replace("{access_key}", accessKey);

        //TODO: use logger
        System.out.println("url: " + browserstackUrl);
        System.out.println("capabilities: " + capabilities);

        URL url = new URL(browserstackUrl);
        WebDriver driver = new RemoteWebDriver(url, capabilities);

        //TODO: use logger
        System.out.println("driver: " + driver);

        return driver;
    }

    public WebDriver getLocalDriver() throws MalformedURLException
    {
        URL seleniumURL = new URL("http://localhost:4444/wd/hub");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", "any");
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "latest");

        //TODO: use logger
        System.out.println("url: " + browserstackUrl);
        System.out.println("capabilities: " + capabilities);

        WebDriver driver = new RemoteWebDriver(seleniumURL, capabilities);

        //TODO: use logger
        System.out.println("driver: " + driver);

        return driver;
    }
}
