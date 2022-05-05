import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.platform.engine.TestExecutionResult;

@Execution(ExecutionMode.CONCURRENT)
public class ConcurrentTests
{
    @Test
    public void takes_7_seconds() throws InterruptedException
    {
        Thread.sleep(7000);
    }

    @Test
    public void takes_6_seconds() throws InterruptedException
    {
        Thread.sleep(6000);
    }

    @Test
    public void takes_5_seconds() throws InterruptedException
    {
        Thread.sleep(5000);
    }

    @Test
    public void takes_4_seconds() throws InterruptedException
    {
        Thread.sleep(4000);
    }

    @Test
    public void takes_3_seconds() throws InterruptedException
    {
        Thread.sleep(3000);
    }

    @Test
    public void takes_2_seconds() throws InterruptedException
    {
        Thread.sleep(2000);
    }

    @Test
    public void takes_1_seconds() throws InterruptedException
    {
        Thread.sleep(1000);
    }

    @AfterEach
    public void finishTestCase(TestExecutionResult result) {
        System.out.println("after...");
    }
}
