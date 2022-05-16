package parameterized;

import com.browserstack.framework.BrowserManager;
import com.browserstack.framework.BrowserStackExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;


public class CrossBrowserTest
{
	RemoteWebDriver browser;
	Properties browserStackProperties = new Properties();
	String testName;

	@RegisterExtension
	BrowserStackExtension browserStackExtension = new BrowserStackExtension();


	@BeforeEach
	public void setup(TestInfo testInfo) throws MalformedURLException
	{
		browserStackProperties.setProperty("test_name", getTestName(testInfo));
		browserStackProperties.setProperty("build", "Testing Browserstack Framework");
	}


	@ParameterizedTest
	@ValueSource(strings = {"chrome", "firefox", "edge"})
	public void openPage(String browserName, TestInfo testInfo) throws MalformedURLException
	{
		System.out.println("Getting browser: " + browserName + " for test: " + testName);
		browser = getBrowser(browserName);

		browser.get("https://bstackdemo.com/");
		String title = browser.getTitle();

		assertThat(title).contains("Stack");
	}

	public RemoteWebDriver getBrowser(String browserName) throws MalformedURLException
	{
		browserStackProperties.setProperty("browser", browserName);
		BrowserManager browserManager = new BrowserManager(browserStackProperties);
		RemoteWebDriver browser = browserManager.getBrowserStackDriver();

		browserStackExtension.setSessionId(browser.getSessionId().toString());

		return browser;
	}

	public String getTestName(TestInfo testInfo)
	{
		String className = testInfo.getTestClass().get().getSimpleName();
		String methodName = testInfo.getTestMethod().get().getName();
		String displayName = testInfo.getDisplayName();

		if (! displayName.equals(methodName))
		{
			displayName = methodName + displayName;
		}

		testName = className + " " + displayName;
		return testName;
	}


	@AfterEach
	public void tearDown()
	{
		if (browser != null)
		{
			browser.quit();
		}
	}
}
