package com.browserstack.framework;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.util.logging.Logger;

public class TestResultUpdater
{
    static Logger log = Logger.getLogger(TestResultUpdater.class.getSimpleName());

    public static void main(String[] args)
    {
        String sessionId = "527462f8d033c54b9a60a5ef2244737b1f1e9a69";
        boolean passed = true;
        String reason = "set test status from TestResultUpdater";

        setTestStatus(sessionId, passed, reason);
    }

    public static void setTestStatus(String sessionId, boolean passed, String reason)
    {
        String BROWSERSTACK_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
        String BROWSERSTACK_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");

        String url =  "https://api.browserstack.com/automate/sessions/<session-id>.json"
                .replace("<session-id>", sessionId);

        log.info("url: " + url);

        String json = new TestStatus(passed, reason).toJson();

        log.info("json: " + json);

        HttpResponse<JsonNode> response = Unirest.put(url)
                .basicAuth(BROWSERSTACK_USERNAME, BROWSERSTACK_ACCESS_KEY)
                .contentType("application/json")
                .body(json)
                .asJson();

        log.info("response: " + response.getStatus() + " " + response.getStatusText());
        log.info("response body: " + response.getBody().toPrettyString());
    }
}
