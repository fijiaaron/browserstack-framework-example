package bstackdemo.test;

import com.browserstack.framework.BrowserManager;
import com.browserstack.framework.BrowserStackExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

@Execution(ExecutionMode.CONCURRENT)
public abstract class SeleniumTest
{
    Logger log;

    TestInfo testInfo;
    String build = "no build";
    Properties properties;
    String baseurl;

    WebDriver driver;
    String sessionId;

    @RegisterExtension
    BrowserStackExtension browserStackExtension = new BrowserStackExtension();

    public SeleniumTest()
    {
        log = Logger.getLogger(this.getClass().getName());
        log.info("Instantiated test");
        build = System.getenv("BUILD_TAG");
    }


    @BeforeEach
    public void setup(TestInfo testInfo) throws IOException
    {
        this.testInfo = testInfo;
        log.info("Setting up test: " + testInfo.getDisplayName());
        log.info("Tags: " + testInfo.getTags());

        properties = getProperties("test.properties");

        if (this.testInfo.getTags().contains("selenium"))
        {
            log.info("getting browser...");
            BrowserManager browserManager = new BrowserManager(properties);
            driver = browserManager.getDriver();

            if (driver instanceof RemoteWebDriver)
            {
                log.info("getting sessionId of RemoteWebDriver instance: ");
                sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
                System.out.println("sessionId: " + sessionId);
                browserStackExtension.setSessionId(sessionId);
            }
            else
            {
                log.info("not running remote webdriver");
            }

            baseurl = properties.getProperty("baseUrl", "https://www.bstackdemo.com");
        }
    }


    @AfterEach
    public void teardown()
    {
        log.info("Completed test: " + testInfo.getDisplayName());

        if (driver != null)
        {
            driver.quit();
        }
    }


    public Properties getProperties(String propertiesFile)
    {
        // default properties
        Properties properties = new Properties();
        properties.setProperty("browser", "Chrome");
        properties.setProperty("browser_version", "latest");
        properties.setProperty("os", "Windows");
        properties.setProperty("os_version", "11");
        properties.setProperty("platform", "browserstack");
        properties.setProperty("test_name", this.testInfo.getDisplayName());

        // set build
        if (build != null)
        {
            properties.setProperty("build", build);
        }

        // load properties from file (src/main/resources/test.properties)
        try
        {
            InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFile);
            properties.load(resourceStream);
        }
        catch (IOException e)
        {
            System.out.println("unable to load test.properties file... using defaults");
            e.printStackTrace();
        }

        return properties;
    }

    // this should only be used for debugging purposes
    // used by delay() to slow down execution to watch steps
    public void pause(int seconds)
    {
        try
        {
            Thread.sleep(1000 * seconds);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
