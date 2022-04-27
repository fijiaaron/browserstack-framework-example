import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestResultExtension implements AfterTestExecutionCallback
{
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception
    {
        Boolean passed = context.getExecutionException().isEmpty();
        String result = passed ? "PASSED" : "FAILED";

        System.out.println("Test Result: " + context.getDisplayName() + " " + result);
    }
}
