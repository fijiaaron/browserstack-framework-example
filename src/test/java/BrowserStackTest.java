import com.browserstack.framework.BrowserManager;
import com.browserstack.framework.BrowserStackExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class BrowserStackTest
{
	Logger log = Logger.getLogger(BrowserStackTest.class.getSimpleName());

	RemoteWebDriver driver;

	@RegisterExtension
	BrowserStackExtension browserStackExtension = new BrowserStackExtension();


	@BeforeEach
	public void setup(TestInfo test) throws MalformedURLException
	{
		browserstackProperties.setProperty("name", test.getDisplayName());
		browserstackProperties.setProperty("build", "Browserstack Framework Tests");

		driver = new BrowserManager(browserstackProperties).getBrowserStackDriver();
		browserStackExtension.setSessionId(driver.getSessionId().toString());
	}


	@Test
	public void openPageSuccess() throws InterruptedException
	{
		driver.get("https://one-shore.com");

		String sessionId = driver.getSessionId().toString();
		System.out.println("sessionId: " + sessionId);

		String title = driver.getTitle();
		System.out.println("title: " + title);

		Thread.sleep(5000);

		assertThat(title).contains("One Shore");
	}

	@Test
	public void openPageFailure()
	{
		driver.get("https://one-shore.com");

		String sessionId = driver.getSessionId().toString();
		System.out.println("sessionId: " + sessionId);

		String title = driver.getTitle();
		System.out.println("title: " + title);

		assertThat(title).doesNotContain("One Shore");
	}

	@AfterEach
	public void teardown()
	{
		if (driver != null)
		{
			driver.quit();
		}
	}


	Properties browserstackProperties = new Properties()
	{{
		setProperty("browser", "Chrome");
		setProperty("browser_version", "latest");
		setProperty("os", "windows");
		setProperty("os_version:", "10");
		setProperty("name", "BrowserstackTest");
		setProperty("build", "testing framework");
	}};
}
