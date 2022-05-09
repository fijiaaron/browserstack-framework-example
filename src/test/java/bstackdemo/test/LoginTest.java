package bstackdemo.test;

import bstackdemo.test.pages.LoginPage;
import bstackdemo.test.pages.SignedInPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends SeleniumTestBase
{
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
	@Tag("failing")
	@Test
	public void testLoginFailure()
	{
		var loginPage = new LoginPage(driver, baseurl);
		loginPage.open();
		loginPage.setDelay(1);

		assertThat(driver.getTitle()).isEqualTo(LoginPage.title);

		loginPage.enterUsername("demouser");
		loginPage.enterPassword("invalidpassword");
		loginPage.pressLoginButton();

		assertThat(loginPage.getErrorMessage()).isEqualTo("invalid password");
	}
}
