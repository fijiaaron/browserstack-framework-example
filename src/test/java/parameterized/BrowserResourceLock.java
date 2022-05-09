package parameterized;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class BrowserResourceLock
{
	private List<String> resources;

	int n = 1;
	int m = 1;

	@BeforeEach
	void before()
	{
		resources = new ArrayList<>();
		resources.add("test");
	}

	@AfterEach
	void after()
	{
		resources.clear();
	}

	public String getCaller()
	{
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	public String getThread()
	{
		return Thread.currentThread().getName();
	}

	public void sleep(int seconds)
	{
		try { Thread.sleep(1000 * seconds); } catch (InterruptedException e) {}
	}

}
