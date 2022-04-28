import browserstack.test.BrowserManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ExtendWith(TestResultExtension.class)
public abstract class BaseTest
{
    WebDriver driver;
    WebDriverWait wait;

    TestInfo testInfo;

    Properties properties;

    @BeforeEach
    public void setup(TestInfo testInfo) throws IOException
    {
        this.testInfo = testInfo;

        //@TODO: Add logging
        System.out.println("Running test: " + testInfo.getDisplayName());
        System.out.println("Tags: " + testInfo.getTags());

        properties = getProperties("test.properties");

        if (this.testInfo.getTags().contains("selenium")) {
            System.out.println("getting browser...");
            BrowserManager browserManager = new BrowserManager(properties);
            driver = browserManager.getDriver();
        }
    }

    @AfterEach
    public void teardown()
    {
        System.out.println("Completed test: " + testInfo.getDisplayName());

        if (driver != null) {
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
        properties.setProperty("provider", "browserstack");

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
//            throw new RuntimeException(e);
        }
        return properties;
    }
}
