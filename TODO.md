## Project setup
 - create Maven project
 - set JDK version in pom.xml
 - add dependencies

## Dependencies
 - done  
   * selenium-java 
   * junit-jupiter-platform 
   * unirest (for API calls)
   * assertj (optional

## Group tests
 - done using @Tag annotation 
 - includeTags / excludeTags in pom.xml

## Base Test
 - done 
 - get local/remote/browserstack driver from BrowserManager
 - add wrappers for WebDriverWait

## Separation of Concerns with Page Object pattern
 - done using SeleniumPage base page
 - call super(driver, baseUrl) from individual pages
 - inject webdriver 
 - locators (DRY)
 - all selenium interaction done here
 - check for current page

## Configuration
 - done with capabilities prpooerties file
 - pass BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY as environment variables
 - add option for system properties

## Run tests locally or on Browserstack
 - done using BrowserManager
 - read platform from configuration
 - add chromedriver_path to configuration

## Report status to Browserstack
 - done using Unirest to call browserstack API

## Run tests in Parallel
  - done using junit-platform properties

## Results in @afterEach or Data in
TODO
 - pass sessionId to: TestWatcher, Extension, ExtensionContext
 - or get test status in: AfterEach -- not available
 - or use TestExecutionListener, ServiceLoader
 - aftereachcallball
 - aftereach exceptioncoun
 - 

## Parameterized Tests
TODO 
 - parallel run browser / os / versions

## Test Report
TODO 
 - choose reporting tool
 - magentys, allure, extentreports?