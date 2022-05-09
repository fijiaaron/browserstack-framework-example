## mvn clean test failyre

    [ERROR] SimpleSeleniumTest.testOpenHomePageSlowly  Time elapsed: 0.007 s  <<< ERROR!
    java.lang.NullPointerException: Cannot invoke "java.lang.CharSequence.toString()" because "replacement" is null
    at java.base/java.lang.String.replace(String.java:2956)
    at com.browserstack.framework.BrowserManager.getBrowserStackDriver(BrowserManager.java:50)
    at com.browserstack.framework.BrowserManager.getDriver(BrowserManager.java:36)
    at SeleniumTest.setup(SeleniumTest.java:54)


    [ERROR] ConcurrentTests.takes_1_seconds  Time elapsed: 1.009 s  <<< ERROR!
    org.junit.jupiter.api.extension.ParameterResolutionException: No ParameterResolver registered for parameter [org.junit.platform.engine.TestExecutionResult arg0] in method [public void ConcurrentTests.finishTestCase(org.junit.platform.engine.TestExecutionResult)].
