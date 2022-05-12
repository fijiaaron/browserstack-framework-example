package parameterized.before;

import com.google.common.base.Joiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestBeforeEach
{
	@BeforeEach
	public void setupBrowser()
	{
		print("setup: " + currentMethod());
	}

	@AfterEach
	public void teardownBrowser()
	{
		print("teardown: " + currentMethod());
	}

	@Test
	public void simpleTest()
	{
		print("test: " + currentMethod());
		print(currentMethod());
	}

	public String currentMethod()
	{
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		print(stacktrace);
		return stacktrace[2].getMethodName();
	}

	public void print(Object... messages)
	{
		String combined = Stream.of(messages)
				.map(Object::toString)
				.collect(Collectors.joining("\n"));

//		StringBuilder combined = new StringBuilder();
//		for (Object message : messages)
//		{
//			combined.append(message.toString() + "\n");
//		}

		System.out.println(combined);
	}
}
