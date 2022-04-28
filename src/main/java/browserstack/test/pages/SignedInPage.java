package browserstack.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SignedInPage extends SeleniumPage
{
    public static String path = "/";
    public static String title = "StackDemo";

    public static By username = By.cssSelector(".username");
    public static By logoutLink = By.id("logout");

    public SignedInPage(WebDriver driver, String baseurl)
    {
        super(driver, baseurl + path);
    }

    public boolean currentPage()
    {
        wait.until(visibilityOfElementLocated(logoutLink));

        if (! driver.getTitle().equals(title)) {
            return false;
        }

        if (! driver.getCurrentUrl().endsWith("signin=true")) {
            return false;
        }

        return true;
    }
}
