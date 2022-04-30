Framework Components
====================


Project & Dependencies
----------------------

Maven Project
Java compiler target version `11`

Dependencies
 * Selenium (Automation) `selenium-java` version 4.1.3
 * JUnit 5  (Test Runner) `junit-jupiter-engine` version 5.8.2
 * AssertJ (Optional Assertions) `assertj-core` version 3.22.0
 * Unirest (For updating test results via REST API call) `unirest-java` version 3.13.6
 * Maven Surefire Plugin `maven-surefire-plugin` version 2.22.2

Configuration
-------------

Configuration is handled in two places:

Maven `pom.xml` file

 * Include / Exclude test by file name patterns `test*.java` and `*Test.java`
 * Include / Exclude tests by `@Tag` annotation
 * Parallelization `<parallel.count>5</parallel.count>` handled by maven-surefire-plugin


`test.properties` file under `src/main/resources`

Set browser capabilities:
 * `browser` (e.g. Chrome, Firefox, Edge, Safari)
 * `browser_version` (e.g. `latest`)
 * `os` (e.g. Windows) 
 * `os_version` (e.g. 11)

Choose the platform to run on:
 * chromedriver (local)
 * localhost (selenium-server-standalone)
 * browserstack

Also set the following environment variables when using Browserstack:
 * BROWSERSTACK_USERNAME
 * BROWSERSTACK_ACCESS_KEY

Components
----------

`SeleniumTest` is an abstract base class that handles functionality not related to core test requirements:

1. initialize logging for a test
2. load configuration file and environment variables (see also `test.properties`)
3. start and stop WebDriver instance for test (see also `BrowserManager`)
4. reporting pass/fail test results (see also `TestResultExtension`)
5. other helper functions you may need

Each test case can extend `SeleniumTest` and benefit from the common setup and other helpers.  It also make sure that your `WebDriver` instance is created and destroyed cleanly for each test.

`SeleniumPage` is an abstract base class following the page object pattern that allows for code reuse and enforces separation of concerns between:

1. element locators
2. automation steps
3. synchronization (waiting for elements to be visible, clickable, etc)
4. configuring a `baseUrl` for the environment under test
5. other functions you may use shared between pages

`LoginPage` and `SignedInPage` are simple examples that extend `SeleniumPage`

You do not have to use the `Page Object` abstraction -- and there are actually many ways this can be implemented.  The pattern can also be applied for components that appear on several pages and you can use `Composition` (instead of `Inheritance` to include components.

This pattern uses `Dependency Injection` to pass a `WebDriver` instance and steps that implement browser interaction are put here.  This makes it easy to find where a change needs to be made, using the `Single Responsibility` principle,

It also gives a convenient place to keep your locators (xpath, css, id, etc) so you can reference them as variables, so if the location of an element changes, you only need to change it in one place -- helping to keep your code `DRY` (Don't repeat yourself).

`BrowserManager` allows you to control (in one place) how `WebDriver` instances are created.  It uses properties (either from a configuration file -- as shown, or by injecting system properties via maven or command line, e.g. with `-D propertyName=value` ).  

See the configuration section above for the current options.  You can expand to suit your needs, including for example, setting the environment `baseUrl` for a test run.

`TestResultExtension` allows you to get the pass/fail status of each test and use it (along with the test name, reason, and other metadata) for reporting purposes, either using your own reporting framework, and for reporting test results to BrowserStack.

BrowserStack does not know whether your test `passed` or `failed` on its own, since there may be steps or conditions outside of browser automation. It can only tell if the `WebDriver` automation steps succeeded or encountered an error.  By reporting (via an API call -- see `TestResult` for an example) to BrowserStack each test result, you can use BrowserStacks test history to see how tests perform.

`SimpleTest` is an example that demonstrates how you can use this framework (without incurring the cost of starting a browser) to group tests using the `@Tag` annotation.  

By including `@Tag("selenium")` before a test method, it will start the browser.  

`SimpleSeleniumTest` is an example the demonstrates how you can incorporate all of the above by extending `SeleniumTest`.   The `testLogin()` method shows how you can incorporate page objects to keep your test functionality separate from your browser automation.


Test Execution
--------------

Tests can be executed in your IDE individually or by using maven:

    mvn clean test

You want to first set your environment variables either via secrets (in github or jenkins) or on the command line:

    export BROWSERSTACK_USERNAME=<your username>
    export BROWSERSTACK_ACCESS_KEY=<your access key>

TODO
----

Test parallelization can be accomplished via maven surefire.
You can also separate tests into different suites to run as part of different jobs

You can set up jenkins jobs or github actions to execute a test run on every commit, deployment, or nightly.

If you set the `BUILD_TAG` environment variable, Browserstack will be able to group all tests run in a single build together for improved reporting.

Test parallelization and CI/CD setup are not yet implemented in this framework, but will be demonstrated in a follow up.

