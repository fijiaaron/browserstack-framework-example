package com.browserstack.framework;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.engine.execution.AfterEachMethodAdapter;
import org.junit.jupiter.engine.extension.ExtensionRegistry;

import java.util.logging.Logger;

public class BrowserStackExtension implements AfterEachMethodAdapter
{
	String BROWSERSTACK_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
	String BROWSERSTACK_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");

	Logger log = Logger.getLogger(BrowserStackExtension.class.getSimpleName());

	Boolean onBrowserStack = true;
	String sessionId;

	@Override
	public void invokeAfterEachMethod(ExtensionContext context, ExtensionRegistry registry) throws Throwable
	{
		log.info("updating test status with sessionId: " + sessionId);

		boolean passed = true;
		String reason = "";

		// check if there is an exception in the context to determine if it failed
		if (context.getExecutionException().isPresent())
		{
			passed = false;
			reason = context.getExecutionException().get().getMessage();
		}

		setTestStatus(sessionId, passed, reason);
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}


	public void setTestStatus(String sessionId, boolean status, String reason)
	{
		String url =  "https://api.browserstack.com/automate/sessions/<session-id>.json"
				.replace("<session-id>", sessionId);

		String json = new TestStatus(status, reason).toJson();

		System.out.println(json);

		if (onBrowserStack)
		{

			HttpResponse<JsonNode> response = Unirest.put(url)
					.basicAuth(BROWSERSTACK_USERNAME, BROWSERSTACK_ACCESS_KEY)
					.contentType("application/json")
					.body(json)
					.asJson();

			System.out.println("response: " + response.getStatus() + " " + response.getStatusText());
			System.out.println(response.getBody().toPrettyString());
		}
	}


	public void reportToBrowserStack(boolean onBrowserStack)
	{
		this.onBrowserStack = onBrowserStack;
	}
}
