import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@ExtendWith(TestResultExtension.class)
public abstract class SeleniumTest
{
    WebDriver driver;
    WebDriverWait wait;

    TestInfo testInfo;

    @BeforeEach
    public void setup(TestInfo testInfo)
    {
        this.testInfo = testInfo;
        System.out.println("Running test: " + testInfo.getDisplayName());
        System.out.println("Tags: " + testInfo.getTags());
    }

    @AfterEach
    public void teardown()
    {
        System.out.println("Completed test: " + testInfo.getDisplayName());
    }
}
