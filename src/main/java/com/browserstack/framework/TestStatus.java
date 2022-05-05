package com.browserstack.framework;

import com.google.gson.Gson;

public class TestStatus
{
	String status;
	String reason;

	public TestStatus(boolean passed)
	{
		setStatus(passed);
	}

	public TestStatus(boolean passed, String reason)
	{
		setStatus(passed);
		setReason(reason);
	}

	public void setStatus(boolean passed)
	{
		status = passed ? "passed" : "failed";
	}

	public void setReason(String reason)
	{
		if (reason != null)
		{
			this.reason = reason;
		}
	}

	public String toJson()
	{
		return new Gson().toJson(this);
	}
}