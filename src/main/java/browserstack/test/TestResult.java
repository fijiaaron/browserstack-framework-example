package browserstack.test;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class TestResult
{
    String sessionId;

    public TestResult(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public void setTestStatus(boolean status)
    {
        setTestStatus(status, null);
    }

    public void setTestStatus(boolean status, String reason)
    {
        String url =  "https://api.browserstack.com/automate/sessions/<session-id>.json"
                .replace("<session-id>", sessionId);

        String BROWSERSTACK_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
        String json = "{\"status\":\"<status>\", \"reason\":\"<reason>\"}"
                .replace("<status>", status ? "passed" : "failed")
                .replace("<reason>", reason);

        HttpResponse<JsonNode> response = Unirest.put(url)
                .body("").asJson();
    }

    public static void main(String[] args)
    {
        String sessionId = "7999c24d00e4abb7692cf7b2c311dc72ccf099f2";
        boolean status = true;
        String reason = "testing setting test status";

        new TestResult(sessionId).setTestStatus(status, reason);
    }
}
