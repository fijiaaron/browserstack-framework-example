package parameterized;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import static org.assertj.core.api.Assertions.assertThat;

public class CrossBrowserTest2 extends BrowserResourceLock
{
	@Test
	@ResourceLock(value = "n")
	public void test1()
	{
		String testName = getCaller();
		System.out.println("start " + testName + " thread " + getThread());
		sleep(3);
		assertThat(StaticResource.n).isEqualTo(1);
		System.out.println("end " + testName + " thread " + getThread());
	}

	@Test
	@ResourceLock(value = "m")
	public void test2()
	{

		String testName = getCaller();
		System.out.println("start " + testName + " thread " + getThread());
		sleep(3);
		assertThat(StaticResource.m++).isEqualTo(2);
		System.out.println("end " + testName + " thread " + getThread());
	}
}
