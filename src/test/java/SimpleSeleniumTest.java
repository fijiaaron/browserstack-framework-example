import bstackdemo.test.pages.LoginPage;
import bstackdemo.test.pages.SignedInPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleSeleniumTest extends SeleniumTest
{
    @Tag("selenium")
    @Tag("smoke")
    @Disabled
    @Test
    public void testHomePage()
    {
        String url = baseurl;
        driver.get(url);

        String title = driver.getTitle();
        log.info("got title: " + title);

        assertThat(title).isEqualTo("StackDemo");
    }

    @Tag("selenium")
    @Tag("smoke")
    @Tag("slow")
    @Test
    public void testOpenHomePageSlowly()
    {
        String url = baseurl;
        driver.get(url);

        String title = driver.getTitle();
        log.info("got title: " + title);

        pause(10);

        assertThat(title).isEqualTo("StackDemo");
    }


    @Tag("selenium")
    @Tag("smoke")
    @Tag("login")
    @Test
    public void testLogin()
    {
        var loginPage = new LoginPage(driver, baseurl);
        loginPage.open();
        assertThat(driver.getTitle()).isEqualTo(LoginPage.title);

        loginPage.enterUsername("demouser");
        loginPage.enterPassword("testingisfun99");
        loginPage.pressLoginButton();

        var signedInPage = new SignedInPage(driver, baseurl);
        assertThat(signedInPage.currentPage()).isTrue();
    }

    @Tag("selenium")
    @Tag("smoke")
    @Tag("login")
    @Tag("slow")
    @Test
    public void testLoginSlowly()
    {
        var loginPage = new LoginPage(driver, baseurl);
        loginPage.open();
        assertThat(driver.getTitle()).isEqualTo(LoginPage.title);

        loginPage.enterUsername("demouser");
        loginPage.enterPassword("testingisfun99");
        loginPage.pressLoginButton();

        pause(10);

        var signedInPage = new SignedInPage(driver, baseurl);
        assertThat(signedInPage.currentPage()).isTrue();
    }
}
