package browserstack.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.time.Duration;

public abstract class SeleniumPage
{
    WebDriver driver;
    WebDriverWait wait;
    String url;
    Duration timeout = Duration.ofSeconds(30);
    int delay = 0;
    public SeleniumPage(WebDriver driver, String url)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
        this.url = url;
    }

    public void setDelay(int seconds)
    {
        this.delay = seconds;
    }

    public WebElement whenPresent(By locator)
    {
        return wait.until(presenceOfElementLocated(locator));
    }

    public WebElement whenVisible(By locator)
    {
        return wait.until(visibilityOfElementLocated(locator));
    }

    public WebElement whenActive(By locator)
    {
        return wait.until(elementToBeClickable(locator));
    }


    // this should only be used for debugging and defaults to 0
    protected void delay()
    {
        sleep(delay);
    }



    // this should only be used for debug purposes
    protected void sleep(int seconds)
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
