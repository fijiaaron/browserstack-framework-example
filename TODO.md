## Project setup
 - create Maven project
 - set JDK version in pom.xml
 - add dependencies

## Dependencies
 - done  
   * selenium-java 
   * junit-jupiter-platform
   * junit-jupiter-api
   * unirest (for API calls)
   * assertj (optional)
   * maven surefire plugin
 

## Group tests
 - done 
 - using @Tag annotation 
 - set includeTags / excludeTags in pom.xml
 - smoke, selenium, slow tags used
 - feature specific tags (e.g. login)

## Base Test
 - done 
 - create SeleniumTestBase
 - set default Properties
 - load properties from file /src/test/resource/test.properties 
 - get local/remote/browserstack driver from BrowserManager
 - create and destroy webdriver instance in @BeforeEach and @AfterEach
 - add pause function for debugging purposes
 - get sessionId from WebDriver instance
 - @RegisterExtension BrowserStackExtension for reporting test results to Browserstack

## Separation of Concerns with Page Object pattern
 - done
 - using SeleniumPage base class
 - inject WebDriver instance via constructor
 - call super(driver, url) from individual pages
 - pass baseUrl + path to create URL 
 - add whenPresent(), whenVisibile(), whenClickable()) to wrap WebDriverWait and ExpectedConditions
 - add pause() and setDelay() to be able to slow down and watch tests for debugging purposes
 - use By locators and wrapper functions for each element on page (DRY)
 - all selenium interaction done in Page Objects
 - check for current page

## Configuration
 - done
 - set capabilities in test.properties file
 - pass BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY as environment variables
 - add option for system properties
 - set baseUrl in test.properties
 - specify platform (localhost, browserstack, chromedriver) to execute tests on

## Run tests locally or on Browserstack
 - done
 - create BrowserManager class
 - load Properties from test/resources/test.properties in SeleniumTestBase
 - set url / capabilities for both local and browserstack
 - inject BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY from env variables into browserstack URL
 - read platform from configuration
 - add chromedriver_path to configuration

## Report status to Browserstack
 - done
 - use Unirest to call browserstack REST API
 - create TestStatus class to hold data and generate JSON payload
 - get sessionId from RemoteWebDriverInstance
 - get BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY from env variables

## Run tests in Parallel
  - done
  - create test/resources/junit-platform.properties
  - set junit.jupiter.execution.parallel.enabled=true
  - set junit.jupiter.execution.parallel.config.strategy=dynamic
  - add @Execution(ExecutionMode.CONCURRENT) annotation in base test 

## Results in @afterEach or Data in
 - done
 - register BrowserStackExtension in base test
 - pass sessionId to BrowserStackExtension in base test setup
 - get test status from ExtensionContext
 - call browserstack API in BrowserStackExtension.invokeAfterEachMethod()
 

## Parameterized Tests
TODO 
 - parallel run browser / os / versions
 - 

## Test Report
TODO 
 - choose reporting tool
 - magentys, allure, extentreports?