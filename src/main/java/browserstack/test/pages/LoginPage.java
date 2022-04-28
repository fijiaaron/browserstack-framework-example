package browserstack.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LoginPage extends SeleniumPage
{
    public static String path = "/signin";
    public static String title = "StackDemo";
    public static By usernameField = By.cssSelector("#username input");
    public static By passwordField = By.cssSelector("#password input");
    public static By loginButton = By.cssSelector("#login-btn");

    public LoginPage(WebDriver driver, String baseUrl)
    {
        super(driver, baseUrl + LoginPage.path);
    }

    public LoginPage open()
    {
        driver.get(url);
        return this;
    }

    public LoginPage enterUsername(String username)
    {
        WebElement usernameField = wait.until(elementToBeClickable(LoginPage.usernameField));
        usernameField.sendKeys("demouser" + Keys.ENTER);
        delay();
        return this;
    }

    public LoginPage enterPassword(String password)
    {
        // should not include test data like username or password in page objects
        whenActive(passwordField).sendKeys("testingisfun99" + Keys.ENTER);
        delay();
        return this;
    }

    public LoginPage pressLoginButton()
    {
        whenActive(loginButton).click();
        delay();
        // don't return the next page -- only use fluent methods for the current class
        return this;
    }

    public boolean currentPage()
    {
        wait.until(visibilityOfElementLocated(loginButton));

        if (! driver.getCurrentUrl().endsWith("/signin")) {
            return false;
        }

        return true;
    }

    public void login(String username, String password)
    {
        if (! currentPage())
        {
            open();
        }

        enterUsername(username);
        enterPassword(password);
        pressLoginButton();

        //TODO: sanity check that login succeeded
    }
}
