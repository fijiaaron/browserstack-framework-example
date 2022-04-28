import browserstack.test.BrowserManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Logger;

@ExtendWith(TestResultExtension.class)
public abstract class SeleniumTestBase
{
    Logger log;

    TestInfo testInfo;
    String build = "default build";
    Properties properties;
    String baseurl;

    WebDriver driver;

    public SeleniumTestBase()
    {
        log = Logger.getLogger(this.getClass().getName());
        log.info("Instantiated test");
        build = System.getenv("build");
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

        if (build != null)
        {
            properties.setProperty("build", build);
        }

        // load properties from file (src/main/resources/test.properties)
        try
        {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(propertiesFile);
            properties.load(stream);
        }
        catch (IOException e)
        {
            System.out.println("unable to load test.properties file... using defaults");
            e.printStackTrace();
        }

        return properties;
    }
}
