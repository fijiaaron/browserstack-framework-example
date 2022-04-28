import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.logging.Logger;

public class TestResultExtension implements AfterTestExecutionCallback
{
    Logger log;
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception
    {
        log = Logger.getLogger(context.getDisplayName());

        Boolean passed = context.getExecutionException().isEmpty();
        String result = passed ? "PASSED" : "FAILED";

        log.info("Test Result: " + context.getDisplayName() + " " + result);
    }
}
