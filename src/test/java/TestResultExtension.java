import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

public class TestResultExtension implements AfterTestExecutionCallback
{
    Logger log;
    JavascriptExecutor jse;
    public void setJavascriptExecutor(JavascriptExecutor jse)
    {
        this.jse = jse;
    }

    public void updateTestResultBrowserStack()
    {
        jse.executeScript("browserstack_executor: {\"action\": \"getSessionDetails\"}");
    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception
    {
        log = Logger.getLogger(context.getDisplayName());

        Boolean passed = context.getExecutionException().isEmpty();
        String result = passed ? "PASSED" : "FAILED";

        log.info("Test Result: " + context.getDisplayName() + " " + result);

        updateTestResultBrowserStack();
    }
}
